package view;

import model.Spelbord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartUI extends JFrame {
    private JLabel lblTitel;
    private JLabel lblCrazyLogo;

    private JComboBox cboSpelers;

    private JCheckBox chkComputer;

    private JButton btnDeal;
    private JButton btnHighscores;
    private JButton btnInfo;
    private JButton btnSpelregels;


    private int aantalSpelers;

    public StartUI(){
        super("Crazy Eights");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(600,600);
        super.setLocationRelativeTo(null);
        maakComponenten();
        maakLayout();
        behandelEvents();
        super.setVisible(true);
    }

    public void maakComponenten(){
        lblTitel = new JLabel("Crazy Eights");
        lblCrazyLogo = new JLabel();

        cboSpelers = new JComboBox();
        cboSpelers.addItem(2);
        cboSpelers.addItem(3);
        cboSpelers.addItem(4);
        cboSpelers.setToolTipText("Aantal spelers?");

        chkComputer = new JCheckBox("Computer?");

        btnDeal = new JButton("Start");
        btnHighscores = new JButton("Highscores");
        btnInfo = new JButton("Info");
        btnSpelregels = new JButton("Spelregels");
    }

    public void maakLayout(){
        super.add(lblTitel, BorderLayout.NORTH);
        lblTitel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel pnlLogo = new JPanel();
        pnlLogo.setLayout(new BorderLayout());
        lblCrazyLogo.setSize(50,50);
        lblCrazyLogo.setBackground(Color.WHITE);
        lblCrazyLogo.setOpaque(true);
        //lblCrazyLogo.setVerticalAlignment(SwingConstants.CENTER);
        pnlLogo.add(lblCrazyLogo, BorderLayout.CENTER);
        pnlLogo.setBorder(new EmptyBorder(20,20,20,20));
        super.add(pnlLogo, BorderLayout.CENTER);

        cboSpelers.setPreferredSize(new Dimension(10,10));
        cboSpelers.setMaximumSize(new Dimension(10,10));

        JPanel pnlSpelerContainer = new JPanel();
        pnlSpelerContainer.setMaximumSize(new Dimension(50,50));
        pnlSpelerContainer.setLayout(new GridLayout(4,1,10,10));
        pnlSpelerContainer.add(cboSpelers);
        pnlSpelerContainer.add(chkComputer);
        pnlSpelerContainer.add(new JLabel());
        pnlSpelerContainer.add(btnDeal);
        pnlSpelerContainer.setBorder(new EmptyBorder(0, 0, 0, 10));
        super.add(pnlSpelerContainer, BorderLayout.EAST);

        JPanel pnlBottomContainer = new JPanel();
        btnHighscores.setMinimumSize(new Dimension(10,10));
        pnlBottomContainer.add(btnHighscores);
        btnInfo.setMaximumSize(new Dimension(200, 200));
        pnlBottomContainer.add(btnInfo);
        btnSpelregels.setPreferredSize(new Dimension(40,20));
        pnlBottomContainer.add(btnSpelregels);
        super.add(pnlBottomContainer, BorderLayout.SOUTH);
    }

    public void behandelEvents() {
        btnDeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //index start bij 0 -> aantal spelers = index + 2
                int aantalSpelers = cboSpelers.getSelectedIndex() + 2;

                //doorgeven aan model
                dispose();
                new SpelbordUI();
            }
        });
    }
}
