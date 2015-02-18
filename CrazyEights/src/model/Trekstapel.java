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
        for(int i=0; i<4;i++){
            for(int j=1; j<=13;j++){
                kaarten.add(new Kaart(j,Kleur.values()[i]));
            }
        }
        schudStapel();
    }

    /**
     * Stapel schudden voor het spel te starten
     */
    private void schudStapel(){
        Collections.shuffle(kaarten);
    }

    /**
     * Kaart nemen van de stapel en deze 'verwijderen' uit de stapel
     */
    public Kaart neemKaart() {
        Kaart kaart = kaarten.get(0);
        kaarten.remove(kaart);
        return kaart;
    }

    /**
     * TEST METHODE
     */
    public List<Kaart> getKaarten() {
        return kaarten;
    }
}
