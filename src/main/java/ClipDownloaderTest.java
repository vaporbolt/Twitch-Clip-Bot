import java.util.concurrent.TimeUnit;
import java.io.File;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;

public class ClipDownloaderTest {

    public static void main(String[] args)
            throws IOException, GeneralSecurityException, InterruptedException {
        // the hour of the day the video will upload.
        
        int uploadHour =  12;

        /* 
         * create a youtube uploader object which has an authorized oauth2.0 credential with a stored refresh token that persists during run time
         * opens user browser for authentication.
         */
        YoutubeUploader uploader = new YoutubeUploader();

        while (true) {

            // keeps track of the current hour of the day.
            int hourOfDay = java.time.LocalDateTime.now().getHour();

            // check if the hour of the day is 1 am.
            if (hourOfDay == uploadHour) {

                ArrayList<String> listOfClips = new ArrayList<String>();

                
                // get a list of twitch clips from a valid http get request. 
                listOfClips = ClipFinder.findClips(
                        "https://api.twitch.tv/kraken/clips/top?trending=true&limit=10&game=Music&language=en");

                // gets the download link from the list of clips. (generated
                // from the offset
                listOfClips = ClipDownloader.getDownloadLink(listOfClips);

                
                // downloads all the clips.
                ClipDownloader.downloadClips(listOfClips);

                // the directory where the downloaded clips are stored.
                File dir = new File(
                        "C:\\\\Users\\\\john Chastanet\\\\Downloads\\\\Output");
                // the directory where the upscaled clips will go.
                
                File fixed = new File(
                        "C:\\Users\\john Chastanet\\Downloads\\Output2");

                // the directory for the final output
                File listing = new File(
                        "C:\\Users\\john Chastanet\\Downloads\\Final Output\\list.txt");
                
                File video = new File(
                        "C:\\Users\\john Chastanet\\Downloads\\Final Output\\OWCLIPS.mp4");
                
          
                
                // you have to run this for apache to work
                // for some reason. 
                BasicConfigurator.configure();
                
                
                
                
                // upscale all of the downloaded clips
                // so they are same resolution and can concactanate.
                MergerFiles.resize(dir);
                
                
                File[] directoryListing = fixed.listFiles();

                
                //
                MergerFiles.merge(directoryListing[0], listing, video);
                
                // upload the video. 
                uploader.uploadVideo(video);

                File[] clipsDirectory = dir.listFiles();
                File[] fixedClipsDirectory = fixed.listFiles();
                
                
                // clear the file directory. 
                for (File tmp : clipsDirectory) {
                    tmp.delete();
                }
                
                
                // 
                for (File tmp : fixedClipsDirectory) {
                    tmp.delete();
                }

            }
            
            

            // thread sleeps for 1 hour.
            Thread.sleep(1000 * 3600);

        }
    }
}
