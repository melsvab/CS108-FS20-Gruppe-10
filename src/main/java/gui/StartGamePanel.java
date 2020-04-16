package gui;

import org.slf4j.Logger;
import server.Profil;
import server.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;

public class StartGamePanel extends BackgroundTextArea {

    JTextField boardSize;
    JTextField coinOccurence;
    private JLabel boardSizeText;
    private JLabel coinOccurenceText;
    private JButton send;
    DataOutputStream dos;

    StartGamePanel() {
        this.boardSize = new JTextField(10);
        this.coinOccurence = new JTextField(10);
        this.boardSizeText = new JLabel("Enter Boardsize:  ");
        this.coinOccurenceText = new JLabel("Enter Coin Occurrence:  ");
        this.send = new JButton("Send");
        this.setPreferredSize(new Dimension(350,250));
        this.setBackground(new Color(1,1,1, (float) 0.01));


        this.setBorder(BorderFactory.createTitledBorder(("Choose the Gamesettings:")));

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(boardSizeText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(boardSize, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(coinOccurenceText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(coinOccurence, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        send.addActionListener(this::actionPerformed);
        this.add(send, gbc);

        this.setVisible(false);
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            dos.writeUTF(Protocol.STR1.name() + ":" + boardSize.getText() + ":" + coinOccurence.getText());
        } catch (IOException f) {
            System.err.println(f.toString());
        }
        this.setVisible(false);
    }

    public static void main(String[] args) {
        gui.StartGamePanel game = new gui.StartGamePanel();
        JFrame frame = new JFrame();
        frame.getContentPane().add(game);
        frame.setSize(new Dimension(350,250));
        frame.setVisible(true);
    }
}
