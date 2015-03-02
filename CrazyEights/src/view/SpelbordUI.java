package view;

import controller.Controller;
import model.Kaart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;


//TODO: REFACTOR!
//TODO: rekening houden met resizen...

public class SpelbordUI extends JFrame {
    private Controller controller;

    private TrekStapelLabel lblTrekstapel;
    private AflegStapelLabel lblAflegstapel;

    private SpelerAfbeelding[] lblSpelersAfbeelding;

    private JPanel pnlKaartContainer[];
    private JLayeredPane[] lpnlkaartContainer;

    private JPanel[] pnlSpelerContainer;
    private JLabel[] lblSpelerNaam;

    private List<KaartLabel> kaartenSpeler1;
    private List<KaartLabel> kaartenSpeler2;
    private List<KaartLabel> kaartenSpeler3;
    private List<KaartLabel> kaartenSpeler4;

    private final int AANTAL_KAARTEN_2_SPELERS = 7;
    private final int AANTAL_KAARTEN_MEERDERE_SPELERS = 5;

    private int kaartBreedte = 70;
    private int kaartBreedteVerticaal = 100;

    public SpelbordUI(Controller controller) throws HeadlessException {
        super("Crazy Eights");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(1000, 900);
        super.setLocationRelativeTo(null);

        this.controller = controller;
        controller.startSpel();

        maakComponenten();
        maakLayout();
        behandelEvents();
        super.setVisible(true);
    }

    public void maakComponenten() {
        lblAflegstapel = new AflegStapelLabel(controller.getSpelbord().getAflegstapel().getBovensteKaart().getHorizontaleImageString());
        lblAflegstapel.setPreferredSize(new Dimension(100, 140));
        lblTrekstapel = new TrekStapelLabel();
        lblTrekstapel.setPreferredSize(new Dimension(100, 140));

        lblSpelerNaam = new JLabel[controller.getAantalSpelers()];
        lblSpelersAfbeelding = new SpelerAfbeelding[controller.getAantalSpelers()];

        pnlSpelerContainer = new JPanel[controller.getAantalSpelers()];
        pnlKaartContainer = new JPanel[controller.getAantalSpelers()];
        lpnlkaartContainer = new JLayeredPane[controller.getAantalSpelers()];

        kaartenSpeler1 = new LinkedList<>();
        kaartenSpeler2 = new LinkedList<>();
        kaartenSpeler3 = new LinkedList<>();
        kaartenSpeler4 = new LinkedList<>();
        for (int i = 0; i < controller.getAantalKaartenSpeler(); i++) {
            kaartenSpeler1.add(new KaartLabel(controller.getSpelerKaarten(0).get(i).getHorizontaleImageString(), controller));
            kaartenSpeler2.add(new KaartLabel(controller.getSpelerKaarten(1).get(i).getHorizontaleImageString(), controller));
            if (controller.getAantalSpelers() == 3) {
                kaartenSpeler3.add(new KaartLabel(controller.getSpelerKaarten(2).get(i).getVerticaleImageString(), controller));
            } else if (controller.getAantalSpelers() == 4) {
                kaartenSpeler3.add(new KaartLabel(controller.getSpelerKaarten(2).get(i).getVerticaleImageString(), controller));
                kaartenSpeler4.add(new KaartLabel(controller.getSpelerKaarten(3).get(i).getVerticaleImageString(), controller));
            }
        }
    }

    public void maakLayout() {
        /**
         * Container voor de 2 stapels aanmaken
         * Aftrek + legstapel toevoegen
         */
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

        /**
         * 1. speler label aanmaken en naam van de ingegeven speler toevoegen
         * 2. x aantal containers aanmaken, 1 voor elke speler -> speler afbeelding in het midden plaatsen
         * 3. voor elke speler panel met zijn kaarten aanmaken
         */
        for (int i = 0; i < controller.getAantalSpelers(); i++) {
            //1
            lblSpelerNaam[i] = new JLabel();
            lblSpelerNaam[i].setText(controller.getSpelerNaam(i));
            lblSpelerNaam[i].setHorizontalAlignment(SwingConstants.CENTER);

            lblSpelersAfbeelding[i] = new SpelerAfbeelding();
            lblSpelersAfbeelding[i].setPreferredSize(new Dimension(150, 100));

            //2
            pnlSpelerContainer[i] = new JPanel();
            pnlSpelerContainer[i].setLayout(new BorderLayout());
            pnlSpelerContainer[i].add(lblSpelersAfbeelding[i], BorderLayout.CENTER);

            //3
            lpnlkaartContainer[i] = new JLayeredPane();
            lpnlkaartContainer[i].setPreferredSize(new Dimension(kaartBreedte * controller.getAantalKaartenSpeler() - 80, 100));
            pnlKaartContainer[i] = new JPanel();
            //lpnlkaartContainer[i].setBackground(Color.BLUE);
            lpnlkaartContainer[i].setOpaque(true);
            pnlKaartContainer[i].add(lpnlkaartContainer[i]);
        }

        if (controller.getAantalSpelers() == 3) {
            lpnlkaartContainer[2].setPreferredSize(new Dimension(100, 350));

        } else if (controller.getAantalSpelers() == 4) {
            lpnlkaartContainer[2].setPreferredSize(new Dimension(100, 350));
            lpnlkaartContainer[3].setPreferredSize(new Dimension(100, 350));
        }

        /**
         * gaat speler label op 1 kant plaatsen, elke speler label krijgt 7 kaart labels
         */
        int minus = 40;

        for (int j = 0; j < controller.getAantalKaartenSpeler(); j++) {
            kaartenSpeler1.get(j).setBounds(minus, 0, kaartBreedte, 100);
            kaartenSpeler2.get(j).setBounds(minus, 0, kaartBreedte, 100);
            minus += 40;
            lpnlkaartContainer[0].add(kaartenSpeler1.get(j), new Integer(j));
            lpnlkaartContainer[1].add(kaartenSpeler2.get(j), new Integer(j));
            if (pnlSpelerContainer.length == 2) {
                tweeSpelersContainerLayOut();
            } else if (pnlSpelerContainer.length == 3) {
                kaartenSpeler3.get(j).setBounds(0, minus, 100, kaartBreedte);
                lpnlkaartContainer[2].add(kaartenSpeler3.get(j), new Integer(j));
                tweeSpelersContainerLayOut();
                derdeSpelerContainerLayOut();
            } else {
                kaartenSpeler3.get(j).setBounds(0, minus, 100, kaartBreedte);
                kaartenSpeler4.get(j).setBounds(0, minus, 100, kaartBreedte);
                lpnlkaartContainer[2].add(kaartenSpeler3.get(j), new Integer(j));
                lpnlkaartContainer[3].add(kaartenSpeler4.get(j), new Integer(j));
                tweeSpelersContainerLayOut();
                derdeSpelerContainerLayOut();
                vierdeSpelerContainerLayOut();
            }
        }
    }

    /**
     * containers worden toegevoegd afhankelijk van het aantal spelers op de aangegeven positie
     */
    public void tweeSpelersContainerLayOut() {
        super.add(pnlSpelerContainer[0], BorderLayout.SOUTH);
        pnlSpelerContainer[0].add(lblSpelerNaam[0], BorderLayout.SOUTH);
        pnlSpelerContainer[0].add(pnlKaartContainer[0], BorderLayout.NORTH);

        super.add(pnlSpelerContainer[1], BorderLayout.NORTH);
        pnlSpelerContainer[1].add(lblSpelerNaam[1], BorderLayout.NORTH);
        pnlSpelerContainer[1].add(pnlKaartContainer[1], BorderLayout.SOUTH);
    }

    public void derdeSpelerContainerLayOut() {
        JPanel pnlLegeSpeler = new JPanel();
        pnlLegeSpeler.setPreferredSize(new Dimension(250, 100));
        super.add(pnlSpelerContainer[2], BorderLayout.EAST);
        super.add(pnlLegeSpeler, BorderLayout.WEST);
        pnlSpelerContainer[2].add(lblSpelerNaam[2], BorderLayout.EAST);
        pnlSpelerContainer[2].add(pnlKaartContainer[2], BorderLayout.WEST);
    }

    public void vierdeSpelerContainerLayOut() {
        super.add(pnlSpelerContainer[3], BorderLayout.WEST);
        pnlSpelerContainer[3].add(lblSpelerNaam[3], BorderLayout.WEST);
        pnlSpelerContainer[3].add(pnlKaartContainer[3], BorderLayout.EAST);
    }

    public void behandelEvents() {
        /**
         * wanneer men op de trekstapel klikt krijgt de speler een nieuwe kaart
         */
        /*lblTrekstapel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Kaart nieuweKaart = controller.getSpelbord().getTrekstapel().neemKaart();
                //TODO: werkt niet omdat de kaart niet op de juiste plaats wordt getekend
                lpnlkaartContainer[0].add(new KaartLabel(nieuweKaart.getHorizontaleImageString(), controller));
                revalidate();
                repaint();
                controller.getSpelers().get(0).voegKaartToe(nieuweKaart);
            }
        });*/


        for (int i = 0; i < controller.getAantalKaartenSpeler(); i++) {
            final int finalI = i;

            kaartenSpeler1.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    KaartLabel lblGeklikteKaart = kaartenSpeler1.get(finalI); //kaart label van de geklikte kaart in variable steken
                    Kaart geklikteKaart = controller.getSpelerKaarten(0).get(finalI);
                    String imageStringGeklikteKaart = geklikteKaart.getHorizontaleImageString();
                    if (controller.speelKaartMogelijk(geklikteKaart)) {
                        kaartenSpeler1.remove(lblGeklikteKaart);//gaat geklikte kaart label verwijderen
                        lpnlkaartContainer[0].removeAll();
                        int minus = 40;
                        for (KaartLabel k : kaartenSpeler1) {
                            k.setBounds(minus, 0, kaartBreedte, 100);
                            minus += 40;
                            lpnlkaartContainer[0].add(k);
                        }
                        lpnlkaartContainer[0].setPreferredSize(new Dimension(kaartBreedte * kaartenSpeler1.size() - 80, 100));
                        revalidate();
                        repaint();

                        controller.speelKaart(geklikteKaart, 0);
                        lblAflegstapel.setImageString(imageStringGeklikteKaart); // image string van de aflgegstapel vervangen door de image string van de geklikte kaart
                        revalidate();
                        repaint();
                    }
                }
            });

            kaartenSpeler2.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    KaartLabel lblGeklikteKaart = kaartenSpeler2.get(finalI); //kaart label van de geklikte kaart in variable steken
                    String imageStringGeklikteKaart = lblGeklikteKaart.getImageString(); //image string van de geklikte kaart label in een variable steken
                    Kaart geklikteKaart = controller.getSpelerKaarten(1).get(finalI);
                    if (controller.speelKaartMogelijk(geklikteKaart)) {
                        kaartenSpeler2.remove(lblGeklikteKaart);//gaat geklikte kaart label verwijderen
                        lpnlkaartContainer[1].removeAll();
                        int minus = 40;
                        for (KaartLabel k : kaartenSpeler2) {
                            k.setBounds(minus, 0, 100, kaartBreedte);
                            minus += 40;
                            lpnlkaartContainer[1].add(k);
                        }
                        lpnlkaartContainer[1].setPreferredSize(new Dimension(kaartBreedte * kaartenSpeler2.size() - 80, 100));
                        revalidate();
                        repaint();

                        controller.speelKaart(geklikteKaart, 1);
                        lblAflegstapel.setImageString(imageStringGeklikteKaart); // image string van de aflgegstapel vervangen door de image string van de geklikte kaart
                        revalidate();
                        repaint();
                    }
                }
            });

            if (controller.getAantalSpelers() >= 3 ) {
                kaartenSpeler3.get(i).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        super.mouseReleased(e);
                        KaartLabel lblGeklikteKaart = kaartenSpeler3.get(finalI); //kaart label van de geklikte kaart in variable steken
                        //image string van de geklikte kaart label in een variable steken
                        Kaart geklikteKaart = controller.getSpelerKaarten(2).get(finalI);
                        String imageStringGeklikteKaart = geklikteKaart.getHorizontaleImageString();
                        if (controller.speelKaartMogelijk(geklikteKaart)) {
                            kaartenSpeler3.remove(lblGeklikteKaart);//gaat geklikte kaart label verwijderen
                            lpnlkaartContainer[2].removeAll();
                            int minus = 40;
                            for (KaartLabel k : kaartenSpeler3) {
                                k.setBounds(minus, 0, kaartBreedte, 100);
                                minus += 40;
                                lpnlkaartContainer[2].add(k);
                            }
                            lpnlkaartContainer[2].setPreferredSize(new Dimension(kaartBreedte * kaartenSpeler3.size() - 80, 100));
                            revalidate();
                            repaint();

                            controller.speelKaart(geklikteKaart, 2);
                            lblAflegstapel.setImageString(imageStringGeklikteKaart); // image string van de aflgegstapel vervangen door de image string van de geklikte kaart
                            revalidate();
                            repaint();
                        }
                    }
                });
            }

            if (controller.getAantalSpelers() == 4 ) {
                kaartenSpeler4.get(i).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        super.mouseReleased(e);
                        KaartLabel lblGeklikteKaart = kaartenSpeler4.get(finalI); //kaart label van de geklikte kaart in variable steken
                        //image string van de geklikte kaart label in een variable steken
                        Kaart geklikteKaart = controller.getSpelerKaarten(3).get(finalI);
                        String imageStringGeklikteKaart = geklikteKaart.getHorizontaleImageString();
                        if (controller.speelKaartMogelijk(geklikteKaart)) {
                            kaartenSpeler4.remove(lblGeklikteKaart);//gaat geklikte kaart label verwijderen
                            lpnlkaartContainer[3].removeAll();
                            int minus = 40;
                            for (KaartLabel k : kaartenSpeler4) {
                                k.setBounds(minus, 0, kaartBreedte, 100);
                                minus += 40;
                                lpnlkaartContainer[3].add(k);
                            }
                            lpnlkaartContainer[3].setPreferredSize(new Dimension(kaartBreedte * kaartenSpeler4.size() - 80, 100));
                            revalidate();
                            repaint();

                            controller.speelKaart(geklikteKaart, 3);
                            lblAflegstapel.setImageString(imageStringGeklikteKaart); // image string van de aflgegstapel vervangen door de image string van de geklikte kaart
                            revalidate();
                            repaint();
                        }
                    }
                });
            }
        }
    }


    public void geklikteKaart() {

    }
}
