package TweetMap.tweet;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreaming {

	static String ConsumerKey="OkdTvQevRCEpqK0imEwEbDHcm";
    static String ConsumerSecret="4SWX7uNl0l6pFGyHlkx8plAw2ahduPctUePi7tcZtjxdfNa40i";
    static String AccessToken="593902431-jp0f1Tp3XjBxMAY2gIQPbdnYY1NzUpl6XgHtNNzf";
    static String TokenSecret="UTjWeRlUgSO7teyqsPOuYztJ9y2QGUFAbmrbwc8SjW073";
   
    public static void main(String[] args) {
    	TwitterStreaming stream=new TwitterStreaming();
    	stream.Streaming();
    }
   
   public void Streaming(){
    	ConfigurationBuilder builder=new ConfigurationBuilder();
    	builder.setDebugEnabled(true).setOAuthConsumerKey(ConsumerKey)
    	.setOAuthConsumerSecret(ConsumerSecret)
    	.setOAuthAccessToken(AccessToken)
    	.setOAuthAccessTokenSecret(TokenSecret);
      TwitterStream twitterStream = new TwitterStreamFactory(builder.build()).getInstance();
      final ImportES importer=new ImportES();
      StatusListener listener = new StatusListener(){
            public void onStatus(Status st) {
                 if (st.getGeoLocation()!= null&&st.getUser()!=null) {
                     System.out.println(st.getUser()+","+st.getUser().getId());
                     importer.ImportTweet(st);
                 } 
            }
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
	
			public void onScrubGeo(long arg0, long arg1) {
				
			}
			
			public void onStallWarning(StallWarning arg0) {
				
			}
			
        };
       
        twitterStream.addListener(listener);
       
        twitterStream.sample();
        
       }
    
}
