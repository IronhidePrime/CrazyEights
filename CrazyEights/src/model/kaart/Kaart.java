package model.kaart;

import model.kaart.Kleur;

/**
 * kaart heeft verschillende eigenschappen
 * de imageStrings dienen om een juiste afbeelding voor deze kaart te kunnen tonen (zowel horizontaal als verticaal)
 */
public class Kaart {
    private int waarde;
    private boolean isBeeldKant;
    private Kleur kleur;
    private String horizontaleImageString;
    private String verticaleImageString;

    public Kaart(int waarde,Kleur kleur, String HorizontaleImageString, String VerticaleImageString){
        this.waarde = waarde;
        this.kleur = kleur;
        this.horizontaleImageString = HorizontaleImageString;
        this.verticaleImageString = VerticaleImageString;
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
}
