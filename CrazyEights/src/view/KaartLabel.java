package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ironhide on 20/02/2015.
 */
public class KaartLabel extends JLabel{
    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/view/images/kaartLabel.png"));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        g.drawImage(image, 20, 10, getWidth(), getHeight(), this);
    }
}
