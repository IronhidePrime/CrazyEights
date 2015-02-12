package controller;

import model.Kaart;
import model.Spelbord;
import model.Speler;
import view.StartUI;

import java.util.List;

public class GameController {
    public static void main(String[] args) {
        new StartUI();

        int aantal=0;
        //Speler rob = new Speler("Rob", false);

        //Speler sanderhomo = new Speler("Sander", false);


        Spelbord spelletje = new Spelbord();

        spelletje.kaartenUitdelen();
        spelletje.beginSpel();

        List<Kaart> kaarten = spelletje.getAflegstapel().getKaarten();
        System.out.println(kaarten.get(0));
    }
}
