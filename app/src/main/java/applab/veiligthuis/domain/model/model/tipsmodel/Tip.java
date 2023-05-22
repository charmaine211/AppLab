package applab.veiligthuis.domain.model.model.tipsmodel;

public class Tip {
    private String id;
    private String titel;
    private String beschrijving;
    private TipCategorie categorie;
    private boolean verwijderd = false;

    public Tip(){

    }

    public Tip(String id, String titel, String beschrijving, TipCategorie categorie){
        this.id = id;
        this.titel = titel;
        this.beschrijving = beschrijving;
        this.categorie = categorie;
    }

    public boolean isVerwijderd(){
        return verwijderd;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setBeschrijving(String beschrijving){
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
    public void setCategorie(TipCategorie categorie){
        this.categorie = categorie;
    }
    public String getTitel(){
        return titel;
    }
    public void setTitel(String titel){
        this.titel = titel;
    }
}
