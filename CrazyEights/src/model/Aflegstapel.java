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

    public void legKaart(Kaart kaart){
        kaarten.add(kaart);
    }
}
