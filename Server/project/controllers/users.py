# -*- coding: utf-8 -*-
from project import app
from flask import Flask, render_template, request, jsonify, Response, session, redirect, flash, url_for
from mongokit import Connection, Document, Collection
from bson.objectid import ObjectId # For ObjectId to work

from project import *

# DB 선택
db = connection.airbnb

# User Document
class User(Document):
    structure = {
    'id' : unicode,
    'userName' : unicode,
    'email' : unicode,
    'profile' : unicode
    }
    required_fields = ['id', 'userName', 'email', 'profile']
    use_dot_notation = True

    def __repr__(self):
        return '<User %r>' % (self.name)

connection.register([User])
# collection = connection['OurMemories'].User
# User = collection.User()
# user = db.User # collection 선택
