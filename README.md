# cloud-HW1
tweetmap
ReadMe
1.	Use the Twitter Streaming API to fetch tweets from the twitter hosts in real time
2.	Use Elasticsearch to store the tweets in the backend
(1) initialize the jest client
(2) From the user status we get, we use a simple method getIndex() to fetch the text that correspond to the key word and store it in the elastic search
(3) Use a jsonBuilder to configure the data format with username, created time , text, keyword ,latitude and longitude
(4) Index the document into the Elasticsearch 
3.	Using javascript to connect to Elasticsearch and search for a few key words (including game, movie, fashion, trump, fashion, food) and use the Google map API to show markers on the map corresponding to different key words.
