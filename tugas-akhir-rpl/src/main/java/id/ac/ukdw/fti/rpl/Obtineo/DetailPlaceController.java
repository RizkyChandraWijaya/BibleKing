package id.ac.ukdw.fti.rpl.Obtineo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id.ac.ukdw.fti.rpl.Obtineo.database.Database;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Events;
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
    private ObservableList<Events> events = FXCollections.observableArrayList();
    private String selectedItem = new String(); 
    private List<String> selectedItemVerses = new ArrayList<String>();
    private String displayTitle;
    private String featureType;
    private String eventVerses = "";
    private String duration;
    int newDuration;
    private ArrayList<Integer> arrDuration = new ArrayList<Integer>();
    Map<String, Integer> durationMap = new HashMap<String, Integer>();


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
                        String replaceEventTitle = "LOWER(REPLACE(title,\"'\",\" \"))";
                        String queryEvent = "SELECT title,verseSort,verses,startDate,duration,predecessor,partOf,places FROM events where "+replaceEventTitle+" like '"+verses2.getTimeline().toLowerCase().replace("'", " ")+"'";
                        events = Database.instance.getAllEvents(queryEvent);
                        for(Events events2 : events ){
                            duration = events2.getDuration();
                            if(duration != null){
                                String duration2 = duration.substring(duration.length()-1);                            
                                if(duration2.equals("D")){
                                    newDuration = Integer.parseInt(duration.substring(0,duration.length()-1))*1;
                                }else if(duration2.equals("M")){
                                    newDuration = Integer.parseInt(duration.substring(0,duration.length()-1))*30;
                                }else if(duration2.equals("Y")){
                                    newDuration = Integer.parseInt(duration.substring(0,duration.length()-1))*365;
                                }
                            arrDuration.add(newDuration); 
                                
                               
                            }
                            
                        }
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

        //==============Pie Chart=====================
        Map<String, Integer> counts = new HashMap<String, Integer>();
        String[] arrEvent = eventVerses.split(", ");
        durationMap.put(eventVerses, newDuration);

        for (String ft : arrEvent) { 
            if (counts.containsKey(ft)) { 
                counts.put(ft, counts.get(ft) + 1); 
            } else { 
                counts.put(ft, 1); 
            } 
        }
        ArrayList<String> arrEvent1 = new ArrayList<String>();
        ArrayList<Integer> arrDuration1 = new ArrayList<Integer>(); 

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            arrEvent1.add(entry.getKey());
            arrDuration1.add(entry.getValue());
        }
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        for(int i=0;i<arrEvent1.size(); i++){
            list.add(new PieChart.Data(arrEvent1.get(i),arrDuration1.get(i)));
        }
        placePieChart.setData(list);
    // System.out.println(list);
    }

    
    

//     for (String ft : arrFeatureType) { 
//         if (counts.containsKey(ft)) { 
//             counts.put(ft, counts.get(ft) + 1); 
//         } else { 
//             counts.put(ft, 1); 
//         } 
//     } 
    
//     ArrayList<String> arrNameFt = new ArrayList<String>();
//     ArrayList<Integer> arrCountFt = new ArrayList<Integer>();
    
//     for (Map.Entry<String, Integer> entry : counts.entrySet()) {
//         // System.out.println(entry.getKey()+" "+entry.getValue());
//         arrNameFt.add(entry.getKey());
//         arrCountFt.add(entry.getValue());
//     }
    
//     ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
//     for(int i=0;i<arrNameFt.size(); i++){
//         list.add(new PieChart.Data(arrNameFt.get(i),arrCountFt.get(i)));
//         }
//     // System.out.println(list);
    
//     eventPieChart.setData(list);

// }

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
