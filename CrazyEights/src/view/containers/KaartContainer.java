package view.containers;

import controller.Controller;
import model.speler.Computer;
import model.kaart.Kaart;
import model.kaart.Kleur;
import model.speler.Mens;
import view.labels.AflegStapelLabel;
import view.labels.KaartLabel;
import view.labels.TrekStapelLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * container waar een speler zijn kaarten in worden getekend
 * kaarBreedte & kaartOverlap dienen om kaarten over elkaar te kunnen tekenen
 */
public class KaartContainer extends JLayeredPane {
    private int kaartBreedte = 70;
    private int kaartOverlap = 40;

    private List<KaartLabel> kaartenSpeler;
    private Controller controller;
    private AflegStapelLabel lblAflegStapel;
    private TrekStapelLabel lblTrekstapel;
    private Robot robot;
    private JLabel lblStatus;
    private JFrame jframe;


    public KaartContainer(Controller controller, AflegStapelLabel lblAflegStapel, TrekStapelLabel lblTrekstapel, JLabel lblStatus, JFrame jframe) {
        this.controller = controller;
        this.setPreferredSize(new Dimension(new Dimension(kaartOverlap * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte, 100)));
        this.setOpaque(false);
        this.lblAflegStapel = lblAflegStapel;
        this.lblTrekstapel = lblTrekstapel;
        this.jframe = jframe;

        this.lblStatus = lblStatus;
    }

