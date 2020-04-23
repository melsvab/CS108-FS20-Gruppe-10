package gui;

import org.slf4j.Logger;
import server.Profil;
import server.Protocol;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Melanie
 * This class is shown if a player presses the start button at the "ButtonsClient" panel.
 * Board size as well as coin occurrance can be chosen and will be sent to the server.
 */

public class StartGamePanel extends BackgroundScoreArea {

    /**
     * This is a spinner for the board size.
     */
    JSpinner boardSize;

    /**
     * This is a spinner for the coin occurrence.
     */
    JSpinner coinOccurrence;

    /**
     * This is the data output stream to send
     * the chosen numbers to the server.
     */
    DataOutputStream dos;

    /**
     * This is the score layer
     */
    ScorePanel score;

    /**
     * Instantiates a new panel to start a game
     *
     * @param dos  the data output stream
     * @param score  the panel with the game
     */

    StartGamePanel(DataOutputStream dos, ScorePanel score) {
        // Data output stream will be saved to send a message later
        this.dos = dos;

        this.score = score;

        // Spinners are used for a player to choose a board size and the coin occurrence.
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(10, 10, 50,1);
        this.boardSize = new JSpinner(spinnerModel);
        SpinnerNumberModel spinnerModel2 = new SpinnerNumberModel(0, 0, 100,1);
        this.coinOccurrence = new JSpinner(spinnerModel2);

        // Text with information next to the spinners, so the player knows what he or she is deciding about.
        JLabel boardSizeText = new JLabel("Enter board size:  ");
        JLabel coinOccurrenceText = new JLabel("Enter coin occurrence:  ");
        JButton send = new JButton("Send");


        //settings for the layout
        this.setMinimumSize(new Dimension(920,600));
        this.setBackground(new Color(1,1,1, (float) 0.01));
        this.setBorder(BorderFactory.createTitledBorder(("Choose game settings:")));

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // The text about the board size is on the left top.
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(boardSizeText, gbc);

        // The spinner for the board size is on the right top.
        gbc.gridx = 1;
        this.add(boardSize, gbc);

        // The text about the coin occurrence is on the left bottom.
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(coinOccurrenceText, gbc);

        // The spinner for the coin occurrence is on the right bottom.
        gbc.gridx = 1;
        this.add(coinOccurrence, gbc);

        // The send button is at the left bottom underneath the text for the coin occurrence
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        send.addActionListener(this::actionPerformed);
        this.add(send, gbc);

        this.setVisible(false);
    }

    /*
    *  This method automatically starts if a client presses the send button
    *  and sends the input given by the spinners to the server.
    */
    public void actionPerformed(ActionEvent e) {
        try {
            dos.writeUTF(Protocol.STR1.name() + ":" + boardSize.getValue() + ":" + coinOccurrence.getValue());
        } catch (IOException f) {
            System.err.println(f.toString());
        }
        score.makeAllCenterPanelsInvisibleExcept(0);
    }

}
