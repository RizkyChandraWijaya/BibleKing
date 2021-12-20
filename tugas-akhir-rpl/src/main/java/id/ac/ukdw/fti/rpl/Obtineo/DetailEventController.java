package id.ac.ukdw.fti.rpl.Obtineo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import id.ac.ukdw.fti.rpl.Obtineo.database.Database;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Events;
import id.ac.ukdw.fti.rpl.Obtineo.modal.People;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;

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
    private Tab peopleEvent;

    @FXML
    private BarChart peopleEventBar;

    @FXML
    private Label detailBarChart;


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

    @FXML
    private LineChart<String, Number> timeline;

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
    private String eventPeople;
    private ArrayList<String> arrFeatureType = new ArrayList<String>();
    private ArrayList<String> arrGender = new ArrayList<String>();
    private ObservableList<People> peopleFromEvent = FXCollections.observableArrayList();




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //=========================table====================================
        selectedItem = EventController.getSelectedItem();
        selectedItemVerses = EventController.getSelectedItemVerses();
        for (String ayat:selectedItemVerses) {
            String query = "SELECT osisRef,verseText,yearNum,places,placesCount,timeline,people FROM verses where lower(osisRef)='"+ayat.toLowerCase()+"'";
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
        String queryEvent = "SELECT title,verseSort,verses,startDate,duration,predecessor,partOf,places,peoples FROM events where "+replaceTitle+" like '"+selectedItem.toLowerCase().replace("'", " ")+"'";
        events = Database.instance.getAllEvents(queryEvent);
        
        // String queryEvent = "SELECT title,verseSort,verses,startDate,duration,predecessor,partOf,places FROM events where lower(title) like '"+selectedItem.toLowerCase()+"'";
        // events = Database.instance.getAllEvents(queryEvent);
        Set<String> uniques = new HashSet<String>();
        Set<String> uniquesPeople = new HashSet<String>();

        
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

                //perulangan untuk akses places from event
                for (Places places3:placesFromEvent){
                    if(places3.getDisplayTitle()!=null){
                        if(placesFromEvent.indexOf(places3)==placesFromEvent.size()-1){
                            if(!uniques.contains(places3.getDisplayTitle())){
                                uniques.add(places3.getDisplayTitle());
                                arrFeatureType.add(places3.getFeatureType());
                            }
                        }else{
                            if(!uniques.contains(places3.getDisplayTitle())){
                                uniques.add(places3.getDisplayTitle());
                                arrFeatureType.add(places3.getFeatureType());
                            }
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

            if(events2.getEventPeople()!=null){
                eventPeople = events2.getEventPeople();
                String[] peopleSplit = eventPeople.split(",");
                for (String peopleSplit2 : peopleSplit) {
                    String queryPeople = "SELECT personLookup,name,gender FROM people where lower(personLookUp)='"+peopleSplit2.toLowerCase()+"'";
                    peopleFromEvent.addAll(Database.instance.getAllPeople(queryPeople));
                }

            }else{
                eventPeople = "unknown";
            }

            //perulangan untuk akses people from event
            for (People people2:peopleFromEvent){
                if(people2.getName()!=null){
                    if(peopleFromEvent.indexOf(people2)==peopleFromEvent.size()-1){
                        if(!uniquesPeople.contains(people2.getName())){
                            uniquesPeople.add(people2.getName());
                            arrGender.add(people2.getGender());
                        }
                    }else{
                        if(!uniquesPeople.contains(people2.getName())){
                            uniquesPeople.add(people2.getName());
                            arrGender.add(people2.getGender());
                        }
                    }
                }
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
                System.out.println(arrPlaces.toString());
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

        timeline.getData().addAll(series);


        //=============================BAR GENDER=======================================
        
        // Map<String, Integer> countsBar = new HashMap<String, Integer>(); 
        Integer countFemale= Collections.frequency(arrGender, "Female");
        Integer countMale= Collections.frequency(arrGender, "Male");
        // System.out.println(countFemale+" dan "+countMale);
        // System.out.println(uniquesPeople+" dan "+arrGender);
        XYChart.Series dataPeople = new XYChart.Series<>();
        dataPeople.setName(selectedItem);
        dataPeople.getData().add(new XYChart.Data("Female",countFemale));
        dataPeople.getData().add(new XYChart.Data("Male",countMale));

        peopleEventBar.getData().add(dataPeople);

        detailBarChart.setText(uniquesPeople.toString());

        //=============================BAR GENDER=======================================


        //PIE CHART BERHASIL
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
        System.out.println(list);
        System.out.println(arrFeatureType);
        
        eventPieChart.setData(list);
        
        //DETAIL PIE CHART

        detailPieChart.setText(uniques.toString());

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
