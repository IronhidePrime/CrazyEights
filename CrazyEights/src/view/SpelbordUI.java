package view;

import controller.Controller;
import model.Kaart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


//TODO: REFACTOR!
//TODO: rekening houden met resizen...

public class SpelbordUI extends JFrame {
    private Controller controller;
    private TrekStapelLabel lblTrekstapel;
    private AflegStapelLabel lblAflegstapel;
    private SpelerLabel[] lblSpelersAfbeelding;
    private JLayeredPane[] lpnlkaartContainer;

    JPanel[] pnlSpelerKaartContainer;
    JLabel[] lblSpelerNaam;

    private List<KaartLabel> kaartenSpeler1;
    private List<KaartLabel> kaartenSpeler2;
    private List<KaartLabel> kaartenSpeler3;
    private List<KaartLabel> kaartenSpeler4;

    private final int AANTAL_KAARTEN_2_SPELERS = 7;
    private final int AANTAL_KAARTEN_MEERDERE_SPELERS = 5;

    private JPanel pnlKaartContainer[];

    public SpelbordUI(Controller controller) throws HeadlessException {
        super("Crazy Eights");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(1000, 700);
        super.setLocationRelativeTo(null);

        this.controller = controller;
        controller.startSpel();

        maakComponenten();
        maakLayout();
        behandelEvents();
        super.setVisible(true);
    }

