package model;

import java.util.LinkedList;
import java.util.List;

public class Spelbord {
    //constanten
    private static final int KAARTEN_2_SPELERS = 7;
    private static final int KAARTEN_MEER_SPELERS = 5;

    private Aflegstapel aflegstapel;
    private Trekstapel trekstapel;
    private List<Speler> spelers;


    public Spelbord() {
        aflegstapel = new Aflegstapel();
        trekstapel = new Trekstapel();

        spelers = new LinkedList<>();
    }

    /**
     * speler toevoegen en getter voor het aantal spelers
     */
    public int getAantalSpelers() {
        return spelers.size();
    }

    public void voegSpelerToe(String naam) {
        spelers.add(new Speler(naam));
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
                spelers.get(i).voegKaartToe(trekstapel.neemKaart());
            }
        }
    }

    public Trekstapel getTrekstapel() {
        return trekstapel;
    }

    public Aflegstapel getAflegstapel() {
        return aflegstapel;
    }

    public List<Speler> getSpelers() {
        return spelers;
    }
}
