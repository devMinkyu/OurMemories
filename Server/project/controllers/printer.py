# -*- coding: utf-8 -*-
from project import app
from flask import Flask, render_template, request, jsonify, Response, session, redirect, flash, url_for, send_from_directory
# from flask_wtf import FlaskForm
# from wtforms import StringField
# from wtforms.validators import DataRequired
# from flask_restful import Resource, Api
from mongokit import Connection, Document, Collection
from bson.objectid import ObjectId # For MONGODB ObjectId to work
from flask_oauth import OAuth, OAuthException
from flask_session import Session
from werkzeug import secure_filename
import os

# from project.models import *

from project import *

# api = Api(app)

# test DB
# db = connection.facebook
# user = db.user

# android DB
db = connection.OurMemories # DB 선택
user = db.users # collection 선택
images = db.image # image collection 선택
url = db.imageURL

@app.route('/')
def index():
    print(SENDING_IMAGE_PATH)
    return redirect(url_for('login'))


# android의 access token으로 페이스북 정보 가져오기 / Json 형식으로 Android로 넘기기
@app.route('/facebookLogin', methods=['GET', 'POST'])
def login():
    accessToken = request.form['accessToken']
    # print(accessToken) # 토큰을 받았는지 test

    session['oauth_token'] = (accessToken, '')

    me = facebook.get('/me?fields=id,name,email,picture')

    # Json 파싱을 통해 값을 가져온다
    # 키 값으로 유저 정보를 가져온다
    user_id = (me.data['id'])
    name = (me.data['name'])
    email = (me.data['email'])
    picture = (me.data['picture']['data']['url'])

    is_user = user.find_one({"id" : user_id})
    # 로그인 할때 유저 아이디가 없는 경우
    if is_user == None:
        # isSuccess
        isSuccess = 'true/insert'
        # transform user data to json
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(user_id,name,email,picture)))
        # isSuccess와 userLoginResult를 Json으로
        sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
        user.insert({'id' : user_id, 'userName' : name, 'email' : email, 'profile' : picture, 'accessToken' : accessToken})
    # 로그인 할때 유저 아이디가 있는 경우
    elif is_user != None:
        # isSuccess
        isSuccess = 'true/update'
        # transform user data to json
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(user_id,name,email,picture)))
        # isSuccess와 userLoginResult를 Json으로
        # sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
        user.update({'id' : user_id}, {'id' : user_id, 'userName' : name, 'email' : email, 'profile' : picture, 'accessToken' : accessToken})

        # image collection에 있는 id값을 가져온다.
        memoryId = images.find({"userId" : user_id})
        memoryArray = [] # 메모리 배열
        if memoryId != None:
            for docs in memoryId:
                # memory 데이터를 JSON으로
                memory_object = dict(zip(('_id', 'memoryTitle', 'memoryFromDate', 'memoryToDate', 'memoryLatitude', 'memoryLongitude', 'memoryNation', 'memoryClassification'),(docs['userId'], docs['memoryTitle'], docs['memoryFromDate'], docs['memoryToDate'], docs['memoryLatitude'], docs['memoryLongitude'], docs['memoryNation'], docs['memoryClassification'] )))
                memoryArray.append(memory_object)
            # print(memoryArray)
            sendToAndroid = dict(zip(('isSuccess', 'userLoginResult', 'userLoginMemoryResult'), (isSuccess, user_object, memoryArray) ))
        else:
            sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
    else:
        # isSuccess
        isSuccess = 'false'
        # transform user data to json
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(0,0,0,0)))
        # isSuccess와 userLoginResult를 Json으로
        sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))

    return jsonify(sendToAndroid)


# 자동로그인
@app.route('/profile', methods=['GET', 'POST'])
def profile():
    userId = request.form['userId']
    is_user = user.find_one({"id" : userId})

    if is_user != None:
        isSuccess = 'true'
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl', 'authLogin'),(is_user['id'],is_user['userName'],is_user['email'],is_user['profile'],"1")))

        # image collection에 있는 id값을 가져온다.
        memoryId = images.find({"userId" : userId})
        memoryArray = [] # 메모리 배열
        if memoryId != None:
            for docs in memoryId:
                # memory 데이터를 JSON으로
                memory_object = dict(zip(('_id', 'memoryTitle', 'memoryFromDate', 'memoryToDate', 'memoryLatitude', 'memoryLongitude', 'memoryNation', 'memoryClassification'),(docs['userId'], docs['memoryTitle'], docs['memoryFromDate'], docs['memoryToDate'], docs['memoryLatitude'], docs['memoryLongitude'], docs['memoryNation'], docs['memoryClassification'] )))
                memoryArray.append(memory_object)
            # print(memoryArray)
            sendToAndroid = dict(zip(('isSuccess', 'userProfileResult', 'userProfileMemoryResult'), (isSuccess, user_object, memoryArray) ))
        else:
            sendToAndroid = dict(zip(('isSuccess', 'userProfileResult'), (isSuccess, user_object)))

    else:
        isSuccess = 'false'
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl', 'authLogin'),(0,0,0,0,"0")))
        sendToAndroid = dict(zip(('isSuccess', 'userProfileResult'), (isSuccess, user_object)))

    return jsonify(sendToAndroid)


