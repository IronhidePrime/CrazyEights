package view.labels;

import javax.swing.*;
import java.awt.*;

/**
 * label voor de trekstapel
 */
public class TrekStapelLabel extends JLabel{
    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/view/images/kaartAchterkant.png"));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
