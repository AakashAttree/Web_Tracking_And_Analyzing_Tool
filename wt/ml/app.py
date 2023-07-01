import numpy as np
from flask import Flask, request, jsonify, render_template
import pickle
import predictCustomer
import clusteringOfSessions

# Create flask app
flask_app = Flask(__name__)
model = pickle.load(open("customerprediction.pkl", "rb"))

@flask_app.route("/")
def Home():
    return render_template("index.html")

@flask_app.route("/predict", methods = ["POST"])
def predict():
    float_features = [int(x) for x in request.form.values()]
    print(float_features)
    features = [np.array(float_features)]
    prediction = model.predict(features)
    return "{}".format(prediction)

@flask_app.route("/createCustomerPredictTemplate")
def create():
    predictCustomer.CreateCustomerPreditTemplate().create()
    model = pickle.load(open("customerprediction.pkl", "rb"))
    return "success"

@flask_app.route("/clusterSession")
def clusteringOfSessions():
    clusteringOfSessions.ClusterinOfSessions().cluster()
   

if __name__ == "__main__":
    flask_app.run(debug=True)