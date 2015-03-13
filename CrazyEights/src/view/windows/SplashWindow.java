package view.windows;

import javax.swing.*;

/**
 * geeft een splashWindow bij het laden
 */
public class SplashWindow extends JWindow {
    public SplashWindow() {
        JLabel imageLabel = new JLabel(new
                ImageIcon(getClass().getResource("/view/images/splashScreen.jpg")));
        add(imageLabel);
        setSize(300, 300);
        setLocationRelativeTo(this);
        setAlwaysOnTop(true);
        pack();
        setVisible(true);
        try {
            Thread.sleep(5000); // 5 sec pauzeren
        } catch (InterruptedException e) {
            // doe niets
        }
        this.dispose();
    }
}

