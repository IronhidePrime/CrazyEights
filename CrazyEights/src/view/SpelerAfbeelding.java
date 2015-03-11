package view;

import javax.swing.*;
import java.awt.*;

public class SpelerAfbeelding extends JLabel {
    private String imageString;

    public SpelerAfbeelding (String imageString) {
        this.imageString = imageString;
    }
    protected void paintComponent(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(imageString));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        int width = (this.getWidth() - image.getWidth(null)) / 2;
        int height = (this.getHeight() - image.getHeight(null)) / 2;
        g.drawImage(image, width, height, null);
    }
}