/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import dao.MembreDao;
import entities.Membre;
import fragments.Compte;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.kairos.core.Activity;

/**
 *
 * @author omar
 */
public class modif_image extends Activity {
   
    @FXML
    Label info;
    @FXML
    ImageView images;
    
    
       File image_path=null;
       int id=63;
        MembreDao md= new MembreDao();
         Membre m =new Membre();
    @Override
    public void onCreate() {
      
        super.onCreate();
       
        m=md.consulter(id);
        
         
        setContentView(getClass().getResource("../fxml/modif_image.fxml"));
         if(!(m.getImageName()==null)){
         DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());

        String accessToken = "tKqn9aG0UZAAAAAAAAAACMXrAofgnDqtNNSoX5gVYYeJCioYsaVLRnFcsdhQmWsC";

        DbxClient client = new DbxClient(config, accessToken);
        //int id= (int)getIntent().getExtras().get("id");
        try {
            String url=client.createTemporaryDirectUrl("/"+id+"/"+m.getImageName()).url;
            
            
           
                Image image = new Image(url, 149, 180, true, true, true);
                images.setImage(image);
            
        } catch (DbxException ex) {
            Logger.getLogger(Compte.class.getName()).log(Level.SEVERE, null, ex);
        }}
    
         
    }
    
 public void charger()
 {
      FileChooser f = new FileChooser();
        File s = f.showOpenDialog(null);
        
        
        if (!s.getPath().equals(null)) {
            image_path = s;
            info.setText(s.getPath());
        }
      
         
 }
 public void confirmer() throws DbxException, FileNotFoundException, IOException
 { if(image_path==null)
 {
             JOptionPane.showMessageDialog(null, "Veuillez choisir une image");
 } 
 else {
     
 
      DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());

                    String accessToken = "tKqn9aG0UZAAAAAAAAAACMXrAofgnDqtNNSoX5gVYYeJCioYsaVLRnFcsdhQmWsC";

                    DbxClient client = new DbxClient(config, accessToken);

                    ////////////////////////////
                  /*   InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(new File("c:/banner.jpg"));
            os = new FileOutputStream(new File("c:/wamp/www/banner.jpg"));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }*/
                    
                    
                    
                    
                    
                    
                    

                   ////////////////////////////////
                    File inputFile = image_path;
                    FileInputStream inputStream = new FileInputStream(inputFile);
                  
                    try {
                      if(!(m.getImageName()==null))
                       client.delete("/"+id+"/"+m.getImageName());
                        DbxEntry.File uploadedFile = client.uploadFile("/"+id+"/"+image_path.getName(),
                                DbxWriteMode.add(), inputFile.length(), inputStream);
                        md.modifier_imageName(id, image_path.getName());
                        System.out.println("Uploaded: " + uploadedFile.toString());
                    } finally {
                        inputStream.close();
                    }
                    Stage s = (Stage) info.getScene().getWindow();
        s.close();
 }}
 
 
 public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}
}
