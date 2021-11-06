package id.ac.ukdw.fti.rpl.Obtineo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Events;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Places;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Verses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
    final String url = "jdbc:sqlite:vizbible.db";
    private ObservableList<Verses> verses = FXCollections.observableArrayList();
    private ObservableList<Events> events = FXCollections.observableArrayList();
    private ObservableList<Places> places = FXCollections.observableArrayList();
    private Connection connection = null;
    public static Database instance = new Database();

    //inisialisasi class Database dan koneksinya
    public Database() {
        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //menjalankan query yang dipanggil di class controller lalu set verses di class Verses
    public ObservableList<Verses> getAllVerses(String query) {
        verses.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Verses verse = new Verses();
                verse.setVerseId(result.getInt("verseId"));
                verse.setVerse(result.getString("osisRef"));
                verse.setVerseText(result.getString("verseText"));
                verses.add(verse);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return verses;
    }

    //menjalankan query yang dipanggil di class controller lalu set event di class Events  
    public ObservableList<Events> getAllEvents(String query) {
        events.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Events event = new Events();
                event.setEventTitle(result.getString("title"));
                event.setVerses(result.getString("verses"));
                events.add(event);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return events;
    }

    //menjalankan query yang dipanggil di class controller lalu set places di class Places
    public ObservableList<Places> getPlacesgetAllPlaces(String query) {
        try {
            places.clear();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Places place = new Places();
                place.setDisplayTitle(result.getString("displayTitle"));
                place.setVerses(result.getString("verses"));
                places.add(place);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return places;
    }
}