package view;

import model.Spelbord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartUI extends JFrame {
    private JLabel lblTitel;
    private JComboBox cboSpelers;
    //logo toe te voegen
    private JCheckBox chkComputer;
    private JButton btnDeal;
    private int aantalSpelers;

    public StartUI(){
        super("Crazy Eights");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(300,200);
        super.setLocationRelativeTo(null);
        maakComponenten();
        maakLayout();
        behandelEvents();
        super.setVisible(true);
    }

    public void maakComponenten(){
        lblTitel = new JLabel("Crazy Eights");
        cboSpelers = new JComboBox();
        cboSpelers.addItem(2);
        cboSpelers.addItem(3);
        cboSpelers.addItem(4);
        cboSpelers.setToolTipText("Aantal spelers?");
        btnDeal = new JButton("Deal");
        chkComputer = new JCheckBox("Computer?");
    }

    public void maakLayout(){
        super.setLayout(new BorderLayout());
        super.add(lblTitel, BorderLayout.NORTH);
        lblTitel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel pnlPlayerContainer = new JPanel();
        pnlPlayerContainer.setLayout(new GridLayout(3,1,10,10));
        pnlPlayerContainer.add(cboSpelers);
        pnlPlayerContainer.add(chkComputer);
        pnlPlayerContainer.add(btnDeal);
        pnlPlayerContainer.setBorder(new EmptyBorder(0,0,0,10));
        super.add(pnlPlayerContainer, BorderLayout.EAST);


    }

    public void behandelEvents() {
        btnDeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //index start bij 0 -> aantal spelers = index + 2
                int aantalSpelers = cboSpelers.getSelectedIndex() + 2;

                //doorgeven aan model
                Spelbord test = new Spelbord();
                test.setAantalSpelers(aantalSpelers);
                System.out.println("Aantal spelers: " + aantalSpelers);
                dispose();
                new SpelBordUI();
            }
        });
    }
}
