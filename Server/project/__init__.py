# -*- coding: utf-8 -*-
# __version__ = '0.1'
from flask import Flask, session
from flask_debugtoolbar import DebugToolbarExtension
from mongokit import Connection, Document, Collection
from bson.objectid import ObjectId # For ObjectId to work
from flask_oauth import OAuth, OAuthException
from flask_session import Session

FACEBOOK_APP_ID = '147688489311870'
FACEBOOK_APP_SECRET = '19210ee5aceec1dee4d2521028da935d'

# app = Flask('project')
app = Flask(__name__)

SENDING_IMAGE_PATH = 'http://13.124.79.162:8000/'
UPLOAD_FOLDER = '/home/ec2-user/OurMemories/Server/uploadFolder' # 아마존 컴퓨터에 이미지 저장되는 경로
ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif', 'mp4'])
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024

app.config['SESSION_TYPE'] = 'filesystem'
app.config['SECRET_KEY'] = 'ill never tell'
app.debug = True

oauth = OAuth()

facebook = oauth.remote_app(
    'facebook',
    consumer_key=FACEBOOK_APP_ID,
    consumer_secret=FACEBOOK_APP_SECRET,
    request_token_params={'scope': 'email, public_profile'},
    base_url='https://graph.facebook.com',
    request_token_url=None,
    access_token_url='/oauth/access_token',
    access_token_method='GET',
    authorize_url='https://www.facebook.com/dialog/oauth'
)

# configuration
MONGODB_HOST = 'localhost'
MONGODB_PORT = 27017
# MONGODB_DATABASE = '123'

# create the little application object
# app = Flask(__name__)
app.config.from_object(__name__)

# connection to the database
connection = Connection(app.config['MONGODB_HOST'],
                        app.config['MONGODB_PORT'])

# toolbar = DebugToolbarExtension(app)

from project.controllers import *
