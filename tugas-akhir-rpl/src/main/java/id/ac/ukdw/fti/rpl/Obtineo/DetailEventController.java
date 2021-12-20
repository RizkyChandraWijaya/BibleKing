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
import id.ac.ukdw.fti.rpl.Obtineo.modal.People;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Places;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Verses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
    private String displayPeople = "";
    private String displayFeature = "";
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
        Set<String> uniques = new HashSet<String>();
        Set<String> uniquesPeople = new HashSet<String>();

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
        String[] uniquePlaces = uniques.toArray(new String[uniques.size()]);

        for(int m = 0; m<uniquePlaces.length;m++){
            if(uniquePlaces[m] == uniquePlaces[uniquePlaces.length-1]){
                displayTitlePlaces += uniquePlaces[m];   
            }else{
                displayTitlePlaces += uniquePlaces[m] + ", ";    
            }
        }
        int lengtPlaceFromEvent = displayTitlePlaces.length();
        if(lengtPlaceFromEvent>100){
            for (int i=1;i<=Math.floor(lengtPlaceFromEvent/100);i++){
                
                displayTitlePlaces = displayTitlePlaces.substring(0, 100*i+1) +"-\n"+displayTitlePlaces.substring(100*i+1,lengtPlaceFromEvent);
                
            }
        }
        labelDetail.setText(    "Event: "+selectedItem +"\n\n"+
                                "Places: "+displayTitlePlaces+"\n\n"+
                                "Duration: "+duration+"\n\n"+
                                "Predecessor: "+predecessor+"\n\n"+
                                "Part Of: "+partOf+"\n\n"+
                                "Start Date: "+startDate+"\n\n"
            );

        //~~~~~~~~~~~~~~~~~~timeline~~~~~~~~~~~~~~~~~~~
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
                }
            }
        }
        timeline.getData().addAll(series);
        //=============================BAR GENDER=======================================
        
        Integer countFemale= Collections.frequency(arrGender, "Female");
        Integer countMale= Collections.frequency(arrGender, "Male");
        XYChart.Series dataPeople = new XYChart.Series<>();
        dataPeople.setName(selectedItem);
        dataPeople.getData().add(new XYChart.Data("Female",countFemale));
        dataPeople.getData().add(new XYChart.Data("Male",countMale));
        peopleEventBar.getData().add(dataPeople);

        String[] arrUniquePeople = uniquesPeople.toArray(new String[uniquesPeople.size()]);

        for(int n = 0; n<arrUniquePeople.length;n++){
            if(arrUniquePeople[n]!=null){
                if(arrUniquePeople[n] == arrUniquePeople[arrUniquePeople.length-1]){
                    displayPeople += arrUniquePeople[n];   
                }else{
                    displayPeople += arrUniquePeople[n] + ", ";    
                }
            }
        }
        int popleLength = displayPeople.length();  
        if(popleLength>40){
            for (int i=1;i<=Math.floor(popleLength/40);i++){
                
                displayPeople = displayPeople.substring(0, 40*i+1) +"-\n"+displayPeople.substring(40*i+1,popleLength);
                
            }
        }
        detailBarChart.setText(displayPeople);

        //=============================BAR GENDER=======================================

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
            arrNameFt.add(entry.getKey());
            arrCountFt.add(entry.getValue());
        }
        
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        for(int i=0;i<arrNameFt.size(); i++){
            list.add(new PieChart.Data(arrNameFt.get(i),arrCountFt.get(i)));
            }    
        eventPieChart.setData(list);

        //DETAIL PIE CHART
        String[] arrFeature = uniques.toArray(new String[uniques.size()]);

        for(int n = 0; n<arrFeature.length;n++){
            if(arrFeature[n]!=null){
                if(arrFeature[n] == arrFeature[arrFeature.length-1]){
                    displayFeature += arrFeature[n];   
                }else{
                    displayFeature += arrFeature[n] + ", ";    
                }
            }
        }
        int featureLength = displayFeature.length();  
        if(featureLength>40){
            for (int i=1;i<=Math.floor(featureLength/40);i++){
                displayFeature = displayFeature.substring(0, 40*i+1) +"-\n"+displayFeature.substring(40*i+1,featureLength);
            }
        }
        detailPieChart.setText(displayFeature);
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
    }
}
