# -*- coding: utf-8 -*-
from project import app
from flask import Flask, render_template, request, jsonify, Response, session, redirect, flash, url_for
from mongokit import Connection, Document, Collection
from bson.objectid import ObjectId # For ObjectId to work

from project import *

# DB 선택
db = connection.airbnb

# place Document
class Place(Document):
    structure = {
    'doName' : unicode,
    'city' : unicode,
    'place' : unicode,
    'explain' : unicode,
    'picture' : unicode
    }
    required_fields = ['doName', 'city', 'place', 'explain', 'picture']
    use_dot_notation = True

    def __repr__(self):
        return '<Place %r>' % (self.name)

connection.register([Place])
# collection = connection['OurMemories'].Place
# Place = collection.Place()
# place = db.Place # collection 선택
