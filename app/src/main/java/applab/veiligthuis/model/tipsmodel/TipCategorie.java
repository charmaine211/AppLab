package applab.veiligthuis.model.tipsmodel;

public enum TipCategorie {
    LichamelijkGeweld("Lichamelijk geweld"),
    FysiekGeweld("Fysiek geweld"),
    FinancieelMisbruik("Financieel misbruik"),
    Stalking("Stalking"),
    PsychischGeweld("Psychisch geweld");

    private final String userfriendlyName;

    TipCategorie(String userfriendlyName) {
        this.userfriendlyName = userfriendlyName;
    }

    public String toUserfriendlyname() {
        return userfriendlyName;
    }

    public static TipCategorie parseString(String value) {
        switch (value) {
            case "Lichamelijk geweld":
                return TipCategorie.LichamelijkGeweld;
            case "Fysiek geweld":
                return TipCategorie.FysiekGeweld;
            case "Financieel misbruik":
                return TipCategorie.FinancieelMisbruik;
            case "Stalking":
                return TipCategorie.Stalking;
            case "Psychisch geweld":
                return TipCategorie.PsychischGeweld;
            default:
                throw new IllegalArgumentException("Invalid value for TipCategorie: " + value);
        }
    }

}
