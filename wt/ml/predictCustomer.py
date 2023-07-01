from pymongo import MongoClient
import pandas as pd
import numpy as np
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
import pickle
class CreateCustomerPreditTemplate:
    def create(self):
        try:
            conn = MongoClient()
            print("Connected successfully!!!")
        except:  
            print("Could not connect to MongoDB")
          
        # database
        db = conn.webtrackingdata
          
        # Created or Switched to collection names: my_gfg_collection
        collection = db.get_collection("session-cluster")
        
        values = collection.find({})
        
        columns=["numberOfRequests", 
                 "averageRequestTime", 
                 "totalDurationOfSession", 
                 "numberOfDistinctPageVisit", 
                 "numberOfDistinctActivities",
                 "knownContact",
                 "hasSocialMediaId",
                 "customer"]
        
        outerList=[]
        sessionIds=[]
        for data in values:
            innerList=[]
            sessionIds.append(data["sessionId"])
            innerList.append(data["numberOfRequests"])
            innerList.append(data["averageRequestTime"])
            innerList.append(data["totalDurationOfSession"])
            innerList.append(data["numberOfDistinctPageVisit"])
            innerList.append(data["numberOfDistinctActivities"])
            innerList.append(data["knownContact"])
            innerList.append(data["hasSocialMediaId"])
            innerList.append(data["customer"])
            outerList.append(innerList)
        
        df = pd.DataFrame(outerList)
        
        # copy the data
        df_max_scaled = df.copy()
        
        X = df_max_scaled.iloc[:, :-1].values
        y = df_max_scaled.iloc[:, -1].values
        
        #from sklearn.preprocessing import StandardScaler
        #sc = StandardScaler()
        #df_max_scaled = sc.fit_transform(X)
        
        from sklearn.naive_bayes import GaussianNB
        classifier = GaussianNB()
        classifier.fit(X, y)
        pickle.dump(classifier, open("customerprediction.pkl","wb"))
        return "success"

CreateCustomerPreditTemplate().create()