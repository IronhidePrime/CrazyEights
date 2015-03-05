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

    public KaartContainer(Controller controller, AflegStapelLabel lblAflegStapel) {
        this.controller = controller;
        this.setPreferredSize(new Dimension(new Dimension(kaartOverlap * (controller.getAantalKaartenSpeler() - 1) + kaartBreedte, 100)));
        this.setOpaque(false);
        this.lblAflegStapel = lblAflegStapel;
    }

    public void maakLists(int spelerNr){
        kaartenSpeler = new LinkedList<>();
        for (int i = 0; i < controller.getAantalKaartenSpeler(); i++) {
            kaartenSpeler.add(new KaartLabel(controller.getSpelerKaarten(spelerNr).get(i).getHorizontaleImageString(), controller));
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
                    revalidate();
                    repaint();
                }

            });
        }
    }

    public void printKaarten(){
        for (KaartLabel kaartLabel: kaartenSpeler){
            System.out.println(kaartLabel.getImageString());
        }
    }

}
