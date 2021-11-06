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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

public class DetailEventController implements Initializable {
    private Stage stage;
    private Scene scene;
    private ObservableList<Verses> verses = FXCollections.observableArrayList();
    private String selectedItem = new String(); 
    private List<String> selectedItemVerses = new ArrayList<String>();  

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
    private Tab graphEventDesc;

    @FXML
    private ImageView btnBackEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedItem = EventController.getSelectedItem();
        selectedItemVerses = EventController.getSelectedItemVerses();
        for (String ayat:selectedItemVerses) {
            String query = "SELECT osisRef, verseId, verseText  FROM verses where lower(osisRef)='"+ayat.toLowerCase()+"'";
            verses.addAll(Database.instance.getAllVerses(query));
        }
        tableEventVerse.setPlaceholder(new Label("Tidak ada ayat yang ditemukan pada event "+selectedItem));
        eventTitle.setText(selectedItem);
        columnEventVerse.setCellValueFactory(new PropertyValueFactory<Verses, String>("verse"));
        columnEventVerseIsi.setCellValueFactory(new PropertyValueFactory<Verses, String>("verseText"));
        tableEventVerse.setItems(verses);
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
}
