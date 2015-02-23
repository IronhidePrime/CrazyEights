package model;

import java.util.LinkedList;
import java.util.List;

public class Aflegstapel {
    private List<Kaart> kaarten;

    public Aflegstapel() {
        kaarten = new LinkedList<>();
    }

    public List<Kaart> getKaarten() {
        return kaarten;
    }

    /**
     * kaart op de aflegstapel leggen
     */
    public void legKaart(Kaart kaart){
        kaarten.add(kaart);
    }

    /**
     * kaart die als laatste werd toegevoegd (kaart ligt vanboven en op deze kaart wordt voortgespeeld) opvragen
     */
    public Kaart getBovensteKaart() {
        return kaarten.get(kaarten.size()-1);
    }
}
