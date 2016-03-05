package TweetMap.tweet;
import static org.elasticsearch.common.xcontent.XContentFactory.*;
import twitter4j.Status;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import java.io.IOException;
import java.net.UnknownHostException;

public class ImportES {
   public void ImportTweet(Status status ) {
        String index=getIndex(status.getText());
        JestClient client = null;
        if (index != null) {
            try {
                JestClientFactory ft = new JestClientFactory();
                ft.setHttpClientConfig(new HttpClientConfig
                        .Builder("http://localhost:9200")
                        .multiThreaded(true)
                        .build());
                client = ft.getObject();
                String source = jsonBuilder() 
                        .startObject().field("userName", status.getUser().getName())
                        .field("createdTime", status.getCreatedAt().toString())
                        .field("text", status.getText())
                        .field("keyword",index)
                        .field("latitude", status.getGeoLocation().getLatitude())
                        .field("longtitude",status.getGeoLocation().getLongitude())
                        .endObject().string();
                Index add = new Index.Builder(source).index(index).type("tweet").build();
                client.execute(add);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     }
   public String getIndex(String s){
	   String index = "";
       if (s.toLowerCase().contains("california")) {
           index = "california";
       } else if (s.toLowerCase().contains("game")) {
           index = "game";
       } else if (s.toLowerCase().contains("fashion")) {
           index = "fashion";
       } else if (s.toLowerCase().contains("trump")) {
           index = "trump";
       } else if (s.toLowerCase().contains("food")) {
           index = "food";
       } else if (s.toLowerCase().contains("movie")) {
           index = "movie";
       }
       else {
            index = null;
       }

	   return index;
   }

}
