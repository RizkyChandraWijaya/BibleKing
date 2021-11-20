package id.ac.ukdw.fti.rpl.Obtineo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import id.ac.ukdw.fti.rpl.Obtineo.database.Database;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Places;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Verses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

public class DetailPlaceController implements Initializable{
    private Stage stage;
    private Scene scene;

    @FXML
    private GridPane PlaceDetailPage;

    @FXML
    private Text placeTitle;

    @FXML
    private TableView<Verses> tablePlaceVerses;

    @FXML
    private TableColumn<Verses, String> columnPlaceVerse;

    @FXML
    private TableColumn<Verses, String> columnPlaceVerseIsi;

    @FXML
    private TabPane tabEventDesc;

    @FXML
    private Tab textPlaceDesc;

    @FXML
    private AnchorPane text;

    @FXML
    private Label labelDetail;

    @FXML
    private Tab pieChart;

    @FXML
    private PieChart placePieChart;

    @FXML
    private Tab graphPlaceDesc;

    @FXML
    private ScatterChart<String,Number> chartEvent;

    @FXML
    private ImageView btnBackPlace;

    private ObservableList<Verses> verses = FXCollections.observableArrayList();
    private ObservableList<Places> places = FXCollections.observableArrayList();
    private String selectedItem = new String(); 
    private List<String> selectedItemVerses = new ArrayList<String>();
    private String displayTitle;
    private String featureType;
    private String eventVerses = "";


    //yang dijalankan pertama kali
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //memanggil objek PlaceController untuk mendapatkan place yang ingin ditampilkan
        selectedItem = PlaceController.getSelectedItem();
        selectedItemVerses = PlaceController.getSelectedItemVerses();

        //perulangan untuk menampilkan seluruh ayat yang ada di list ke table
        for (String ayat:selectedItemVerses) {
            String query = "SELECT osisRef, verseText,yearNum,places,placesCount,timeline FROM verses where lower(osisRef)='"+ayat.toLowerCase()+"'";
            verses.addAll(Database.instance.getAllVerses(query));
        }
        
        //set nilai table dan label text
        tablePlaceVerses.setPlaceholder(new Label("Tidak ada ayat yang ditemukan pada place "+selectedItem));
        placeTitle.setText(selectedItem);
        columnPlaceVerse.setCellValueFactory(new PropertyValueFactory<Verses, String>("verse"));
        columnPlaceVerseIsi.setCellValueFactory(new PropertyValueFactory<Verses, String>("verseText"));
        tablePlaceVerses.setItems(verses);

        //==============================text=================================
        String replaceTitle = "LOWER(REPLACE(displayTitle,\"'\",\" \"))";
        String queryPlace = "SELECT placeLookup,displayTitle,verses,featureType,verseCount FROM places where "+replaceTitle+" like '"+selectedItem.toLowerCase().replace("'", " ")+"'";
        places = Database.instance.getAllPlaces(queryPlace);

        for (Places places2 : places) {

            if(places2.getDisplayTitle()!=null){
                displayTitle = places2.getDisplayTitle();
            }else{
                displayTitle = "unknown";
            }

            if(places2.getFeatureType()!=null){
                featureType = places2.getFeatureType();
            }else{
                featureType = "unknown";
            }  

            if(places2.getVerses()!=null){
                for (Verses verses2 : verses) {
                    if(verses2.getTimeline()!=null){
                        if(verses.indexOf(verses2)==verses.size()-1){
                            eventVerses = eventVerses + verses2.getTimeline();
                        }else{
                            eventVerses = eventVerses + verses2.getTimeline()+", ";
                        }
                    }
                }
            }else{
                eventVerses = "unknown";
            }  
            
        }

        
        int eventVerseLenght = eventVerses.length();
        if(eventVerseLenght>100){
            for (int i=1;i<=Math.floor(eventVerseLenght/100);i++){
                
                eventVerses = eventVerses.substring(0, 100*i+1) +"-\n"+eventVerses.substring(100*i+1,eventVerseLenght);
                
            }
        }
        labelDetail.setText(    "Places: "+displayTitle +"\n\n"+
                                "Feature Type: "+featureType+"\n\n"+
                                "Events : "+eventVerses +"\n\n"
            );

        //==============================text=================================
    }

    //pindah ke halaman searchingPlaces
    @FXML
    void backBtn(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PlacesPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        verses.clear();
        selectedItem = null;
        selectedItemVerses.clear();
    }

    //pindah ke halaman home
    @FXML
    void backHome(MouseEvent event) throws IOException{
        verses.clear();
        selectedItem = null;
        selectedItemVerses.clear();
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }  

    @FXML
    void detailText(MouseEvent event){
        
    }
}
