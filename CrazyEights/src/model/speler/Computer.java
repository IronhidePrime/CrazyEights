package model.speler;

import model.kaart.Kaart;
import model.spelbord.Spelbord;

import java.util.List;

/**
 * computerspeler erft van de klasse speler
 * extra methodes
 */
public class Computer extends Speler {
    private Spelbord spelbord;

    public Computer(String naam, Spelbord spelbord) {
        super(naam);
        this.spelbord = spelbord;
    }

    public Spelbord getSpelbord() {
        return spelbord;
    }

    public Kaart getTeSpelenKaart() {
        /**
         * gaat kijken of de computer een kaart kan spelen
         * eerst kijken of hij een kaart met dezelfde kleur kan spelen
         * daarna kijken of hij een kaart met dezelfde waarde kan spelen
         * ten slotte kijken of hij een 8 bezit
         */
        Kaart teSpelenKaart = null;
        Kaart kaartSpelbord = getSpelbord().getAflegstapel().getBovensteKaart();

        for (Kaart kaart : super.getKaarten()) {
            System.out.println(kaart.getHorizontaleImageString());
            System.out.println(super.getKaarten().size());

            if (kaart.getKleur() == kaartSpelbord.getKleur() || kaart.getWaarde() == kaartSpelbord.getWaarde() || kaart.getWaarde() == 8) {
                teSpelenKaart = kaart;
                break;
            }
        }
        return teSpelenKaart;
    }

    public void voegKaartToe() {
        /**
         * neem kaart van de trekstapel
         * voeg de getrokken kaart toe aan de speler zijn kaarten
         */
        Kaart kaart = getSpelbord().getTrekstapel().neemKaart();
        super.voegKaartToe(kaart);
    }

    public List<Kaart> getKaarten() {
        return super.getKaarten();
    }
}
