package gui;

import server.Message;
import server.Protocol;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;

public class JoinGamePanel extends BackgroundScoreArea {

    private JSpinner gameNumber;
    private JLabel gameNumberText;
    private JButton send;
    private JRadioButton spectate;
    private JLabel spectateText;
    DataOutputStream dos;

    JoinGamePanel(DataOutputStream dos) {
        this.dos = dos;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100,1);
        this.gameNumber = new JSpinner(spinnerModel);
        this.gameNumberText = new JLabel("Enter Game Number:  ");
        this.send = new JButton("Send");
        this.spectate = new JRadioButton();
        this.spectateText = new JLabel("Spectator Mode:   ");

        this.setPreferredSize(new Dimension(350,250));
        this.setBackground(new Color(1,1,1, (float) 0.01));

        this.setBorder(BorderFactory.createTitledBorder(("Join the Game:")));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(gameNumberText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(gameNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(spectateText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(spectate, gbc);


        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        send.addActionListener(this::actionPerformed);
        this.add(send, gbc);

        this.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (spectate.isSelected()) {
                dos.writeUTF(Protocol.SPEC.name() + ":" + gameNumber.getValue());
            }
            else {
                dos.writeUTF(Protocol.JOIN.name() + ":" + gameNumber.getValue());
            }
        } catch (IOException f) {
            System.err.println(f.toString());
        }
        this.setVisible(false);
    }

    public static void main(String[] args) {
        /*gui.JoinGamePanel game = new gui.JoinGamePanel();
        JFrame frame = new JFrame();
        frame.getContentPane().add(game);
        frame.setSize(new Dimension(350,250));
        frame.setVisible(true);*/
    }
}
