package id.ac.ukdw.fti.rpl.Obtineo.modal;

public class Events {
    private String eventTitle;
    private String verses;
    private String startDate;
    private String duration;
    private String partOf;
    private String placesVerses;
    private String predecessor;
 
     public void setStartDate(String startDate) {
         this.startDate = startDate;
      }

      public void setPredecessor(String predecessor) {
        this.predecessor = predecessor;
     }
      public void setDuration(String duration) {
        this.duration = duration;
     }
 
     public void setPlacesVerses(String placesVerses) {
         this.placesVerses = placesVerses;
      }

      public void setPartOf(String partOf) {
        this.partOf = partOf;
     }

    public void setEventTitle(String eventTitle) {
       this.eventTitle = eventTitle;
    }

    public void setVerses(String verses) {
        this.verses = verses;
     }

    public String getPartOf() {
        return this.partOf;
    }

    public String getPredecessor() {
        return this.predecessor;
    }

     public String getPlacesVerses() {
        return this.placesVerses;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getEventTitle() {
        return this.eventTitle;
    }

    public String getVerses() {
        return this.verses;
    }
}