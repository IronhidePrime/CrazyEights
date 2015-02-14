package controller;

import model.Spelbord;


public class CrazyEightsController {
    private Spelbord spelbord;

    public CrazyEightsController() {
        this.spelbord = new Spelbord();
    }

    public void zetAantalSpelersSpelbord(int aantalSpelers){
        spelbord.setAantalSpelers(aantalSpelers);
    }

    public int getAantalSpelersSpelbord(){
        return spelbord.getAantalSpelers();
    }
}
