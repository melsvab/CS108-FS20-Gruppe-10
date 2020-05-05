package gui;

import msc.GameMusic;
import server.Message;
import server.Profil;
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

    /**
     * The spinner with the game number
     */
    private JSpinner gameNumber;

    /**
     * The spinner with the spectator button
     */
    private JRadioButton spectate;

    /**
     * The data output stream to send messages to the server
     */
    DataOutputStream dos;

    /**
     * The score panel to set visibility for all other panels
     * that are at the same spot as this panel here.
     */
    ScorePanel score;

    /**
     * A profile of the client
     */
    Profil profile;

    /**
     * Initialises a new join a game panel.
     * that is invisible until someone presses the join a game button
     *
     * @param dos the data output stream
     * @param score the score panel where this panel is in
     *
     */
    JoinGamePanel(DataOutputStream dos, ScorePanel score, Profil profile) {
        // Data output stream will be saved to send a message later
        this.dos = dos;
        this.profile = profile;

        this.score = score;

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100,1);
        this.gameNumber = new JSpinner(spinnerModel);
        JLabel gameNumberText = new JLabel("Enter game number:  ");
        JButton send = new JButton("Send");
        this.spectate = new JRadioButton();
        JLabel spectateText = new JLabel("Spectator mode:   ");

        this.setMinimumSize(new Dimension(920,600));
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
        this.add(gameNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(spectateText, gbc);

        gbc.gridx = 1;
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
     * Sends the decision of a game number to the server
     * @param e an event because someone pressed the 'send' button
     */
    public void actionPerformed(ActionEvent e) {
        profile.gmsc.createButtonSound();
        try {
            if (spectate.isSelected()) {
                dos.writeUTF(Protocol.SPEC.name() + ":" + gameNumber.getValue());
            } else {
                dos.writeUTF(Protocol.JOIN.name() + ":" + gameNumber.getValue());
            }
        } catch (IOException f) {
            System.err.println(f.toString());
        }
        score.makeAllCenterPanelsInvisibleExcept(0);
    }

}
