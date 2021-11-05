package id.ac.ukdw.fti.rpl.Obtineo.modal;

public class Events {
    private String eventTitle;
    private int verseId;
    private String verses;

    public void setEventTitle(String eventTitle) {
       this.eventTitle = eventTitle;
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

    public String getEventTitle() {
        return this.eventTitle;
    }

    public String getVerses() {
        return this.verses;
    }
}

