package view.labels;

import javax.swing.*;
import java.awt.*;

/**
 * label voor het Crazy Eights logo (eigen design)
 */
public class LogoLabel extends JLabel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/view/images/logo2.png"));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        int x = (this.getWidth() - image.getWidth(null)) / 2;
        g.drawImage(image, x, 0, null);
    }
}
