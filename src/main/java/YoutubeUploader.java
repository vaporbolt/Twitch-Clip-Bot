/**
 * Sample Java code for youtube.videos.insert
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/guides/code_samples#java
 * refresh token 1//04E1QalK8LKr1CgYIARAAGAQSNwF-L9IrpiUVp_XflNuPxx7OguJlLeDLip8WxaDkg_tfu7WqkTB_MMr5squQLefbyIU7xJmOUY4
 */




import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class YoutubeUploader {
    // You need to set this value for your code to compile.
    // For example: ... DEVELOPER_KEY = "YOUR ACTUAL KEY";
    private static final String DEVELOPER_KEY = "AIzaSyDp9s7fcE-URQFoIdlorWeYnr-hil8HTKw";

    private static final String APPLICATION_NAME = "Clip Compiler";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    final static Collection<String> SCOPES = 
        new ArrayList<String>(Arrays.asList(new String[] { "https://www.googleapis.com/auth/youtube.upload"}));
    
    // stores the credential for the lifetime of the application.
    // tldr: while it's running, you don't need to request user log in for
    // an accesstoken after the first time.
    static MemoryDataStoreFactory dataStore = new MemoryDataStoreFactory();
    
    static AuthorizationCodeInstalledApp authorization;
    
    

   
    public YoutubeUploader()
    {
    	try {
			getService();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
    	
    	if(authorization!= null && authorization.getFlow().loadCredential("Overwatch")!= null)
    	{
    		return authorization.getFlow().loadCredential("Overwatch");
    	}
    	
    	
        InputStream in = YoutubeUploader.class.getResourceAsStream("\\client_secret.json");
        GoogleClientSecrets clientSecrets =
          GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        
        // create the builder
        GoogleAuthorizationCodeFlow.Builder blah = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES);
       
        
        GoogleAuthorizationCodeFlow flow =
            new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(dataStore)
            .addRefreshListener(new DataStoreCredentialRefreshListener("Overwatch", dataStore))
            
            .build();
        
        
     
        
        
        
        
        	
        
        
        
      
        
       // Credential credential =
        authorization = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver());
        
        Credential credential = authorization.authorize("Overwatch");
        
       
        
       
        
       
      
        return credential;
    }
    
    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
    	    
    	final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public  void uploadVideo(File file)
        throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        YouTube youtubeService = getService();
        System.out.println("uploading video now");
        
 
        
        // Define the Video object, which will be uploaded as the request body.
        Video video = new Video();
        
        // Add the snippet object property to the Video object.
        VideoSnippet snippet = new VideoSnippet();
        snippet.setCategoryId("22");
        snippet.setDescription("Clips belong to the streamers featured.");
        snippet.setTitle("Fall Guys best moments of the week");
        video.setSnippet(snippet);
        
        // Add the status object property to the Video object.
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus("public");
        video.setStatus(status);

        // TODO: For this request to work, you must replace "YOUR_FILE"
        //       with a pointer to the actual file you are uploading.
        //       The maximum file size for this operation is 128GB.
        File mediaFile = file;
        InputStreamContent mediaContent =
            new InputStreamContent("video/*",
                new BufferedInputStream(new FileInputStream(mediaFile)));
        mediaContent.setLength(mediaFile.length());

        // Define and execute the API request
        YouTube.Videos.Insert request = youtubeService.videos()
            .insert("snippet,status", video, mediaContent);
        Video response = request.setKey(DEVELOPER_KEY)
            .setNotifySubscribers(true)
            .execute();
       
        
        System.out.println(response);
    }
}
