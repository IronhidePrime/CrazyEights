package view;

import javax.swing.*;
import java.awt.*;

public class SpelerLabel extends JLabel {
    protected void paintComponent(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/view/images/spelerIcon.png"));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        int x = (this.getWidth() - image.getWidth(null)) / 2;
        int y = (this.getHeight() - image.getHeight(null)) / 2;
        g.drawImage(image, x, y, null);
    }
}
