package model.spelbord;

import model.kaart.Kaart;

import java.util.LinkedList;
import java.util.List;

/**
 * aflegstapel heeft een lijst van kaarten waarop moet gespeeld worden
 */
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
        return getKaarten().get(kaarten.size()-1);
    }
}
