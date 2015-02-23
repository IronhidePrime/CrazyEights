package view;

import javax.swing.*;
import java.awt.*;

public class AflegStapelLabel extends JLabel {
    private String imageString;

    public AflegStapelLabel(String imageString) {
        this.imageString = imageString;
    }
    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(imageString));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