# 웹에서 페이스북 로그인을 위한
# @app.route('/facebookLogin')
# def login():
#     callback = url_for(
#         'facebook_authorized',
#         next=request.args.get('next') or request.referrer or None,
#         _external=True
#     )
#     # print(callback)
#     return facebook.authorize(callback=callback)


# 웹에서 페이스북 정보 받는 부분
# @app.route('/login/authorized')
# @facebook.authorized_handler
# def facebook_authorized(resp):
#     if resp is None:
#         return 'Access denied: reason=%s error=%s' % (
#             request.args['error_reason'],
#             request.args['error_description']
#         )
#     if isinstance(resp, OAuthException):
#         return 'Access denied: %s' % resp.message
#
#     session['oauth_token'] = (resp['access_token'], '')
#     me = facebook.get('/me?fields=id,name,email,picture')
#     # print(me.data)
#
#     # Json 파싱을 통해 값을 가져온다
#     # 키 값으로 가져온다
#     user_id = (me.data['id'])
#     name = (me.data['name'])
#     email = (me.data['email'])
#     picture = (me.data['picture']['data']['url'])
#
#     # user Id가 DB에 있는지 확인
#     is_user = user.find_one({"id" : user_id})
#     if is_user == None:
#         # isSuccess
#         isSuccess = 'true/insert'
#         # transform user data to json
#         user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(user_id,name,email,picture)))
#         # isSuccess와 userLoginResult를 Json으로
#         sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
#         user.insert({'id' : user_id, 'userName' : name, 'email' : email, 'profile' : picture, 'accessToken' : resp['access_token']})
#     elif is_user != None:
#         # isSuccess
#         isSuccess = 'true/update'
#         # transform user data to json
#         user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(user_id,name,email,picture)))
#         # isSuccess와 userLoginResult를 Json으로
#         sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
#         user.update({'id' : user_id}, {'id' : user_id, 'userName' : name, 'email' : email, 'profile' : picture, 'accessToken' : resp['access_token']})
#     else:
#         # isSuccess
#         isSuccess = 'false'
#         # transform user data to json
#         user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(0,0,0,0)))
#         # isSuccess와 userLoginResult를 Json으로
#         sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
#     # for doc in is_user:
#     #     print(doc['id'])
#
#     # print(sendToAndroid)
#     return jsonify(sendToAndroid)
#
#     # return 'Logged in as id=%s name=%s email=%s picture=%s redirect=%s' % \
#     #     (me.data['id'], me.data['name'], me.data['email'], me.data['picture'], request.args.get('next'))


# 웹에서 페이스북 토큰 받는 부분
@facebook.tokengetter
def get_facebook_oauth_token():
    return session.get('oauth_token')


# OurMemories 추억에서 입력한 값 DB에 저장
@app.route('/memory', methods=['GET', 'POST'])
def multyData():

    # memory 추억에서 입력한 값 가져오기
    userId = request.form['userId']
    memoryTitle = request.form['memoryTitle']
    memoryFromDate = request.form['memoryFromDate']
    memoryToDate = request.form['memoryToDate']
    memoryLatitude = request.form['memoryLatitude']
    memoryLongitude = request.form['memoryLongitude']
    memoryNation = request.form['memoryNation']
    memoryClassification = request.form['memoryClassification']
    text = request.form['text']
    address = request.form['memoryAddress']

    # memory 데이터를 JSON으로
    # memory_object = dict(zip(('_id', 'memoryTitle', 'memoryFromDate', 'memoryToDate', 'memoryLatitude', 'memoryLongitude', 'memoryNation', 'memoryClassification'),(user_id,name,email,picture)))

    image = request.files['uploadFile'] # android에서 보낸 사진 받기
    # print(image)

    imageName = (secure_filename(image.filename)) # 사진 이름만 변수에 저장
    # print(imageName)

    image.save(os.path.join(app.config['UPLOAD_FOLDER'], imageName)) # 폴더에 이미지 저장

    redirect(url_for('uploaded_file', filename=imageName)) # 웹페이지에 사진을 띄위기 위해

    path = SENDING_IMAGE_PATH + 'uploads/' + imageName # android에 보낼 image URL
    # print(path)

    # 정보들 DB에 저장
    images.insert({'userId' : userId, 'memoryTitle' : memoryTitle, 'memoryFromDate' : memoryFromDate, 'memoryToDate' : memoryToDate, 'memoryLatitude' : memoryLatitude, 'memoryLongitude' : memoryLongitude, 'memoryNation' : memoryNation, 'memoryClassification' : memoryClassification, 'text' : text, 'media' : path, 'memoryAddress' : address})

    info = images.find({"userId" : userId})
    for doc in info:
        if doc['memoryTitle'] == memoryTitle:
            mId = str(doc['_id'])
            isSuccess = 'true'
            # print(mId)
            break

    if isSuccess != 'true':
        isSuccess = 'false'


    sendToAndroid = dict(zip(('isSuccess', 'id'), (isSuccess, mId)))
    print(jsonify(sendToAndroid))

    return jsonify(sendToAndroid)


