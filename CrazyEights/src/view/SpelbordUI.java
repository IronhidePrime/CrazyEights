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
        lblAflegstapel.setPreferredSize(new Dimension(150,250));
        lblTrekstapel = new TrekStapelLabel();
        lblTrekstapel.setPreferredSize(new Dimension(150,250));
        lblSpelers = new SpelerLabel[controller.getAantalSpelers()];
        for(int i=0;i<lblSpelers.length;i++){
            lblSpelers[i] = new SpelerLabel();
            lblSpelers[i].setPreferredSize(new Dimension(50,50));
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

        //2 containers aanmaken, 1 voor elke speler
        JPanel[] pnlSpelerKaartContainer = new JPanel[controller.getAantalSpelers()];
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
