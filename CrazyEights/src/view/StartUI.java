package view;

import controller.Controller;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class StartUI extends JFrame {
    private JLabel lblTitel;
    private JLabel lblCrazyLogo;
    private JLabel lblAantalSpelers;

    private JComboBox cboSpelers;

    private JCheckBox chkMultiplayer;

    private JButton btnDeal;
    private JButton btnHighscores;
    private JButton btnLaden;
    private JButton btnSpelregels;

    private Controller controller;

    private int aantalSpelers;


    public StartUI(){
        super("Crazy Eights v1.0");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(600,600);
        super.setLocationRelativeTo(null);
        maakComponenten();
        maakLayout();
        behandelEvents();
        super.setVisible(true);
        this.controller = new Controller();

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("/view/images/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setIconImage(image);
    }

    public void maakComponenten(){
        lblTitel = new JLabel("Crazy Eights");
        lblCrazyLogo = new LogoLabel();
        lblAantalSpelers = new JLabel("Aantal spelers:");

        cboSpelers = new JComboBox();
        cboSpelers.addItem(2);
        cboSpelers.addItem(3);
        cboSpelers.addItem(4);
        cboSpelers.setToolTipText("Aantal spelers?");

        chkMultiplayer = new JCheckBox("Multiplayer");

        btnDeal = new JButton("Start");
        btnHighscores = new JButton("Highscores");
        btnLaden = new JButton("Laad spel");
        btnSpelregels = new JButton("Spelregels");
    }

    public void maakLayout(){
        /**
         * Panel voor de titel vanboven weer te geven
         */
        JPanel pnlTitel = new JPanel();
        pnlTitel.setBorder(new EmptyBorder(20,0,0,0));
        lblTitel.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitel.setFont(new Font("Arial", Font.BOLD, 30));
        pnlTitel.add(lblTitel);
        super.add(pnlTitel, BorderLayout.NORTH);

        /**
         * Panel voor logo en combobox/checkbox, start mooi in het midden weer te geven
         */
        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new GridLayout(1, 2, 30, 0));
        pnlCenter.add(lblCrazyLogo);

        JPanel pnlSpelerContainer = new JPanel();
        pnlSpelerContainer.setLayout(new GridLayout(4,2,10,10));
        pnlSpelerContainer.setBorder(new EmptyBorder(50,0,0,0));
        pnlSpelerContainer.add(lblAantalSpelers);
        pnlSpelerContainer.add(cboSpelers);
        pnlSpelerContainer.add(new JLabel());
        pnlSpelerContainer.add(chkMultiplayer);
        pnlSpelerContainer.add(new JLabel());
        pnlSpelerContainer.add(new JLabel());
        pnlSpelerContainer.add(new JLabel());
        pnlSpelerContainer.add(btnDeal);
        pnlCenter.add(pnlSpelerContainer);

        pnlCenter.setBorder(new EmptyBorder(20,20,20,20));
        super.add(pnlCenter, BorderLayout.CENTER);

        /**
         * Panel voor spelregels, info en highscores ondereen weer te geven
         */
        JPanel pnlBottomContainer = new JPanel();
        pnlBottomContainer.setLayout(new GridLayout(1,3,10,0));
        pnlBottomContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        btnSpelregels.setPreferredSize(new Dimension(0,50));
        pnlBottomContainer.add(btnSpelregels);
        pnlBottomContainer.add(btnLaden);
        pnlBottomContainer.add(btnHighscores);
        super.add(pnlBottomContainer, BorderLayout.SOUTH);
    }

    public void behandelEvents() {
        btnDeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                /**
                 * Namen van de spelers opvragen
                 * Doorgeven aan controller -> spelbord
                 */
                aantalSpelers = cboSpelers.getSelectedIndex() + 2;
                String[] namenSpelers = new String[aantalSpelers];
                for (int i=0; i<aantalSpelers; i++) {
                    if (chkMultiplayer.isSelected()) {
                        namenSpelers[i] = checkGeldigheidNaam(i);
                        //controller.zetPropertySpelerBord(aantalSpelers,true);
                        controller.setMultiplayer(true);
                        controller.maakMens(namenSpelers[i]);
                    } else {
                        if (i==0) {
                            controller.setMultiplayer(false);
                            namenSpelers[i] = checkGeldigheidNaam(i);
                            //controller.zetPropertySpelerBord(aantalSpelers, false);
                            //controller.zetPropertySpelersSingle(namenSpelers[i]);
                            controller.maakMens(namenSpelers[i]);
                        } else if (i==1) {
                            controller.maakComputer("Hans En Grietje");
                        } else if (i==2) {
                            controller.maakComputer("Supper Eddy");
                        } else if (i==3) {
                            controller.maakComputer("Badass Kristiaan");
                        }
                    }
                }
                /*
                if (controller.vraagPropertyMultiplayer()){
                    controller.zetPropertySpelersMulti(namenSpelers);
                }
                */
                controller.setSpelGeladen(false);
                new SpelbordUI(controller);
            }
        });

        btnLaden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                for (int i=0;i<controller.vraagPropertyAantalSpelers();i++){
                    if (!controller.vraagIsMultiplayer()){
                        if (i==0) {
                            controller.maakMens(controller.vraagSpelerNaamProperty(0));
                        } else if (i==1) {
                            controller.maakComputer("Hans");
                        } else if (i==2) {
                            controller.maakComputer("Supper Eddy");
                        } else if (i==3) {
                            controller.maakComputer("Kristiaan");
                        }
                    } else {
                        controller.maakMens(controller.vraagSpelerNaamProperty(i));
                    }
                }
                controller.setSpelGeladen(true);
                new SpelbordUI(controller);
            }
        });

        btnSpelregels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SpelRegelsWindow();
            }
        });
    }

    public String checkGeldigheidNaam(int spelerNr) {
        boolean juisteNaam = false;
        String spelersNaam = "";
        while(!juisteNaam) {
            try {
                spelersNaam = JOptionPane.showInputDialog(null, "Geef de naam van speler " + (spelerNr + 1) + " (max 10 karakters) ", "Naam", JOptionPane.QUESTION_MESSAGE);
                if (spelersNaam.length() > 10) {
                    JOptionPane.showMessageDialog(null, "De naam bevatte meer als 10 karakters!");
                    juisteNaam = false;
                } else if (spelersNaam.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Geen naam ingegeven!!!");
                    juisteNaam = false;
                } else {
                    juisteNaam = true;
                }
            } catch (NullPointerException nullEx) {
                juisteNaam = true;
                spelersNaam = "Defaultje";
            }
        }
        return spelersNaam;
    }
}

