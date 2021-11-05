package id.ac.ukdw.fti.rpl.Obtineo;

import id.ac.ukdw.fti.rpl.Obtineo.database.Database;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Events;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class PlaceController{

    @FXML
    private ListView versesView;

    @FXML
    private TextField searchBar;
    ObservableList<Events> events = FXCollections.observableArrayList();
    ObservableList<String> listItem = FXCollections.observableArrayList();
    
    @FXML
    void searchEvent(ActionEvent event) {
        final String query = "SELECT title, verseSort, verses FROM events where lower(title) like '%"+searchBar.getText().toLowerCase()+"%'";
        
        events = Database.instance.getAllEvents(query);

        
        for (Events events2 : events) {
            listItem.add(events2.getEventTitle()+"\n"+events2.getVerses());
        }     
        versesView.setItems(listItem);
        
    }

    @FXML
    void deleteSugesstion(KeyEvent event) {
        versesView.getItems().clear();
        events.clear();
        listItem.clear();
    }


    // @FXML
    // void tampilSementara(KeyEvent event) {

    // }
}