    public void maakComponenten() {
        lblAflegstapel = new AflegStapelLabel(controller.getSpelbord().getAflegstapel().getBovensteKaart().getImageString());
        lblAflegstapel.setPreferredSize(new Dimension(100, 140));
        lblTrekstapel = new TrekStapelLabel();
        lblTrekstapel.setPreferredSize(new Dimension(100, 140));
        lblSpelersAfbeelding = new SpelerLabel[controller.getAantalSpelers()];
        for (int i = 0; i < controller.getAantalSpelers(); i++) {
            lblSpelersAfbeelding[i] = new SpelerLabel();
        }

        lpnlkaartContainer = new JLayeredPane[controller.getAantalSpelers()];

        pnlSpelerKaartContainer = new JPanel[controller.getAantalSpelers()];
        lblSpelerNaam = new JLabel[controller.getAantalSpelers()];
        pnlKaartContainer = new JPanel[controller.getAantalSpelers()];


        kaartenSpeler1 = new ArrayList<KaartLabel>();
        kaartenSpeler2 = new ArrayList<KaartLabel>();
        kaartenSpeler3 = new ArrayList<KaartLabel>();
        kaartenSpeler4 = new ArrayList<KaartLabel>();
        for (int i = 0; i < controller.getAantalKaartenSpeler(); i++) {
            kaartenSpeler1.add(new KaartLabel(controller.getSpelerKaarten(0).get(i).getImageString(),controller));
            kaartenSpeler2.add(new KaartLabel(controller.getSpelerKaarten(1).get(i).getImageString(),controller));
            if (controller.getAantalSpelers() == 3){
                kaartenSpeler1.add(new KaartLabel(controller.getSpelerKaarten(0).get(i).getImageString(),controller));
                kaartenSpeler2.add(new KaartLabel(controller.getSpelerKaarten(1).get(i).getImageString(),controller));
                kaartenSpeler3.add(new KaartLabel(controller.getSpelerKaarten(2).get(i).getImageString(),controller));
            } else if(controller.getAantalSpelers() ==4){
                kaartenSpeler1.add(new KaartLabel(controller.getSpelerKaarten(0).get(i).getImageString(),controller));
                kaartenSpeler2.add(new KaartLabel(controller.getSpelerKaarten(1).get(i).getImageString(),controller));
                kaartenSpeler3.add(new KaartLabel(controller.getSpelerKaarten(2).get(i).getImageString(),controller));
                kaartenSpeler4.add(new KaartLabel(controller.getSpelerKaarten(3).get(i).getImageString(),controller));
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
         * x aantal containers aanmaken, 1 voor elke speler
         */
        for (int i = 0; i < controller.getAantalSpelers(); i++) {
            pnlSpelerKaartContainer[i] = new JPanel();
            pnlSpelerKaartContainer[i].setLayout(new BorderLayout());
            pnlSpelerKaartContainer[i].add(lblSpelersAfbeelding[i], BorderLayout.CENTER);
            pnlSpelerKaartContainer[i].setPreferredSize(new Dimension(200, 200));
            pnlSpelerKaartContainer[i].setBackground(Color.RED);
            pnlSpelerKaartContainer[i].setOpaque(true);
        }

        /**
         * speler label aanmaken en naam van de ingegeven speler toevoegen
         */
        for (int i = 0; i < controller.getAantalSpelers(); i++) {
            lblSpelerNaam[i] = new JLabel();
            lblSpelerNaam[i].setText(controller.getSpelerNaam(i));
            lblSpelerNaam[i].setHorizontalAlignment(SwingConstants.CENTER);
        }

        /**
         * gaat speler label op 1 kant plaatsen, elke speler label krijgt 7 kaart labels
         */
        int kaartBreedte = 70;
        int minus = 40;

        for (int i = 0; i < controller.getAantalSpelers(); i++) {
            lpnlkaartContainer[i] = new JLayeredPane();
            lpnlkaartContainer[i].setPreferredSize(new Dimension(kaartBreedte*controller.getAantalKaartenSpeler(), 100));
            pnlKaartContainer[i] = new JPanel();
            pnlKaartContainer[i].setLayout(new FlowLayout());
            pnlKaartContainer[i].add(lpnlkaartContainer[i]);
        }

        for (int j = 0; j < controller.getAantalKaartenSpeler(); j++) {
            kaartenSpeler1.get(j).setBounds(minus, 0, kaartBreedte, 100);
            kaartenSpeler2.get(j).setBounds(minus, 0, kaartBreedte, 100);
            minus += 50;
            if (pnlSpelerKaartContainer.length == 2) {
                lpnlkaartContainer[0].add(kaartenSpeler1.get(j), j);
                lpnlkaartContainer[1].add(kaartenSpeler2.get(j), j);
                tweeSpelersContainerLayOut();
            } else if (pnlSpelerKaartContainer.length == 3) {
                kaartenSpeler3.get(j).setBounds(minus, 0, kaartBreedte, 100);
                lpnlkaartContainer[0].add(kaartenSpeler1.get(j), 0);
                lpnlkaartContainer[1].add(kaartenSpeler2.get(j), 0);
                lpnlkaartContainer[2].add(kaartenSpeler3.get(j), 0);
                tweeSpelersContainerLayOut();
                derdeSpelerContainerLayOut();
            } else {
                kaartenSpeler3.get(j).setBounds(minus, 0, kaartBreedte, 100);
                kaartenSpeler4.get(j).setBounds(minus, 0, kaartBreedte, 100);
                lpnlkaartContainer[0].add(kaartenSpeler1.get(j), 0);
                lpnlkaartContainer[1].add(kaartenSpeler2.get(j), 0);
                lpnlkaartContainer[2].add(kaartenSpeler3.get(j), 0);
                lpnlkaartContainer[3].add(kaartenSpeler4.get(j), 0);
                tweeSpelersContainerLayOut();
                derdeSpelerContainerLayOut();
                vierdeSpelerContainerLayOut();
            }
        }
    }

    public void behandelEvents() {
        /**
         * wanneer men op de trekstapel klikt krijgt de speler een nieuwe kaart
         */
        lblTrekstapel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Kaart nieuweKaart = controller.getSpelbord().getTrekstapel().neemKaart();
                //TODO: werkt niet omdat de kaart niet op de juiste plaats wordt getekend
                lpnlkaartContainer[0].add(new KaartLabel(nieuweKaart.getImageString(),controller));
                revalidate();
                repaint();
                controller.getSpelers().get(0).voegKaartToe(nieuweKaart);
            }
        });

