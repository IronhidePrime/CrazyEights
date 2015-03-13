package controller;

import model.kaart.Kaart;
import model.kaart.Kleur;
import model.spelbord.Spelbord;
import model.speler.Computer;
import model.speler.Mens;
import model.speler.Speler;
import view.gui.SpelbordUI;
import view.gui.StartUI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Controller {
    /**
     * constanten die gebruikt worden
     */
    private static final int KAARTEN_2_SPELERS = 7;
    private static final int KAARTEN_MEER_SPELERS = 5;

    private Spelbord spelbord;
    private List<Speler> spelers;

    private int speelRichting = 0;

    private boolean spelGeladen = false;
    private boolean isMultiplayer;
    private boolean spelGedaan = false;

    public Controller() {
        this.spelbord = new Spelbord();
        this.spelers = new LinkedList<>();
    }

    public boolean isSpelGeladen() {
        return spelGeladen;
    }

    public void setSpelGeladen(boolean spelGeladen) {
        this.spelGeladen = spelGeladen;
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public void setMultiplayer(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
    }

    public boolean isSpelGedaan() {
        return spelGedaan;
    }

    public void setSpelGedaan(boolean spelGedaan) {
        this.spelGedaan = spelGedaan;
    }

    /**
     * maken speler
     * spelersnaam opvragen
     * de speler zijn kaarten opvragen
     * alle spelers opvragen
     * aantal spelers opvragen
     */
    public void maakMens (String naam) {
        spelers.add(new Mens(naam));
    }

    public void maakComputer (String naam) {
        spelers.add(new Computer(naam, this.spelbord));
    }

    public String getSpelerNaam(int spelerNr){
        return spelers.get(spelerNr).getNaam();
    }

    public List<Kaart> getSpelerKaarten (int spelerNr) {
        return spelers.get(spelerNr).getKaarten();
    }

    public List<Speler> getSpelers() {
        return spelers;
    }

    public int getAantalSpelers() {
        return spelers.size();
    }

    public Spelbord getSpelbord() {
        return spelbord;
    }

    /**
     * 2 spelers? -> 7 kaarten
     * 3 of 4 spelers? -> 5 kaarten
     */
    public void kaartenUitdelen() {
        for (int i = 0; i < spelers.size(); i++) {
            for (int j = 0; j < getAantalKaartenSpeler(); j++) {
                spelers.get(i).voegKaartToe(spelbord.getTrekstapel().neemKaart());
            }
        }
    }

    /**
     * START SPEL
     *  1. kaarten uitdelen
     *  2. bovenste kaart van de trekstapel omdraaien op de aflegstapel
     *  3. speler 1 aan beurt
     */
    public void startSpel() {
        //1
        if (!spelGeladen){
            kaartenUitdelen();
        } else {
            for (int i=0; i<vraagPropertyAantalSpelers();i++){
                getSpelerKaarten(i).removeAll(getSpelerKaarten(i));
                for (int j=0; j<vraagPropertyAantalKaarten(i);j++){
                    getSpelerKaarten(i).add(new Kaart(vraagPropertyKaartWaarde(i,j),vraagPropertyKaartKleur(i,j),vraagPropertyKaartImgString(i,j,false),vraagPropertyKaartImgString(i,j,true)));
                }
            }
            verwijderBestaandeKaarten();
        }
        //2
        beginKaart();

        //3
        getSpelers().get(0).setAanBeurt(true);
    }

    public void verwijderBestaandeKaarten(){
        List<Kaart> trekStapelKaarten =  getSpelbord().getTrekstapel().getKaarten();
        List<Kaart> teVerwijderenKaarten = new ArrayList<Kaart>();
        for (int i=0; i<vraagPropertyAantalSpelers();i++){
            for (Kaart kaart: getSpelerKaarten(i)){
                for (Kaart trekStapelKaart: trekStapelKaarten){
                    if (kaart.getHorizontaleImageString().equals(trekStapelKaart.getHorizontaleImageString())){
                      teVerwijderenKaarten.add(trekStapelKaart);
                    }
                }
            }
        }
        trekStapelKaarten.removeAll(teVerwijderenKaarten);
    }

    public void herstartSpel(){
        new StartUI();
    }

    /**
     * kaart van de aflegstapel waarop men de eerste kaart moet leggen wanneer het spel begint
     */
    public void beginKaart() {
        Kaart beginkaart = spelbord.getTrekstapel().neemKaart();
        spelbord.getAflegstapel().legKaart(beginkaart);
    }

    /**
     * speler speelt zijn kaart -> kaart wordt verwijderd bij speler
     * deze kaart komt op de aflegstapel -> op deze kaart wordt voortgespeeld
     */
    public void speelKaart(Kaart kaart, int spelerNr){
        spelers.get(spelerNr).speelKaart(kaart);
        spelbord.getAflegstapel().legKaart(kaart);
    }

    /**
     * nagaan of men een kaart kan spelen
     * voor een kaart te kunnen spelen moet waarde of kleur gelijk zijn, men kan ook de 8 spelen (zie spelregels)
     */
    public boolean speelKaartMogelijk(Kaart kaart){
        Kaart aflegkaart = spelbord.getAflegstapel().getBovensteKaart();
        if (kaart.getKleur() == aflegkaart.getKleur()|| kaart.getWaarde() == aflegkaart.getWaarde() || kaart.getWaarde() == 8){
            return true;
        } else {
            return false;
        }
    }

    public int getAantalKaartenSpeler(){
        if(getAantalSpelers()==2){
            return KAARTEN_2_SPELERS;
        } else {
            return KAARTEN_MEER_SPELERS;
        }
    }

    /**
     * zet de volgende speler aan de beurt
     */
    public void beeindigBeurt(int spelerNr){
        getSpelers().get(spelerNr).setAanBeurt(false);
        if (spelerNr+1 == getSpelers().size()){
            getSpelers().get(0).setAanBeurt(true);
        } else {
            getSpelers().get(spelerNr+1).setAanBeurt(true);
        }
    }

    /**
     * bij een dame wordt de beurt van de volgende speler overgeslagen
     * deze methode houdt ook rekening in welke speelrichting wordt gespeeld zodat de juiste speler wordt overslagen
     afhankelijk van de speelrichting
     */
    public void beeindigBeurtDame (int spelerNr) {
        if (speelRichting == 0) {
            getSpelers().get(spelerNr).setAanBeurt(false);
            if (getSpelers().size() == 2) {
                getSpelers().get(spelerNr).setAanBeurt(true);
            } else if (getSpelers().size() == 3) {
                if (spelerNr == 0) {
                    getSpelers().get(2).setAanBeurt(true);
                } else {
                    getSpelers().get(spelerNr - 1).setAanBeurt(true);
                }
            } else if (getSpelers().size() == 4) {
                if (spelerNr == 0 || spelerNr == 1) {
                    getSpelers().get(spelerNr + 2).setAanBeurt(true);
                } else {
                    getSpelers().get(spelerNr - 2).setAanBeurt(true);
                }
            }
        } else {
            getSpelers().get(spelerNr).setAanBeurt(false);
            if (getSpelers().size() == 2) {
                getSpelers().get(spelerNr).setAanBeurt(true);
            } else if (getSpelers().size() == 3) {
                if (spelerNr == 0) {
                    getSpelers().get(1).setAanBeurt(true);
                } else if (spelerNr == 1) {
                    getSpelers().get(2).setAanBeurt(true);
                } else {
                    getSpelers().get(0).setAanBeurt(true);
                }
            } else if (getSpelers().size() == 4) {
                if (spelerNr == 2 || spelerNr == 3) {
                    getSpelers().get(spelerNr - 2).setAanBeurt(true);
                } else {
                    getSpelers().get(spelerNr + 2).setAanBeurt(true);
                }
            }
        }
    }

    /**
     * bij een haas wordt van speelrichting veranderd
     * afhankelijk van het aantal spelers wie er aan de beurt komt
     */
    public void speelRichting (int spelerNr) {
        if (speelRichting == 0) {
            beeindigBeurt(spelerNr);
        } else {
            if (getSpelers().size() == 2) {
                beeindigBeurt(spelerNr);
            } else if (getSpelers().size() == 3) {
                getSpelers().get(spelerNr).setAanBeurt(false);
                if (spelerNr == 0) {
                    getSpelers().get(2).setAanBeurt(true);
                } else {
                    getSpelers().get(spelerNr -1).setAanBeurt(true);
                }
            } else if (getSpelers().size() == 4) {
                getSpelers().get(spelerNr).setAanBeurt(false);
                if (spelerNr == 0) {
                    getSpelers().get(3).setAanBeurt(true);
                } else {
                    getSpelers().get(spelerNr-1).setAanBeurt(true);
                }
            }
        }
    }

    public int getSpeelRichting() {
        return speelRichting;
    }

    public void setSpeelRichting(int speelRichting) {
        this.speelRichting = speelRichting;
    }

    public int getSpelerNrAanBeurt() {
        for (int i=0; i<spelers.size(); i++) {
            if (spelers.get(i).getAanBeurt()) {
                return i;
            }
        }
        return 0;
    }

    public String getSpelerNaamAanBeurt() {
        return spelers.get(getSpelerNrAanBeurt()).getNaam();
    }

    /**
     * gaat de trekstapel terug opvullen wanneer deze leeg is
     * neemt kaarten van de aflegstapel en voegt deze aan de trekstapel toe
     */
    public void vulTrekStapel(){
        Kaart bovensteKaart = getSpelbord().getAflegstapel().getBovensteKaart();
        List<Kaart> kaartenAflegStapel = getSpelbord().getAflegstapel().getKaarten();
        Set<Kaart> kaartSet = new HashSet<>(kaartenAflegStapel);
        getSpelbord().getAflegstapel().getKaarten().removeAll(getSpelbord().getAflegstapel().getKaarten());
        getSpelbord().getAflegstapel().getKaarten().add(bovensteKaart);
        getSpelbord().getTrekstapel().getKaarten().addAll(kaartSet);

        Collections.shuffle(getSpelbord().getTrekstapel().getKaarten());
    }

    public void zetSpelBordProperties(){
        try (FileOutputStream out = new
                FileOutputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.setProperty("aantalSpelers", String.valueOf(getAantalSpelers()));
            atts.setProperty("isMultiplayer", String.valueOf(isMultiplayer()));
            for (int i=0; i<getAantalSpelers();i++){
                for (int j=0;j<getSpelerKaarten(i).size();j++){
                    atts.setProperty("SpelerKaarten"+ i + "ImgStringH" + j,getSpelerKaarten(i).get(j).getHorizontaleImageString());
                    atts.setProperty("SpelerKaarten"+ i +"ImgStringV" + j,getSpelerKaarten(i).get(j).getVerticaleImageString());
                    atts.setProperty("SpelerKaarten"+ i +"Waarde" + j, String.valueOf(getSpelerKaarten(i).get(j).getWaarde()));
                    atts.setProperty("SpelerKaarten"+ i +"Kleur" + j,String.valueOf(getSpelerKaarten(i).get(j).getKleur()));
                    atts.setProperty("SpelerKaarten"+ i +"Aantal",String.valueOf(getSpelerKaarten(i).size()));
                    atts.setProperty("naamSpeler"+ i, getSpelerNaam(i));
                }
            }
            atts.setProperty("SpelerIntAanBeurt", String.valueOf(getSpelerNrAanBeurt()));
            atts.storeToXML(out, "SpelerBordProperties.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public int vraagPropertyAantalSpelers(){
        int aantalSpelers = 2;
        try (FileInputStream in = new
                FileInputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            aantalSpelers = Integer.valueOf(atts.getProperty("aantalSpelers"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return aantalSpelers;
    }

    public String vraagSpelerNaamProperty(int spelerNr){
        String spelersNaam = "";
        try (FileInputStream in = new
                FileInputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            spelersNaam = atts.getProperty("naamSpeler"+spelerNr);
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return spelersNaam;
    }

    public boolean vraagIsMultiplayer(){
        boolean isMultiplayer = false;
        try (FileInputStream in = new
                FileInputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            isMultiplayer = Boolean.valueOf(atts.getProperty("isMultiplayer"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return isMultiplayer;
    }

    public int vraagPropertyAantalKaarten(int spelerNr){
        int aantalKaarten = 0;
        try (FileInputStream in = new
                FileInputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            aantalKaarten = Integer.valueOf(atts.getProperty("SpelerKaarten"+ spelerNr +"Aantal"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return aantalKaarten;
    }

    public int vraagPropertyKaartWaarde(int spelerNr, int kaartNr){
        int kaartWaarde = 0;
        try (FileInputStream in = new
                FileInputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            kaartWaarde = Integer.valueOf(atts.getProperty("SpelerKaarten"+ spelerNr +"Waarde" + kaartNr));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartWaarde;
    }

    public Kleur vraagPropertyKaartKleur(int spelerNr, int kaartNr){
        Kleur kaartKleur = Kleur.harten;
        try (FileInputStream in = new
                FileInputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            atts.getProperty("SpelerKaarten"+ spelerNr +"Kleur" + kaartNr);
            switch (atts.getProperty("SpelerKaarten"+ spelerNr +"Kleur" + kaartNr)){
                case "ruiten": kaartKleur = Kleur.ruiten;
                    break;
                case "klaveren": kaartKleur = Kleur.klaveren;
                    break;
                case "schoppen": kaartKleur = Kleur.schoppen;
                    break;
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartKleur;
    }

    public String vraagPropertyKaartImgString(int spelerNr, int kaartNr,Boolean verticaal){
        String imgString = "";
        try (FileInputStream in = new
                FileInputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            if (!verticaal){
                imgString = atts.getProperty("SpelerKaarten"+ spelerNr + "ImgStringH" + kaartNr);
            } else {
                imgString = atts.getProperty("SpelerKaarten"+ spelerNr + "ImgStringV" + kaartNr);
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return imgString;
    }
}



