package model;

public class Spelbord {
    private Aflegstapel aflegstapel;
    private Trekstapel trekstapel;
    private Speler[] spelers;

    //overige attributen
    private int aantalSpelers;

    public Spelbord() {
        aflegstapel = new Aflegstapel();
        trekstapel = new Trekstapel();

        //test voor 2 spelers (7 kaarten)
        spelers = new Speler[aantalSpelers];
    }

    public void setAantalSpelers(int aantalSpelers) {
        this.aantalSpelers = aantalSpelers;
    }

    public void kaartenUitdelen() {
        int aantalKaarten;
        if (spelers.length == 1) {
            aantalKaarten = 7;
        } else {
            aantalKaarten = 5;
        }

        for (int i = 0; i < spelers.length; i++) {
            for (int j = 0; j < aantalKaarten; j++) {
                spelers[i].trekKaart(trekstapel.getKaarten().get(j));
                trekstapel.getKaarten().remove(j);
            }
        }
    }


    public void beginSpel() {
        //bovenste kaart van de trekstapel omdraaien op de aflegstapel
        Kaart beginkaart = trekstapel.getKaarten().get(0);
        trekstapel.getKaarten().remove(beginkaart);
        beginkaart.setBeeldKant(true);
        aflegstapel.legKaart(beginkaart);

        //eerste speler aan de beurt bij het begin van het spel
        spelers[0].setAanBeurt(true);
    }

    //TODO!!
    public void speelSpel() {
        Kaart aflegKaart = aflegstapel.getKaarten().get(aflegstapel.getKaarten().size() - 1);
        for (int i = 0; i < spelers.length; i++) {
            if (spelers[i].getAanBeurt()) {
                for (int j = 0; j < spelers[i].getKaarten().size(); j++) {
                    Kaart spelerKaart = spelers[i].getKaarten().get(j);
                    if (aflegKaart.getWaarde() == spelerKaart.getWaarde()) {
                        spelers[i].speelKaart(spelerKaart);
                    }
                }
            }
        }
    }


    public Aflegstapel getAflegstapel() {
        return aflegstapel;
    }

    public Trekstapel getTrekstapel() {
        return trekstapel;
    }
}
