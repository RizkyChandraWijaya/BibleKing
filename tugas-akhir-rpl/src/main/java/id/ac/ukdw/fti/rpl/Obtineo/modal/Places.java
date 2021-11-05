package id.ac.ukdw.fti.rpl.Obtineo.modal;

public class Places {
    private String placeTitle;
    private int verseId;
    private String verses;

    public void setPlaceTitle(String placeTitle) {
       this.placeTitle = placeTitle;
    }

    public void setVerses(String verses) {
        this.verses = verses;
     }

    public void setVerseId(int verseId) {
        this.verseId = verseId;
    }

    public int getVerseId() {
        return this.verseId;
    }

    public String getPlaceTitle() {
        return this.placeTitle;
    }

    public String getVerses() {
        return this.verses;
    }
}
