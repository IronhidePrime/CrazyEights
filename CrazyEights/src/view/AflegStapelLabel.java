package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class AflegStapelLabel extends JLabel {
    private String imageString;
    private Controller controller;

    public AflegStapelLabel(String imageString, Controller controller) {
        this.imageString = imageString;
        this.controller = controller;
    }
    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(imageString));
        Image image = imageIcon.getImage();
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
