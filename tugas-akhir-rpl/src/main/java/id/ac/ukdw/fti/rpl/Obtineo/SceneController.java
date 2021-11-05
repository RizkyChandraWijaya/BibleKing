package id.ac.ukdw.fti.rpl.Obtineo;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import id.ac.ukdw.fti.rpl.Obtineo.database.Database;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Events;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SceneController{

    @FXML
    private Button btnPlaces;

    @FXML
    private Button btnEvents;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ListView<String> versesView;
    

    @FXML
    private ImageView HomeLogo;

    Image myImage = new Image(getClass().getResourceAsStream("logo.jpeg"));

    public void displayImage() {
        HomeLogo.setImage(myImage);
       }

    @FXML
    void eventsPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("EventsPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
       }

    @FXML
    void placesPage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("PlacesPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

    // private ObservableList<Events> events = FXCollections.observableArrayList();
    
    // public void initialize(URL location, ResourceBundle resources) {
        
    //     versesView = new ListView<String>();
    //     ObservableList<String> items =FXCollections.observableArrayList ("Single", "Double", "Suite", "Family App");
    //     versesView.getItems().addAll(items);
        // events = Database.instance.getAllEvents();
    //     // versesView.getItems().add("eee");
    //     System.out.println("Ini initialize");
    //     if(events.size()==0) {
    //         System.out.println("gagal baca database");
    //     }else{
    //         for (Events events2 : events) {
    //             // System.out.println(events2.getEventTitle());
    //         }
    //     }
        



        //versesView.getItems().addAll(PropertyValueFactory<Events, String>("eventTitle"));

    
        // Connection conn = null;
        
        // try{
        //     Class.forName("org.sqlite.JDBC");
        //     conn = DriverManager.getConnection();
        //     System.out.println("KONEK");
        // }
        // catch (Exception e){
        //     System.out.println("Error Database");
        //     System.out.println(e.getMessage());
        // }

        // String query = "SELECT verseSort from events";

        // try {
        //     Statement statement = conn.createStatement();
        //     ResultSet hasil = statement.executeQuery(query);

        //     while (hasil.next()) {
        //         String verseSort = hasil.getString("verseSort");
                
        //         versesView.getItems().add(verseSort);
        //     }

        // } catch (Exception e) {
        //     //TODO: handle exception
        //     e.printStackTrace();
        // }
        
        

    


