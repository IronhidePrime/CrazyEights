package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SpelProperties {
    public static void main(String[] args) {
        writeProperties();
        readProperties();
    }

    private static void writeProperties() {
        try (FileOutputStream out = new
                FileOutputStream("Application.properties")) {
            Properties atts = new Properties();
            atts.setProperty("Pad", "");
            atts.setProperty("Score", "189");
            atts.setProperty("Naam", "Freddy");
            atts.storeToXML(out, "Application properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }


    private static void readProperties() {
        try (FileInputStream in = new
                FileInputStream("Application.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
    }

}
