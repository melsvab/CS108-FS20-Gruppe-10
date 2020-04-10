package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import server.*;

public class ButtonsClient {

    JPanel panel;
    JButton playerlist;
    JButton create;
    JButton help;
    JButton back;
    JButton gamelist;
    JButton highscore;
    JButton quit;
    DataOutputStream dos;
    Profil profile;
    Logger logger;


    ButtonsClient() {
        this.panel = new JPanel();
        this.playerlist = new JButton("Playerlist");
        this.gamelist = new JButton("Gamelist");
        this.highscore= new JButton("Highscore");
        this.create = new JButton("Create Game");
        this.quit = new JButton("Quit");
        this.help = new JButton("Help");
        this.back = new JButton("Leave Lobby");

        //creates a GridLayout with 4 rows, and 2 colums. Buttons get their place depending of the order of panel.add(something).
        panel.setLayout(new GridLayout(4,2));
        //sets PreferedSize(can sometimes not work properly in the MainFrame because of the other panels.
        panel.setPreferredSize(new Dimension( 240, 150 ) );

        /*adds an ActionListener, so the button does something when you click on it. The ActionPerformed is not implemented right now,
         *so nothing is happening.
         */

        playerlist.addActionListener(this::actionPerformed);
        gamelist.addActionListener(this::actionPerformed);
        help.addActionListener(this::actionPerformed);
        highscore.addActionListener(this::actionPerformed);
        create.addActionListener(this::actionPerformed);
        back.addActionListener(this::actionPerformed);
        quit.addActionListener(this::actionPerformed);

        //adds button to the panel
        panel.add(create);
        panel.add(gamelist);
        panel.add(playerlist);
        panel.add(highscore);
        panel.add(back);
        panel.add(quit);
        panel.add(help);
    }

    public void actionPerformed(ActionEvent e1) {
        /*
         * To Do: Add the DataOutPutStream to the ActionEvent like ClientChatGUI.
         * Change in the ClientReaderThread System.out.println to ccg.append(msg).
         * Change the help-message so only Join:number, str1:number:number, spectatormode,
         * appear or find another way how to implement something which needs an individual input.
         */
        try {
            if (e1.getSource().equals(playerlist)) {
                logger.info("asked for PlayerList");
                /*
                 * Sends keyword to server.
                 * Here: client asks for the list of all players that are currently on the server
                 *
                 */
                dos.writeUTF(Protocol.PLL1.name());

            } else if (e1.getSource().equals(gamelist)) {
                /*
                 * Sends keyword to server.
                 * Here: client asks for the list of all games and their status
                 *
                 */

                dos.writeUTF(Protocol.GML1.name());

            } else if (e1.getSource().equals(create)) {
                logger.info("created a new Lobby");
                // This keyword is used to create a new lobby.
                if (profile.isInGame) {
                    // Clients cannot join a new lobby if they are already in one.
                    profile.mainFrame.chat.receiveMsg(Message.inLobbyAlready);
                } else {
                    dos.writeUTF(Protocol.CRE1.name());
                    dos.writeUTF(Protocol.CHAT.name() + ":" + Message.enterLobby);
                }

            } else if (e1.getSource().equals(highscore)) {
                logger.info("asking for Highscore");
                /*
                 * Sends keyword to server.
                 * Here: client asks for the high score list
                 *
                 */

                dos.writeUTF(Protocol.HSC1.name());

            } else if (e1.getSource().equals(help)) {
                profile.mainFrame.chat.receiveMsg(Message.helpMessage);

            } else if (e1.getSource().equals(back)) {
                // This keyword is used to go out of a lobby.
                if (profile.isInGame) {
                    dos.writeUTF(Protocol.BACK.name());
                } else {
                    profile.mainFrame.chat.receiveMsg("You have not joined a lobby yet "
                            + "so there is no need to go back!");
                }

            } else { //equals quit
                logger.info("Quitting");
                /*
                 * Informing server about his / her choice.
                 * If player is not active he / she cannot write anymore.
                 */
                dos.writeUTF(Protocol.QUIT.name());
                System.out.println("\nClosing program...\n");
                profile.clientIsOnline = false;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println(e.toString());
                }
            }
        } catch (IOException f) {
            System.err.println(f.toString());
        }
    }

    public void setDosProLogger(DataOutputStream dos, Profil profile, Logger logger) {

        this.dos = dos;
        this.profile = profile;
        this.logger = logger;
    }

    public JPanel getPanel() {
        /*
         *returns Panel so the mainFrame can use it. If panel would be public this probably is not necessary.
         */
        return panel;
    }
}
