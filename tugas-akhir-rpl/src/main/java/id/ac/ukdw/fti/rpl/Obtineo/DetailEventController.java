package id.ac.ukdw.fti.rpl.Obtineo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import id.ac.ukdw.fti.rpl.Obtineo.database.Database;
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
import javafx.scene.chart.XYChart;

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
    private Tab graphEventDesc;

    @FXML
    private TextArea detailText;

    @FXML
    private ScatterChart<String,Number> chartEvent;

    @FXML
    private ImageView btnBackEvent;

    @FXML
    private AnchorPane text;

    private Stage stage;
    private Scene scene;
    private ObservableList<Verses> verses = FXCollections.observableArrayList();
    private ObservableList<Verses> events = FXCollections.observableArrayList();
    private String selectedItem = new String(); 
    private List<String> selectedItemVerses = new ArrayList<String>();
    private String osisRef;
    private String verseText;
    private String yearNum;
    private String places;
    private String placesCount;
    private String timeline;
      

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
        text.getChildren().clear();
        String selectedVerses = tableEventVerse.getSelectionModel().getSelectedItem().toString();
        // String queryText = "SELECT title,startDate,duration,predecessor,partOf,'places (from verses)'  FROM event where lower(osisRef)='"+selectedVerses.toLowerCase()+"'";
        // events.addAll(Database.instance.getAllVerses(queryText));

        Label labelVerses = new Label(selectedVerses);
        
        text.getChildren().add(labelVerses);
        

    }
}
