package id.ac.ukdw.fti.rpl.Obtineo.modal;

public class Places {
    private String displayTitle;
    private String placeLookup;
    private String verses;
    private String featureType;
    private int verseCount;

    public void setDisplayTitle(String displayTitle) {
       this.displayTitle = displayTitle;
    }

    public void setVerseCount(int verseCount) {
        this.verseCount = verseCount;
     }

    public void setVerses(String verses) {
        this.verses = verses;
     }

    public void setPlaceLookup(String placeLookup) {
        this.placeLookup = placeLookup;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getVerseId() {
        return this.placeLookup;
    }

    public String getDisplayTitle() {
        return this.displayTitle;
    }

    public int getVerseCoun() {
        return this.verseCount;
    }

    public String getVerses() {
        return this.verses;
    }

    public String getFeatureType(){
        return this.featureType;
    }


}
