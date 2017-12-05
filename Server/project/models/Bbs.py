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
# collection = connection['airbnb'].bbs
# bbs = collection.BBS()
