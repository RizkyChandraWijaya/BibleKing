package id.ac.ukdw.fti.rpl.Obtineo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;

public class DetailEventController implements Initializable {
    @FXML
    private GridPane EventDetailPage;

    @FXML
    private Text eventTitle;

    @FXML
    private TableView<Verses> tableEventVerse;

    @FXML
    private TableColumn<Verses, String> columnEventVerse;

    @FXML
    private TableColumn<Verses, String> columnEventVerseIsi;

    @FXML
    private TabPane tabEventDesc;

    @FXML
    private Tab textEventDesc;

    @FXML
    private Tab pieChart;

    @FXML
    private Label detailPieChart;

    @FXML
    private PieChart eventPieChart = new PieChart();

    @FXML
    private Tab graphEventDesc;

    @FXML
    private TextArea detailText;

    @FXML
    private ScatterChart<String,Number> chartEvent;

    @FXML
    private ImageView btnBackEvent;

    @FXML
    private AnchorPane text;

    @FXML
    private Label labelDetail;

    private Stage stage;
    private Scene scene;
    private ObservableList<Verses> verses = FXCollections.observableArrayList();
    private ObservableList<Events> events = FXCollections.observableArrayList();
    private ObservableList<Places> placesFromEvent = FXCollections.observableArrayList();
    private String selectedItem = new String(); 
    private List<String> selectedItemVerses = new ArrayList<String>();
    private String yearNum;
    private String places;
    private String displayTitlePlaces = "";
    private String featureTypePlaces = "";
    private String title;
    private String placeEvent;
    private String duration;
    private String predecessor;
    private String partOf;
    private String startDate;
    private ArrayList<String> arrFeatureType = new ArrayList<String>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //=========================table====================================
        selectedItem = EventController.getSelectedItem();
        selectedItemVerses = EventController.getSelectedItemVerses();
        for (String ayat:selectedItemVerses) {
            String query = "SELECT osisRef,verseText,yearNum,places,placesCount,timeline  FROM verses where lower(osisRef)='"+ayat.toLowerCase()+"'";
            verses.addAll(Database.instance.getAllVerses(query));
        }
        tableEventVerse.setPlaceholder(new Label("Tidak ada ayat yang ditemukan pada event "+selectedItem));
        eventTitle.setText(selectedItem);
        columnEventVerse.setCellValueFactory(new PropertyValueFactory<Verses, String>("verse"));
        columnEventVerseIsi.setCellValueFactory(new PropertyValueFactory<Verses, String>("verseText"));
        tableEventVerse.setItems(verses);
        //=========================table====================================
        
        //=========================text====================================
        String replaceTitle = "LOWER(REPLACE(title,\"'\",\" \"))";
        String queryEvent = "SELECT title,verseSort,verses,startDate,duration,predecessor,partOf,places FROM events where "+replaceTitle+" like '"+selectedItem.toLowerCase().replace("'", " ")+"'";
        events = Database.instance.getAllEvents(queryEvent);
        
        // String queryEvent = "SELECT title,verseSort,verses,startDate,duration,predecessor,partOf,places FROM events where lower(title) like '"+selectedItem.toLowerCase()+"'";
        // events = Database.instance.getAllEvents(queryEvent);
        Set<String> uniques = new HashSet<String>();
        Map<String, String> pair = new HashMap<String, String>();
        for (Events events2 : events) {
            title = events2.getEventTitle();
            if(events2.getPlacesVerses()!=null){
                placeEvent = events2.getPlacesVerses();
                String[] placesSplit = placeEvent.split(",");
                for (String placesSplit2 : placesSplit) {
                    String queryPlaces = "SELECT placeLookup,displayTitle,verses,featureType,verseCount FROM places where lower(placeLookup)='"+placesSplit2.toLowerCase()+"'";
                    placesFromEvent.addAll(Database.instance.getAllPlaces(queryPlaces));
                }

                
                for (Places places2:placesFromEvent){
                    if(places2.getDisplayTitle()!=null){
                        if(placesFromEvent.indexOf(places2)==placesFromEvent.size()-1){
                            // displayTitlePlaces = displayTitlePlaces+places2.getDisplayTitle();
                            uniques.add(places2.getDisplayTitle());

                        }else{
                            // displayTitlePlaces = displayTitlePlaces+places2.getDisplayTitle()+", ";
                            uniques.add(places2.getDisplayTitle());
                        }
                        //System.out.println(places2.getFeatureType());
                        if(places2.getFeatureType()!=null){
                            arrFeatureType.add(places2.getFeatureType());
                        }else{
                            arrFeatureType.add("Unknown");
                        }
                    }
                }
            }else{
                // displayTitlePlaces = "Unknown";
                arrFeatureType.add("Unknown");
            }
            

            if(events2.getDuration()!=null){
                duration = events2.getDuration();
            }else{
                duration = "unknown";
            }
            
            if(events2.getPredecessor()!=null){
                predecessor = events2.getPredecessor();
            }else{
                predecessor = "unknown";
            }
            
            if(events2.getPartOf()!=null){
                partOf = events2.getPartOf();
            }else{
                partOf = "unknown";
            }
            
            if(events2.getStartDate()!=null){
                startDate = events2.getStartDate();
            }else{
                startDate = "unknown";
            }
            
        }
        
    
        int lengtPlaceFromEvent = displayTitlePlaces.length();
        if(lengtPlaceFromEvent>100){
            for (int i=1;i<=Math.floor(lengtPlaceFromEvent/100);i++){
                
                displayTitlePlaces = displayTitlePlaces.substring(0, 100*i+1) +"-\n"+displayTitlePlaces.substring(100*i+1,lengtPlaceFromEvent);
                
            }
        }
        
