package id.ac.ukdw.fti.rpl.Obtineo.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import id.ac.ukdw.fti.rpl.Obtineo.modal.Events;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Places;
import id.ac.ukdw.fti.rpl.Obtineo.modal.Verse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Database {
    
    
    final String url = "jdbc:sqlite:vizbible.db";
    private ObservableList<Events> events = FXCollections.observableArrayList();
    private ObservableList<Places> places = FXCollections.observableArrayList();
    private Connection connection = null;
    public static Database instance = new Database();

    public Database() {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Berhasil");
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Database gagal");
            System.out.println(e.getMessage());
        }
    }

    public Connection openConnection() {
        return connection;
    }

    public ObservableList<Events> getAllEvents(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Events event = new Events();
                event.setEventTitle(result.getString("title"));
                event.setVerses(result.getString("verses"));
                // event.setVerseId(result.getInt("verseSort"));
                events.add(event);
            }
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.getMessage());
        }
        return events;
    }
    
    public ObservableList<Places> getPlacesgetAllPlaces(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Places place = new Places();
                place.setPlaceTitle(result.getString("title"));
                place.setVerses(result.getString("verses"));
                // event.setVerseId(result.getInt("verseSort"));
                places.add(place);
            }
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.getMessage());
        }
        return places;
    }
}



    // final private String url = "jdbc:sqlite:vizbible.sqlite";
    
    // // public static Database instance = new Database();
    // private void connect(){
    //     Connection conn = null;
    //     try{
    //         conn = DriverManager.getConnection(url);
    //         System.out.println("KONEK");
    //     }
    //     catch (Exception e){
    //         System.out.println(e.getMessage());
    //     }
    // }

    // public ArrayList tampil(String title) {
    // String querySelect = "SELECT verses,verseCount from places WHERE displayTitle = '"+title+"'";
    
    //     ArrayList<Verse>hasil = new ArrayList<Verse>();
    //     try(
    //         Connection connection = DriverManager.getConnection(url);
    //         Statement statement = connection.createStatement();
    //         ResultSet rs = statement.executeQuery(querySelect)){
    //         while (rs.next()) {
                
    //             // verse.setVerseId(result.getInt("verseId"));
    //             // verse.setVerse(result.getString("osisRef"));
    //             hasil.add(new Verse(rs.getString("verses"),rs.getInt("verseCount")));
    //             System.out.println(rs.getString("verses"));
    //             return hasil;
    //         }
    //         return hasil;
    //     }
    //         catch (Exception e){
    //             System.out.println(e.getMessage());
    //         }
    //     return hasil;
        
    
    // public Database(){
    //     try {
    //         connection = DriverManager.getConnection(url);
    //     }catch (Exception e){
    //         System.out.println(e.getMessage());
    //     }
    // }
    // public ObservableList<Verse> getAllVerse(){
    //     ObservableList<Verse> verses = FXCollections.observableArrayList();
    //     try {
    //         Statement statement = connection.createStatement();
    //         ResultSet result = statement.executeQuery(querySelect);
    //         while (result.next()) {
    //             Verse verse = new Verse();
    //             verse.setVerseId(result.getInt("verseId"));
    //             verse.setVerse(result.getString("osisRef"));
    //             verse.setVerseText(result.getString("verseText"));
    //             verses.add(verse);
                // System.out.println(result.getInt("verseId"));
                // System.out.println(result.getString("osisRef"));
                // System.out.println(result.getString("verseText"));


    //         }
    //     } catch (Exception e) {
    //         //TODO: handle exception
    //     }
    //     return null;
    // }
        // }

//         public String lihatAyat(String ayat){
//             String query = "select verseText from verses where osisRef = '" + ayat + "'";
//             try{
//                 Connection conn = DriverManager.getConnection(url);
//                 Statement statement = conn.createStatement();
//                 ResultSet rs = statement.executeQuery(query);

//                 while(rs.next()) {
//                     return rs.getString("verseText");
//                 }
//                 return rs.getString("verseText");
//             }catch (Exception e){
//                 return e.getMessage();
//             }
//         }
// }
