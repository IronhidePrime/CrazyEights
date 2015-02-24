package controller;

import model.Kaart;
import model.Kleur;
import model.Spelbord;
import model.Speler;

import java.util.LinkedList;
import java.util.List;

public class Controller {
    /**
     * Constanten die gebruikt worden
     */
    private static final int KAARTEN_2_SPELERS = 7;
    private static final int KAARTEN_MEER_SPELERS = 5;

    private Spelbord spelbord;
    private List<Speler> spelers;

    public Controller() {
        this.spelbord = new Spelbord();
        this.spelers = new LinkedList<>();
    }

    /**
     * maken speler
     * spelersnaam opvragen
     * de speler zijn kaarten opvragen
     * aantal spelers opvragen
     */
    public void maakSpeler (String naam) {
        spelers.add(new Speler(naam));
    }

    public String getSpelerNaam(int spelerNr){
        return spelers.get(spelerNr).getNaam();
    }

    public List<Kaart> getSpelerKaarten (int spelerNr) {
        return spelers.get(spelerNr).getKaarten();
    }

    public int getAantalSpelers() {
        return spelers.size();
    }

    public Spelbord getSpelbord() {
        return spelbord;
    }

    /**
     * 2 spelers? -> 7 kaarten
     * 3 of 4 spelers? -> 5 kaarten
     */
    public void kaartenUitdelen() {
        int aantalKaarten;
        if (spelers.size() == 2) {
            aantalKaarten = KAARTEN_2_SPELERS;
        } else {
            aantalKaarten = KAARTEN_MEER_SPELERS;
        }

        for (int i = 0; i < spelers.size(); i++) {
            for (int j = 0; j < aantalKaarten; j++) {
                spelers.get(i).voegKaartToe(spelbord.getTrekstapel().neemKaart());
            }
        }
    }

    /**
     * START SPEL
     *  1. kaarten uitdelen
     *  2. bovenste kaart van de trekstapel omdraaien op de aflegstapel
     *  3. speler 1 aan beurt
     */
    public void startSpel() {
        //1
        kaartenUitdelen();

        //2
        beginKaart();

        //3
        spelers.get(0).setAanBeurt(true);
    }
    public void beginKaart() {
        Kaart beginkaart = spelbord.getTrekstapel().neemKaart();
        beginkaart.setBeeldKant(true);
        spelbord.getAflegstapel().legKaart(beginkaart);
    }

    /**
     * methoden die kunnen voorkomen waneer een speler aan beurt is
     */

    public void speelKaart(Kaart kaart,int spelerNr){
        spelers.get(spelerNr).speelKaart(kaart);
        spelbord.getAflegstapel().legKaart(kaart);
    }

    public boolean speelKaartMogelijk(Kaart kaart){
        Kaart aflegkaart = spelbord.getAflegstapel().getBovensteKaart();
        if (kaart.getKleur() == aflegkaart.getKleur()|| kaart.getWaarde() == aflegkaart.getWaarde() || kaart.getWaarde() == 8){
            return true;
        } else {
            return false;
        }
    }
}


