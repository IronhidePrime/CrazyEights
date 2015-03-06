package model;

import java.util.List;

public class Computer extends Speler {
    private Spelbord spelbord;

    public Computer(String naam, Spelbord spelbord) {
        super(naam);
        this.spelbord = spelbord;
    }

    public Spelbord getSpelbord() {
        return spelbord;
    }

    public Kaart speeltKaart() {
        /**
         * gaat kijken of de computer een kaart kan spelen
         * eerst kijken of hij een kaart met dezelfde kleur kan spelen
         * daarna kijken of hij een kaart met dezelfde waarde kan spelen
         * ten slotte kijken of hij een 8 bezit
         */
        for (Kaart kaart : super.getKaarten()) {
            if (kaart.getKleur() == getSpelbord().getAflegstapel().getBovensteKaart().getKleur()) {
                return kaart;
            }
        }

        for (Kaart kaart : super.getKaarten()) {
            if (kaart.getWaarde() == getSpelbord().getAflegstapel().getBovensteKaart().getWaarde()) {
                return kaart;
            }
        }

        for (Kaart kaart : super.getKaarten()) {
            if (kaart.getWaarde() == 8) {
                return kaart;
            }
        }
        return null;
    }

    /**
     * neem kaart van de trekstapel
     */
    public void voegKaartToe() {
        Kaart kaart = getSpelbord().getTrekstapel().neemKaart();
        super.voegKaartToe(kaart);
    }

    public List<Kaart> getKaarten() {
        return super.getKaarten();
    }

}
