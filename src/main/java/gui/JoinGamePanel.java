package gui;

import server.Message;
import server.Protocol;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Melanie
 * This class has the layor that shows up if a player presses the join game button.
 */

public class JoinGamePanel extends BackgroundScoreArea {

    private JSpinner gameNumber;
    private JRadioButton spectate;
    DataOutputStream dos;

    /**
     * Initialises a new join a game panel
     * that is invisible until someone presses the join a game button
     */

    JoinGamePanel(DataOutputStream dos) {
        // Data output stream will be saved to send a message later
        this.dos = dos;


        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100,1);
        this.gameNumber = new JSpinner(spinnerModel);
        JLabel gameNumberText = new JLabel("Enter game number:  ");
        JButton send = new JButton("Send");
        this.spectate = new JRadioButton();
        JLabel spectateText = new JLabel("Spectator mode:   ");

        this.setPreferredSize(new Dimension(350,250));
        this.setBackground(new Color(1,1,1, (float) 0.01));

        this.setBorder(BorderFactory.createTitledBorder(("Join the game:")));
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

    /**
     * sends the decision of a game number to the server
     */
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

}
