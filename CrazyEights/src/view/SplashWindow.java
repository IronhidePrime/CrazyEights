package view;

import javax.swing.*;

public class SplashWindow extends JWindow {
    public SplashWindow() {
        JLabel imageLabel = new JLabel(new
                ImageIcon(getClass().getResource("images/splashScreen.jpg")));
        add(imageLabel);
        setSize(300, 300);
        setLocationRelativeTo(this);
        setAlwaysOnTop(true);
        pack();
        setVisible(true);
        try {
            Thread.sleep(3000); // 3 sec pauzeren
        } catch (InterruptedException e) {
            // doe niets
        }
        this.dispose();
    }
}

