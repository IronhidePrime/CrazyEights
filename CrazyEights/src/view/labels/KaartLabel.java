package view.labels;

import javax.swing.*;
import java.awt.*;

/**
 * gaat een kaart weergeven
 * de imageString die gebruikt wordt hangt af van de positie waar de kaart wordt getekend
 * onderaan of bovenaan zal deze imageString horizontaal moeten zijn
 * links of rechts zal deze imageString verticaal moeten zijn
 * speler niet aan de beurt? kaartLabel omdraaien
 */
public class KaartLabel extends JLabel{
    private String imageString;

    private String horizontale;
    private String verticale;
    private String omgedraaidH;
    private String omgedraaidV;

    public KaartLabel(String horizontale, String verticale) {
        this.horizontale = horizontale;
        this.verticale = verticale;

        this.omgedraaidH = "/view/images/kaartAchterkant.png";
        this.omgedraaidV = "/view/images/kaartAchterkantV.png";
    }

    @Override
    protected void paintComponent(Graphics g) {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(imageString));
            Image image = imageIcon.getImage();
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
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
