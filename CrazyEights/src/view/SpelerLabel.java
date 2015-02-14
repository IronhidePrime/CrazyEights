package view;

import javax.swing.*;
import java.awt.*;

public class SpelerLabel extends JLabel {
    protected void paintComponent(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/view/images/spelerIcon.png"));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 85, 85, 0, 0,
                image.getWidth(this), image.getHeight(this), this);
    }
}
