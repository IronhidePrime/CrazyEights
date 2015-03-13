package model.speler;

import model.kaart.Kaart;

import java.util.LinkedList;
import java.util.List;

/**
 * speler heeft een naam, lijst van kaarten en een eigenschap voor te kijken of de betreffende speler aan de beurt is
 */
public class Speler {
    protected String naam;
    protected List<Kaart> kaarten;
    protected boolean isAanBeurt;

    public Speler(String naam) {
        this.naam = naam;
        kaarten = new LinkedList<>();
    }

    public void setAanBeurt(boolean isAanBeurt) {
        this.isAanBeurt = isAanBeurt;
    }

    public boolean getAanBeurt() {
        return isAanBeurt;
    }

    public String getNaam() {
        return naam;
    }

    public void speelKaart(Kaart kaart) {
        kaarten.remove(kaart);
    }

    public void voegKaartToe(Kaart kaart) {
        kaarten.add(kaart);
    }

    public List<Kaart> getKaarten() {
        return kaarten;
    }
}
