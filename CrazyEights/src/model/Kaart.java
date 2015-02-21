package model;

public class Kaart {
    private int waarde;
    private boolean isBeeldKant;
    private Kleur kleur;
    private String imageString;

    public Kaart(int waarde,Kleur kleur, String imageString){
        this.waarde = waarde;
        this.kleur = kleur;
        this.imageString = imageString;
        this.isBeeldKant = false;
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

    public String getImageString() {
        return imageString;
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
