package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonsGame {

    JPanel panel;
    JButton up; //Optional: use Basic.Arrow.Button instead (and find out how to get the size of the arrow button larger)
    JButton down; //Optional: find out how you get the Buttons to be square
    JButton left;
    JButton right;

    ButtonsGame() {
        this.panel = new JPanel();
        this.up = new JButton("Up");
        this.right = new JButton("Right");
        this.down = new JButton("Down");
        this.left = new JButton("Left");

        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.BLUE); //Optional: Background with the Water-Design
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setPreferredSize(new Dimension( 300, 300 ) );

        up.setPreferredSize(new Dimension(100,100));
        right.setPreferredSize(new Dimension(100,100));
        down.setPreferredSize(new Dimension(100,100));
        left.setPreferredSize(new Dimension(100,100));

        up.addActionListener(this::actionPerformed);
        right.addActionListener(this::actionPerformed);
        down.addActionListener(this::actionPerformed);
        left.addActionListener(this::actionPerformed);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        //gbc.gridwidth = 2;
        panel.add(up, gbc);

        gbc.fill = GridBagConstraints.BOTH; //Optional: Find out why the "Left" Button is smaller
        gbc.gridx = 0;
        gbc.gridy = 1;
        //gbc.gridwidth = 2;
        panel.add(left, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        //gbc.gridwidth = 2;
        panel.add(right, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 2;
        //gbc.gridwidth = 2;
        panel.add(down, gbc);


        panel.setVisible(false);

    }
    /*
     * To Do: Add the DataOutPutStream to the ActionEvent like ClientChatGUI.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(up)) {

        } else if (e.getSource().equals(right)) {

        } else if (e.getSource().equals(down)) {

        } else { //equals left

        }
    }
    public void setVisible(boolean b) {
        /*
         * Note: This method will be used to let the buttons only appear if the client is inGame.
         */
        panel.setVisible(b);
    }

    public JPanel getPanel() {
        return panel;
    }
}
