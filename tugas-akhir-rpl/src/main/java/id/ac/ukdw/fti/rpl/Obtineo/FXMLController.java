// package id.ac.ukdw.fti.rpl.Obtineo;

// import java.net.URL;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.ResourceBundle;

// import id.ac.ukdw.fti.rpl.Obtineo.database.Database;
// import id.ac.ukdw.fti.rpl.Obtineo.modal.ButtonLabel;
// import id.ac.ukdw.fti.rpl.Obtineo.modal.Verse;
// import javafx.collections.ObservableList;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.Initializable;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableView;
// import javafx.scene.control.TextField;
// import javafx.scene.control.cell.PropertyValueFactory;
// import javafx.scene.layout.AnchorPane;

// public class FXMLController{

//     Database db = new Database();


//     @FXML
//     private ScrollPane scrollPane;

//     @FXML
//     private AnchorPane tampilanAyat;

//     @FXML
//     private Button buttonSearch;
//     @FXML
//     private TextField pencarian;

//     private List<Label> labels = new ArrayList<>();
    
//     @FXML
//     public void searching(ActionEvent event) {

//         String cari = pencarian.getText().toLowerCase();
    
//         ArrayList<Verse> hasil = db.tampil(cari);
//         try{
//             for(Verse verse : hasil) {
//                 System.out.println(verse);
//                 if(verse.getVerseCount()>1){
//                     String[] split = verse.getverses().split(",");
//                     for (String i : split){
//                         System.out.println(i);
//                         ButtonLabel btn = createLabel(i);
//                         labels.add(btn.getLabel());
//                     }
//                 }
//                 else{
//                     ButtonLabel btn = createLabel(verse.getverses());
//                     labels.add(btn.getLabel());
//                 }
//             }
//         }
//         catch(Exception e){
//             e.getMessage();
//         }
        
//         tampilanAyat.getChildren().addAll(labels);
//     }
//     // @FXML
//     // private TableView<Verse> tableVerses;

//     // @FXML
//     // private TableColumn<Verse, Integer> verseIdColumn;

//     // @FXML
//     // private TableColumn<Verse, String> verseColumn;

//     // @FXML
//     // private TableColumn<Verse, String> verseTextColumn;
    
   

//     private ButtonLabel createLabel(String ayat) {
//         try{
//             String[] split = ayat.split(", ");
//             Label label = new Label();
//             ButtonLabel btnlbl = new ButtonLabel(label);
//             return btnlbl;
//         }
//         catch(Exception e){
//             Label label = new Label();
//             ButtonLabel btnlbl = new ButtonLabel(label);
//             return btnlbl;
//         }


//     }

//     // @Override
//     // public void initialize(URL location, ResourceBundle resources) {
//     //     // TODO Auto-generated method stub
//     //     // ObservableList<Verse> verses = Database.instance.getAllVerse();
//     //     // verseIdColumn.setCellValueFactory(new PropertyValueFactory<Verse,Integer>("verseId"));
//     //     // verseColumn.setCellValueFactory(new PropertyValueFactory<Verse,String>("verse"));
//     //     // verseTextColumn.setCellValueFactory(new PropertyValueFactory<Verse,String>("verseText"));
//     //     // tableVerses.setItems(verses);


        
        
        
//     // }
// }

