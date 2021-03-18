import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class ClipDownloader
{

  
  
  /**
 * @param link
 * @return
 */
public static  ArrayList<String> getDownloadLink(ArrayList<String> link)
  {
    ArrayList<String> downloadLinks = new ArrayList<String>();
    
    for(int i = 0; i < link.size(); i++)
    {
       int beforeDot = link.get(i).indexOf("-preview");
       String baseLink = link.get(i).substring(0, beforeDot);
       downloadLinks.add(baseLink + ".mp4");
    }
    return downloadLinks;
  }
  
  
  
  public static void downloadClips(ArrayList<String> listOfClips) throws IOException
  {
	  for(int i = 0; i < listOfClips.size(); i ++)
	    {
	     ClipDownloader.downloadClip(listOfClips.get(i));
	    }
  }
  
  /**
 * @param link the url link the clip that will be downloaded.
 * @throws IOException
 */
public static void downloadClip(String link) throws IOException
  
  
  {
    String folder = null;

    //Exctract the name of the video from the src attribute
    int indexname = link.lastIndexOf("/");

    if (indexname == link.length()) {
        link = link.substring(1, indexname);
    }

    indexname = link.lastIndexOf("/");
    String name = link.substring(indexname, link.length());

    System.out.println(name);

    //Open a URL Stream
    URL url = new URL(link);
    InputStream in = url.openStream();
    OutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\john Chastanet\\Downloads\\Output"+ name));
    for (int b; (b = in.read()) != -1;) {
        out.write(b);
    }
    out.close();
    in.close();
}
}
