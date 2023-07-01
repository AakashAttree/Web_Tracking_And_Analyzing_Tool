from pymongo import MongoClient
import pandas as pd
from sklearn.cluster import KMeans
class ClusterinOfSessions:
    def cluster(self):
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
            outerList.append(innerList)
        
        df = pd.DataFrame(outerList)
        
        # copy the data
        df_max_scaled = df.copy()
          
        # apply normalization techniques
        #from sklearn.preprocessing import StandardScaler
        #sc = StandardScaler()
        #df_max_scaled = sc.fit_transform(df_max_scaled)
        
        #wcss = []
        #max_value = 10 if (len(outerList)>10) else len(outerList)
        #print(max_value)
        #for i in range(1, max_value):
        #    kmeans = KMeans(n_clusters = i, init = 'k-means++', random_state = 42)
        #    kmeans.fit(df_max_scaled)
        #    wcss.append(kmeans.inertia_)
            
        kmeans3 = KMeans(3)
        kmeans3.fit(df_max_scaled)
        identified_clusters3 = kmeans3.fit_predict(df_max_scaled)  
            
        index=0
        for sessionId in sessionIds:
            collection.update_many({"sessionId":sessionId}, { "$set" : {"cluster": int(identified_clusters3[index])}})
            index = index+1
            
        return "success"    
    