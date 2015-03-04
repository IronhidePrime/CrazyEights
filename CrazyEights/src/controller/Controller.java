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
     * alle spelers opvragen
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

    public List<Speler> getSpelers() {
        return spelers;
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
        for (int i = 0; i < spelers.size(); i++) {
            for (int j = 0; j < getAantalKaartenSpeler(); j++) {
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
        getSpelers().get(0).setAanBeurt(true);
    }

    /**
     * spel is ten einde wanneer een van de spelers erin slaagt om al zijn kaarten te hebben afgelegd
     */
    public boolean eindeSpel() {
        for (Speler speler : spelers) {
            if (speler.getKaarten().size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * kaart van de aflegstapel waarop men de eerste kaart moet leggen wanneer het spel begint
     */
    public void beginKaart() {
        Kaart beginkaart = spelbord.getTrekstapel().neemKaart();
        beginkaart.setBeeldKant(true);
        spelbord.getAflegstapel().legKaart(beginkaart);
    }

    /**
     * speler speelt zijn kaart -> kaart wordt verwijderd bij speler
     * deze kaart komt op de aflegstapel -> op deze kaart wordt voortgespeeld
     */
    public void speelKaart(Kaart kaart, int spelerNr){
        spelers.get(spelerNr).speelKaart(kaart);
        spelbord.getAflegstapel().legKaart(kaart);
    }

    /**
     * nagaan of men een kaart kan spelen
     * voor een kaart te kunnen spelen moet waarde of kleur gelijk zijn, men kan ook de 8 spelen (zie spelregels)
     */
    public boolean speelKaartMogelijk(Kaart kaart){
        Kaart aflegkaart = spelbord.getAflegstapel().getBovensteKaart();
        if (kaart.getKleur() == aflegkaart.getKleur()|| kaart.getWaarde() == aflegkaart.getWaarde() || kaart.getWaarde() == 8){
            return true;
        } else {
            return false;
        }
    }

    /* BRAINSTORM OVER BEURT -> speelspel zeker fout

    public int getSpelerAanBeurt() {
        int spelerNr=0;
        int i=0;
        for (Speler s : spelers) {
            if (s.getAanBeurt()) {
                spelerNr = i;
            }
            i++;
        }
        return spelerNr;
    }

    public void setSpelerAanBeurt (int spelerNr) {
        getSpelers().get(spelerNr).setAanBeurt(true);
    }


    public void speelSpel() {
        while (!eindeSpel()) {
            if (getSpelerAanBeurt() == getSpelers().size()) {
                setSpelerAanBeurt(0);
            } else {
                setSpelerAanBeurt(getSpelerAanBeurt()+1);
            }
        }
    }*/

    public int getAantalKaartenSpeler(){
        if(getAantalSpelers()==2){
            return KAARTEN_2_SPELERS;
        } else {
            return KAARTEN_MEER_SPELERS;
        }
    }

    public boolean checkBeurt(int spelerNr){
        return getSpelers().get(spelerNr).getAanBeurt();
    }


}



