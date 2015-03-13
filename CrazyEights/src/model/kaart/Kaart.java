package model.kaart;

/**
 * kaart heeft verschillende eigenschappen
 * de imageStrings dienen om een juiste afbeelding voor deze kaart te kunnen tonen (zowel horizontaal als verticaal)
 */
public class Kaart {
    private int waarde;
    private Kleur kleur;
    private String horizontaleImageString;
    private String verticaleImageString;

    public Kaart(int waarde, Kleur kleur, String horizontaleImageString, String verticaleImageString){
        this.waarde = waarde;
        this.kleur = kleur;
        this.horizontaleImageString = horizontaleImageString;
        this.verticaleImageString = verticaleImageString;
    }

    public int getWaarde() {
        return waarde;
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
