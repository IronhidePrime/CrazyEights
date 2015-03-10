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
        if (!vraagPropertySpelGeladen()){
            kaartenUitdelen();
        } else {
            for (int i=0; i<vraagPropertySpelersAantal();i++){
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
        }

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

    public void zetPropertyKaarten1(List<Kaart> kaarten){
        try (FileOutputStream out = new
                FileOutputStream("KaartenProperties1.properties")) {
            Properties atts = new Properties();
            for (int i=0;i<kaarten.size();i++){
                atts.setProperty("WaardeKaart" + i,String.valueOf(kaarten.get(i).getWaarde()));
                atts.setProperty("KleurKaart" + i,String.valueOf(kaarten.get(i).getKleur()));
                atts.setProperty("kaart" + i,kaarten.get(i).getHorizontaleImageString());
                atts.setProperty("kaartV" + i,kaarten.get(i).getVerticaleImageString());
            }
            atts.storeToXML(out, "KaartenProperties1.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public Kleur vraagPropertyKleur1(int kaartNummer){
        Kleur kaartKleur = Kleur.harten;
        try (FileInputStream in = new
                FileInputStream("KaartenProperties1.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            if (atts.getProperty("KleurKaart" + kaartNummer).equals("ruiten")){
                kaartKleur = Kleur.ruiten;
            } else if (atts.getProperty("KleurKaart" + kaartNummer).equals("klaveren")){
                kaartKleur = Kleur.klaveren;
            } else if (atts.getProperty("KleurKaart" + kaartNummer).equals("schoppen")){
                kaartKleur = Kleur.schoppen;
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartKleur;
    }

    public int vraagPropertyWaarde1(int kaartNummer){
        int kaartWaarde = 0;
        try (FileInputStream in = new
                FileInputStream("KaartenProperties1.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            kaartWaarde = Integer.valueOf(atts.getProperty("WaardeKaart" + kaartNummer));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartWaarde;
    }

    public String vraagPropertyKaartImgString1(int kaartNummer,Boolean horizontaal){
        String imgString = "";
        try (FileInputStream in = new
                FileInputStream("KaartenProperties1.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            if (horizontaal){
                imgString = atts.getProperty("kaart" + kaartNummer);
            } else {
                imgString = atts.getProperty("kaartV" + kaartNummer);
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return imgString;
    }

    public void zetPropertyKaarten2(List<Kaart> kaarten){
        try (FileOutputStream out = new
                FileOutputStream("KaartenProperties2.properties")) {
            Properties atts = new Properties();
            for (int i=0;i<kaarten.size();i++){
                atts.setProperty("WaardeKaart" + i,String.valueOf(kaarten.get(i).getWaarde()));
                atts.setProperty("KleurKaart" + i,String.valueOf(kaarten.get(i).getKleur()));
                atts.setProperty("kaart" + i,kaarten.get(i).getHorizontaleImageString());
                atts.setProperty("kaartV" + i,kaarten.get(i).getVerticaleImageString());
            }
            atts.storeToXML(out, "KaartenProperties2.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public Kleur vraagPropertyKleur2(int kaartNummer){
        Kleur kaartKleur = Kleur.harten;
        try (FileInputStream in = new
                FileInputStream("KaartenProperties2.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            if (atts.getProperty("KleurKaart" + kaartNummer).equals("ruiten")){
                kaartKleur = Kleur.ruiten;
            } else if (atts.getProperty("KleurKaart" + kaartNummer).equals("klaveren")){
                kaartKleur = Kleur.klaveren;
            } else if (atts.getProperty("KleurKaart" + kaartNummer).equals("schoppen")){
                kaartKleur = Kleur.schoppen;
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartKleur;
    }

    public int vraagPropertyWaarde2(int kaartNummer){
        int kaartWaarde = 0;
        try (FileInputStream in = new
                FileInputStream("KaartenProperties2.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            kaartWaarde = Integer.valueOf(atts.getProperty("WaardeKaart" + kaartNummer));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartWaarde;
    }

    public String vraagPropertyKaartImgString2(int kaartNummer,Boolean horizontaal){
        String imgString = "";
        try (FileInputStream in = new
                FileInputStream("KaartenProperties2.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            if (horizontaal){
                imgString = atts.getProperty("kaart" + kaartNummer);
            } else {
                imgString = atts.getProperty("kaartV" + kaartNummer);
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return imgString;
    }

    public void zetPropertyKaarten3(List<Kaart> kaarten){
        try (FileOutputStream out = new
                FileOutputStream("KaartenProperties3.properties")) {
            Properties atts = new Properties();
            for (int i=0;i<kaarten.size();i++){
                atts.setProperty("WaardeKaart" + i,String.valueOf(kaarten.get(i).getWaarde()));
                atts.setProperty("KleurKaart" + i,String.valueOf(kaarten.get(i).getKleur()));
                atts.setProperty("kaart" + i,kaarten.get(i).getHorizontaleImageString());
                atts.setProperty("kaartV" + i,kaarten.get(i).getVerticaleImageString());
            }
            atts.storeToXML(out, "KaartenProperties3.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public Kleur vraagPropertyKleur3(int kaartNummer){
        Kleur kaartKleur = Kleur.harten;
        try (FileInputStream in = new
                FileInputStream("KaartenProperties3.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            if (atts.getProperty("KleurKaart" + kaartNummer).equals("ruiten")){
                kaartKleur = Kleur.ruiten;
            } else if (atts.getProperty("KleurKaart" + kaartNummer).equals("klaveren")){
                kaartKleur = Kleur.klaveren;
            } else if (atts.getProperty("KleurKaart" + kaartNummer).equals("schoppen")){
                kaartKleur = Kleur.schoppen;
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartKleur;
    }

    public int vraagPropertyWaarde3(int kaartNummer){
        int kaartWaarde = 0;
        try (FileInputStream in = new
                FileInputStream("KaartenProperties3.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            kaartWaarde = Integer.valueOf(atts.getProperty("WaardeKaart" + kaartNummer));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartWaarde;
    }

    public String vraagPropertyKaartImgString3(int kaartNummer,Boolean horizontaal){
        String imgString = "";
        try (FileInputStream in = new
                FileInputStream("KaartenProperties3.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            if (horizontaal){
                imgString = atts.getProperty("kaart" + kaartNummer);
            } else {
                imgString = atts.getProperty("kaartV" + kaartNummer);
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return imgString;
    }

    public void zetPropertyKaarten4(List<Kaart> kaarten){
        try (FileOutputStream out = new
                FileOutputStream("KaartenProperties4.properties")) {
            Properties atts = new Properties();
            for (int i=0;i<kaarten.size();i++){
                atts.setProperty("WaardeKaart" + i,String.valueOf(kaarten.get(i).getWaarde()));
                atts.setProperty("KleurKaart" + i,String.valueOf(kaarten.get(i).getKleur()));
                atts.setProperty("kaart" + i,kaarten.get(i).getHorizontaleImageString());
                atts.setProperty("kaartV" + i,kaarten.get(i).getVerticaleImageString());
            }
            atts.storeToXML(out, "KaartenProperties4.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public Kleur vraagPropertyKleur4(int kaartNummer){
        Kleur kaartKleur = Kleur.harten;
        try (FileInputStream in = new
                FileInputStream("KaartenProperties4.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            if (atts.getProperty("KleurKaart" + kaartNummer).equals("ruiten")){
                kaartKleur = Kleur.ruiten;
            } else if (atts.getProperty("KleurKaart" + kaartNummer).equals("klaveren")){
                kaartKleur = Kleur.klaveren;
            } else if (atts.getProperty("KleurKaart" + kaartNummer).equals("schoppen")){
                kaartKleur = Kleur.schoppen;
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartKleur;
    }

    public int vraagPropertyWaarde4(int kaartNummer){
        int kaartWaarde = 0;
        try (FileInputStream in = new
                FileInputStream("KaartenProperties4.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            kaartWaarde = Integer.valueOf(atts.getProperty("WaardeKaart" + kaartNummer));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartWaarde;
    }

    public String vraagPropertyKaartImgString4(int kaartNummer,Boolean horizontaal){
        String imgString = "";
        try (FileInputStream in = new
                FileInputStream("KaartenProperties4.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            if (horizontaal){
                imgString = atts.getProperty("kaart" + kaartNummer);
            } else {
                imgString = atts.getProperty("kaartV" + kaartNummer);
            }
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return imgString;
    }

    public void zetPropertyKaartAantal1(int aantalKaarten){
        try (FileOutputStream out = new
                FileOutputStream("KaartListProperties1.properties")) {
            Properties atts = new Properties();
            atts.setProperty("aantalKaartenSpeler1", String.valueOf(aantalKaarten));

            atts.storeToXML(out, "KaartenProperties1.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public void zetPropertyKaartAantal2(int aantalKaarten){
        try (FileOutputStream out = new
                FileOutputStream("KaartListProperties2.properties")) {
            Properties atts = new Properties();
            atts.setProperty("aantalKaartenSpeler2", String.valueOf(aantalKaarten));

            atts.storeToXML(out, "KaartenProperties2.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public void zetPropertyKaartAantal3(int aantalKaarten){
        try (FileOutputStream out = new
                FileOutputStream("KaartListProperties3.properties")) {
            Properties atts = new Properties();
            atts.setProperty("aantalKaartenSpeler3", String.valueOf(aantalKaarten));

            atts.storeToXML(out, "KaartenProperties3.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public void zetPropertyKaartAantal4(int aantalKaarten){
        try (FileOutputStream out = new
                FileOutputStream("KaartListProperties4.properties")) {
            Properties atts = new Properties();
            atts.setProperty("aantalKaartenSpeler4", String.valueOf(aantalKaarten));

            atts.storeToXML(out, "KaartenProperties4.properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    public int vraagPropertyKaartAantal1(){
        int kaartAantal = 0;
        try (FileInputStream in = new
                FileInputStream("KaartListProperties1.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            kaartAantal = Integer.valueOf(atts.getProperty("aantalKaartenSpeler1"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartAantal;
    }

    public int vraagPropertyKaartAantal2(){
        int kaartAantal = 0;
        try (FileInputStream in = new
                FileInputStream("KaartListProperties2.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            kaartAantal = Integer.valueOf(atts.getProperty("aantalKaartenSpeler2"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartAantal;
    }

    public int vraagPropertyKaartAantal3(){
        int kaartAantal = 0;
        try (FileInputStream in = new
                FileInputStream("KaartListProperties3.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            kaartAantal = Integer.valueOf(atts.getProperty("aantalKaartenSpeler3"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartAantal;
    }

    public int vraagPropertyKaartAantal4(){
        int kaartAantal = 0;
        try (FileInputStream in = new
                FileInputStream("KaartListProperties4.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            kaartAantal = Integer.valueOf(atts.getProperty("aantalKaartenSpeler4"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartAantal;
    }

    public int vraagPropertyKaartAantal(){
        int kaartAantal = 0;
        try (FileInputStream in = new
                FileInputStream("KaartListProperties.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            kaartAantal = Integer.valueOf(atts.getProperty("aantalKaarten"));
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
        return kaartAantal;
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



