# -*- coding: utf-8 -*-
from project import app
from flask import Flask, render_template, request, jsonify, Response, session, redirect, flash, url_for
# from flask_wtf import FlaskForm
# from wtforms import StringField
# from wtforms.validators import DataRequired
# from flask_restful import Resource, Api
from mongokit import Connection, Document, Collection
from bson.objectid import ObjectId # For ObjectId to work

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

# DB내용 가져올 수 있는지 test
# @app.route('/')
# def index():
#
#     todos = bbs.find()
#
#     return render_template('bbsList.html', todos = todos)

# facebook Login
@app.route('/')
def index():

    return render_template('facebookLogin.html')

# Get facebook token to Login
@app.route('/facebookLogin', methods = ['POST'])
def facebookLogin():

    token = request.form['accessToken']
    print(token)

    return render_template('facebookLogin.html', token = token)

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
