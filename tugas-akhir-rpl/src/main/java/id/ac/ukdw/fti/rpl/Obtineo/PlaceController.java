package id.ac.ukdw.fti.rpl.Obtineo;

import id.ac.ukdw.fti.rpl.Obtineo.database.Database;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Places;
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
    ObservableList<Places> places = FXCollections.observableArrayList();
    ObservableList<String> listPlaceList = FXCollections.observableArrayList();
    
    @FXML
    void searchPlaces(ActionEvent event) {
        final String query = "SELECT displayTitle, verses FROM places where lower(displayTitle) like '%"+searchBar.getText().toLowerCase()+"%'";
        
        places = Database.instance.getPlacesgetAllPlaces(query);

        
        for (Places places2 : places) {
            listPlaceList .add(places2.getDisplayTitle()+"\n"+places2.getVerses());
        }     
        versesView.setItems(listPlaceList );
        
    }

    @FXML
    void deleteSugesstion(KeyEvent event) {
        versesView.getItems().clear();
        places.clear();
        listPlaceList .clear();
    }


    // @FXML
    // void tampilSementara(KeyEvent event) {

    // }
}
