package view;

import controller.CrazyEightsController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

//TODO: REFACTOR!

public class SpelbordUI extends JFrame{
    private CrazyEightsController controller;
    private TrekStapelLabel lblTrekstapel;
    private AftrekStapelLabel lblAflegstapel;
    private SpelerLabel[] lblSpelers; //TODO: dynamish aanpassen, halen uit StartUI
    private JLabel[] lblKaarten; //TODO: als aantal spelers 2 is 7 kaarten, meerdere spelers 5
    //TODO: x aantam keer kaarten labels aanmaken, x is aantal spelers
    private JTextArea txtBeurt;

    public SpelbordUI(CrazyEightsController controller){
        super("Crazy Eights");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(1000,700);
        super.setLocationRelativeTo(null);
        maakComponenten();
        maakLayout();
        behandelEvents();
        super.setVisible(true);

        this.controller = controller;
    }

    public void maakComponenten(){
        lblAflegstapel = new AftrekStapelLabel();
        lblAflegstapel.setPreferredSize(new Dimension(150,250));
        lblTrekstapel = new TrekStapelLabel();
        lblTrekstapel.setPreferredSize(new Dimension(150,250));
        lblSpelers = new SpelerLabel[4];
        for(int i=0;i<lblSpelers.length;i++){
            lblSpelers[i] = new SpelerLabel();
            lblSpelers[i].setPreferredSize(new Dimension(50,50));
        }


        lblKaarten = new JLabel[7];
        for(int i=0;i<lblKaarten.length;i++){
            lblKaarten[i] = new JLabel("Kaart " + i);
        }
        txtBeurt = new JTextArea("Speler X is aan de beurt");
    }

    public void maakLayout(){

        //komen de 2 stapels in
        JPanel stapelContainer = new JPanel();
        stapelContainer.setLayout(new GridBagLayout());
        JPanel trekstapelContainer = new JPanel();
        JPanel aflegstapelContainer = new JPanel();
        trekstapelContainer.add(lblTrekstapel);
        aflegstapelContainer.add(lblAflegstapel);
        trekstapelContainer.setBorder(new EmptyBorder(0, 0, 0, 30));
        aflegstapelContainer.setBorder(new EmptyBorder(0, 30, 0, 0));
        stapelContainer.add(trekstapelContainer);
        stapelContainer.add(aflegstapelContainer);
        super.add(stapelContainer, BorderLayout.CENTER);
        //

        //2 containers aanmaken, 1 voor elke speler
        JPanel[] pnlSpelerKaartContainer = new JPanel[2]; //TODO: dynamisch maken aan het aantal spelers, haal uit UI
        JLabel test = new JLabel();
        JLabel test2 = new JLabel();
        for(int i=0; i<pnlSpelerKaartContainer.length;i++){
            pnlSpelerKaartContainer[i] = new JPanel();
            pnlSpelerKaartContainer[i].setLayout(new GridLayout(1,1));
            pnlSpelerKaartContainer[i].setPreferredSize(new Dimension(400, 100));
            pnlSpelerKaartContainer[i].add(lblSpelers[i]); //TODO: Speler label centreren

        }

        super.add(pnlSpelerKaartContainer[0], BorderLayout.SOUTH);
        super.add(pnlSpelerKaartContainer[1], BorderLayout.NORTH);
    }



    public void behandelEvents(){

    }

}
