# -*- coding: utf-8 -*-
from project import app
from flask import Flask, render_template, request, jsonify, Response, session, redirect, flash, url_for, send_from_directory
# from flask_wtf import FlaskForm
# from wtforms import StringField
# from wtforms.validators import DataRequired
# from flask_restful import Resource, Api
from mongokit import Connection, Document, Collection
from bson.objectid import ObjectId # For ObjectId to work
from flask_oauth import OAuth, OAuthException
from flask_session import Session
from werkzeug import secure_filename
import os

# from project.models import *

from project import *

# APP_ROOT = os.path.dirname(os.path.abspath(__file__))

# api = Api(app)

# 게시판 DB
# db = connection.airbnb
# ddd = db.bbs

# test DB
# db = connection.facebook
# user = db.user

# android DB
db = connection.OurMemories # DB 선택
user = db.users # collection 선택
images = db.image # image collection 선택

@app.route('/')
def index():
    print(SENDING_IMAGE_PATH)
    return redirect(url_for('login'))


# android의 access token으로 페이스북 정보 가져오기 / Json 형식으로 Android로 넘기기
@app.route('/facebookLogin', methods=['GET', 'POST'])
def login():
    accessToken = request.form['accessToken']
    print(accessToken) # 토큰을 받았는지 test

    session['oauth_token'] = (accessToken, '')

    me = facebook.get('/me?fields=id,name,email,picture')

    # Json 파싱을 통해 값을 가져온다
    # 키 값으로 가져온다
    user_id = (me.data['id'])
    name = (me.data['name'])
    email = (me.data['email'])
    picture = (me.data['picture']['data']['url'])

    is_user = user.find_one({"id" : user_id})
    if is_user == None:
        # isSuccess
        isSuccess = 'true/insert'
        # transform user data to json
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(user_id,name,email,picture)))
        # isSuccess와 userLoginResult를 Json으로
        sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
        user.insert({'id' : user_id, 'userName' : name, 'email' : email, 'profile' : picture, 'accessToken' : accessToken})
    elif is_user != None:
        # isSuccess
        isSuccess = 'true/update'
        # transform user data to json
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(user_id,name,email,picture)))
        # isSuccess와 userLoginResult를 Json으로
        sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
        user.update({'id' : user_id}, {'id' : user_id, 'userName' : name, 'email' : email, 'profile' : picture, 'accessToken' : accessToken})
    else:
        # isSuccess
        isSuccess = 'false'
        # transform user data to json
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(0,0,0,0)))
        # isSuccess와 userLoginResult를 Json으로
        sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))

    return jsonify(sendToAndroid)

# 사진 저장
@app.route('/memory', methods=['GET', 'POST'])
def multyData():
    image = request.files['uploadFile'] # android에서 보낸 사진 받기
    # print(image)
    imageName = (secure_filename(image.filename)) # 사진 이름만 변수에 저장
    print(imageName)

    image.save(os.path.join(app.config['UPLOAD_FOLDER'], imageName)) # 폴더에 이미지 저장

    path = SENDING_IMAGE_PATH + imageName # android에 보낼 image URL
    print(path)
    #
    # sendToAndroid = dict(zip( ('mediaMemory', 'isSuccess'), (path, 'true') ))
    # print(sendToAndroid)
    #
    # return jsonify(sendToAndroid)

    return redirect(url_for('uploaded_file', filename=imageName))

@app.route('/uploads/<filename>')
def uploaded_file(filename):
    return send_from_directory(app.config['UPLOAD_FOLDER'], filename)

# 자동로그인
@app.route('/profile', methods=['GET', 'POST'])
def profile():
    userId = request.form['userId']
    is_user = user.find_one({"id" : userId})

    if is_user != None:
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl', 'authLogin'),(is_user['id'],is_user['userName'],is_user['email'],is_user['profile'],"1")))
        sendToAndroid = dict(zip(('isSuccess', 'userProfileResult'), ("true", user_object)))
    else :
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl', 'authLogin'),(0,0,0,0,"0")))
        sendToAndroid = dict(zip(('isSuccess', 'userProfileResult'), ("false", user_object)))

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


@app.route('/login/authorized')
@facebook.authorized_handler
def facebook_authorized(resp):
    if resp is None:
        return 'Access denied: reason=%s error=%s' % (
            request.args['error_reason'],
            request.args['error_description']
        )
    if isinstance(resp, OAuthException):
        return 'Access denied: %s' % resp.message

    session['oauth_token'] = (resp['access_token'], '')
    me = facebook.get('/me?fields=id,name,email,picture')
    # print(me.data)

    # Json 파싱을 통해 값을 가져온다
    # 키 값으로 가져온다
    user_id = (me.data['id'])
    name = (me.data['name'])
    email = (me.data['email'])
    picture = (me.data['picture']['data']['url'])

    # user Id가 DB에 있는지 확인
    is_user = user.find_one({"id" : user_id})
    if is_user == None:
        # isSuccess
        isSuccess = 'true/insert'
        # transform user data to json
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(user_id,name,email,picture)))
        # isSuccess와 userLoginResult를 Json으로
        sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
        user.insert({'id' : user_id, 'userName' : name, 'email' : email, 'profile' : picture, 'accessToken' : resp['access_token']})
    elif is_user != None:
        # isSuccess
        isSuccess = 'true/update'
        # transform user data to json
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(user_id,name,email,picture)))
        # isSuccess와 userLoginResult를 Json으로
        sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
        user.update({'id' : user_id}, {'id' : user_id, 'userName' : name, 'email' : email, 'profile' : picture, 'accessToken' : resp['access_token']})
    else:
        # isSuccess
        isSuccess = 'false'
        # transform user data to json
        user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(0,0,0,0)))
        # isSuccess와 userLoginResult를 Json으로
        sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
    # for doc in is_user:
    #     print(doc['id'])

    # print(sendToAndroid)
    return jsonify(sendToAndroid)

    # return 'Logged in as id=%s name=%s email=%s picture=%s redirect=%s' % \
    #     (me.data['id'], me.data['name'], me.data['email'], me.data['picture'], request.args.get('next'))


@facebook.tokengetter
def get_facebook_oauth_token():
    return session.get('oauth_token')


# DB내용 가져올 수 있는지 test
# @app.route('/')
# def index():
#
#     todos = ddd.find()
#
#     return render_template('bbsList.html', todos = todos)


# 게시판
# @app.route('/')
# def index():
#
#     return render_template('bbs.html')
#
# # 게시판 글쓰기
# @app.route('/write', methods=['POST'])
# def write():
#     ddd.title = request.form['title']
#     ddd.content = request.form['content']
#
#     ddd.insert({'title' : request.form['title'], 'content' : request.form['content']})
#     # bbs.save()
#
#     todos = ddd.find()
#     return render_template('bbsList.html', todos = todos)


# class CreateForm(FlaskForm):
#     text = StringField('name', validators=[DataRequired()])
#
# class HelloWorld(Resource):
#     def get(self):
#         return {'hello': 'world'}
#
# api.add_resource(HelloWorld, '/')


# @app.route('/print', methods=['GET', 'POST'])
# def printer():
#     form = CreateForm(request.form)
#     if request.method == 'POST' and form.validate():
#         from project.models.Printer import Printer
#         printer = Printer()
#         printer.show_string(form.text.data)
#         return render_template('printer/index.html')
#     return render_template('printer/print.html', form=form)
