package applab.veiligthuis.model.tipsmodel;

public class Tip {

    private String titel;
    private String beschrijving;
    private TipCategorie categorie;
    private boolean verwijderd = false;

    public Tip(){

    }

    public Tip(String titel, String beschrijving, TipCategorie categorie){
        this.titel = titel;
        this.beschrijving = beschrijving;
        this.categorie = categorie;
    }

    public boolean isVerwijderd(){
        return verwijderd;
    }

    public void pasBeschrijvingAan(String beschrijving){
        this.beschrijving = beschrijving;
    }
    public String getBeschrijving(){
        return beschrijving;
    }
    public void verwijder(){
        verwijderd = true;
    }
    public TipCategorie getCategorie(){
        return categorie;
    }
    public void pasCategorieAan(TipCategorie categorie){
        this.categorie = categorie;
    }
    public String getTitel(){
        return titel;
    }
    public void pasTitelAan(String titel){
        this.titel = titel;
    }
}