        displayTitlePlaces = displayTitlePlaces.join(", ", uniques);
        labelDetail.setText(    "Event: "+selectedItem +"\n\n"+
                                "Places: "+displayTitlePlaces+"\n\n"+
                                "Duration: "+duration+"\n\n"+
                                "Predecessor: "+predecessor+"\n\n"+
                                "Part Of: "+partOf+"\n\n"+
                                "Start Date: "+startDate+"\n\n"
            );
        //=========================text====================================

        //~~~~~~~~~~~~~~~~~~timeline~~~~~~~~~~~~~~~~~~~
        // NumberAxis xAxis = new NumberAxis(0, 90, 10);
        // NumberAxis yAxis = new NumberAxis(20, 90, 10);
        // chartEvent = new ScatterChart(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        
        for (Verses verse : verses) {
            places = verse.getPlaces();
            yearNum = verse.getYearNum();

            if(places!=null) {
                int placeCount = verse.getPlacesCount();
                String[] arrPlaces = places.split(",");
                if(yearNum!=null){
                    int year = Integer.parseInt(yearNum);  
                    for(int i = 0; i < placeCount; i++){
                        series.getData().add(new XYChart.Data(arrPlaces[i],year));
                    }
                    // series.getData().add(new XYChart.Data(place,yearNum)); 
                }else{
                    // int tahun=Integer.parseInt(yearNum);
                    // series.getData().add(new XYChart.Data("place",0));
                    for(int i = 0; i < placeCount; i++){
                        series.getData().add(new XYChart.Data(arrPlaces[i],0));
                    }
                }
            }
            // series.getData().add(new XYChart.Data(place,tahun));
            
        }

        chartEvent.getData().addAll(series);

        //PIE CHART
        Map<String, Integer> counts = new HashMap<String, Integer>(); 
 
        for (String ft : arrFeatureType) { 
            if (counts.containsKey(ft)) { 
                counts.put(ft, counts.get(ft) + 1); 
            } else { 
                counts.put(ft, 1); 
            } 
        } 
        
        ArrayList<String> arrNameFt = new ArrayList<String>();
        ArrayList<Integer> arrCountFt = new ArrayList<Integer>();
        
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            // System.out.println(entry.getKey()+" "+entry.getValue());
            arrNameFt.add(entry.getKey());
            arrCountFt.add(entry.getValue());
        }
        
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        for(int i=0;i<arrNameFt.size(); i++){
            list.add(new PieChart.Data(arrNameFt.get(i),arrCountFt.get(i)));
            }
        // System.out.println(list);
        
        eventPieChart.setData(list);
        
        //DETAIL PIE CHART

        // detailPieChart.setText(pair.toString());
    }

    @FXML
    void backBtn(MouseEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("EventsPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        verses.clear();
        selectedItem = null;
        selectedItemVerses.clear();
    }

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
        // String selectedVerses = tableEventVerse.getSelectionModel().getSelectedItem().toString();
        // String queryText = "SELECT title,startDate,duration,predecessor,partOf,'places (from verses)'  FROM event where lower(osisRef)='"+selectedVerses.toLowerCase()+"'";
        // events.addAll(Database.instance.getAllVerses(queryText));
    }
}
