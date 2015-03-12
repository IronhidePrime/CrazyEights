package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SpelRegelsWindow extends JDialog {
    private JPanel pnlComponentContainer;
    private JTextArea txtSpelregels;
    private JLabel lblCrazyLogo;
    private JButton btnOK;

    public SpelRegelsWindow() {
        super.setSize(500, 825);
        super.setLocationRelativeTo(this);
        super.setAlwaysOnTop(true);
        //super.setResizable(false);

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("/view/images/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.setIconImage(image);
        super.setVisible(true);

        maakComponenten();
        behandelEvents();
    }

    public void maakComponenten() {
        pnlComponentContainer = new JPanel(new BorderLayout());
        pnlComponentContainer.setBorder(new EmptyBorder(10,10,0,10));
        txtSpelregels = new JTextArea();
        txtSpelregels.setOpaque(false);
        txtSpelregels.setEditable(false);
        txtSpelregels.setLineWrap(true);
        txtSpelregels.setWrapStyleWord(true);
        txtSpelregels.setFont(new Font("Arial", Font.BOLD, 12));

        pnlComponentContainer.add(txtSpelregels, BorderLayout.CENTER);
        vulTextArea(getSpelregels());

        lblCrazyLogo = new LogoLabel();
        lblCrazyLogo.setPreferredSize(new Dimension(250,250));
        pnlComponentContainer.add(lblCrazyLogo, BorderLayout.NORTH);

        btnOK = new JButton("OK");
        pnlComponentContainer.add(btnOK, BorderLayout.SOUTH);

        super.add(pnlComponentContainer);
    }

    public List<String> getSpelregels() {
        Path spelregels = Paths.get("spelregels.txt");
        List<String> zinnen= null;

        try {
            zinnen = Files.readAllLines(spelregels, Charset.defaultCharset());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Kon het bestand niet lezen!", "Geen spelregels beschikbaar!", JOptionPane.OK_OPTION);
        }
        return zinnen;
    }

    public void vulTextArea(List<String> zinnen) {
        for (String zin : zinnen) {
            txtSpelregels.append(zin + "\n");
        }
    }

    public void behandelEvents() {
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}

