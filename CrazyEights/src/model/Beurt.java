package model;

public class Beurt {
    private Speler speler;
    private boolean isAanBeurt;

    public Beurt(Speler speler){
        this.speler = speler;
        isAanBeurt = true;
    }

    public void setAanBeurt(boolean isAanBeurt) {
        this.isAanBeurt = isAanBeurt;
    }
}
