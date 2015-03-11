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

    private int speelRichting = 0;


    private boolean spelGeladen = false;
    private boolean isMultiplayer;

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

        //if (!vraagPropertySpelGeladen()){
        if (!spelGeladen){
            kaartenUitdelen();
            System.out.println("nieuw spel, kaarten gewoon verdelen");
        } else {
            //kaartenUitdelen();
            for (int i=0; i<vraagPropertyAantalSpelers();i++){
                getSpelerKaarten(i).removeAll(getSpelerKaarten(i));
                for (int j=0; j<vraagPropertyAantalKaarten(i);j++){
                    getSpelerKaarten(i).add(new Kaart(vraagPropertyKaartWaarde(i,j),vraagPropertyKaartKleur(i,j),vraagPropertyKaartImgString(i,j,false),vraagPropertyKaartImgString(i,j,true)));
                }
            }
        }

        //} else {
           /* for (int i=0; i<vraagPropertySpelersAantal();i++){
                getSpelerKaarten(i).removeAll(getSpelerKaarten(i));
                if (i==0){
                    for (int j=0;j<vraagPropertyKaartAantal1();j++){
                        getSpelerKaarten(0).add(new Kaart(vraagPropertyWaarde1(j),vraagPropertyKleur1(j),vraagPropertyKaartImgString1(j,true),vraagPropertyKaartImgString1(j,false)));
                    }
                    System.out.println("Kaart objecten speler 1 aangemaakt");
                    System.out.println("er zitten nu " + getSpelerKaarten(0).size() + " kaarten in");
                } else if (i==1){
                    for (int j=0;j<vraagPropertyKaartAantal2();j++){
                        getSpelerKaarten(1).add(new Kaart(vraagPropertyWaarde2(j),vraagPropertyKleur2(j),vraagPropertyKaartImgString2(j, true),vraagPropertyKaartImgString2(j, false)));
                    }
                    System.out.println("Kaart objecten speler 2 aangemaakt");
                    System.out.println("er zitten nu " + getSpelerKaarten(1).size() + " kaarten in");
                } else if (i==2){
                    for (int j=0;j<vraagPropertyKaartAantal3();j++){
                        getSpelerKaarten(2).add(new Kaart(vraagPropertyWaarde3(j),vraagPropertyKleur3(j),vraagPropertyKaartImgString3(j, true),vraagPropertyKaartImgString3(j, false)));
                    }
                    System.out.println("Kaart objecten speler 3 aangemaakt");
                    System.out.println("er zitten nu " + getSpelerKaarten(2).size() + " kaarten in");
                } else if (i==3){
                    for (int j=0;j<vraagPropertyKaartAantal4();j++){
                        getSpelerKaarten(3).add(new Kaart(vraagPropertyWaarde4(j),vraagPropertyKleur4(j),vraagPropertyKaartImgString4(j, true),vraagPropertyKaartImgString4(j, false)));
                    }
                    System.out.println("Kaart objecten speler 2 aangemaakt");
                    System.out.println("er zitten nu " + getSpelerKaarten(3).size() + " kaarten in");
                }
            }
        }*/

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


    public void vulTrekStapel(){
        Kaart bovensteKaart = getSpelbord().getAflegstapel().getBovensteKaart();
        getSpelbord().getTrekstapel().getKaarten().addAll(getSpelbord().getAflegstapel().getKaarten());
        getSpelbord().getAflegstapel().getKaarten().removeAll(getSpelbord().getAflegstapel().getKaarten());
        getSpelbord().getAflegstapel().getKaarten().add(bovensteKaart);
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



