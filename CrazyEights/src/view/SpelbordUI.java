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

//TODO: niet alle kaartImages zijn aanwezig, SANDER KUT!!!

public class SpelbordUI extends JFrame {
    private Controller controller;

    private TrekStapelLabel lblTrekstapel;
    private AflegStapelLabel lblAflegstapel;

    private SpelerAfbeelding[] lblSpelersAfbeelding;

    private JPanel pnlKaartContainer[];
    private JLayeredPane[] lpnlkaartContainer;

    private JPanel[] pnlSpelerContainer;
    private JLabel[] lblSpelerNaam;

    private List<KaartLabel> kaartenSpeler0;
    private List<KaartLabel> kaartenSpeler1;
    private List<KaartLabel> kaartenSpeler2;
    private List<KaartLabel> kaartenSpeler3;

    private int kaartBreedte = 70;
    private int kaartOverlap = 40;

    private AchtergrondLabel achtergrond;



    public SpelbordUI(Controller controller) throws HeadlessException {
        super("Crazy Eights");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(1100, 900);
        super.setLocationRelativeTo(null);

        this.controller = controller;
        controller.startSpel();

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
        lblAflegstapel = new AflegStapelLabel(aflegKaart.getHorizontaleImageString());
        lblAflegstapel.setPreferredSize(new Dimension(100, 140));

        /**
         * trekstapel omgedraaid label
         */
        lblTrekstapel = new TrekStapelLabel();
        lblTrekstapel.setPreferredSize(new Dimension(100, 140));

        lblSpelerNaam = new JLabel[controller.getAantalSpelers()];
        lblSpelersAfbeelding = new SpelerAfbeelding[controller.getAantalSpelers()];

        /**
         * spelerContainer met een kaartContainer waarin een LayeredPane zit om kaarten over elkaar te tekenen
         */
        pnlSpelerContainer = new JPanel[controller.getAantalSpelers()];
        pnlKaartContainer = new JPanel[controller.getAantalSpelers()];
        lpnlkaartContainer = new JLayeredPane[controller.getAantalSpelers()];

        /**
         * voor elke speler een lijst met JLabels voor zijn kaarten
         * de lijsten opvullen adhv het aantal spelers
         * de menselijke spelers hun kaarten zijn met de beeldkant naar BOVEN
         * de computerspelers hun kaarten zijn met de beeldkant naar BENEDEN
         */
        kaartenSpeler0 = new LinkedList<>();
        kaartenSpeler1 = new LinkedList<>();
        kaartenSpeler2 = new LinkedList<>();
        kaartenSpeler3 = new LinkedList<>();
        for (int i = 0; i < controller.getAantalKaartenSpeler(); i++) {
            kaartenSpeler0.add(new KaartLabel(controller.getSpelerKaarten(0).get(i).getHorizontaleImageString(), controller));
            if (controller.getSpelers().get(1) instanceof Mens) {
                kaartenSpeler1.add(new KaartLabel(controller.getSpelerKaarten(1).get(i).getHorizontaleImageString(), controller));
                if (controller.getAantalSpelers() >= 3) {
                    kaartenSpeler2.add(new KaartLabel(controller.getSpelerKaarten(2).get(i).getVerticaleImageString(), controller));
                }
                if (controller.getAantalSpelers() == 4) {
                    kaartenSpeler3.add(new KaartLabel(controller.getSpelerKaarten(3).get(i).getVerticaleImageString(), controller));
                }
            } else if (controller.getSpelers().get(1) instanceof Computer) {
                kaartenSpeler1.add(new KaartLabel(controller.getSpelerKaarten(1).get(i).getOmgekeerdeImageString(), controller));
                if (controller.getAantalSpelers() >= 3) {
                    kaartenSpeler2.add(new KaartLabel(controller.getSpelerKaarten(2).get(i).getOmgekeerdeImageString(), controller));
                }
                if (controller.getAantalSpelers() == 4) {
                    kaartenSpeler3.add(new KaartLabel(controller.getSpelerKaarten(3).get(i).getOmgekeerdeImageString(), controller));
                }
            }
        }

        achtergrond = new AchtergrondLabel();
    }

