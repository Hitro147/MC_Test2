import flask
from flask import request
import os
import base64

app = flask.Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def handle_request():
    return "Successful Connection"

@app.route('/upload', methods=['POST'])
def process_json():
    content_is_json = request.is_json  
    if (content_is_json):
        content = request.get_json()
        category_name = content['category']
        filename = content['filename']
        base64image = content['base64_image']
        if not os.path.exists(category_name):
            os.makedirs(category_name)
        with open(category_name+"/"+f"{filename}.png", "wb") as fh:
            fh.write(base64.b64decode(base64image))

        return 'Image Uploaded Completed Sucessfully!'
    else:
        return 'Content-Type not supported!'


app.run(host="0.0.0.0", port=8080, debug=True)