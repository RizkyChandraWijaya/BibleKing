package id.ac.ukdw.fti.rpl.Obtineo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.ac.ukdw.fti.rpl.Obtineo.database.Database;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Events;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventController{
    @FXML
    private ListView versesView;

    @FXML
    private TextField searchBar;
    private ObservableList<Events> events = FXCollections.observableArrayList();
    private ObservableList<String> listItem = FXCollections.observableArrayList();
    private static String selectedItem = new String(); 
    private static List<String> selectedItemVerses = new ArrayList<String>();  
    public static EventController instance = new EventController();
    private Stage stage;
    private Scene scene;
    
    @FXML
    void searchEvent(ActionEvent event) {
        if(searchBar.getText().isEmpty()){
            alert(event);
        }else{
            final String query = "SELECT title, verseSort, verses, startDate, duration, predecessor, partOf, places, peoples FROM events where lower(title) like '%"+searchBar.getText().toLowerCase()+"%'";
            events = Database.instance.getAllEvents(query);
            for (Events events2 : events) {
                listItem.add(events2.getEventTitle()+"\n"+events2.getVerses());
            }
            if(listItem.size()>0){
                versesView.setItems(listItem);
            }else{
                alert(event);
            }
        }
    }

    @FXML
    void deleteSugesstion(KeyEvent event) {
        versesView.setPlaceholder(new Label(" "));
        versesView.getItems().clear();
        events.clear();
        listItem.clear();
    }

    @FXML
    void selectedEvent(MouseEvent event) throws IOException{
        try {
            String text = versesView.getSelectionModel().getSelectedItem().toString();
            String[] kumpulanAyat = text.split("\n");
            String[] ayat = kumpulanAyat[1].split(",");
            selectedItem = kumpulanAyat[0];

            for (int i = 0; i < ayat.length; i++) {
                selectedItemVerses.add(ayat[i].strip());
            }

            Parent root = FXMLLoader.load(getClass().getResource("DetailEvent.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @FXML
    void backHome(MouseEvent event) throws IOException{
        events.clear();
        listItem.clear();
        selectedItem = null;
        selectedItemVerses.clear();
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private  void alert(ActionEvent event){
        if(searchBar.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
            alert.setTitle("Bible King");
            alert.setHeaderText(null);
            alert.setContentText("Masukkan keyword pencarian terlebih dahulu");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
            String keyword = searchBar.getText();
            alert.setTitle("Bible King");
            alert.setHeaderText(null);
            alert.setContentText("Pencarian keyword: "+keyword+" tidak ditemukan");
            alert.showAndWait();
        }
    }

    public static String getSelectedItem() {
        return selectedItem;
    }

    public static List<String> getSelectedItemVerses() {
        return selectedItemVerses;
    }
}