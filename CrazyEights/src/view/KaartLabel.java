package view;

import controller.Controller;
import model.Kaart;

import javax.swing.*;
import java.awt.*;

public class KaartLabel extends JLabel{
    private String imageString;
    private Controller controller;


    private String horizontale;
    private String verticale;
    private String omgedraaidH;
    private String omgedraaidV;

    public KaartLabel(String imageString, Controller controller) {
        this.imageString = imageString;
        this.controller = controller;
    }

    public KaartLabel(String horizontale, String verticale, Controller controller) {
        this.horizontale = horizontale;
        this.verticale = verticale;

        this.omgedraaidH = "/view/images/kaartAchterkant.png";
        this.omgedraaidV = "/view/images/kaartAchterkantV.png";

        this.controller = controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(imageString));
            Image image = imageIcon.getImage();
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }

    public String getImageString() {
        return imageString;
    }


    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getHorizontale() {
        return horizontale;
    }

    public String getVerticale() {
        return verticale;
    }

    public String getOmgedraaidH() {
        return omgedraaidH;
    }

    public String getOmgedraaidV() {
        return omgedraaidV;
    }
}
