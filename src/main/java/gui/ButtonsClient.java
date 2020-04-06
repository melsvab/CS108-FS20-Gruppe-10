package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ButtonsClient {

    JPanel panel;
    JButton playerlist;
    JButton create;
    JButton help;
    JButton back;
    JButton gamelist;
    JButton highscore;
    JButton quit;

    ButtonsClient() {
        this.panel = new JPanel();
        this.playerlist = new JButton("Playerlist");
        this.gamelist = new JButton("Gamelist");
        this.highscore= new JButton("Highscore");
        this.create = new JButton("Create Game");
        this.quit = new JButton("Quit");
        this.help = new JButton("Help");
        this.back = new JButton("Leave Lobby");

        panel.setLayout(new GridLayout(4,2));
        panel.setPreferredSize(new Dimension( 240, 150 ) );

        playerlist.addActionListener(this::actionPerformed);
        gamelist.addActionListener(this::actionPerformed);
        help.addActionListener(this::actionPerformed);
        highscore.addActionListener(this::actionPerformed);
        create.addActionListener(this::actionPerformed);
        back.addActionListener(this::actionPerformed);
        quit.addActionListener(this::actionPerformed);

        panel.add(create);
        panel.add(gamelist);
        panel.add(playerlist);
        panel.add(highscore);
        panel.add(back);
        panel.add(quit);
        panel.add(help);
    }

    public void actionPerformed(ActionEvent e) {
        /*
         * To Do: Add the DataOutPutStream to the ActionEvent like ClientChatGUI.
         * Change in the ClientReaderThread System.out.println to ccg.append(msg).
         * Change the help-message so only Join:number, str1:number:number, spectatormode,
         * appear or find another way how to implement something which needs an individual input.
         */
        if (e.getSource().equals(playerlist)) {
            //for example: dos.writeUTF(Protocol.PLL1.name());
        } else if (e.getSource().equals(gamelist)) {

        } else if (e.getSource().equals(help)) {

        } else if (e.getSource().equals(create)) {

        } else if (e.getSource().equals(highscore)) {

        } else if (e.getSource().equals(help)) {

        } else if (e.getSource().equals(back)) {

        } else { //equals quit

        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
