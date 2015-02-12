package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class SpelBordUI extends JFrame{
    //GIT TEST!!
    private JLabel lblTrekstapel;
    private JLabel lblAflegstapel;
    private JLabel[] lblSpelers = new JLabel[4]; //dynamisch maken
    private JLabel lblKaart;

    public SpelBordUI(){
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
        lblKaart = new JLabel("kaart kut");
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

        //spelers en kaarten op positie plaatsen
        JPanel[] pnlSpelerKaartContainer = new JPanel[4]; //dynamisch maken
        for(int i=0; i<pnlSpelerKaartContainer.length; i++){ //4 containers initialiseren
            pnlSpelerKaartContainer[i] = new JPanel(new BorderLayout());
        }
        lblSpelers[0].setHorizontalAlignment(SwingConstants.CENTER);
        lblSpelers[1].setHorizontalAlignment(SwingConstants.CENTER);
        lblKaart.setHorizontalAlignment(SwingConstants.CENTER);

        //kaarten en spelers aan de 4 containers toevoegen
        pnlSpelerKaartContainer[0].add(lblSpelers[0], BorderLayout.SOUTH);
        pnlSpelerKaartContainer[0].add(lblKaart,BorderLayout.NORTH);
        pnlSpelerKaartContainer[1].add(lblSpelers[1], BorderLayout.NORTH);
        pnlSpelerKaartContainer[2].add(lblSpelers[2], BorderLayout.EAST);
        pnlSpelerKaartContainer[3].add(lblSpelers[3], BorderLayout.WEST);
        super.add(pnlSpelerKaartContainer[0], BorderLayout.SOUTH);
        super.add(pnlSpelerKaartContainer[1], BorderLayout.NORTH);
        super.add(pnlSpelerKaartContainer[2], BorderLayout.EAST);
        super.add(pnlSpelerKaartContainer[3], BorderLayout.WEST);

    }

    public void behandelEvents(){

    }

}
