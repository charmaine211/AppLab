package applab.veiligthuis.model;

import java.time.LocalDateTime;

public class Melding {

    private String key;

    private String uid;
    private String plaatsnaam;
    private boolean beroepsmatig;
    private String beschrijving;
    private String datum;
    private String typeGeweld = null;

    public Melding(){
        // Lege constructor nodig voor calls naar Datasnapshot.getValue(Melding.class).
    }

    public Melding(String plaatsnaam, boolean beroepsmatig, String beschrijving, String datum){
        this.plaatsnaam = plaatsnaam;
        this.beroepsmatig = beroepsmatig;
        this.beschrijving = beschrijving;
        this.datum = datum;
    }

    public Melding(String plaatsnaam, String beschrijving, String datum){
        this.plaatsnaam = plaatsnaam;
        this.beschrijving = beschrijving;
        this.datum = datum;
        this.beroepsmatig = false;
    }

    public Melding(String plaatsnaam, String beschrijving, String datum, String uid){
        this.plaatsnaam = plaatsnaam;
        this.beschrijving = beschrijving;
        this.datum = datum;
        this.beroepsmatig = false;
        this.uid = uid;
    }

    public void setKey(String key){

        this.key = key;
    }

    public String getKey(){

        return key;
    }

    public String getUid(){

        return uid;
    }

    public String getPlaatsnaam(){

        return plaatsnaam;
    }

    public void setPlaatsnaam(String plaatsnaam){

        this.plaatsnaam = plaatsnaam;
    }

    public boolean getBeroepsmatig(){

        return beroepsmatig;
    }

    public void setBeroepsmatig(boolean beroepsmatig){

        this.beroepsmatig = beroepsmatig;
    }

    public String getBeschrijving(){

        return beschrijving;
    }

    public void setBeschrijving(String beschrijving){

        this.beschrijving = beschrijving;
    }

    public String getDatum(){
        return datum;
    }

    public String getTypeGeweld(){
        if (this.typeGeweld == null) {
            return "Ongecatoriseerd";
        }
        return typeGeweld;
    }

    public void setTypeGeweld(String typeGeweld){
        this.typeGeweld = typeGeweld;
    }

    public String toString() {
        return "Melding: " +
                "key = " + key +
                ", gebruiker = " + uid +
                ", plaatsnaam = " + plaatsnaam +
                ", beroepsmatig = " + beroepsmatig +
                ", beschrijving = " + beschrijving +
                ", datum = " + datum +
                ", typeGeweld = " + typeGeweld;
    }
}
