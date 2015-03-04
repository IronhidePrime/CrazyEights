package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Trekstapel  {
    /**
     Hierin zitten alle beginkaarten (52), na het uitdelen wordt dit de stapel waarvan kaarten kunnen genomen worden (trekstapel)
    */


    private List<Kaart> kaarten;

    public Trekstapel() {
        kaarten = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 13; j++) {
                switch (i) {
                    case 0:
                        kaarten.add(new Kaart(j, Kleur.values()[i], "/view/images/harten/harten" + j + ".png",
                                "/view/images/harten/verticaal/harten" + j + ".png"));
                        break;
                    case 1:
                        kaarten.add(new Kaart(j, Kleur.values()[i], "/view/images/ruiten/ruiten" + j + ".png",
                                "/view/images/ruiten/verticaal/ruiten" + j + ".png"));
                        break;
                    case 2:
                        kaarten.add(new Kaart(j, Kleur.values()[i], "/view/images/klaveren/klaveren" + j + ".png",
                                "/view/images/klaveren/verticaal/klaveren" + j + ".png"));
                        break;
                    case 3:
                        kaarten.add(new Kaart(j, Kleur.values()[i], "/view/images/schoppen/schoppen" + j + ".png",
                                "/view/images/schoppen/verticaal/schoppen" + j + ".png"));
                        break;
                }
            }
            schudStapel();
        }
    }

        /**
         * Stapel schudden voor het spel te starten
         */

    private void schudStapel() {
        Collections.shuffle(kaarten);
    }

    /**
     * Er wordt een kaart genomen van de stapel en deze wordt 'verwijderd' uit de trekstapel
     */
    public Kaart neemKaart() {
        Kaart kaart = getBovensteKaart();
        kaarten.remove(kaart);
        return kaart;
    }


    public Kaart getBovensteKaart() {
        Kaart kaart = getKaarten().get(kaarten.size()-1);
        return kaart;
    }

    /**
     * TEST METHODE
     */
    public List<Kaart> getKaarten() {
        return kaarten;
    }
}
