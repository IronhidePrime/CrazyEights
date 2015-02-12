package model;

public class Kaart {
    private int waarde;
    private boolean isBeeldKant;
    private Kleur kleur;

    public Kaart(int waarde,Kleur kleur){
        this.waarde = waarde;
        this.kleur = kleur;
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


    @Override
    public String toString() {
        return "Kaart{" +
                "waarde=" + waarde +
                ", isBeeldKant=" + isBeeldKant +
                ", kleur=" + kleur +
                '}';
    }
}
