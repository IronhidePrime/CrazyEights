package view;

import controller.Controller;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.LinkedList;

//TODO: REFACTOR!

public class SpelbordUI extends JFrame{
    private Controller controller;
    private TrekStapelLabel lblTrekstapel;
    private AflegStapelLabel lblAflegstapel;
    private SpelerLabel[] lblSpelers;
    private List<JLabel> lblKaarten;
    private JTextArea txtBeurt;

    public SpelbordUI(Controller controller){
        super("Crazy Eights");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(1000,700);
        super.setLocationRelativeTo(null);

        this.controller = controller;
        controller.startSpel();

        maakComponenten();
        maakLayout();
        behandelEvents();
        super.setVisible(true);


    }

    public void maakComponenten(){
        lblAflegstapel = new AflegStapelLabel();
        lblAflegstapel.setPreferredSize(new Dimension(140,200));
        lblTrekstapel = new TrekStapelLabel();
        lblTrekstapel.setPreferredSize(new Dimension(140,200));
        lblSpelers = new SpelerLabel[controller.getAantalSpelers()];
        for(int i=0;i<lblSpelers.length;i++){
            lblSpelers[i] = new SpelerLabel();
        }


        lblKaarten = new LinkedList<JLabel>();

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

        //x aantal containers aanmaken, 1 voor elke speler
        //x is aantal spelers
        JPanel[] pnlSpelerKaartContainer = new JPanel[controller.getAantalSpelers()];
        for(int i=0; i<pnlSpelerKaartContainer.length;i++){
            pnlSpelerKaartContainer[i] = new JPanel();
            pnlSpelerKaartContainer[i].setLayout(new BorderLayout());
            pnlSpelerKaartContainer[i].add(lblSpelers[i], BorderLayout.CENTER);
            pnlSpelerKaartContainer[i].setPreferredSize(new Dimension(115, 100));

        }

        //speler label aanmaken en naam van de ingegeven speler toevoegen
        JLabel[] lblSpelerNaam = new JLabel[controller.getAantalSpelers()];
        for (int i=0; i<controller.getAantalSpelers(); i++){
            lblSpelerNaam[i] = new JLabel();
            lblSpelerNaam[i].setText(controller.getSpelerNaam(i));
            lblSpelerNaam[i].setHorizontalAlignment(SwingConstants.CENTER);
        }

        //gaat speler label op 1 kant plaatsen, er zijn altijd minstens 2 spelers als het er 3 zij komt er een label east bij, bij 4 spelers komt er een label east en west
        if(pnlSpelerKaartContainer.length == 2){
            super.add(pnlSpelerKaartContainer[0], BorderLayout.SOUTH);
            pnlSpelerKaartContainer[0].add(lblSpelerNaam[0], BorderLayout.SOUTH);
            super.add(pnlSpelerKaartContainer[1], BorderLayout.NORTH);
            pnlSpelerKaartContainer[1].add(lblSpelerNaam[1], BorderLayout.NORTH);
        } else if (pnlSpelerKaartContainer.length == 3) {
            super.add(pnlSpelerKaartContainer[0], BorderLayout.SOUTH);
            pnlSpelerKaartContainer[0].add(lblSpelerNaam[0], BorderLayout.SOUTH);
            super.add(pnlSpelerKaartContainer[1], BorderLayout.NORTH);
            pnlSpelerKaartContainer[1].add(lblSpelerNaam[1], BorderLayout.NORTH);
            super.add(pnlSpelerKaartContainer[2], BorderLayout.EAST);
            pnlSpelerKaartContainer[2].add(lblSpelerNaam[2], BorderLayout.EAST);
        } else {
            super.add(pnlSpelerKaartContainer[0], BorderLayout.SOUTH);
            pnlSpelerKaartContainer[0].add(lblSpelerNaam[0], BorderLayout.SOUTH);
            super.add(pnlSpelerKaartContainer[1], BorderLayout.NORTH);
            pnlSpelerKaartContainer[1].add(lblSpelerNaam[1], BorderLayout.NORTH);
            super.add(pnlSpelerKaartContainer[2], BorderLayout.EAST);
            pnlSpelerKaartContainer[2].add(lblSpelerNaam[2], BorderLayout.EAST);
            super.add(pnlSpelerKaartContainer[3], BorderLayout.WEST);
            pnlSpelerKaartContainer[3].add(lblSpelerNaam[3], BorderLayout.WEST);
        }
    }
    public void behandelEvents(){

    }

}
