package controller;

import model.Kaart;
import model.Spelbord;

public class Controller {
    private Spelbord spelbord;

    public Controller() {
        this.spelbord = new Spelbord();
    }

    public int getAantalSpelers() {
        return spelbord.getAantalSpelers();
    }

    public void maakSpeler (String naam) {
        spelbord.voegSpelerToe(naam);
    }

    public String getSpelerNaam(int spelerNr){
        return spelbord.getSpelers().get(spelerNr).getNaam();
    }

    /**
     * START SPEL
     *  1. kaarten uitdelen
     *  2. bovenste kaart van de trekstapel omdraaien op de aflegstapel
     *  3. speler 1 aan beurt
     */
    public void startSpel() {
        //1
        spelbord.kaartenUitdelen();

        //2
        Kaart beginkaart = spelbord.getTrekstapel().neemKaart();
        beginkaart.setBeeldKant(true);
        spelbord.getAflegstapel().legKaart(beginkaart);

        //3
        spelbord.getSpelers().get(0).setAanBeurt(true);
    }
}

