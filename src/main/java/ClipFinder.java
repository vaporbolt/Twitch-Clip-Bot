import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Seth roper contains methods for parsing through a twitch streamer's clips page and
 *         getting the top 10.
 */
public class ClipFinder
{

  /**
   * Parses through a given a twitch url get request
   * for valid twitch.tv url for valid links.
   * @param url the twitch.tv url
   * @return 
   */
  public static ArrayList<String> findClips(String url) throws IOException
  {
	// the arrayList of urls that will be generated
	ArrayList<String> urlList = new ArrayList<String>();
	
	// create the url object.
    URL clips = new URL(url);
	    
	// establish the http connection.
    HttpURLConnection twitchConnection = (HttpURLConnection) clips.openConnection();
    
   

    
    
    

   

    twitchConnection.setUseCaches(true);
    twitchConnection.setDoInput(true);
    twitchConnection.setDoOutput(true);

    // http get request
    twitchConnection.setRequestMethod("GET");
    // set the api key
    twitchConnection.setRequestProperty("Client-ID", "5qjwqjlq7hsuevnaewbjolhk4f6gl9");
    // translates user names to user ids. Not entirely important for the purpose of this program. 
    twitchConnection.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");

    
    // get response code.
    int responseCode = twitchConnection.getResponseCode();
    System.out.println("Response Code : " + responseCode);

    
    // read the json response for the get request.
    BufferedReader iny = new BufferedReader(
        new InputStreamReader(twitchConnection.getInputStream()));
    String output;
    StringBuffer jsonres = new StringBuffer();

    while ((output = iny.readLine()) != null)
    {
      jsonres.append(output);
    }
    iny.close();

    
    // printing result from response
    String temp = jsonres.toString();
    System.out.println(temp);

    ArrayList<String> tempTwo = new ArrayList<String>();

    // extract the urls from the http request.
    tempTwo = extractUrls(temp);

    // parse the list of urls for the urls that contain and offset, denoted
    // with "preview-480." these represent mp4 files.
    for (int i = 0; i < tempTwo.size(); i++)
    {
      if (tempTwo.get(i).contains("preview-480"))
      {
        urlList.add(tempTwo.get(i));
      }

    }

    return urlList;

  }

  /**
   *helper method that extracts urls from the text.
 * @param text the text to extract urls from
 * @return an arrayList of urls stored in strings.
 */
public static ArrayList<String> extractUrls(String text)
  {
    ArrayList<String> containedUrls = new ArrayList<String>();
    String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
    Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
    Matcher urlMatcher = pattern.matcher(text);

    while (urlMatcher.find())
    {
      containedUrls.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
    }

    return containedUrls;
  }

}
