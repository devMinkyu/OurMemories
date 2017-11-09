# -*- coding: utf-8 -*-
from project import app
from flask import render_template, request, jsonify
from flask_wtf import FlaskForm
from wtforms import StringField
from wtforms.validators import DataRequired
from flask_restful import Resource, Api
from mongokit import Connection, Document, Collection

from project.models import *

api = Api(app)

# @app.route('/')
# def bbs():
#     return render_template('bbs.html')
#
# collection = connection['airbnb'].bbs
# bbs = collection.BBS()
# bbs = db.bbs # collection 선택
#
# # 게시판 글쓰기
# @app.route('/write', methods=['POST'])
# def write():
#     bbs.title = request.form['title']
#     bbs.content = request.form['content']
#     bbs.save()
#
#     return render_template('bbsList.html')

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

@app.route('/')
def index():
    data = dict(zip(('code', 0), ('msg', 'ok')))
    return jsonify(data)
