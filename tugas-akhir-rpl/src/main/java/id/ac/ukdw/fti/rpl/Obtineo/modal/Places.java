package id.ac.ukdw.fti.rpl.Obtineo.modal;

public class Places {
    private String displayTitle;
    private int placeLookup;
    private String verses;

    public void setDisplayTitle(String displayTitle) {
       this.displayTitle = displayTitle;
    }

    public void setVerses(String verses) {
        this.verses = verses;
     }

    public void setPlaceLookup(int placeLookup) {
        this.placeLookup = placeLookup;
    }

    public int getVerseId() {
        return this.placeLookup;
    }

    public String getDisplayTitle() {
        return this.displayTitle;
    }

    public String getVerses() {
        return this.verses;
    }


}
