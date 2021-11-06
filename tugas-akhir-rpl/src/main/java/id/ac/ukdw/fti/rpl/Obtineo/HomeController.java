package id.ac.ukdw.fti.rpl.Obtineo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;

public class HomeController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    Image myImage = new Image(getClass().getResourceAsStream("logo.jpeg"));

    @FXML
    private Button btnPlaces;

    @FXML
    private Button btnEvents;

    @FXML
    private ImageView HomeLogo;
    
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
    void placesPage(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("PlacesPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}