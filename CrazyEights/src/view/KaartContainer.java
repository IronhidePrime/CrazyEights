package view;

import controller.Controller;
import model.Kaart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class KaartContainer extends JLayeredPane {
    private int kaartBreedte = 70;
    private int kaartOverlap = 40;
    private List<KaartLabel> kaartenSpeler;
    private Controller controller;
    private AflegStapelLabel lblAflegStapel;
    private TrekStapelLabel lblTrekstapel;

    public KaartContainer(Controller controller, AflegStapelLabel lblAflegStapel, TrekStapelLabel lblTrekstapel) {
        this.controller = controller;
        this.setPreferredSize(new Dimension(new Dimension(kaartOverlap * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte, 100)));
        this.setOpaque(false);
        this.lblAflegStapel = lblAflegStapel;
        this.lblTrekstapel = lblTrekstapel;
    }

    public void maakLists(int spelerNr){
        kaartenSpeler = new LinkedList<>();
        for (int i = 0; i < controller.getAantalKaartenSpeler(); i++) {
            if (spelerNr==0 || spelerNr == 1){
                kaartenSpeler.add(new KaartLabel(controller.getSpelerKaarten(spelerNr).get(i).getHorizontaleImageString(), controller));
            }
            if (spelerNr==2 || spelerNr == 3){
                kaartenSpeler.add(new KaartLabel(controller.getSpelerKaarten(spelerNr).get(i).getVerticaleImageString(), controller));
            }
        }
    }

    public void tekenKaartLabels(){
        kaartOverlap = 0;
        for (int j = 0; j < controller.getAantalKaartenSpeler(); j++) {
            kaartenSpeler.get(j).setBounds(kaartOverlap, 0, kaartBreedte, 100);
            this.add(kaartenSpeler.get(j), new Integer(j));
            kaartOverlap += 40;
        }
    }

    public void tekenKaartLabelsVerticaal(){
        kaartOverlap = 0;
        for (int j = 0; j < controller.getAantalKaartenSpeler(); j++) {
            kaartenSpeler.get(j).setBounds(0, kaartOverlap, 100, kaartBreedte);
            this.add(kaartenSpeler.get(j), new Integer(j));
            kaartOverlap += 40;
        }
    }

    public void speelKaartEvent(List<Kaart> spelerKaarten, int spelerNr){
        /**
         * 1. KaartObjectIndex is de index van het kaart Object dat verwijderd moet worden nadat dat je een kaart kan spelen uit de lijst van kaarten van de speler
         * 2. controleren of het kaartObject gespeeld kan worden en controleren of de huidige speler aan beurt is, zo ja wordt het kaartObject en het bijbehorende kaartlabel verwijderd
         */

        for (KaartLabel kaartLabel : kaartenSpeler){

            kaartLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);

                    //1
                    int KaartObjectIndex  = 0;
                    String imageStringKaartLabel = kaartLabel.getImageString();
                    System.out.println("geklikte kaart is" + kaartLabel.getImageString());
                    for (int i=0; i<spelerKaarten.size();i++){
                        String imageStringKaartObjectH = spelerKaarten.get(i).getHorizontaleImageString();
                        String imageStringKaartObjectV = spelerKaarten.get(i).getVerticaleImageString();

                        if (imageStringKaartObjectH.equals(imageStringKaartLabel) || imageStringKaartObjectV.equals(imageStringKaartLabel)){
                            KaartObjectIndex = i;
                            System.out.println("KaartIndex is " + KaartObjectIndex);
                            System.out.println("img string KaartObject is " + controller.getSpelers().get(spelerNr).getKaarten().get(KaartObjectIndex).getHorizontaleImageString());
                            System.out.println("img string kaartLabel is " + kaartLabel.getImageString());
                        }
                    }

                    if (controller.speelKaartMogelijk(spelerKaarten.get(KaartObjectIndex)) && controller.getSpelers().get(spelerNr).getAanBeurt()){
                        Kaart teSpelenKaart = controller.getSpelerKaarten(spelerNr).get(KaartObjectIndex);
                        System.out.println("teSpelen kaart is " + teSpelenKaart.getHorizontaleImageString());
                        kaartenSpeler.remove(kaartLabel);
                        remove(kaartLabel);
                        removeAll();

                        kaartOverlap=0;
                        if (spelerNr == 0 || spelerNr == 1){
                            for (KaartLabel kaartLabel1: kaartenSpeler){
                                kaartLabel1.setBounds(kaartOverlap,0,kaartBreedte,100);
                                kaartOverlap += 40;
                                add(kaartLabel1);
                                lblAflegStapel.setImageString(kaartLabel.getImageString());
                            }
                            kaartOverlap = 40;
                            zetBreedte(kaartOverlap * (kaartenSpeler.size() - 1) + kaartBreedte, 100);
                        }

                        if (spelerNr == 2 || spelerNr == 3){
                            for (KaartLabel kaartLabel1: kaartenSpeler){
                                kaartLabel1.setBounds(0,kaartOverlap,100,kaartBreedte);
                                kaartOverlap += 40;
                                add(kaartLabel1);
                                lblAflegStapel.setImageString(controller.getSpelerKaarten(spelerNr).get(KaartObjectIndex).getHorizontaleImageString());
                            }
                            kaartOverlap = 40;
                            zetBreedte(100, kaartOverlap * (kaartenSpeler.size() - 1) + kaartBreedte);
                        }
                        controller.speelKaart(teSpelenKaart, spelerNr);
                        controller.beeindigBeurt(spelerNr);
                        System.out.println("bovenste kaart op aflegstapel is" + controller.getSpelbord().getAflegstapel().getBovensteKaart().getHorizontaleImageString());
                    }
                    revalidate();
                    repaint();
                }
            });
        }
    }

    public void zetBreedte(int breedte, int hoogte){
        setPreferredSize(new Dimension(breedte,hoogte));
    }

    public List<KaartLabel> getKaartenSpeler() {
        return kaartenSpeler;
    }

    public void trekKaartEvent(int spelerNr){
        lblTrekstapel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                System.out.println("er is op de trekstapel geklikt");

                if (controller.getSpelers().get(spelerNr).getAanBeurt()) {
                Kaart getrokkenKaart = controller.getSpelbord().getTrekstapel().neemKaart();
                System.out.println("getrokken kaart is" + getrokkenKaart.getHorizontaleImageString());
                KaartLabel lblGetrokkenKaart = new KaartLabel(getrokkenKaart.getHorizontaleImageString(), controller);
                controller.getSpelerKaarten(spelerNr).add(getrokkenKaart);

                kaartOverlap = 0;
                    if (spelerNr == 0 || spelerNr == 1){
                        removeAll();
                        kaartenSpeler.add(lblGetrokkenKaart);
                        for (int i = 0; i < kaartenSpeler.size(); i++) {
                            kaartenSpeler.get(i).setBounds(kaartOverlap, 0, kaartBreedte, 100);
                            kaartOverlap += 40;
                            add(kaartenSpeler.get(i), new Integer(i));
                        }
                        kaartOverlap = 40;
                        zetBreedte(kaartOverlap * (kaartenSpeler.size() - 1) + kaartBreedte, 100);
                    }

                    if (spelerNr == 2 || spelerNr == 3){
                        removeAll();
                        lblGetrokkenKaart.setImageString(getrokkenKaart.getVerticaleImageString());
                        kaartenSpeler.add(lblGetrokkenKaart);
                        for (int i = 0; i < kaartenSpeler.size(); i++) {
                            kaartenSpeler.get(i).setBounds(0, kaartOverlap, 100, kaartBreedte);
                            kaartOverlap += 40;
                            add(kaartenSpeler.get(i), new Integer(i));
                        }
                        kaartOverlap = 40;
                        zetBreedte(100, kaartOverlap * (kaartenSpeler.size() - 1) + kaartBreedte);
                    }
                }
                revalidate();
                repaint();
                speelKaartEvent(controller.getSpelerKaarten(spelerNr),spelerNr);
                System.out.println("Size trekstapel na het trekken van een kaart" + controller.getSpelbord().getTrekstapel().getKaarten().size());
            }
        });
    }
}
