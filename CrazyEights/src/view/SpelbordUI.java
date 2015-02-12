package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

//TODO: REFACTOR!

public class SpelbordUI extends JFrame{
    private JLabel lblTrekstapel;
    private JLabel lblAflegstapel;
    private JLabel[] lblSpelers = new JLabel[4]; //TODO: dynamish aanpassen, halen uit StartUI
    private JLabel[] lblKaarten = new JLabel[7]; //TODO: als aantal spelers 2 is 7 kaarten, meerdere spelers 5
    //TODO: x aantam keer kaarten labels aanmaken, x is aantal spelers

    public SpelbordUI(){
        super("Crazy Eights");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(1000,700);
        super.setLocationRelativeTo(null);
        maakComponenten();
        maakLayout();
        behandelEvents();
        super.setVisible(true);
    }

    public void maakComponenten(){
        lblAflegstapel = new JLabel("Aflegstapel");
        lblAflegstapel.setOpaque(true);
        lblAflegstapel.setPreferredSize(new Dimension(150,250));
        lblAflegstapel.setBackground(Color.BLUE);
        lblTrekstapel = new JLabel("Trekstapel");
        lblTrekstapel.setOpaque(true);
        lblTrekstapel.setPreferredSize(new Dimension(150,250));
        lblTrekstapel.setBackground(Color.WHITE);
        for(int i=0;i<lblSpelers.length;i++){
            lblSpelers[i] = new JLabel("speler " + i);
        }

        for(int i=0;i<lblKaarten.length;i++){
            lblKaarten[i] = new JLabel("Kaart " + i);
        }
    }

    public void maakLayout(){

        JPanel stapelContainer = new JPanel();
        stapelContainer.setLayout(new GridBagLayout());

        JPanel trekstapelContainer = new JPanel();
        JPanel aflegstapelContainer = new JPanel();
        trekstapelContainer.add(lblTrekstapel);
        aflegstapelContainer.add(lblAflegstapel);
        trekstapelContainer.setBorder(new EmptyBorder(0, 0, 0, 10));
        aflegstapelContainer.setBorder(new EmptyBorder(0, 10, 0, 0));

        stapelContainer.add(trekstapelContainer);
        stapelContainer.add(aflegstapelContainer);
        super.add(stapelContainer, BorderLayout.CENTER);

        //4 spelerEnKaart containers aanmaken
        //4 kaart containers aanmaken
        JPanel[] pnlSpelerKaartContainer = new JPanel[4]; //TODO: dynamisch maken
        JPanel[] pnlKaartContainer = new JPanel[4];
        for(int i=0; i<pnlSpelerKaartContainer.length;i++){
            pnlSpelerKaartContainer[i] = new JPanel();
            pnlSpelerKaartContainer[i].setLayout(new BorderLayout());

            pnlKaartContainer[i] = new JPanel();
        }

        pnlSpelerKaartContainer[0].add(lblSpelers[0], BorderLayout.SOUTH);
        pnlSpelerKaartContainer[0].add(pnlKaartContainer[0], BorderLayout.NORTH);
        for(int i=0; i<lblKaarten.length;i++){
            pnlKaartContainer[0].add(lblKaarten[i]);
        }
        lblSpelers[0].setHorizontalAlignment(SwingConstants.CENTER);

        pnlSpelerKaartContainer[1].add(lblSpelers[1], BorderLayout.NORTH);
        pnlSpelerKaartContainer[1].add(pnlKaartContainer[1], BorderLayout.SOUTH);
        for(int i=0; i<lblKaarten.length;i++){
            pnlKaartContainer[1].add(lblKaarten[i]);
        }
        lblSpelers[1].setHorizontalAlignment(SwingConstants.CENTER);

        super.add(pnlSpelerKaartContainer[0], BorderLayout.SOUTH);
        super.add(pnlSpelerKaartContainer[1], BorderLayout.NORTH);
    }



    public void behandelEvents(){

    }

}
