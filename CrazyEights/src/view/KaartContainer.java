package view;

import controller.Controller;
import model.Computer;
import model.Kaart;
import model.Kleur;
import model.Mens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class KaartContainer extends JLayeredPane {
    private int kaartBreedte = 70;
    private int kaartOverlap = 40;
    private List<KaartLabel> kaartenSpeler;
    private Controller controller;
    private AflegStapelLabel lblAflegStapel;
    private TrekStapelLabel lblTrekstapel;
    private Robot robot;

    public KaartContainer(Controller controller, AflegStapelLabel lblAflegStapel, TrekStapelLabel lblTrekstapel) {
        this.controller = controller;
        this.setPreferredSize(new Dimension(new Dimension(kaartOverlap * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte, 100)));
        this.setOpaque(false);
        this.lblAflegStapel = lblAflegStapel;
        this.lblTrekstapel = lblTrekstapel;
    }

    public void maakLists(int spelerNr) {
        /**
         * speler 0 kaarten altijd horizontaal
         * speler 1 als het een mens is ook horizontaal
         * speler 2 & 3 als het een mens is verticaal
         * als de speler een computer is moet de kaart omgedraaid zijn
         */
        kaartenSpeler = new LinkedList<>();
        for (int i = 0; i < controller.getAantalKaartenSpeler(); i++) {
            if (spelerNr == 0) {
                KaartLabel kaartLabel = new KaartLabel(controller.getSpelerKaarten(spelerNr).get(i).getHorizontaleImageString(), controller.getSpelerKaarten(spelerNr).get(i).getVerticaleImageString(), controller);
                kaartLabel.setImageString(kaartLabel.getOmgedraaidH());
                if (controller.getSpelers().get(1) instanceof Computer) {
                    kaartLabel.setImageString(kaartLabel.getHorizontale());
                    revalidate();
                    repaint();
                }
                kaartenSpeler.add(kaartLabel);
            }
            if (controller.getSpelers().get(spelerNr) instanceof Mens) {
                if (spelerNr == 1) {
                    KaartLabel kaartLabel = new KaartLabel(controller.getSpelerKaarten(spelerNr).get(i).getHorizontaleImageString(), controller.getSpelerKaarten(spelerNr).get(i).getVerticaleImageString(), controller);
                    kaartLabel.setImageString(kaartLabel.getOmgedraaidH());
                    kaartenSpeler.add(kaartLabel);
                    revalidate();
                    repaint();
                }
                if (spelerNr == 2 || spelerNr == 3) {
                    KaartLabel kaartLabel = new KaartLabel(controller.getSpelerKaarten(spelerNr).get(i).getHorizontaleImageString(), controller.getSpelerKaarten(spelerNr).get(i).getVerticaleImageString(), controller);
                    kaartLabel.setImageString(kaartLabel.getOmgedraaidV());
                    kaartenSpeler.add(kaartLabel);
                    revalidate();
                    repaint();
                }
            } else if (controller.getSpelers().get(spelerNr) instanceof Computer) {

                Kaart kaart = controller.getSpelerKaarten(spelerNr).get(i);
                KaartLabel kaartLabel = new KaartLabel(kaart.getHorizontaleImageString(), kaart.getVerticaleImageString(), controller);
                kaartLabel.setImageString(kaartLabel.getOmgedraaidH());
                if (spelerNr == 2 || spelerNr == 3) {
                    kaartLabel.setImageString(kaartLabel.getOmgedraaidV());
                }
                kaartenSpeler.add(kaartLabel);
            }
        }
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void tekenKaartLabels() {
        kaartOverlap = 0;
        for (int j = 0; j < controller.getAantalKaartenSpeler(); j++) {
            kaartenSpeler.get(j).setBounds(kaartOverlap, 0, kaartBreedte, 100);
            add(kaartenSpeler.get(j), new Integer(j));
            kaartOverlap += 40;
        }
    }

    public void tekenKaartLabelsVerticaal() {
        kaartOverlap = 0;
        for (int j = 0; j < controller.getAantalKaartenSpeler(); j++) {
            kaartenSpeler.get(j).setBounds(0, kaartOverlap, 100, kaartBreedte);
            this.add(kaartenSpeler.get(j), new Integer(j));
            kaartOverlap += 40;
        }
    }


    public void speelKaartEvent(List<Kaart> spelerKaarten, int spelerNr) {
        /**
         * 1. KaartObjectIndex is de index van het kaart Object dat verwijderd moet worden nadat dat je een kaart kan spelen uit de lijst van kaarten van de speler
         * 2. controleren of het kaartObject gespeeld kan worden en controleren of de huidige speler aan beurt is, zo ja wordt het kaartObject en het bijbehorende kaartlabel verwijderd
         */
        for (KaartLabel kaartLabel : kaartenSpeler) {
            kaartLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);

                    //1
                    int KaartObjectIndex = 0;
                    String imageStringKaartLabel = kaartLabel.getHorizontale();
                    for (int i = 0; i < spelerKaarten.size(); i++) {
                        String imageStringKaartObjectH = spelerKaarten.get(i).getHorizontaleImageString();

                        if (imageStringKaartObjectH.equals(imageStringKaartLabel)) {
                            KaartObjectIndex = i;
                            System.out.println("KaartIndex is " + KaartObjectIndex);
                            System.out.println("img string KaartObject is " + controller.getSpelers().get(spelerNr).getKaarten().get(KaartObjectIndex).getHorizontaleImageString());
                            System.out.println("img string kaartLabel is " + kaartLabel.getImageString());
                        }
                    }
                    if (controller.speelKaartMogelijk(spelerKaarten.get(KaartObjectIndex)) && controller.getSpelers().get(spelerNr).getAanBeurt()) {
                        Kaart teSpelenKaart = controller.getSpelerKaarten(spelerNr).get(KaartObjectIndex);
                        controller.speelKaart(teSpelenKaart, spelerNr);
                        System.out.println("de aflegstapel heeft nu " + controller.getSpelbord().getAflegstapel().getKaarten().size() + " kaarten");
                        System.out.println("teSpelen kaart is " + teSpelenKaart.getHorizontaleImageString());
                        kaartenSpeler.remove(kaartLabel);
                        remove(kaartLabel);

                        if (controller.getSpelerKaarten(spelerNr).size() == 0) {
                            JOptionPane.showMessageDialog(null, "Je hebt gewonnen");
                            int keuze = JOptionPane.showConfirmDialog(null, "Nog een keer spelen?");
                            if (keuze == JOptionPane.YES_OPTION) {
                                controller.herstartSpel();
                            }
                        }


                        if (spelerNr == 0) {
                            hertekenKaartenHorizontaal();
                            for (KaartLabel kaartLabel1 : kaartenSpeler) {
                                if (controller.getSpelers().get(1) instanceof Computer) {
                                    kaartLabel1.setImageString(kaartLabel1.getHorizontale());
                                } else {
                                    kaartLabel1.setImageString(kaartLabel1.getOmgedraaidH());
                                }
                            }
                            lblAflegStapel.setImageString(kaartLabel.getHorizontale());
                            revalidate();
                            repaint();
                        }

                        if (controller.getSpelers().get(spelerNr) instanceof Mens) {
                            if (spelerNr == 1) {
                                hertekenKaartenHorizontaal();
                                for (KaartLabel kaartLabel1 : kaartenSpeler) {
                                    kaartLabel1.setImageString(kaartLabel1.getOmgedraaidH());
                                }
                                lblAflegStapel.setImageString(kaartLabel.getHorizontale());
                            }
                            if (spelerNr == 2 || spelerNr == 3) {
                                hertekenKaartenVerticaal();
                                for (KaartLabel kaartLabel1 : kaartenSpeler) {
                                    kaartLabel1.setImageString(kaartLabel1.getOmgedraaidV());
                                }
                                lblAflegStapel.setImageString(kaartLabel.getHorizontale());
                            }

                        }
                        revalidate();
                        repaint();

                        controller.speelKaart(teSpelenKaart, spelerNr);
                        System.out.println("bovenste kaart op aflegstapel is" + controller.getSpelbord().getAflegstapel().getBovensteKaart().getHorizontaleImageString());

                        /**
                         * bij een 8 kan de speler van kleur veranderen
                         */
                        if (teSpelenKaart.getWaarde() == 8) {
                            String[] keuzes = {"Harten", "Ruiten", "Klaveren", "Schoppen"};
                            System.out.println("KAART IS EEN 8");
                            int keuze = JOptionPane.showOptionDialog(null, "In welke kleur wil je veranderen?", "verander kleur",
                                    0, JOptionPane.INFORMATION_MESSAGE, null, keuzes, null);
                            System.out.println("Keuze is" + keuze);
                            switch (keuze) {
                                case 0:
                                    lblAflegStapel.setImageString("images/harten/harten8.png");
                                    controller.getSpelbord().getAflegstapel().legKaart(new Kaart(8, Kleur.harten, "images/harten/harten8.png", "images/harten/verticaal/harten8.png"));
                                    break;
                                case 1:
                                    lblAflegStapel.setImageString("images/ruiten/ruiten8.png");
                                    controller.getSpelbord().getAflegstapel().legKaart(new Kaart(8, Kleur.ruiten, "images/ruiten/ruiten8.png", "images/ruiten/verticaal/ruiten8.png"));
                                    break;
                                case 2:
                                    lblAflegStapel.setImageString("images/klaveren/klaveren8.png");
                                    controller.getSpelbord().getAflegstapel().legKaart(new Kaart(8, Kleur.klaveren, "images/klaveren/klaveren8.png", "images/klaveren/verticaal/klaveren8.png"));
                                    break;
                                case 3:
                                    lblAflegStapel.setImageString("images/schoppen/schoppen8.png");
                                    controller.getSpelbord().getAflegstapel().legKaart(new Kaart(8, Kleur.ruiten, "images/schoppen/schoppen8.png", "images/ruiten/verticaal/schoppen8.png"));
                                    break;
                            }
                            System.out.println("na kleur verandere is de bovenste kaart " + controller.getSpelbord().getAflegstapel().getBovensteKaart());
                        }

                        /**
                         * bepaalt wie de volgende speler aan beurt is na het spelen van een kaart
                         */
                        if (controller.getSpeelRichting() == 0) {
                            if (teSpelenKaart.getWaarde() == 1) {
                                controller.setSpeelRichting(1);
                                controller.speelRichting(spelerNr);
                                JOptionPane.showMessageDialog(null, "Er is van spelrichting veranderd");
                            } else if (teSpelenKaart.getWaarde() == 12) {
                                controller.beeindigBeurtDame(spelerNr);
                                JOptionPane.showMessageDialog(null, "De volgende speler wordt overgeslagen");
                            } else {
                                controller.beeindigBeurt(spelerNr);
                            }
                        } else {
                            if (teSpelenKaart.getWaarde() == 1) {
                                controller.setSpeelRichting(0);
                                controller.speelRichting(spelerNr);
                                JOptionPane.showMessageDialog(null, "Er is van spelrichting veranderd");
                            } else if (teSpelenKaart.getWaarde() == 12) {
                                controller.beeindigBeurtDame(spelerNr);
                                JOptionPane.showMessageDialog(null, "De volgende speler wordt overgeslagen");
                            } else {
                                controller.speelRichting(spelerNr);
                            }
                        }
                    }


                    if (controller.getSpelers().get(1) instanceof Computer) {
                        JOptionPane.showMessageDialog(null, "computers gaan nu hun kaarten spelen");
                        robot.mouseMove(getX() + 500, getY() + 700);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        System.out.println("fake click");
                    }


                    System.out.println("bovenste kaart op aflegstapel is" + controller.getSpelbord().getAflegstapel().getBovensteKaart().getHorizontaleImageString());

                }
            });
        }
    }


    public void zetBreedte(int breedte, int hoogte) {
        setPreferredSize(new Dimension(breedte, hoogte));
    }

    public void trekKaartEvent(int spelerNr) {
        lblTrekstapel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (controller.getSpelers().get(spelerNr).getAanBeurt()) {
                    super.mouseReleased(e);
                    Kaart getrokkenKaart;
                    System.out.println("er is op de trekstapel geklikt");
                    if (controller.getSpelbord().getTrekstapel().getKaarten().size() == 1) {
                        System.out.println("stapel vullen");
                        controller.vulTrekStapel();
                    }
                    System.out.println("DDDDDDDDDD");
                    getrokkenKaart = controller.getSpelbord().getTrekstapel().neemKaart();
                    System.out.println("getrokken kaart is" + getrokkenKaart.getHorizontaleImageString());
                    KaartLabel lblGetrokkenKaart = new KaartLabel(getrokkenKaart.getHorizontaleImageString(), getrokkenKaart.getVerticaleImageString(), controller);
                    lblGetrokkenKaart.setImageString(getrokkenKaart.getHorizontaleImageString());
                    controller.getSpelerKaarten(spelerNr).add(getrokkenKaart);

                    kaartenSpeler.add(lblGetrokkenKaart);

                    if (spelerNr == 0 || spelerNr == 1) {
                        hertekenKaartenHorizontaal();
                    }

                    if (spelerNr == 2 || spelerNr == 3) {
                        hertekenKaartenVerticaal();
                    }
                    revalidate();
                    repaint();

                    speelKaartEvent(controller.getSpelerKaarten(spelerNr), spelerNr);
                    if (controller.speelKaartMogelijk(getrokkenKaart)) {
                        System.out.println("je kan de getrokken kaart spelen");
                        //controller.getSpelers().get(spelerNr).setAanBeurt(true);
                    }
                    System.out.println("Size trekstapel na het trekken van een kaart" + controller.getSpelbord().getTrekstapel().getKaarten().size());
                }
            }
        });

    }

    public void computerSpeelEvent(int spelerNr) {
        if (controller.getSpelers().get(spelerNr) instanceof Computer) {
            String imgString;

            Kaart teSpelenKaart = ((Computer) controller.getSpelers().get(spelerNr)).getTeSpelenKaart();
            if (teSpelenKaart != null) {
                controller.speelKaart(teSpelenKaart, spelerNr);
                imgString = controller.getSpelbord().getAflegstapel().getBovensteKaart().getHorizontaleImageString();
                lblAflegStapel.setImageString(imgString);
                kaartenSpeler.remove(kaartenSpeler.size() - 1);
                try {
                    remove(kaartenSpeler.size() - 1);
                    removeAll();
                    kaartOverlap = 0;
                    if (spelerNr == 1) {
                        hertekenKaartenHorizontaal();
                    }

                    if (spelerNr == 2 || spelerNr == 3) {
                        hertekenKaartenVerticaal();
                    }
                    revalidate();
                    repaint();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("negeer");
                }

                if (teSpelenKaart.getWaarde() == 8) {
                    String[] kleurKeuze = {"harten", "ruiten", "klaveren", "schoppen"};
                    Random random = new Random();
                    int kleurKeuzeIndex = random.nextInt(3);
                    System.out.println("computer heeft een 8 gespeeld");
                    System.out.println("HIER KLEUR VERANDEREN");
                    lblAflegStapel.setImageString("images/" + kleurKeuze[kleurKeuzeIndex] + "/" + kleurKeuze[kleurKeuzeIndex] + "8.png");
                    System.out.println(lblAflegStapel.getImageString() + "na veranderen kleur");

                    controller.getSpelbord().getAflegstapel().legKaart(new Kaart(8, Kleur.values()[kleurKeuzeIndex], "images/" + kleurKeuze[kleurKeuzeIndex] + "/" + kleurKeuze[kleurKeuzeIndex] + "8.png", ""));
                    System.out.println("bovenste kaart na verandering door computer" + controller.getSpelbord().getAflegstapel().getBovensteKaart());
                }

                if (teSpelenKaart.getWaarde() == 12) {
                    controller.beeindigBeurtDame(spelerNr);
                    System.out.println("dame gespeelt");
                    System.out.println("speler die aan de beurt was: " + spelerNr);
                } else {
                    controller.beeindigBeurt(spelerNr);
                }

            } else {
                computerTrekEvent(spelerNr);
                revalidate();
                repaint();
            }

            if (controller.getSpelerKaarten(spelerNr).size() == 0) {
                JOptionPane.showMessageDialog(null, "Je hebt verloren");
                int keuze = JOptionPane.showConfirmDialog(null, "Nog een keer spelen?");
                if (keuze == JOptionPane.YES_OPTION) {
                    controller.herstartSpel();
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void computerTrekEvent(int spelerNr) {
        //TODO: kaart trekken -> kaartlabel met getrokken kaart, set imageString omgedraaid
        if (controller.getSpelers().get(spelerNr) instanceof Computer) {

            if (controller.getSpelbord().getTrekstapel().getKaarten().size() == 1) {
                System.out.println("stapel vullen");
                controller.vulTrekStapel();
            }

            ((Computer) controller.getSpelers().get(spelerNr)).voegKaartToe();
            System.out.println("computer heeft een kaart getrokken ");

            if (spelerNr == 1) {
                KaartLabel kaartLabel = new KaartLabel("/view/images/kaartAchterkant.png", "/view/images/kaartAchterkantV.png", controller);
                kaartLabel.setImageString(kaartLabel.getHorizontale());
                kaartenSpeler.add(kaartLabel);

                System.out.println("de computer heeft zoveel kaartlabels: " + kaartenSpeler.size());

                hertekenKaartenHorizontaal();
            }

            if (spelerNr == 2 || spelerNr == 3) {
                KaartLabel kaartLabel = new KaartLabel("/view/images/kaartAchterkant.png", "/view/images/kaartAchterkantV.png", controller);
                kaartLabel.setImageString(kaartLabel.getVerticale());
                kaartenSpeler.add(kaartLabel);

                hertekenKaartenVerticaal();
            }
            revalidate();
            repaint();
            if (((Computer) controller.getSpelers().get(spelerNr)).getTeSpelenKaart() != null) {
                System.out.println("we kunnen de getrokken kaart spelen");
                computerSpeelEvent(spelerNr);
            }
        }
    }

    public void hertekenKaartenHorizontaal() {
        removeAll();
        kaartOverlap = 0;
        for (int i = 0; i < kaartenSpeler.size(); i++) {
            kaartenSpeler.get(i).setBounds(kaartOverlap, 0, kaartBreedte, 100);
            kaartOverlap += 40;
            add(kaartenSpeler.get(i), new Integer(i));
        }

        kaartOverlap = 40;
        zetBreedte(kaartOverlap * (kaartenSpeler.size() - 1) + kaartBreedte, 100);
        revalidate();
        repaint();
    }

    public void hertekenKaartenVerticaal() {
        removeAll();
        kaartOverlap = 0;
        for (int i = 0; i < kaartenSpeler.size(); i++) {
            kaartenSpeler.get(i).setBounds(0, kaartOverlap, 100, kaartBreedte);
            kaartOverlap += 40;
            add(kaartenSpeler.get(i), new Integer(i));
        }

        kaartOverlap = 40;
        zetBreedte(100, kaartOverlap * (kaartenSpeler.size() - 1) + kaartBreedte);
        revalidate();
        repaint();
    }

    public void draaiKaartenOm(int spelerNr) {
        if (controller.getSpelers().get(spelerNr).getAanBeurt()) {
            for (int i = 0; i < kaartenSpeler.size(); i++) {
                if (spelerNr == 0 || spelerNr == 1) {
                    kaartenSpeler.get(i).setImageString(kaartenSpeler.get(i).getHorizontale());
                } else if (spelerNr == 2 || spelerNr == 3) {
                    kaartenSpeler.get(i).setImageString(kaartenSpeler.get(i).getVerticale());
                }
            }
            revalidate();
            repaint();
        }
    }
}
