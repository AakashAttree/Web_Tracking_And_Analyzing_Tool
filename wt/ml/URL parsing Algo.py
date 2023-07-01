#!/usr/bin/env python
# coding: utf-8

# In[40]:


#Importing required libraries

import nltk
from nltk.corpus import brown
get_ipython().system('pip install beautifulsoup4')
from __future__ import division 
import nltk, re, pprint
from nltk import word_tokenize
from bs4 import BeautifulSoup
from urllib import request
url = "http://192.168.56.1:9000/jsp/testtracking.jsp"
response = request.urlopen(url)
#To remove html tags and extra characters like \n, ., - 

response = BeautifulSoup(response.read(),'lxml').get_text()

unwantedCharacters = {
    "\n": " ",
    "," : "",
    "." : "",
    "-" : ""
}

for key in unwantedCharacters:
    response = response.replace(key, unwantedCharacters[key])

from collections import Counter
input_string=response
from nltk.corpus import stopwords
stop_words = set(stopwords.words('english'))
stop_words


# In[41]:



alphanumeric = ""

for character in input_string:
    if character.isalnum() or character == " ":
        alphanumeric += character
        
inputValues = alphanumeric.lower()        
import pandas as pd
from nltk.tokenize import word_tokenize
word_tokens = word_tokenize(inputValues)
filtered_sentence = []
 
for w in word_tokens:
    if w not in stop_words:
        filtered_sentence.append(w)
        
filtered_sentence


# In[42]:


word_freqs = Counter(filtered_sentence)
list = []         
for i in word_freqs:
    list.append({ "word":i, "count":word_freqs[i]} )
list    


# In[43]:


def sortSecond(val):
    if len(val["word"]) == 1:
        return 0
    return val["count"] 

newlist = sorted(list, key=sortSecond, reverse=True)
print(len(newlist))


# In[44]:


class URLParsingData:
    def __init__(self, url, listOfWords): 
        self.url = url 
        self.listOfWords = listOfWords    
get_ipython().system('pip install pymongo')
from pymongo import MongoClient
  
try:
    conn = MongoClient()
    print("Connected successfully!!!")
except:  
    print("Could not connect to MongoDB")
  
# database
db = conn.webtrackingdata
  
# Created or Switched to collection names: my_gfg_collection
collection = db.urlparsingdata
  
# Insert Data
rec_id1 = collection.insert_one({"url":url,"listOfWords":newlist})


# In[ ]:




