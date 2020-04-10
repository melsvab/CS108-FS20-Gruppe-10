package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonsGame extends BackgroundPanelArea {

    JButton up; //Optional: use Basic.Arrow.Button instead (and find out how to get the size of the arrow button larger)
    JButton down; //Optional: find out how you get the Buttons to be square
    JButton left;
    JButton right;

    ButtonsGame() {
        this.up = new JButton("Up");
        this.right = new JButton("Right");
        this.down = new JButton("Down");
        this.left = new JButton("Left");

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.setPreferredSize(new Dimension( 300, 300 ) );

        up.setPreferredSize(new Dimension(100,100));
        right.setPreferredSize(new Dimension(100,100));
        down.setPreferredSize(new Dimension(100,100));
        left.setPreferredSize(new Dimension(100,100));

        up.addActionListener(this::actionPerformed);
        right.addActionListener(this::actionPerformed);
        down.addActionListener(this::actionPerformed);
        left.addActionListener(this::actionPerformed);

        /*x and y are like coordinates. if x=0, y=0 its on the top left, x=1, y=1 its down right for example for a grid with
         * (0,0/0,1/1,0/1,1) -> 4 fields.
         *If you use for example x=0, y=5 the gridBagLayout will automatically assume a coordinated field with y from 0 to 5.
         *So you dont have to initialize something for the grid for this layout.
         *This one has an grid for x = 0 to 2 and y = 0 to 2 so the buttons can be like a cross.
         */
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        //gbc.gridwidth = 2;
        this.add(up, gbc);

        gbc.fill = GridBagConstraints.BOTH; //Optional: Find out why the "Left" Button is smaller
        gbc.gridx = 0;
        gbc.gridy = 1;
        //gbc.gridwidth = 2;
        this.add(left, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        //gbc.gridwidth = 2;
        this.add(right, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 2;
        //gbc.gridwidth = 2;
        this.add(down, gbc);


        this.setVisible(false);

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
}
