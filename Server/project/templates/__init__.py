# -*- coding: utf-8 -*-
__version__ = '0.1'
from flask import Flask
from flask_debugtoolbar import DebugToolbarExtension
from mongokit import Connection, Document, Collection

# app = Flask('project')
app = Flask(__name__)

app.config['SECRET_KEY'] = 'random'
app.debug = True

# configuration
MONGODB_HOST = 'localhost'
MONGODB_PORT = 27017
# MONGODB_DATABASE = '123'

# create the little application object
app = Flask(__name__)
app.config.from_object(__name__)

# connection to the database
connection = Connection(app.config['MONGODB_HOST'],
                        app.config['MONGODB_PORT'])

# jade 사용
# app.jinja_env.add_extension('pyjade.ext.jinja.PyJadeExtension')

toolbar = DebugToolbarExtension(app)

from project.controllers import *
