package id.ac.ukdw.fti.rpl.Obtineo.modal;

public class Verses {
    private String verse;
    private String verseText;
    private String yearNum;
    private String places;
    private int placesCount;
    private String timeline;
    private int peopleCount;
    private String people;

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getTimeline() {
        return this.timeline;
    }
    
    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getVerse() {
        return this.verse;
    }

    public void setVerseText(String verseText) {
        this.verseText = verseText;
    }

    public String getVerseText() {
        return this.verseText;
    }

    public void setYearNum(String yearNum) {
        this.yearNum = yearNum;
    }

    public String getYearNum() {
        return this.yearNum;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getPlaces() {
        return this.places;
    }

    public void setPlacesCount(int placesCount) {
        this.placesCount = placesCount;
    }

    public int getPlacesCount() {
        return this.placesCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public int getPeopleCount() {
        return this.peopleCount;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getPeople() {
        return this.people;
    }
}