    public void maakLayout() {


        /**
         * container voor de 2 stapels (trek- & aflegstapel) aanmaken
         * aftrek + legstapel toevoegen
         * in het midden van ons frame plaatsen
         */
        JPanel stapelContainer = new JPanel();
        stapelContainer.setLayout(new GridBagLayout());
        JPanel trekstapelContainer = new JPanel();
        JPanel aflegstapelContainer = new JPanel();
        trekstapelContainer.add(lblTrekstapel);
        aflegstapelContainer.add(lblAflegstapel);
        aflegstapelContainer.setOpaque(false);
        trekstapelContainer.setBorder(new EmptyBorder(0, 0, 0, 30));
        trekstapelContainer.setOpaque(false);
        aflegstapelContainer.setBorder(new EmptyBorder(0, 30, 0, 0));
        stapelContainer.add(trekstapelContainer);
        stapelContainer.add(aflegstapelContainer);
        stapelContainer.setOpaque(false);
        //super.add(stapelContainer, BorderLayout.CENTER);

        achtergrond.setLayout(new BorderLayout());
        super.add(achtergrond, BorderLayout.CENTER);
        achtergrond.add(stapelContainer,BorderLayout.CENTER);

        /**
         * 1. speler label aanmaken en naam van de ingegeven speler toevoegen
         * 2. x aantal containers aanmaken, 1 voor elke speler -> speler afbeelding in het midden plaatsen
         * 3. voor elke speler panel met zijn kaarten aanmaken (layerdPane in Panel steken)
         *    voor 3 & 4 spelers (verticaal) is de preferredSize van de layeredPane verschillend van speler 1 & 2 (horizontaal)
         */
        for (int i = 0; i < controller.getAantalSpelers(); i++) {
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
            lpnlkaartContainer[i] = new JLayeredPane();
            lpnlkaartContainer[i].setPreferredSize(new Dimension(kaartOverlap  * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte, 100));
            pnlKaartContainer[i] = new JPanel();
            pnlKaartContainer[i].setOpaque(false);
            lpnlkaartContainer[i].setOpaque(false);

        }
        if (controller.getAantalSpelers() >= 2) {
            pnlKaartContainer[0].add(lpnlkaartContainer[0]);
            pnlKaartContainer[1].add(lpnlkaartContainer[1]);
            tweeSpelersContainerLayOut();
        }

        if (controller.getAantalSpelers() >= 3) {
            //voor panel verticaal te centreren -> gridbaglayout
            pnlKaartContainer[2].setLayout(new GridBagLayout());
            pnlKaartContainer[2].add(lpnlkaartContainer[2], new GridBagConstraints());
            lpnlkaartContainer[2].setPreferredSize(new Dimension(100, kaartOverlap * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte));
            derdeSpelerContainerLayOut();
        }

        if (controller.getAantalSpelers() == 4){
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

        kaartOverlap = 0;
        for (int j = 0; j < controller.getAantalKaartenSpeler(); j++) {
            kaartenSpeler0.get(j).setBounds(kaartOverlap, 0, kaartBreedte, 100);
            kaartenSpeler1.get(j).setBounds(kaartOverlap, 0, kaartBreedte, 100);
            lpnlkaartContainer[0].add(kaartenSpeler0.get(j), new Integer(j));
            lpnlkaartContainer[1].add(kaartenSpeler1.get(j), new Integer(j));
            if (pnlSpelerContainer.length >= 3) {
                kaartenSpeler2.get(j).setBounds(0, kaartOverlap, 100, kaartBreedte);
                lpnlkaartContainer[2].add(kaartenSpeler2.get(j), new Integer(j));
            }
            if (pnlSpelerContainer.length == 4) {
                kaartenSpeler3.get(j).setBounds(0, kaartOverlap, 100, kaartBreedte);
                lpnlkaartContainer[3].add(kaartenSpeler3.get(j), new Integer(j));
            }
            kaartOverlap += 40;
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
        herschikKaarten(kaartenSpeler0, controller.getSpelerKaarten(0), 0, 0);
        herschikKaartenNaTrekken(kaartenSpeler0, controller.getSpelerKaarten(0), 0, 0);
        if (controller.getSpelers().get(1) instanceof Mens) {
            herschikKaarten(kaartenSpeler1, controller.getSpelerKaarten(1), 1, 1);
            herschikKaartenNaTrekken(kaartenSpeler1, controller.getSpelerKaarten(1), 1, 1);
            if (controller.getAantalSpelers() >= 3) {
                herschikKaarten(kaartenSpeler2, controller.getSpelerKaarten(2), 2, 2);

                herschikKaartenNaTrekken(kaartenSpeler2, controller.getSpelerKaarten(2), 2, 2);
            }
            if (controller.getAantalSpelers() == 4) {
                herschikKaarten(kaartenSpeler3, controller.getSpelerKaarten(3), 3, 3);
                herschikKaartenNaTrekken(kaartenSpeler3, controller.getSpelerKaarten(3), 3, 3);
            }
        }
    }


    public void herschikKaarten(List<KaartLabel> spelerKaartLabels,List<Kaart> spelerKaarten, int indexContainer, int spelerNr){
        for (KaartLabel kaartLabel : spelerKaartLabels) {
            kaartLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    //hierin gaat komen het index getal van de kaart die uit kaartobjecten moeten worden verwijderd
                    int index = 0;
                    //
                    super.mouseReleased(e);
                    String imageString = kaartLabel.getImageString();
                    //DEBUG//
                    System.out.println("image string clickedCard label is" + imageString);
                    //

                    //deze loop gaat de imgstring van het geklikte kaartlabel vergelijken met de image string, als het dezelfde is dan hebben we de index van de kaart die we moeten verwijderen
                    for (int i=0; i<spelerKaarten.size();i++){
                        if (spelerKaarten.get(i).getHorizontaleImageString().equals(imageString) || spelerKaarten.get(i).getVerticaleImageString().equals(imageString)){
                            index = i;
                            //DEBUG//
                            System.out.println("imageString cardObject is " + spelerKaarten.get(i).getHorizontaleImageString());
                            System.out.println("index is " + index);
                            //
                            break;
                        }
                    }

                    //DEBUG//
                    System.out.println("kaart die check krijgt van methode speelKaartMogelijk() is" + spelerKaarten.get(index).getHorizontaleImageString());
                    System.out.println("deze kaart wordt vergeleken met " + controller.getSpelbord().getAflegstapel().getBovensteKaart().getHorizontaleImageString());
                    //
                    if (controller.speelKaartMogelijk(spelerKaarten.get(index)) && controller.getSpelers().get(spelerNr).getAanBeurt()) {
                        String horizontaleImageString = spelerKaarten.get(index).getHorizontaleImageString();
                        spelerKaartLabels.remove(kaartLabel);
                        lpnlkaartContainer[indexContainer].remove(kaartLabel);
                        controller.speelKaart(spelerKaarten.get(index),0);
                        lpnlkaartContainer[indexContainer].removeAll();

                        kaartOverlap = 0;
                        if (indexContainer == 0 || indexContainer == 1){
                            for (KaartLabel k : spelerKaartLabels) {
                                k.setBounds(kaartOverlap, 0, kaartBreedte, 100);
                                kaartOverlap += 40;
                                lpnlkaartContainer[indexContainer].add(k);
                                lblAflegstapel.setImageString(imageString);
                            }
                            //lpnlkaartContainer[i].setPreferredSize(new Dimension(kaartOverlap  * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte, 100));
                            kaartOverlap = 40;
                            lpnlkaartContainer[indexContainer].setPreferredSize(new Dimension(kaartOverlap * (spelerKaartLabels.size() - 1) + kaartBreedte, 100));
                        } else if (indexContainer == 2 || indexContainer == 3){

                            for (KaartLabel k : spelerKaartLabels) {
                                k.setBounds(0, kaartOverlap, 100, kaartBreedte);
                                kaartOverlap += 40;
                                lpnlkaartContainer[indexContainer].add(k);
                                lblAflegstapel.setImageString(horizontaleImageString);
                            }
                            kaartOverlap = 40;
                            lpnlkaartContainer[indexContainer].setPreferredSize(new Dimension(100, kaartOverlap * (spelerKaartLabels.size() - 1) + kaartBreedte));
                        }

                        controller.getSpelers().get(spelerNr).setAanBeurt(false);
                        if (spelerNr == controller.getAantalSpelers()-1) {
                            controller.getSpelers().get(0).setAanBeurt(true);
                        } else {
                            controller.getSpelers().get(spelerNr+1).setAanBeurt(true);
                        }





                        //DEBUG//
                        System.out.println("size of cardLabels after removal of a card" + spelerKaartLabels.size());
                        System.out.println("size of cardObjects after removal of a card" + spelerKaarten.size());
                        System.out.println("de aflegstapel heeft na het spelen van een kaart" + controller.getSpelbord().getAflegstapel().getKaarten().size());
                        //
                    }
                    revalidate();
                    repaint();
                }
            });
        }
    }
    public void herschikKaartenNaTrekken(List<KaartLabel> spelerKaartLabels,List<Kaart> kaartenSpelers, int indexContainer, int SpelerNr){
        lblTrekstapel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                System.out.println("er is op de trekstapel geklikt");
                Kaart getrokkenKaart = controller.getSpelbord().getTrekstapel().neemKaart();
                KaartLabel lblGetrokkenKaart = new KaartLabel(getrokkenKaart.getHorizontaleImageString(), controller);

                if (controller.getSpelers().get(SpelerNr).getAanBeurt()) {
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
                    herschikKaarten(spelerKaartLabels, kaartenSpelers, indexContainer, SpelerNr);
                }
                revalidate();
                repaint();
            }
        });
    }
}
