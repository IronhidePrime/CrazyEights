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

    public Kaart getTeSpelenKaart() {

        /**
         * gaat kijken of de computer een kaart kan spelen
         * eerst kijken of hij een kaart met dezelfde kleur kan spelen
         * daarna kijken of hij een kaart met dezelfde waarde kan spelen
         * ten slotte kijken of hij een 8 bezit
         */

        Kaart teSpelenKaart = null;
        Kaart kaartSpelbord = getSpelbord().getAflegstapel().getBovensteKaart();
        boolean kaartGevonden = false;

        for (Kaart kaart : super.getKaarten()) {
            System.out.println(kaart.getHorizontaleImageString());
            System.out.println(super.getKaarten().size());

            if (kaart.getKleur() == kaartSpelbord.getKleur() || kaart.getWaarde() == kaartSpelbord.getWaarde() || kaart.getWaarde() == 8) {
                teSpelenKaart = kaart;
                kaartGevonden = true;
                break;
            }
        }

        if (kaartGevonden){
            System.out.println("te spelen kaart is " + teSpelenKaart.getHorizontaleImageString());
        } else {
            System.out.println("kan geen kaart spelen");
        }
        return teSpelenKaart;
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
