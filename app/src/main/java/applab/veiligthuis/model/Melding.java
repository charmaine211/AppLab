package applab.veiligthuis.model;

import java.util.Date;

public class Melding {
    private int id;
    private boolean anomiem;
    private String meldingInfo;
    private String plaatsNaam;
    private Date datum;
    private String typeGeweld;

    public Melding(){
        // Lege constructor nodig voor calls naar Datasnapshot.getValue(Melding.class).
    }

    public Melding(int id, boolean anomiem, String meldingInfo){
        this.id = id;
        this.anomiem = anomiem;
        this.meldingInfo = meldingInfo;
    }

    public int getId(){
        return id;
    }

    public boolean isAnomiem(){
        return anomiem;
    }

    public String getMeldingInfo(){
        return meldingInfo;
    }

    public String toString(){
        return "id: " + id + ", meldingInfo: " + meldingInfo;
    }
}