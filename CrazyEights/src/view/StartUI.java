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
    //TODO: afwerking StartUI (logo, fonts, ...)
    //TODO: keuze multiplayer doorsturen
    //TODO: spelregels/info/highscores toevoegen
    //java.net.URL url = ClassLoader.getSystemResource("/view/images/icon.png");
    private JLabel lblTitel;
    private JLabel lblCrazyLogo;
    private JLabel lblAantalSpelers;

    private JComboBox cboSpelers;

    private JCheckBox chkMultiplayer;

    private JButton btnDeal;
    private JButton btnHighscores;
    private JButton btnInfo;
    private JButton btnSpelregels;

    private Controller controller;


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
        btnInfo = new JButton("Info");
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
        pnlBottomContainer.add(btnInfo);
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
                int aantalSpelers = cboSpelers.getSelectedIndex() + 2;
                for (int i=0; i<aantalSpelers; i++) {
                    if (chkMultiplayer.isSelected()) {
                        String spelersNaam = JOptionPane.showInputDialog(null, "Geef de naam van speler " + (i + 1), "Naam");
                        controller.maakMens(spelersNaam);
                    } else {
                        if (i==0) {
                            controller.maakComputer("Christiaan");
                        } else if (i==1) {
                            controller.maakComputer("Eddy");
                        } else if (i==2) {
                            controller.maakComputer("Hans");
                        } else if (i==3) {
                            controller.maakComputer("Dirk");
                        }
                    }
                }
                new SpelbordUI(controller);
            }
        });
    }
}