        for (int i = 0; i < kaartenSpeler1.size(); i++) {
            final int finalI = i;
            kaartenSpeler1.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    KaartLabel lblGeklikteKaart = kaartenSpeler1.get(finalI); //kaart label van de geklikte kaart in variable steken
                    String imageStringGeklikteKaart = lblGeklikteKaart.getImageString(); //image string van de geklikte kaart label in een variable steken
                    Kaart geklikteKaart = controller.getSpelerKaarten(0).get(finalI);
                    if (controller.speelKaartMogelijk(geklikteKaart)) {
                        lpnlkaartContainer[0].remove(lblGeklikteKaart);//gaat geklikte kaart label verwijderen
                        lblAflegstapel.setImageString(imageStringGeklikteKaart); // image string van de aflgegstapel vervangen door de image string van de geklikte kaart
                        revalidate();
                        repaint();
                        System.out.println("kaart " + finalI + "is geklikt");
                        controller.speelKaart(geklikteKaart, 0);
                        controller.getSpelerKaarten(0).add(finalI, null);//leeg object toevoegen aan de list van kaart objecten van de speler om te voorkomen dat het laatste kaart object in de list buiten de index ligt
                    }
                }
            });
            kaartenSpeler2.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    KaartLabel lblGeklikteKaart = kaartenSpeler2.get(finalI); //kaart label van de geklikte kaart in variable steken
                    String imageStringGeklikteKaart = lblGeklikteKaart.getImageString(); //image string van de geklikte kaart label in een variable steken
                    Kaart geklikteKaart = controller.getSpelerKaarten(1).get(finalI);
                    if (controller.speelKaartMogelijk(geklikteKaart)) {
                        super.mouseReleased(e);
                        lpnlkaartContainer[1].remove(lblGeklikteKaart);//gaat geklikte kaart label verwijderen
                        lblAflegstapel.setImageString(imageStringGeklikteKaart); // image string van de aflgegstapel vervangen door de image string van de geklikte kaart
                        revalidate();
                        repaint();
                        System.out.println("kaart " + finalI + "is geklikt");
                        controller.speelKaart(geklikteKaart, 1);
                        controller.getSpelerKaarten(1).add(finalI, null);//leeg object toevoegen aan de list van kaart objecten van de speler om te voorkomen dat het laatste kaart object in de list buiten de index ligt
                    }
                }
            });
        }
    }

    /**
     * containers worden toegevoegd afhankelijk van het aantal spelers op de aangegeven positie
     */
    public void tweeSpelersContainerLayOut() {
        super.add(pnlSpelerKaartContainer[0], BorderLayout.SOUTH);
        pnlSpelerKaartContainer[0].add(lblSpelerNaam[0], BorderLayout.SOUTH);
        pnlSpelerKaartContainer[0].add(pnlKaartContainer[0], BorderLayout.NORTH);

        super.add(pnlSpelerKaartContainer[1], BorderLayout.NORTH);
        pnlSpelerKaartContainer[1].add(lblSpelerNaam[1], BorderLayout.NORTH);
        pnlSpelerKaartContainer[1].add(pnlKaartContainer[1], BorderLayout.SOUTH);
    }

    public void derdeSpelerContainerLayOut() {
        super.add(pnlSpelerKaartContainer[2], BorderLayout.EAST);
        pnlSpelerKaartContainer[2].add(lblSpelerNaam[2], BorderLayout.EAST);
        pnlSpelerKaartContainer[2].add(pnlKaartContainer[2], BorderLayout.WEST);
    }

    public void vierdeSpelerContainerLayOut() {
        super.add(pnlSpelerKaartContainer[3], BorderLayout.WEST);
        pnlSpelerKaartContainer[3].add(lblSpelerNaam[3], BorderLayout.WEST);
        pnlSpelerKaartContainer[3].add(lpnlkaartContainer[3], BorderLayout.EAST);
    }

    public void geklikteKaart() {

    }
}
