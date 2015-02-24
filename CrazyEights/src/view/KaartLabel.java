package view;

import javax.swing.*;
import java.awt.*;

public class KaartLabel extends JLabel{
    private String imageString;

    public KaartLabel(String imageString) {
        this.imageString = imageString;
    }

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(imageString));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        g.drawImage(image, 20, 10, getWidth(), getHeight(), this);
    }

    public String getImageString() {
        return imageString;
    }
}
