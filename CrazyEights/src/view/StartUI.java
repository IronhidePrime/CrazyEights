package view;

import controller.CrazyEightsController;
import model.Spelbord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class StartUI extends JFrame {
    //TODO: afwerking StartUI (logo, fonts, ...)
    //TODO: keuze multiplayer doorsturen
    //TODO: spelregels/info/highscores toevoegen
    private JLabel lblTitel;
    private JLabel lblCrazyLogo;
    private JLabel lblAantalSpelers;

    private JComboBox cboSpelers;

    private JCheckBox chkComputer;

    private JButton btnDeal;
    private JButton btnHighscores;
    private JButton btnInfo;
    private JButton btnSpelregels;

    private CrazyEightsController controller;

    public StartUI(CrazyEightsController controller){
        super("Crazy Eights v1.0");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(600,600);
        super.setLocationRelativeTo(null);
        maakComponenten();
        maakLayout();
        behandelEvents();
        super.setVisible(true);
        this.controller = controller;
    }

    public void maakComponenten(){
        lblTitel = new JLabel("Crazy Eights");
        lblCrazyLogo = new JLabel("Logo");
        lblAantalSpelers = new JLabel("Aantal spelers:");

        cboSpelers = new JComboBox();
        cboSpelers.addItem(2);
        cboSpelers.addItem(3);
        cboSpelers.addItem(4);
        cboSpelers.setToolTipText("Aantal spelers?");

        chkComputer = new JCheckBox("Multiplayer");

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
        lblCrazyLogo.setBackground(Color.WHITE);
        lblCrazyLogo.setOpaque(true);
        pnlCenter.add(lblCrazyLogo);

        JPanel pnlSpelerContainer = new JPanel();
        pnlSpelerContainer.setLayout(new GridLayout(4,2,10,10));
        pnlSpelerContainer.setBorder(new EmptyBorder(50,0,0,0));
        pnlSpelerContainer.add(lblAantalSpelers);
        pnlSpelerContainer.add(cboSpelers);
        pnlSpelerContainer.add(new JLabel());
        pnlSpelerContainer.add(chkComputer);
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
                //index start bij 0 -> aantal spelers = index + 2
                controller.zetAantalSpelersSpelbord(cboSpelers.getSelectedIndex() + 2);
                //doorgeven aan model
                dispose();
                new SpelbordUI(controller);
            }
        });
    }
}

