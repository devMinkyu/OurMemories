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
db = connection.airbnb

# 게시판 데이터 다큐먼트
class BBS(Document):
    structure = {
    'title' : unicode,
    'content' : unicode,
    }
    required_fields = ['title', 'content']
    use_dot_notation = True

    def __repr__(self):
        return '<BBS %r>' % (self.name)

connection.register([BBS])
collection = connection['airbnb'].bbs
bbs = collection.BBS()
bbs = db.bbs # collection 선택


@app.route('/')
def index():
    return redirect(url_for('login'))


@app.route('/facebookLogin')
def login():
    callback = url_for(
        'facebook_authorized',
        next=request.args.get('next') or request.referrer or None,
        _external=True
    )
    print(callback)
    return facebook.authorize(callback=callback)


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

    # 안드로이드에서 토큰받는 테스트 코드
    accessToken = request.form['accessToken']
    print(accessToken)
    session['oauth_token'] = (accessToken, '')

    # session['oauth_token'] = (resp['access_token'], '')
    # print(resp['access_token'])
    me = facebook.get('/me?fields=id,name,email,picture')
    print(me.data)

    # Json 파싱을 통해 값을 가져온다
    # 키 값으로 가져온다
    # user_id = jsonify(me.data['id'])
    # name = jsonify(me.data['name'])
    # email = jsonify(me.data['email'])
    # picture = jsonify(me.data['picture'])
    # print(user_id)
    # print(name)
    # print(email)
    # print(picture)

    # 각각의 Json 데이터를 만들어준다.
    # rest_json = []
    # rest_json.append({'user_id': user_id})
    # rest_json.append({'name' : name})
    # rest_json.append({'email' : email})
    # rest_json.append({'picture' : picture})
    # print(rest_json)

    # is_success
    # result_object = []
    # result_object.append({'is_success', '' + is_success})

    # return jsonify(me.data)

    # return 'Logged in as id=%s name=%s email=%s picture=%s redirect=%s' % \
    #     (me.data['id'], me.data['name'], me.data['email'], me.data['picture'], request.args.get('next'))

    # 데이터를 json형태로
    test = dict(zip(('isSuccess', 'data'), ( ('true/insert','true/update', 'flask'), me.data)))
    return jsonify(test)


@facebook.tokengetter
def get_facebook_oauth_token():
    return session.get('oauth_token')

# DB내용 가져올 수 있는지 test
# @app.route('/')
# def index():
#
#     todos = bbs.find()
#
#     return render_template('bbsList.html', todos = todos)

# facebook Login
# @app.route('/')
# def index():
#
#     accessToken = 1
#     return render_template('facebookLogin.html', token = accessToken)

# Get facebook token to Login
# @app.route('/facebookLogin', methods = ['POST'])
# def facebookLogin():
#
#     token = request.form['accessToken']
#     print(token)
#
#     return render_template('facebookLogin.html', token = token)

# # 게시판
# @app.route('/')
# def index():
#
#     return render_template('bbs.html')
#
# # 게시판 글쓰기
# @app.route('/write', methods=['POST'])
# def write():
#     bbs.title = request.form['title']
#     bbs.content = request.form['content']
#
#     bbs.insert({'title' : request.form['title'], 'content' : request.form['content']})
#     # bbs.save()
#
#     todos = bbs.find()
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

# @app.route('/')
# def index():
#     data = dict(zip(('code', 0), ('msg', 'ok')))
#     return jsonify(data)