# 앱의 review를 눌렀을 때
@app.route('/memory/view', methods=['GET', 'POST'])
def memoryView():
    string_id = request.form['id']
    print(string_id)

    # image collection에 있는 id값을 가져온다.
    memoryId = images.find_one({"_id" : ObjectId(string_id)})
    # print(memoryId)
    if memoryId != None:
        # memories = images.find({"_id" : ObjectId(string_id)})
        # for docs in memories:
        isSuccess = "true"
        media = memoryId['media']
        text = memoryId['text']
    else:
        isSuccess = "false"

    memoryItemResult = dict(zip( ('mediaMemory', 'textMemory'), (media, text) ))
    memoryItem = dict(zip( ('isSuccess', 'memoryItemResult'), (isSuccess, memoryItemResult) ))

    return jsonify(memoryItem)


# 사진 저장(웹페이지에 사진 띄움)
# @app.route('/memory', methods=['GET', 'POST'])
# def multyData():
#     image = request.files['uploadFile'] # android에서 보낸 사진 받기
#     print(image)
#
#     imageName = (secure_filename(image.filename)) # 사진 이름만 변수에 저장
#     # print(imageName)
#
#     image.save(os.path.join(app.config['UPLOAD_FOLDER'], imageName)) # 폴더에 이미지 저장
#
#     redirect(url_for('uploaded_file', filename=imageName)) # 웹페이지에 사진을 띄위기 위해
#
#     path = SENDING_IMAGE_PATH + 'uploads/' + imageName # android에 보낼 image URL
#     print(path)
#
#     sendToAndroid = dict(zip( ('mediaMemory', 'isSuccess'), (path, 'true') )) # review 보기때 사용
#     return jsonify(sendToAndroid)


# 아마존 컴퓨터 주소/uploads/filename로 디렉토리에 저장된 파일 꺼내옴
@app.route('/uploads/<filename>')
def uploaded_file(filename):
    return send_from_directory(app.config['UPLOAD_FOLDER'], filename)


@app.route('/recom')
def recommand():
    siDo = request.form['siDo'] # 광역시/도
    siGunGu = request.form['siGunGu'] # 구/시/군

    reviewMemoryArray = []

    first = images.find({"memoryClassification" : "1"})
    for docs in first:
        second = docs['memoryAddress']
        if siDo in second:
            if siGunGu in second:
                memory_object = dict(zip(('media', 'title', 'contents', 'address'), (docs['media'], docs['memoryTitle'], docs['text'], docs['memoryAddress']) ))
                reviewMemoryArray.append(memory_object)

    if reviewMemoryArray == None:
        isSuccess = 'false'
        sendToAndroid = dict(zip( ('isSuccess', 'reviewMemoryResult'), (isSuccess, None) ))
    else:
        isSuccess = 'true'
        sendToAndroid = dict(zip( ('isSuccess', 'reviewMemoryResult'), (isSuccess, reviewMemoryArray) ))
        
    return jsonify(sendToAndroid)


    # if isIt != None:
    #     isSuccess = 'true'
    #     for docs in isIt
    #         memory_object = dict(zip(('media', 'title', 'contents', 'address'), (docs['media'], docs['memoryTitle'], docs['text'], docs['memoryAddress']) ))
    #         reviewMemoryArray.append(memory_object)
    #     sendToAndroid = dict(zip( ('isSucce', 'reviewMemoryResult'), (isSuccess, reviewMemoryArray) ))
    # else:
    #     isSuccess 'false'
    #     sendToAndroid = dict(zip( ('isSucce', 'reviewMemoryResult'), (isSuccess, "Null") ))
    #
    # return jsonify(sendToAndroid)
