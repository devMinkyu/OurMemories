# -*- coding: utf-8 -*-
from project import app
from flask import Flask, render_template, request, jsonify, Response, session, redirect, flash, url_for
# from flask_wtf import FlaskForm
# from wtforms import StringField
# from wtforms.validators import DataRequired
# from flask_restful import Resource, Api
from mongokit import Connection, Document, Collection
from bson.objectid import ObjectId # For ObjectId to work
from flask_oauth import OAuth, OAuthException
from flask_session import Session

# from project.models import *

from project import *

# api = Api(app)

# DB 선택
# db = connection.airbnb

# collection 선택
# ddd = db.bbs

db = connection.OurMemories
user = db.users

@app.route('/')
def index():
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

    user.insert({'id' : user_id, 'userName' : name, 'email' : email, 'profile' : picture, 'accessToken' : accessToken})

    # isSuccess
    isSuccess = 'true/insert'

    # transform user data to json
    user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(user_id,name,email,picture)))

    # isSuccess와 userLoginResult를 Json으로
    sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))
    print(sendToAndroid)

    return jsonify(sendToAndroid)


# 웹에서 페이스북 로그인을 위한
# @app.route('/facebookLogin')
# def login():
#     callback = url_for(
#         'facebook_authorized',
#         next=request.args.get('next') or request.referrer or None,
#         _external=True
#     )
#     print(callback)
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

    # user 정보 DB에 저장
    users.insert({'id' : user_id, 'userName' : name, 'email' : email, 'profile' : picture, 'accessToken' : resp['access_token']})

    # isSuccess
    isSuccess = 'true/insert'

    # transform user data to json
    user_object = dict(zip(('userId', 'userName', 'userEmail', 'userProfileImageUrl'),(user_id,name,email,picture)))

    # isSuccess와 userLoginResult를 Json으로
    sendToAndroid = dict(zip(('isSuccess', 'userLoginResult'), (isSuccess, user_object)))

    print(sendToAndroid)

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
