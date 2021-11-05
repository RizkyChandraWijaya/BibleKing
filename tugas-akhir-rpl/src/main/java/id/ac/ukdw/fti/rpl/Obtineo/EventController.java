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

public class EventController{

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

// public class EventController{

//     @FXML
//     private ListView versesView;

//     @FXML
//     private TextField searchBar;
    
//     @FXML
//     void searchEvent(ActionEvent event) {
        
//         try {
//             final String query = "SELECT title, verseSort, verses FROM events where title like '%"+searchBar.getText()+"%'";
//             ObservableList<Events> events = FXCollections.observableArrayList();
//             events = Database.instance.getAllEvents(query);

//             ObservableList<String> listItem = FXCollections.observableArrayList();
//             for (Events events2 : events) {
//                 listItem.add(events2.getEventTitle()+"\n"+events2.getVerses());
        
//             }
        
//         versesView.setItems(listItem);
//         listItem.clear();
            
//         } catch (Exception e) {
//             //TODO: handle exception
//             e.printStackTrace();
//         }
        
//         versesView.getItems().removeAll();
//     }

//     @FXML
//     void deleteSugesstion(KeyEvent event) {
//         versesView.getItems().clear();
//     }


//     // @FXML
//     // void tampilSementara(KeyEvent event) {

//     // }
// }






        // events.getItems().getEventTitle().getText();
        
        // List<String> eventTitle = events.getEventTitle();
    //     // versesView.getItems().add("eee");
    //     System.out.println("Ini initialize");
    //     if(events.size()==0) {
    //         System.out.println("gagal baca database");
    //     }else{
    //         for (Events events2 : events) {
    //             // System.out.println(events2.getEventTitle());
    
        // List value = Arrays.asList("one", "two", "three");
        // List value = Arrays.asList(events).getAllEventTitle().getText());
        // List<String> values = Arrays.asList("one", "two", "three");
       
        
        
        
        
        
    
    

