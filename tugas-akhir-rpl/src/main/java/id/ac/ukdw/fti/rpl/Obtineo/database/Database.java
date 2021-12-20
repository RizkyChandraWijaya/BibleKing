package id.ac.ukdw.fti.rpl.Obtineo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Events;
import id.ac.ukdw.fti.rpl.Obtineo.modal.People;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Places;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Verses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
    final String url = "jdbc:sqlite:vizbible.db";
    private ObservableList<Verses> verses = FXCollections.observableArrayList();
    private ObservableList<Events> events = FXCollections.observableArrayList();
    private ObservableList<Places> places = FXCollections.observableArrayList();
    private ObservableList<People> peoples = FXCollections.observableArrayList();

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
                verse.setVerse(result.getString("osisRef"));
                verse.setVerseText(result.getString("verseText"));
                verse.setYearNum(result.getString("yearNum"));
                verse.setPlaces(result.getString("places"));
                verse.setPlacesCount(result.getInt("placesCount"));
                verse.setTimeline(result.getString("timeline"));
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
                event.setPlacesVerses(result.getString("places"));
                event.setDuration(result.getString("duration"));
                event.setPredecessor(result.getString("predecessor"));
                event.setPartOf(result.getString("partOf"));
                event.setStartDate(result.getString("startDate"));
                event.setEventPeople(result.getString("peoples"));
                events.add(event);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return events;
    }

    //menjalankan query yang dipanggil di class controller lalu set places di class Places
    public ObservableList<Places> getAllPlaces(String query) {
        try {
            places.clear();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Places place = new Places();
                place.setPlaceLookup(result.getString("placeLookUp"));
                place.setDisplayTitle(result.getString("displayTitle"));
                place.setVerses(result.getString("verses"));
                place.setFeatureType(result.getString("featureType"));
                place.setVerseCount(result.getInt("verseCount"));
                places.add(place);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return places;
    }

    public ObservableList<People> getAllPeople(String query) {
        try {
            peoples.clear();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                People people = new People();
                people.setPersonLookup(result.getString("personLookup"));
                people.setName(result.getString("name"));
                people.setGender(result.getString("gender"));
                peoples.add(people);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return peoples;
    }
}