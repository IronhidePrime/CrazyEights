package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sander on 9/03/2015.
 */
public class SpelProperties {
    public static void main(String[] args) {
        writeProperties();
        readProperties();
    }

    private static void writeProperties(){
        try (FileOutputStream out = new FileOutputStream("Application8.properties")) {
            Properties atts = new Properties();
            atts.setProperty("spelerInt","1");
            atts.storeToXML(out, "Application properties");
        } catch (IOException e) {
            System.out.println("Fout bij aanmaken properties-bestand");
        }
    }

    private static void readProperties(){
        try (FileInputStream in = new FileInputStream("Application8.properties")) {
            Properties atts = new Properties();
            atts.loadFromXML(in);
            atts.list(System.out);
        } catch (IOException e) {
            System.out.println("Fout bij het ophalen van properties");
        }
    }
}
