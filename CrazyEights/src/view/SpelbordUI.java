package view;

import controller.Controller;
import model.Kaart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


//TODO: REFACTOR!
//TODO: rekening houden met resizen...

public class SpelbordUI extends JFrame {
    private Controller controller;
    private TrekStapelLabel lblTrekstapel;
    private AflegStapelLabel lblAflegstapel;
    private SpelerLabel[] lblSpelers;
    private JLayeredPane[] lpnlkaartContainer;

    JPanel[] pnlSpelerKaartContainer;
    JLabel[] lblSpelerNaam;

    private List<KaartLabel> kaartenSpeler1;
    private List<KaartLabel> kaartenSpeler2;

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
        lblSpelers = new SpelerLabel[controller.getAantalSpelers()];
        for (int i = 0; i < lblSpelers.length; i++) {
            lblSpelers[i] = new SpelerLabel();
        }

        lpnlkaartContainer = new JLayeredPane[controller.getAantalSpelers()];

        pnlSpelerKaartContainer = new JPanel[controller.getAantalSpelers()];
        lblSpelerNaam = new JLabel[controller.getAantalSpelers()];


        kaartenSpeler1 = new ArrayList<KaartLabel>();
        kaartenSpeler2 = new ArrayList<KaartLabel>();
        for (int i = 0; i < 7; i++) {
            kaartenSpeler1.add(new KaartLabel(controller.getSpelerKaarten(0).get(i).getImageString()));
            kaartenSpeler2.add(new KaartLabel(controller.getSpelerKaarten(1).get(i).getImageString()));
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
        for (int i = 0; i < pnlSpelerKaartContainer.length; i++) {
            pnlSpelerKaartContainer[i] = new JPanel();
            pnlSpelerKaartContainer[i].setLayout(new BorderLayout());
            pnlSpelerKaartContainer[i].add(lblSpelers[i], BorderLayout.CENTER);
            pnlSpelerKaartContainer[i].setPreferredSize(new Dimension(150, 200));
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
        int x = super.getWidth();
        int minus = 50;
        for (int i = 0; i < lpnlkaartContainer.length; i++) {
            lpnlkaartContainer[i] = new JLayeredPane();
            lpnlkaartContainer[i].setPreferredSize(new Dimension(80, 100));
        }

        for (int i = 0; i < controller.getAantalSpelers(); i++) {
            for (int j = 0; j < 7; j++) {
                kaartenSpeler1.get(j).setBounds(x - minus, 0, 70, 100);
                kaartenSpeler2.get(j).setBounds(x - minus, 0, 70, 100);
                minus += 50;
                if (pnlSpelerKaartContainer.length == 2) {
                    lpnlkaartContainer[0].add(kaartenSpeler1.get(j), new Integer(j), 0);
                    lpnlkaartContainer[1].add(kaartenSpeler2.get(j), new Integer(j), 0);
                    tweeSpelersContainerLayOut();
                } else if (pnlSpelerKaartContainer.length == 3) {
                    tweeSpelersContainerLayOut();
                    derdeSpelerContainerLayOut();
                } else {
                    tweeSpelersContainerLayOut();
                    derdeSpelerContainerLayOut();
                    vierdeSpelerContainerLayOut();
                }
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
                lpnlkaartContainer[0].add(new KaartLabel(nieuweKaart.getImageString()));
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
        pnlSpelerKaartContainer[0].add(lpnlkaartContainer[0], BorderLayout.NORTH);

        super.add(pnlSpelerKaartContainer[1], BorderLayout.NORTH);
        pnlSpelerKaartContainer[1].add(lblSpelerNaam[1], BorderLayout.NORTH);
        pnlSpelerKaartContainer[1].add(lpnlkaartContainer[1], BorderLayout.SOUTH);
    }

    public void derdeSpelerContainerLayOut() {
        super.add(pnlSpelerKaartContainer[2], BorderLayout.EAST);
        pnlSpelerKaartContainer[2].add(lblSpelerNaam[2], BorderLayout.EAST);
        pnlSpelerKaartContainer[2].add(lpnlkaartContainer[1], BorderLayout.WEST);
    }

    public void vierdeSpelerContainerLayOut() {
        super.add(pnlSpelerKaartContainer[3], BorderLayout.WEST);
        pnlSpelerKaartContainer[3].add(lblSpelerNaam[3], BorderLayout.WEST);
        pnlSpelerKaartContainer[3].add(lpnlkaartContainer[1], BorderLayout.EAST);
    }

    public void geklikteKaart() {

    }
}
