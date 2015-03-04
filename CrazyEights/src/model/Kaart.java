package model;

public class Kaart {
    private int waarde;
    private boolean isBeeldKant;
    private Kleur kleur;
    private String horizontaleImageString;
    private String verticaleImageString;
    private String omgekeerdeImageString;

    public Kaart(int waarde,Kleur kleur, String HorizontaleImageString, String VerticaleImageString){
        this.waarde = waarde;
        this.kleur = kleur;
        this.horizontaleImageString = HorizontaleImageString;
        this.verticaleImageString = VerticaleImageString;
        this.omgekeerdeImageString = "/view/images/kaartAchterkant.jpg";
    }

    public int getWaarde() {
        return waarde;
    }

    public void setBeeldKant(boolean isBeeldKant) {
        this.isBeeldKant = isBeeldKant;
    }

    public Kleur getKleur() {
        return kleur;
    }

    public String getHorizontaleImageString() {
        return horizontaleImageString;
    }

    public String getVerticaleImageString() {
        return verticaleImageString;
    }

    public String getOmgekeerdeImageString() {
        return omgekeerdeImageString;
    }

    @Override
    public String toString() {
        return "Kaart{" +
                "waarde=" + waarde +
                ", isBeeldKant=" + isBeeldKant +
                ", kleur=" + kleur +
                '}';
    }
}
