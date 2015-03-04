package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ironhide on 4/03/2015.
 */
public class AchtergrondLabel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/view/images/achtergrond.jpg"));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        repaint();
    }
}
