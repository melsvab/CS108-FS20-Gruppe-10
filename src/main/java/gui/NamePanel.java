package gui;

import server.Parameter;
import server.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;

public class NamePanel extends BackgroundScoreArea {

     JTextField nameInput;
     JLabel nameText;
     DataOutputStream dos;
     JButton send;
     JLabel wrongInput;

     NamePanel (DataOutputStream dos) {

        this.dos = dos;

        // Text with information next to the Textfield, so the player knows what he or she is deciding about.
        this.nameText = new JLabel("Enter your new nickname:  ");
        this.nameInput = new JTextField(25);
        this.send = new JButton("Send");
        this.wrongInput = new JLabel("     ");


        //settings for the layout
        this.setPreferredSize(new Dimension(350,250));
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

        // Error-Message if the input contains ":" "." or spaces.
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(wrongInput, gbc);

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
        boolean doesNotContain;
        int x = input.indexOf(" ");
        int y = input.indexOf(".");
        int z = input.indexOf(",");
        if (x == -1 && y == -1 && z == -1) {
            doesNotContain = true;
        }
        else {
            doesNotContain = false;
        }
        Parameter name = new Parameter(input, 3);
        if (name.isCorrect && doesNotContain) {
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
                System.err.toString();
            }

            wrongInput.setText("");
            nameInput.setText("");
            this.setVisible(false);
        }
        else {
            wrongInput.setText("You cannot use \": \", \".\" or spaces!");
        }
    }
}