    public void maakLists(int spelerNr) {
        /**
         * multiplayer: alle spelers hun kaarten zijn in het begin omgedraaid
         * singleplayer: enkel de menselijke speler zijn kaarten zijn zichtbaar
         */
        kaartenSpeler = new LinkedList<>();

        /**
         * bij een opgeslagen spel kaarten terug tekenen
         */
        if (controller.isSpelGeladen()) {
            KaartLabel kaartLabel;
            for (int i = 0; i < controller.vraagPropertyAantalKaarten(spelerNr); i++) {
                kaartLabel = new KaartLabel(controller.getSpelerKaarten(spelerNr).get(i).getHorizontaleImageString(), controller.getSpelerKaarten(spelerNr).get(i).getVerticaleImageString());
                if (spelerNr == 0 || spelerNr == 1) {
                    if (controller.vraagIsMultiplayer()) {
                        kaartLabel.setImageString(kaartLabel.getOmgedraaidH());
                    } else if (!controller.vraagIsMultiplayer() && spelerNr == 0) {
                        kaartLabel.setImageString(kaartLabel.getHorizontale());
                    } else {
                        kaartLabel.setImageString(kaartLabel.getOmgedraaidH());
                    }
                    kaartOverlap = 40;
                    zetBreedte(kaartOverlap * (kaartenSpeler.size()) + kaartBreedte, 100);
                }
                if (spelerNr == 2 || spelerNr == 3) {
                    kaartLabel.setImageString(kaartLabel.getOmgedraaidV());
                    kaartOverlap = 40;
                    zetBreedte(100, kaartOverlap * (kaartenSpeler.size()) + kaartBreedte);
                }
                kaartenSpeler.add(kaartLabel);
            }
            /**
             * voor elke speler zijn kaartLabels tekenen
             */
        } else {
            for (int i = 0; i < controller.getAantalKaartenSpeler(); i++) {
                KaartLabel kaartLabel = new KaartLabel(controller.getSpelerKaarten(spelerNr).get(i).getHorizontaleImageString(), controller.getSpelerKaarten(spelerNr).get(i).getVerticaleImageString());
                if (spelerNr == 0) {
                    kaartLabel.setImageString(kaartLabel.getOmgedraaidH());
                    if (controller.getSpelers().get(1) instanceof Computer) {
                        kaartLabel.setImageString(kaartLabel.getHorizontale());
                    }
                }

                if (controller.getSpelers().get(spelerNr) instanceof Mens) {
                    if (spelerNr == 1) {
                        kaartLabel.setImageString(kaartLabel.getOmgedraaidH());
                    }
                    if (spelerNr == 2 || spelerNr == 3) {
                        kaartLabel.setImageString(kaartLabel.getOmgedraaidV());
                    }
                } else if (controller.getSpelers().get(spelerNr) instanceof Computer) {
                    kaartLabel.setImageString(kaartLabel.getOmgedraaidH());
                    if (spelerNr == 2 || spelerNr == 3) {
                        kaartLabel.setImageString(kaartLabel.getOmgedraaidV());
                    }
                }
                kaartenSpeler.add(kaartLabel);
            }
        }

        /**
         * robot nodig om computerspeler te triggere
         */
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void tekenKaartLabelsGeladenSpel(int spelerNr) {
        kaartOverlap = 0;
        for (int i = 0; i < controller.vraagPropertyAantalKaarten(spelerNr); i++) {
            kaartenSpeler.get(i).setBounds(kaartOverlap, 0, kaartBreedte, 100);
            add(kaartenSpeler.get(i), new Integer(i));
            kaartOverlap += 40;
        }
    }

    public void tekenKaartLabelsVerticaalGeladenSpel(int spelerNr) {
        kaartOverlap = 0;
        for (int i = 0; i < controller.vraagPropertyAantalKaarten(spelerNr); i++) {
            kaartenSpeler.get(i).setBounds(0, kaartOverlap, 100, kaartBreedte);
            add(kaartenSpeler.get(i), new Integer(i));
            kaartOverlap += 40;
        }
    }

    public void speelKaartEvent(List<Kaart> spelerKaarten, int spelerNr) {
        /**
         * 1. kaartObjectIndex is de index van het kaart Object dat verwijderd moet worden nadat dat je een kaart kan spelen uit de lijst van kaarten van de speler
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
                        }
                    }
                    //2
                    if (controller.speelKaartMogelijk(spelerKaarten.get(KaartObjectIndex)) && controller.getSpelers().get(spelerNr).getAanBeurt()) {
                        Kaart teSpelenKaart = controller.getSpelerKaarten(spelerNr).get(KaartObjectIndex);
                        controller.speelKaart(teSpelenKaart, spelerNr); //kaart zal verdwijnen uit de speler zijn kaarten
                        kaartenSpeler.remove(kaartLabel); //het bijbehorende kaartlabel wordt verwijderd

                        /**
                         * hertekent kaarten waardoor het gat dat onstaat bij het spelen terug wordt opgevuld
                         * kaarten worden hierdoor terug mooi in het midden geplaatst
                         */
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

                        /**
                         * wanneer de gespeelde kaart een 8 is en het het spel is nog niet afgelopen mag de speler een kleur kiezen
                         */
                        if (teSpelenKaart.getWaarde() == 8 && !controller.isSpelGedaan()) {
                            String[] keuzes = {"Harten", "Ruiten", "Klaveren", "Schoppen"};
                            int keuze = JOptionPane.showOptionDialog(null, "In welke kleur wil je veranderen?", "verander kleur",
                                    0, JOptionPane.INFORMATION_MESSAGE, null, keuzes, null);
                            switch (keuze) {
                                case 0:
                                    lblAflegStapel.setImageString("/view/images/harten/harten8.png");
                                    controller.getSpelbord().getAflegstapel().legKaart(new Kaart(8, Kleur.harten, "/view/images/harten/harten8.png", "/view/images/harten/verticaal/harten8.png"));
                                    break;
                                case 1:
                                    lblAflegStapel.setImageString("/view/images/ruiten/ruiten8.png");
                                    controller.getSpelbord().getAflegstapel().legKaart(new Kaart(8, Kleur.ruiten, "/view/images/ruiten/ruiten8.png", "/view/images/ruiten/verticaal/ruiten8.png"));
                                    break;
                                case 2:
                                    lblAflegStapel.setImageString("/view/images/klaveren/klaveren8.png");
                                    controller.getSpelbord().getAflegstapel().legKaart(new Kaart(8, Kleur.klaveren, "/view/images/klaveren/klaveren8.png", "/view/images/klaveren/verticaal/klaveren8.png"));
                                    break;
                                case 3:
                                    lblAflegStapel.setImageString("/view/images/schoppen/schoppen8.png");
                                    controller.getSpelbord().getAflegstapel().legKaart(new Kaart(8, Kleur.schoppen, "/view/images/schoppen/schoppen8.png", "/view/images/ruiten/verticaal/schoppen8.png"));
                                    break;
                            }
                        }

                        /**
                         * beurt beeindigen van de speler adhv welke kaart er gespeeld is
                         */
                        if (controller.getSpeelRichting() == 0) {
                            if (teSpelenKaart.getWaarde() == 1) {
                                controller.setSpeelRichting(1);
                                controller.speelRichting(spelerNr);
                                if (controller.getAantalSpelers() > 2 && !controller.isSpelGedaan()) {
                                    JOptionPane.showMessageDialog(null, "Er is van spelrichting veranderd", "Richting veranderd", JOptionPane.DEFAULT_OPTION);
                                }
                            } else if (teSpelenKaart.getWaarde() == 12 && !controller.isSpelGedaan()) {
                                controller.beeindigBeurtDame(spelerNr);
                                JOptionPane.showMessageDialog(null, "De volgende speler wordt overgeslagen", "Volgende speler overslaan", JOptionPane.DEFAULT_OPTION);
                            } else {
                                controller.beeindigBeurt(spelerNr);
                            }
                        } else {
                            if (teSpelenKaart.getWaarde() == 1) {
                                controller.setSpeelRichting(0);
                                controller.speelRichting(spelerNr);
                                if (controller.getAantalSpelers() > 2 && !controller.isSpelGedaan()) {
                                    JOptionPane.showMessageDialog(null, "Er is van spelrichting veranderd");
                                }
                            } else if (teSpelenKaart.getWaarde() == 12 && !controller.isSpelGedaan()) {
                                controller.beeindigBeurtDame(spelerNr);
                                JOptionPane.showMessageDialog(null, "De volgende speler wordt overgeslagen");
                            } else {
                                controller.speelRichting(spelerNr);
                            }
                        }
                        lblStatus.setText("Speler aan beurt: " + controller.getSpelerNaamAanBeurt());

                        /**
                         * als de speler tege de computer speelt moet het computerSpeelEvent worden getriggerd door een "fake click"
                         */
                        if (controller.getSpelers().get(1) instanceof Computer && !controller.isSpelGedaan()) {
                            JOptionPane.showMessageDialog(null, "computers gaan nu hun kaarten spelen");
                            robot.mouseMove(getX() + 500, getY() + 700);
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        }

                        revalidate();
                        repaint();

                        /**
                         * wanneer een speler nog 0 kaarten heeft is spel afgelopen en wordt er een mededeling getoond
                         * kan kiezen om nog eens te spelen of het spel te sluiten
                         */
                        if (controller.getSpelerKaarten(spelerNr).size() == 0 && !controller.isSpelGedaan()) {
                            String felicitaties = "Speler " + controller.getSpelerNaamAanBeurt() + " heeft gewonnen! PROFICIAT!";
                            JOptionPane.showMessageDialog(null, felicitaties, "We hebben een winnaar!!!", JOptionPane.DEFAULT_OPTION);
                            int keuze = JOptionPane.showOptionDialog(null, "Nog een keer spelen?", "Einde spel!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Ja", "Nee"}, "Ja");
                            if (keuze == 0) {
                                controller.herstartSpel();
                                controller.setSpelGedaan(true);
                                jframe.dispose();
                            } else {
                                System.exit(0);
                            }
                        }
                    }
                }
            });
        }
    }

    public void trekKaartEvent(int spelerNr) {
        lblTrekstapel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                /**
                 * enkel wanneer de speler aan beurt is zal er een kaart worden getrokken en wordt deze toegevoegd
                 aan de speler zijn kaartContainer
                 */
                if (controller.getSpelers().get(spelerNr).getAanBeurt()) {
                    Kaart getrokkenKaart;

                    /**
                     * als er maar 1 kaart in de trekstapel zitten gaan we deze vullen met kaarten van de aflegstapel
                     */
                    if (controller.getSpelbord().getTrekstapel().getKaarten().size() == 1) {
                        controller.vulTrekStapel();
                        lblAflegStapel.setImageString(controller.getSpelbord().getAflegstapel().getBovensteKaart().getHorizontaleImageString());
                    }

                    /**
                     * getrokkenKaart toevoegen aan speler zijn kaarten
                     * kaartlabel toevoegen aan de speler zijn kaartcontainer
                     */
                    getrokkenKaart = controller.getSpelbord().getTrekstapel().neemKaart();
                    KaartLabel lblGetrokkenKaart = new KaartLabel(getrokkenKaart.getHorizontaleImageString(), getrokkenKaart.getVerticaleImageString());
                    controller.getSpelerKaarten(spelerNr).add(getrokkenKaart);
                    kaartenSpeler.add(lblGetrokkenKaart);

                    if (spelerNr == 0 || spelerNr == 1) {
                        lblGetrokkenKaart.setImageString(getrokkenKaart.getHorizontaleImageString());
                        hertekenKaartenHorizontaal();
                    }

                    if (spelerNr == 2 || spelerNr == 3) {
                        lblGetrokkenKaart.setImageString(getrokkenKaart.getVerticaleImageString());
                        hertekenKaartenVerticaal();
                    }

                    /**
                     * zorgt ervoor dat er een listener op de getrokken kaart wordt geplaatst
                     */
                    speelKaartEvent(controller.getSpelerKaarten(spelerNr), spelerNr);

                    revalidate();
                    repaint();
                }
            }
        });

    }

    public void computerSpeelEvent(int spelerNr) {
        /**
         * als een computer aan de beurt is gaat hij zoeken of hij een kaart kan spelen
         * zoniet? -> computerTrekEvent
         */
        if (controller.getSpelers().get(spelerNr) instanceof Computer && controller.getSpelers().get(spelerNr).getAanBeurt()) {
            String imgString;
            Kaart teSpelenKaart = ((Computer) controller.getSpelers().get(spelerNr)).getTeSpelenKaart();
            if (teSpelenKaart != null) {
                controller.speelKaart(teSpelenKaart, spelerNr);
                imgString = controller.getSpelbord().getAflegstapel().getBovensteKaart().getHorizontaleImageString();
                lblAflegStapel.setImageString(imgString);
                kaartenSpeler.remove(kaartenSpeler.size() - 1);

                if  (spelerNr == 1) {
                    hertekenKaartenHorizontaal();
                }

                if (spelerNr == 2 || spelerNr == 3) {
                    hertekenKaartenVerticaal();
                }

                if (teSpelenKaart.getWaarde() == 8) {
                    String[] kleurKeuze = {"harten", "ruiten", "klaveren", "schoppen"};
                    Random random = new Random();
                    int kleurKeuzeIndex = random.nextInt(4);
                    lblAflegStapel.setImageString("/view/images/" + kleurKeuze[kleurKeuzeIndex] + "/" + kleurKeuze[kleurKeuzeIndex] + "8.png");
                    controller.getSpelbord().getAflegstapel().legKaart(new Kaart(8, Kleur.values()[kleurKeuzeIndex], "images/" + kleurKeuze[kleurKeuzeIndex] + "/" + kleurKeuze[kleurKeuzeIndex] + "8.png", ""));
                }

                if (controller.getSpeelRichting() == 0) {
                    if (teSpelenKaart.getWaarde() == 1 && !controller.isSpelGedaan()) {
                        controller.setSpeelRichting(1);
                        controller.speelRichting(spelerNr);
                        if (controller.getAantalSpelers() > 2 && !controller.isSpelGedaan()) {
                            JOptionPane.showMessageDialog(null, "Er is van spelrichting veranderd", "Richting veranderd", JOptionPane.DEFAULT_OPTION);
                        }
                    } else if (teSpelenKaart.getWaarde() == 12 && !controller.isSpelGedaan()) {
                        controller.beeindigBeurtDame(spelerNr);
                        JOptionPane.showMessageDialog(null, "De volgende speler wordt overgeslagen");
                    } else {
                        controller.beeindigBeurt(spelerNr);
                    }
                } else {
                    if (teSpelenKaart.getWaarde() == 1 && !controller.isSpelGedaan()) {
                        controller.setSpeelRichting(0);
                        controller.speelRichting(spelerNr);
                        if (controller.getAantalSpelers() > 2 && !controller.isSpelGedaan()) {
                            JOptionPane.showMessageDialog(null, "Er is van spelrichting veranderd", "Richting veranderd", JOptionPane.DEFAULT_OPTION);
                        }
                    } else if (teSpelenKaart.getWaarde() == 12 && !controller.isSpelGedaan()) {
                        controller.beeindigBeurtDame(spelerNr);
                        JOptionPane.showMessageDialog(null, "De volgende speler wordt overgeslagen");
                    } else {
                        controller.speelRichting(spelerNr);
                    }
                }

                if (!controller.getSpelers().get(0).getAanBeurt()) {
                    robot.mouseMove(getX() + 500, getY() + 700);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                }

            } else {
                computerTrekEvent(spelerNr);
                revalidate();
                repaint();
            }

            /**
             * toont wanneer je bent verloren
             */
            if (controller.getSpelerKaarten(spelerNr).size() == 0 && !controller.isSpelGedaan()) {
                JOptionPane.showMessageDialog(null, "Je hebt verloren");
                int keuze = JOptionPane.showOptionDialog(null, "Nog een keer spelen?", "Einde spel!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Ja", "Nee"}, "Ja");
                if (keuze == 0) {
                    controller.herstartSpel();
                    jframe.dispose();
                } else {
                    System.exit(0);
                }
            }

            revalidate();
            repaint();
        }
    }


    public void computerTrekEvent(int spelerNr) {
        /**
         * wanneer computer geen kaart kan spelen gaat hij een kaart trekken
         */
        if (controller.getSpelers().get(spelerNr) instanceof Computer) {

            if (controller.getSpelbord().getTrekstapel().getKaarten().size() == 1) {
                controller.vulTrekStapel();
            }

            ((Computer) controller.getSpelers().get(spelerNr)).voegKaartToe();

            if (spelerNr == 1) {
                KaartLabel kaartLabel = new KaartLabel("/view/images/kaartAchterkant.png", "/view/images/kaartAchterkantV.png");
                kaartLabel.setImageString(kaartLabel.getHorizontale());
                kaartenSpeler.add(kaartLabel);
                hertekenKaartenHorizontaal();
            }

            if (spelerNr == 2 || spelerNr == 3) {
                KaartLabel kaartLabel = new KaartLabel("/view/images/kaartAchterkant.png", "/view/images/kaartAchterkantV.png");
                kaartLabel.setImageString(kaartLabel.getVerticale());
                kaartenSpeler.add(kaartLabel);
                hertekenKaartenVerticaal();
            }
            revalidate();
            repaint();

            /**
             * wanneer hij geen kaart kan spelen gaat de speler nog eens klikken waardoor het trekevent weer wordt getriggerd
             */
            if (((Computer) controller.getSpelers().get(spelerNr)).getTeSpelenKaart() != null) {
                computerSpeelEvent(spelerNr);
            } else {
                if (!controller.getSpelers().get(0).getAanBeurt()) {
                    robot.mouseMove(getX() + 500, getY() + 700);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                }
            }
        }
    }

    /**
     * de breedte van de container instellen zodat deze mooi in het midden komt
     */
    public void zetBreedte(int breedte, int hoogte) {
        setPreferredSize(new Dimension(breedte, hoogte));
    }

    /**
     * algoritme om kaarten in het midden te krijgen en het gat tussen de gespeelde kaarten op te vullen
     */
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
    }

    /**
     * draait de kaarten van een speler om zodat deze zichtbaar worden voor de speler aan beurt
     */
    public void draaiKaartenOm(int spelerNr) {
        if (controller.getSpelers().get(spelerNr).getAanBeurt()) {
            for (int i = 0; i < kaartenSpeler.size(); i++) {
                if (spelerNr == 0 || spelerNr == 1) {
                    kaartenSpeler.get(i).setImageString(kaartenSpeler.get(i).getHorizontale());
                } else if (spelerNr == 2 || spelerNr == 3) {
                    kaartenSpeler.get(i).setImageString(kaartenSpeler.get(i).getVerticale());
                }
            }
        }
    }
}
