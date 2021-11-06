package id.ac.ukdw.fti.rpl.Obtineo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import id.ac.ukdw.fti.rpl.Obtineo.database.Database;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Places;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PlaceController{
    private Stage stage;
    private Scene scene;
    ObservableList<Places> places = FXCollections.observableArrayList();
    ObservableList<String> listPlaces = FXCollections.observableArrayList();
    private static String selectedItem = new String(); 
    private static List<String> selectedItemVerses = new ArrayList<String>();  
    public static EventController instance = new EventController();

    @FXML
    private ListView placesView;

    @FXML
    private TextField searchBar;
    
    @FXML
    void searchPlaces(ActionEvent event) {
        //query select di class Database
        final String query = "SELECT displayTitle, verses FROM places where lower(displayTitle) like '%"+searchBar.getText().toLowerCase()+"%'";       
        
        //manggil database dan method getAllPlaces
        places = Database.instance.getPlacesgetAllPlaces(query);
        
        //foreach buat ambil value dari objek places lalu ditambahkan di listPlaces
        for (Places places2 : places) {
            listPlaces.add(places2.getDisplayTitle()+"\n"+places2.getVerses());
        } 

        //pengecekkan searching ditemukan hasil atau tidak
        if (listPlaces.size()>0) {
            placesView.setItems(listPlaces);
        } else {
            placesView.setPlaceholder(new Label("Pencarian Tidak Ditemukan"));
        }
    }

    //menghapus placeholder dan hasil pencarian onKeyPressed
    @FXML
    void deleteSugesstion(KeyEvent event) {
        placesView.setPlaceholder(new Label(" "));
        placesView.getItems().clear();
        places.clear();
        listPlaces.clear();
    }

    //dijalankan saat listview diklik
    @FXML
    void selectedPlaces(MouseEvent event) throws IOException{
        //ngambil value dari listview yang diklik
        String text = placesView.getSelectionModel().getSelectedItem().toString();

        //split array agar dapat diolah
        String[] kumpulanAyat = text.split("\n");
        String[] ayat = kumpulanAyat[1].split(",");
        selectedItem = kumpulanAyat[0];
        
        //perulangan untuk ayat yang lebih dari 1
        for (int i = 0; i < ayat.length; i++) {
            selectedItemVerses.add(ayat[i].strip());
        }

        //pindah halam ke detailPlaces
        Parent root = FXMLLoader.load(getClass().getResource("DetailPlace.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //pindah ke homePage
    @FXML
    void backHome(MouseEvent event) throws IOException{
        places.clear();
        listPlaces.clear();
        selectedItem = null;
        selectedItemVerses.clear();
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //get nama place yang dipilih
    public static String getSelectedItem() {
        return selectedItem;
    }

    //get list ayat berdasar place yg dipilih
    public static List<String> getSelectedItemVerses() {
        return selectedItemVerses;
    }
}
