# -*- coding: utf-8 -*-
from mongokit import Connection, Document, Collection

# User Document
class User(Document):
    structure = {
    'id' : unicode,
    'userName' : unicode,
    'email' : unicode,
    'profile' : unicode,
    'accessToken' : unicode
    }
    required_fields = ['id', 'userName', 'email', 'profile', 'accessToken']
    use_dot_notation = True

    def __repr__(self):
        return '<User %r>' % (self.name)

connection.register([User])
# collection = connection['OurMemories'].User
# User = collection.User()
# user = db.User # collection 선택
