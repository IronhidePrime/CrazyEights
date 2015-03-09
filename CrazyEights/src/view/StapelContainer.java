package view;

import javax.swing.*;
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
}
