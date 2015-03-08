package view;

import controller.Controller;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StapelContainer extends JPanel {
    private JPanel trekStapelContainer;
    private JPanel aflegStapelContainer;


    public StapelContainer(){
        trekStapelContainer = new JPanel();
        aflegStapelContainer = new JPanel();

        setLayout();
    }

    public void setLayout(){
        this.setLayout(new GridBagLayout());
    }

    public void maakLayout(){
        //trekStapelContainer.setBorder(new EmptyBorder(0, 0, 0, 30));
        //trekStapelContainer.add(lblTrekStapel, BorderLayout.CENTER);

        //aflegStapelContainer.setBorder(new EmptyBorder(0, 0, 0, 30));
        //aflegStapelContainer.add(lblAflegStapel, BorderLayout.CENTER);

        this.add(trekStapelContainer);
        this.add(aflegStapelContainer);
        this.setOpaque(false);
    }
}
