# -*- coding: utf-8 -*-
from project import app
from flask import Flask, render_template, request, jsonify, Response, session, redirect, flash, url_for
from mongokit import Connection, Document, Collection
from bson.objectid import ObjectId # For ObjectId to work

from project import *

# DB 선택
db = connection.airbnb

# place Document
class Time(Document):
    structure = {
    'id' : unicode,
    'manId' : unicode,
    'womanId' : unicode,
    'text' : unicode,
    'picture' : unicode,
    'video' : unicode,
    'latitude' : unicode,
    'longitude' : unicode
    }
    required_fields = ['id', 'manId', 'womanId', 'text', 'picture', 'video', 'latitude', 'longitude']
    use_dot_notation = True

    def __repr__(self):
        return '<Time %r>' % (self.name)

connection.register([Time])
# collection = connection['OurMemories'].Time
# Time = collection.Time()
# timr = db.Time # collection 선택
