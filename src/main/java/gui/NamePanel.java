package gui;

import server.Parameter;
import server.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Melanie
 * This layer is shown if a player presses
 * the 'change name' button.
 */

public class NamePanel extends BackgroundScoreArea {

    /*
    * The name input
    */
     JTextField nameInput;

    /*
     * A label for the text next to the
     * text field where players can type
     * in their preferred nickname
     */
     JLabel nameText;

    /*
     * A data output stream to send
     * the chosen nickname to the server
     */
     DataOutputStream dos;

    /*
     * A button to press if the
     * player has typed in the nickname
     */
     JButton send;

    /*
     * The game panel
     */
     GameGUI game;

    /**
     * Instantiates a new name panel
     *
     * @param dos  the data output stream
     * @param game  the panel with the game
     */
     NamePanel (DataOutputStream dos, GameGUI game) {

        this.dos = dos;

        // Text with information next to the Textfield, so the player knows what he or she is deciding about.
        this.nameText = new JLabel("Enter your new nickname:  ");
        this.nameInput = new JTextField(30);
        this.send = new JButton("Send");
        this.game = game;


        //settings for the layout
        this.setMinimumSize(new Dimension(920,600));
        this.setBackground(new Color(1,1,1,(float)0.01));
        this.setBorder(BorderFactory.createTitledBorder(("Choose your nickname:")));

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15,15,15,15);
        gbc.anchor = GridBagConstraints.WEST;

        // The text about the nickname is on the left top.
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(nameText, gbc);

        // The input for the nickname is on the right top.
        gbc.gridx = 1;
        this.add(nameInput, gbc);


        // The send button is at the left bottom underneath the text for the coin occurrence
        gbc.gridx = 0;
        gbc.gridy = 2;
        send.addActionListener(this::actionPerformed);
        this.add(send, gbc);

        this.setVisible(false);
     }

    /*
    *  This method automatically starts if a client presses the send button
    *  and sends the input written in the JTextField.
    */
    public void actionPerformed(ActionEvent e) {
        String input = "NAME:" + nameInput.getText();

        Parameter name = new Parameter(input, 3);

        String newNickname = name.wordOne;

        /*
         * if the answer is <YEAH> the nickname is change to the
         * system username. If the answer is something else,
         * this input will be used as the nickname.
         */

        if (newNickname.equalsIgnoreCase("YEAH")) {
            newNickname = System.getProperty("user.name");
        }

        // sending the desired nickname to server
        try {
            dos.writeUTF(Protocol.NAME.name() + ":" + newNickname);
        } catch (IOException f) {
            System.err.println(f.toString());
        }

        nameInput.setText("");
        this.setVisible(false);
        game.setVisible(true);

    }
}
