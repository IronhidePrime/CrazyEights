package view;

import controller.Controller;
import model.Computer;
import model.Kaart;
import model.Mens;
import model.Speler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SpelbordUI extends JFrame {
    private Controller controller;

    private TrekStapelLabel lblTrekstapel;
    private AflegStapelLabel lblAflegstapel;

    private SpelerAfbeelding[] lblSpelersAfbeelding;

    private JPanel pnlKaartContainer[];
    private KaartContainer[] lpnlkaartContainer;

    private JPanel[] pnlSpelerContainer;
    private JLabel[] lblSpelerNaam;

    private List<Speler> spelers;

    private int kaartBreedte = 70;
    private int kaartOverlap = 40;
    private int aantalSpelers;

    private AchtergrondLabel achtergrond;
    private StapelContainer stapelContainer;


    public SpelbordUI(Controller controller) throws HeadlessException {
        super("Crazy Eights");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(1100, 900);
        super.setLocationRelativeTo(null);

        this.controller = controller;
        controller.startSpel();

        aantalSpelers = controller.getAantalSpelers();
        spelers = controller.getSpelers();

        maakComponenten();
        maakLayout();
        behandelEvents();
        super.setVisible(true);

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("/view/images/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setIconImage(image);
    }


    public void maakComponenten() {
        /**
         * bovenste kaart van de aflegstapel tonen
         */
        Kaart aflegKaart = controller.getSpelbord().getAflegstapel().getBovensteKaart();
        lblAflegstapel = new AflegStapelLabel(aflegKaart.getHorizontaleImageString(), controller);
        lblAflegstapel.setPreferredSize(new Dimension(100, 140));

        /**
         * trekstapel omgedraaid label
         */
        lblTrekstapel = new TrekStapelLabel();
        lblTrekstapel.setPreferredSize(new Dimension(100, 140));

        lblSpelerNaam = new JLabel[aantalSpelers];
        lblSpelersAfbeelding = new SpelerAfbeelding[aantalSpelers];

        /**
         * spelerContainer met een kaartContainer waarin een LayeredPane zit om kaarten over elkaar te tekenen
         */
        pnlSpelerContainer = new JPanel[aantalSpelers];
        pnlKaartContainer = new JPanel[aantalSpelers];
        lpnlkaartContainer = new KaartContainer[aantalSpelers];

        achtergrond = new AchtergrondLabel();
        stapelContainer = new StapelContainer();
    }

    public void maakLayout() {


        /**
         * container voor de 2 stapels (trek- & aflegstapel) aanmaken
         * aftrek + legstapel toevoegen
         * in het midden van ons frame plaatsen
         */

        JPanel trekstapelContainer = new JPanel();
        trekstapelContainer.add(lblTrekstapel);
        trekstapelContainer.setBorder(new EmptyBorder(0, 0, 0, 30));
        trekstapelContainer.setOpaque(false);
        stapelContainer.add(trekstapelContainer);

        JPanel aflegstapelContainer = new JPanel();
        aflegstapelContainer.add(lblAflegstapel);
        aflegstapelContainer.setOpaque(false);
        aflegstapelContainer.setBorder(new EmptyBorder(0, 30, 0, 0));
        stapelContainer.add(aflegstapelContainer);

        stapelContainer.setOpaque(false);

        achtergrond.setLayout(new BorderLayout());
        super.add(achtergrond, BorderLayout.CENTER);
        achtergrond.add(stapelContainer, BorderLayout.CENTER);


        /**
         * 1. speler label aanmaken en naam van de ingegeven speler toevoegen
         * 2. x aantal containers aanmaken, 1 voor elke speler -> speler afbeelding in het midden plaatsen
         * 3. voor elke speler panel met zijn kaarten aanmaken (layerdPane in Panel steken)
         *    voor 3 & 4 spelers (verticaal) is de preferredSize van de layeredPane verschillend van speler 1 & 2 (horizontaal)
         */
        for (int i = 0; i < aantalSpelers; i++) {
            //1
            lblSpelerNaam[i] = new JLabel();
            lblSpelerNaam[i].setText(controller.getSpelerNaam(i));
            lblSpelerNaam[i].setHorizontalAlignment(SwingConstants.CENTER);
            lblSpelerNaam[i].setForeground(Color.WHITE);

            lblSpelersAfbeelding[i] = new SpelerAfbeelding();
            lblSpelersAfbeelding[i].setPreferredSize(new Dimension(150, 100));

            //2
            pnlSpelerContainer[i] = new JPanel();
            pnlSpelerContainer[i].setLayout(new BorderLayout());
            pnlSpelerContainer[i].setOpaque(false);
            pnlSpelerContainer[i].add(lblSpelersAfbeelding[i], BorderLayout.CENTER);

            //3
            lpnlkaartContainer[i] = new KaartContainer(controller, lblAflegstapel, lblTrekstapel);
            pnlKaartContainer[i] = new JPanel();
            pnlKaartContainer[i].setOpaque(false);

        }
        if (aantalSpelers >= 2) {
            pnlKaartContainer[0].add(lpnlkaartContainer[0]);
            pnlKaartContainer[1].add(lpnlkaartContainer[1]);
            tweeSpelersContainerLayOut();
        }

        if (aantalSpelers >= 3) {
            //voor panel verticaal te centreren -> gridbaglayout
            pnlKaartContainer[2].setLayout(new GridBagLayout());
            pnlKaartContainer[2].add(lpnlkaartContainer[2], new GridBagConstraints());
            lpnlkaartContainer[2].zetBreedte(100, kaartOverlap * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte);
            derdeSpelerContainerLayOut();
        }

        if (aantalSpelers == 4) {
            //voor panel verticaal te centreren -> gridbaglayout
            pnlKaartContainer[3].setLayout(new GridBagLayout());
            pnlKaartContainer[3].add(lpnlkaartContainer[3], new GridBagConstraints());
            lpnlkaartContainer[3].zetBreedte(100, kaartOverlap * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte);
            vierdeSpelerContainerLayOut();
        }

        /**
         * elke speler zijn kaartenlijst opvullen met JLabels met een image van zijn overeenkomstige kaarten
         * aantal kaarten per speler hangt af van het aantal spelers (2 spelers -> 7 kaarten, 3 of 4 spelers -> 5 kaarten)
         */

        for (int i = 0; i < aantalSpelers; i++) {
            lpnlkaartContainer[i].maakLists(i);
            if (i == 0 || i == 1) {
                lpnlkaartContainer[i].tekenKaartLabels();
            }

            if (i == 2 || i == 3) {
                lpnlkaartContainer[i].tekenKaartLabelsVerticaal();
            }
        }
    }

    /**
     * containers worden toegevoegd afhankelijk van het aantal spelers op de aangegeven positie
     */
    public void tweeSpelersContainerLayOut() {
        achtergrond.add(pnlSpelerContainer[0], BorderLayout.SOUTH);
        pnlSpelerContainer[0].add(lblSpelerNaam[0], BorderLayout.SOUTH);
        pnlSpelerContainer[0].add(pnlKaartContainer[0], BorderLayout.NORTH);

        achtergrond.add(pnlSpelerContainer[1], BorderLayout.NORTH);
        pnlSpelerContainer[1].add(lblSpelerNaam[1], BorderLayout.NORTH);
        pnlSpelerContainer[1].add(pnlKaartContainer[1], BorderLayout.SOUTH);
    }

    public void derdeSpelerContainerLayOut() {
        JPanel pnlLegeSpeler = new JPanel();
        pnlLegeSpeler.setOpaque(false);
        pnlLegeSpeler.setPreferredSize(new Dimension(250, 100));

        achtergrond.add(pnlLegeSpeler, BorderLayout.WEST);
        achtergrond.add(pnlSpelerContainer[2], BorderLayout.EAST);
        pnlSpelerContainer[2].add(lblSpelerNaam[2], BorderLayout.EAST);
        pnlSpelerContainer[2].add(pnlKaartContainer[2], BorderLayout.WEST);
    }

    public void vierdeSpelerContainerLayOut() {
        achtergrond.add(pnlSpelerContainer[3], BorderLayout.WEST);
        pnlSpelerContainer[3].add(lblSpelerNaam[3], BorderLayout.WEST);
        pnlSpelerContainer[3].add(pnlKaartContainer[3], BorderLayout.EAST);
    }


    public void behandelEvents() {
        for (int i = 0; i < spelers.size(); i++) {
            if (spelers.get(i) instanceof Mens) {
                System.out.println("speler " + i + "krijgt een mens event");
                lpnlkaartContainer[i].speelKaartEvent(spelers.get(i).getKaarten(), i);
                lpnlkaartContainer[i].trekKaartEvent(i);
            }
        }
    }
}
