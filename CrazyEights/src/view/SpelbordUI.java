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

    /*
    private List<KaartLabel> kaartenSpeler0;
    private List<KaartLabel> kaartenSpeler1;
    private List<KaartLabel> kaartenSpeler2;
    private List<KaartLabel> kaartenSpeler3;
    */

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
        lblAflegstapel = new AflegStapelLabel(aflegKaart.getHorizontaleImageString(),controller);
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

        /**
         * voor elke speler een lijst met JLabels voor zijn kaarten
         * de lijsten opvullen adhv het aantal spelers
         * de menselijke spelers hun kaarten zijn met de beeldkant naar BOVEN
         * de computerspelers hun kaarten zijn met de beeldkant naar BENEDEN
         */

        /*
        kaartenSpeler0 = new LinkedList<>();
        kaartenSpeler1 = new LinkedList<>();
        kaartenSpeler2 = new LinkedList<>();
        kaartenSpeler3 = new LinkedList<>();
        for (int i = 0; i < controller.getAantalKaartenSpeler(); i++) {
            kaartenSpeler0.add(new KaartLabel(controller.getSpelerKaarten(0).get(i).getHorizontaleImageString(), controller));
            if (spelers.get(1) instanceof Mens) {
                kaartenSpeler1.add(new KaartLabel(controller.getSpelerKaarten(1).get(i).getHorizontaleImageString(), controller));
                if (aantalSpelers >= 3) {
                    kaartenSpeler2.add(new KaartLabel(controller.getSpelerKaarten(2).get(i).getVerticaleImageString(), controller));
                }
                if (aantalSpelers == 4) {
                    kaartenSpeler3.add(new KaartLabel(controller.getSpelerKaarten(3).get(i).getVerticaleImageString(), controller));
                }
            } else if (spelers.get(1) instanceof Computer) {
                kaartenSpeler1.add(new KaartLabel(controller.getSpelerKaarten(1).get(i).getOmgekeerdeImageString(), controller));
                if (aantalSpelers >= 3) {
                    kaartenSpeler2.add(new KaartLabel(controller.getSpelerKaarten(2).get(i).getOmgekeerdeImageString(), controller));
                }
                if (aantalSpelers == 4) {
                    kaartenSpeler3.add(new KaartLabel(controller.getSpelerKaarten(3).get(i).getOmgekeerdeImageString(), controller));
                }
            }
        }*/

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
        achtergrond.add(stapelContainer,BorderLayout.CENTER);


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
            lpnlkaartContainer[i] = new KaartContainer(controller,lblAflegstapel);
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
            lpnlkaartContainer[2].setPreferredSize(new Dimension(100, kaartOverlap * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte));
            derdeSpelerContainerLayOut();
        }

        if (aantalSpelers == 4){
            //voor panel verticaal te centreren -> gridbaglayout
            pnlKaartContainer[3].setLayout(new GridBagLayout());
            pnlKaartContainer[3].add(lpnlkaartContainer[3], new GridBagConstraints());
            lpnlkaartContainer[3].setPreferredSize(new Dimension(100, kaartOverlap * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte));
            vierdeSpelerContainerLayOut();
        }

        /**
         * elke speler zijn kaartenlijst opvullen met JLabels met een image van zijn overeenkomstige kaarten
         * aantal kaarten per speler hangt af van het aantal spelers (2 spelers -> 7 kaarten, 3 of 4 spelers -> 5 kaarten)
         */

        lpnlkaartContainer[0].maakLists(0);
        lpnlkaartContainer[1].maakLists(1);
        lpnlkaartContainer[0].tekenKaartLabels();
        lpnlkaartContainer[1].tekenKaartLabels();
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
        /**
         * wanneer men op de trekstapel klikt krijgt de speler een nieuwe kaart
         */

        lpnlkaartContainer[0].speelKaartEvent(spelers.get(0).getKaarten(),0);
        lpnlkaartContainer[1].speelKaartEvent(spelers.get(1).getKaarten(),1);
    }




    public void herschikKaartenNaTrekken(List<KaartLabel> spelerKaartLabels,List<Kaart> kaartenSpelers, int indexContainer, int SpelerNr){
        lblTrekstapel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                System.out.println("er is op de trekstapel geklikt");


                if (spelers.get(SpelerNr).getAanBeurt()) {
                    Kaart getrokkenKaart = controller.getSpelbord().getTrekstapel().neemKaart();
                    KaartLabel lblGetrokkenKaart = new KaartLabel(getrokkenKaart.getHorizontaleImageString(), controller);
                    kaartenSpelers.add(getrokkenKaart);
                    lpnlkaartContainer[indexContainer].removeAll();

                    kaartOverlap = 0;
                    if (indexContainer == 0 || indexContainer == 1) {
                        spelerKaartLabels.add(lblGetrokkenKaart);
                        for (int i = 0; i < spelerKaartLabels.size(); i++) {
                            spelerKaartLabels.get(i).setBounds(kaartOverlap, 0, kaartBreedte, 100);
                            kaartOverlap += 40;
                            lpnlkaartContainer[indexContainer].add(spelerKaartLabels.get(i), new Integer(i));
                        }
                        kaartOverlap = 40;
                        lpnlkaartContainer[indexContainer].setPreferredSize(new Dimension(kaartOverlap * (spelerKaartLabels.size() - 1) + kaartBreedte, 100));
                    } else if (indexContainer == 2 || indexContainer == 3) {
                        KaartLabel lblVerticaalGetrokkenKaart = new KaartLabel(getrokkenKaart.getVerticaleImageString(), controller);
                        spelerKaartLabels.add(lblVerticaalGetrokkenKaart);
                        for (int i = 0; i < spelerKaartLabels.size(); i++) {
                            spelerKaartLabels.get(i).setBounds(0, kaartOverlap, 100, kaartBreedte);
                            kaartOverlap += 40;
                            lpnlkaartContainer[indexContainer].add(spelerKaartLabels.get(i), new Integer(i));
                        }
                        kaartOverlap = 40;
                        lpnlkaartContainer[indexContainer].setPreferredSize(new Dimension(100, kaartOverlap * (spelerKaartLabels.size() - 1) + kaartBreedte));
                    }
                    //System.out.println("breedte jlayeredpane" + lpnlkaartContainer[indexContainer].getWidth());
                    //herschikKaarten(spelerKaartLabels, kaartenSpelers, indexContainer, SpelerNr);
                }
                revalidate();
                repaint();
                System.out.println("Size trekstapel na het trekken van een kaart" + controller.getSpelbord().getTrekstapel().getKaarten().size());
            }
        });
    }
}
