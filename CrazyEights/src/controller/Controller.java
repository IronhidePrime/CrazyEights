package controller;

import model.*;
import view.KaartLabel;
import view.SpelbordUI;
import view.StartUI;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Controller {
    /**
     * Constanten die gebruikt worden
     */
    private static final int KAARTEN_2_SPELERS = 7;
    private static final int KAARTEN_MEER_SPELERS = 5;

    private SpelbordUI spelbordUI;

    private Spelbord spelbord;
    private List<Speler> spelers;


    public Controller() {
        this.spelbord = new Spelbord();
        this.spelers = new LinkedList<>();
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
        kaartenUitdelen();

        //2
        beginKaart();

        //3
        getSpelers().get(0).setAanBeurt(true);
    }

    public void herstartSpel(){
        new StartUI();
    }

    /**
     * spel is ten einde wanneer een van de spelers erin slaagt om al zijn kaarten te hebben afgelegd
     */
    public boolean eindeSpel() {
        for (Speler speler : spelers) {
            if (speler.getKaarten().size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * kaart van de aflegstapel waarop men de eerste kaart moet leggen wanneer het spel begint
     */
    public void beginKaart() {
        Kaart beginkaart = spelbord.getTrekstapel().neemKaart();
        beginkaart.setBeeldKant(true);
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

    public boolean checkOfKaartKanSpelen(int spelerNr){
        Boolean mogelijkeKaart = false;
        for (int i=0; i<getSpelerKaarten(spelerNr).size();i++){
            if (speelKaartMogelijk(getSpelerKaarten(spelerNr).get(i))){
                System.out.println("Speler, je kan een kaart spelen");
                mogelijkeKaart = true;
                break;
            } else {
                System.out.println("je kan geen enkele kaart spelen, jammer");
            }
        }
        return mogelijkeKaart;
    }

    public int getAantalKaartenSpeler(){
        if(getAantalSpelers()==2){
            return KAARTEN_2_SPELERS;
        } else {
            return KAARTEN_MEER_SPELERS;
        }
    }

    public void beeindigBeurt(int spelerNr){
        getSpelers().get(spelerNr).setAanBeurt(false);
        if (spelerNr+1 == getSpelers().size()){
            getSpelers().get(0).setAanBeurt(true);
        } else {
            getSpelers().get(spelerNr+1).setAanBeurt(true);
        }
    }

    public void beeindigBeurtDame (int spelerNr) {
        getSpelers().get(spelerNr).setAanBeurt(false);
        if (getSpelers().size() == 2) {
            getSpelers().get(spelerNr).setAanBeurt(true);
        } else if (getSpelers().size() == 3) {
            if (spelerNr == 0) {
                getSpelers().get(2).setAanBeurt(true);
            } else {
                getSpelers().get(spelerNr-1).setAanBeurt(true);
            }
        } else if (getSpelers().size() == 4) {
            if (spelerNr == 0 || spelerNr == 1) {
                getSpelers().get(spelerNr+2).setAanBeurt(true);
            } else {
                getSpelers().get(spelerNr-2).setAanBeurt(true);
            }
        }
        /*
        if (spelerNr == 2) {
            getSpelers().get(0).setAanBeurt(true);
        } else if (spelerNr == 3) {
            getSpelers().get(1).setAanBeurt(true);
        } else {
            getSpelers().get(spelerNr+2).setAanBeurt(true);
        }*/
    }

    public void veranderSpeelRichting (int spelerNr, int speelRichting) {
        if (speelRichting == 0) {
            beeindigBeurt(spelerNr);
        } else {
            getSpelers().get(spelerNr).setAanBeurt(false);
            if (spelerNr==0) {
                getSpelers().get(3).setAanBeurt(true);
            } else {
                getSpelers().get(spelerNr - 1).setAanBeurt(true);
            }
        }
    }

    public void vulTrekStapel(){
        Kaart bovensteKaart = getSpelbord().getAflegstapel().getBovensteKaart();
        getSpelbord().getTrekstapel().getKaarten().addAll(getSpelbord().getAflegstapel().getKaarten());
        getSpelbord().getAflegstapel().getKaarten().removeAll(getSpelbord().getAflegstapel().getKaarten());
        getSpelbord().getAflegstapel().getKaarten().add(bovensteKaart);
        Collections.shuffle(getSpelbord().getTrekstapel().getKaarten());
    }

    public void zetPropertySpelerBord(int aantalSpelers,Boolean multiplayerJaofNee){
        try (FileOutputStream out = new
                FileOutputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.setProperty("aantalSpelers", String.valueOf(aantalSpelers));
            atts.setProperty("multiplayer?",String.valueOf(multiplayerJaofNee));
            atts.storeToXML(out, "SpelerProperties.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public void zetPropertySpelersSingle(String spelerNaam){
        try (FileOutputStream out = new
                FileOutputStream("SpelerProperties.properties")) {
            Properties atts = new Properties();
            atts.setProperty("naamSpeler1", spelerNaam);
            System.out.println("naam weggeschreven");
            atts.storeToXML(out, "SpelerProperties.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public void zetPropertySpelersMulti(String[] spelerNaam){
        for (String string : spelerNaam){
            System.out.println(string);
        }
        try (FileOutputStream out = new
                FileOutputStream("SpelerProperties.properties")) {
            Properties atts = new Properties();
            atts.setProperty("naamSpeler1", spelerNaam[0]);
            atts.setProperty("naamSpeler2", spelerNaam[1]);
            if (vraagPropertySpelersAantal() == 3){
                atts.setProperty("naamSpeler3", spelerNaam[2]);
            } if (vraagPropertySpelersAantal() == 4){
                atts.setProperty("naamSpeler3", spelerNaam[2]);
                atts.setProperty("naamSpeler4", spelerNaam[3]);
            }
            atts.storeToXML(out, "SpelerProperties.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public void zetPropertySpelerBeurtNr(int spelerNr){
        try (FileOutputStream out = new
                FileOutputStream("BeurtProperties.properties")) {
            Properties atts = new Properties();
            atts.setProperty("intSpelerAanBeurt", String.valueOf(spelerNr));
            System.out.println("beurt weggeschreven");
            atts.storeToXML(out, "BeurtProperties.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public void zetSpelGeladenBoolean(boolean spelGeladen){
        try (FileOutputStream out = new
                FileOutputStream("SpelGeladenProperties.properties")) {
            Properties atts = new Properties();
            atts.setProperty("SpelGeladen", String.valueOf(spelGeladen));
            System.out.println("beurt weggeschreven");
            atts.storeToXML(out, "SpelGeladenProperties.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public void zetPropertyKaarten(List<Kaart> kaarten){
        try (FileOutputStream out = new
                FileOutputStream("KaartenProperties.properties")) {
            Properties atts = new Properties();
            for (int i=0;i<kaarten.size();i++){
                atts.setProperty("WaardeKaart" + i,String.valueOf(kaarten.get(i).getWaarde()));
                atts.setProperty("KleurKaart" + i,String.valueOf(kaarten.get(i).getKleur()));
                atts.setProperty("kaart" + i,kaarten.get(i).getHorizontaleImageString());
                atts.setProperty("kaartV" + i,kaarten.get(i).getVerticaleImageString());
            }
            atts.storeToXML(out, "KaartenProperties.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public boolean vraagPropertySpelGeladen(){
        boolean spelGeladen = false;
        try (FileInputStream in = new
                FileInputStream("SpelGeladenProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            spelGeladen = Boolean.valueOf(atts.getProperty("SpelGeladen"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return spelGeladen;
    }

    public int vraagPropertySpelersAantal(){
        int aantalSpelers = 0;
        try (FileInputStream in = new
                FileInputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            aantalSpelers = Integer.parseInt(atts.getProperty("aantalSpelers"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return aantalSpelers;
    }


    public Boolean vraagPropertyMultiplayer(){
        Boolean multiplayerJaOfNee = false;
        try (FileInputStream in = new
                FileInputStream("SpelerBordProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            multiplayerJaOfNee = Boolean.valueOf(atts.getProperty("multiplayer?"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return multiplayerJaOfNee;
    }

    public String vraagProperySpelerNaamSingle(){
        String spelerNaam = "";
        try (FileInputStream in = new
                FileInputStream("SpelerProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            spelerNaam = atts.getProperty("naamSpeler1");
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return spelerNaam;
    }

    public String[] vraagProperySpelerNaamMulti(){
        String[] spelersNamen = new String[vraagPropertySpelersAantal()];
        System.out.println(spelersNamen.length);
        try (FileInputStream in = new
                FileInputStream("SpelerProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            if (vraagPropertySpelersAantal() == 2){
                spelersNamen[0] = atts.getProperty("naamSpeler1");
                spelersNamen[1] = atts.getProperty("naamSpeler2");
            } else if (vraagPropertySpelersAantal() == 3){
                spelersNamen[0] = atts.getProperty("naamSpeler1");
                spelersNamen[1] = atts.getProperty("naamSpeler2");
                spelersNamen[2] = atts.getProperty("naamSpeler3");
            } else if (vraagPropertySpelersAantal() == 4){
                spelersNamen[0] = atts.getProperty("naamSpeler1");
                spelersNamen[1] = atts.getProperty("naamSpeler2");
                spelersNamen[2] = atts.getProperty("naamSpeler3");
                spelersNamen[3] = atts.getProperty("naamSpeler4");
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }

        for (String string: spelersNamen){
            System.out.println(string);
        }
        return spelersNamen;
    }

    public int vraagBeurtProperty(){
        int spelerNr = 15;
        try (FileInputStream in = new
                FileInputStream("BeurtProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            spelerNr = Integer.valueOf(atts.getProperty("intSpelerAanBeurt"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return spelerNr;
    }
}



