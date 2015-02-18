package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ironhide on 14/02/2015.
 */
public class AflegStapelLabel extends JLabel {
    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/view/images/speelkaart.jpg"));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
