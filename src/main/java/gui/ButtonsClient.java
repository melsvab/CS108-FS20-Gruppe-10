package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.*;


import org.slf4j.Logger;
import server.*;

/**
 * @author Melanie
 * This class is put on the right down corner of the main frame
 * and includes all general buttons that can pressed by clients.
 */

public class ButtonsClient extends JPanel {

    /**
     * A button to see the player list
     */
    JButton playerlist;

    /**
     * A button to create a new lobby
     */
    JButton create;

    /**
     * A button to ask for help
     */
    JButton help;

    /**
     * A button to leave a lobby and its game
     */
    JButton back;

    /**
     * A button to see the game list
     */
    JButton gamelist;

    /**
     * A button to see the highscore list
     */
    JButton highscore;

    /**
     * A button to quit the program
     */
    JButton quit;

    /**
     * A button to start a game
     */
    JButton start;

    /**
     * A button to join a game
     */
    JButton join;

    /**
     * A button to change the nickname
     */
    JButton name;

    /**
     * The data output stream to send a message to the server
     */
    DataOutputStream dos;

    /**
     * The profile of the player
     */
    Profil profile;

    /**
     * The logger to check data
     */
    Logger logger;

    /**
     * Instantiates the panel in the right corner with all buttons
     * @param dos the data output stream to send messages to the server
     * @param profile the profile of the player
     * @param logger the logger to collect data
     */
    ButtonsClient(DataOutputStream dos, Profil profile, Logger logger) {
        this.dos = dos;
        this.profile = profile;
        this.logger = logger;

        this.playerlist = new JButton("Playerlist");
        this.gamelist = new JButton("Gamelist");
        this.highscore = new JButton("Highscore");
        this.create = new JButton("Create Game");
        this.quit = new JButton("Quit");
        this.help = new JButton("Help");
        this.back = new JButton("Leave Lobby");
        this.start = new JButton("Start");
        this.join = new JButton("Join Game");
        this.name = new JButton("Change Name");

        //creates a GridLayout with 4 rows, and 2 colums.
        // Buttons get their place depending of the order of panel.add(something).
        this.setLayout(new GridLayout(5,2));
        //sets PreferredSize(can sometimes not work properly in the MainFrame because of
        // the other panels.
        this.setPreferredSize(new Dimension( 240, 150 ) );

        /* adds an ActionListener, so the button does something when you click on it.
        The ActionPerformed is not implemented right now,
         *so nothing is happening.
         */

        playerlist.addActionListener(this::actionPerformed);
        gamelist.addActionListener(this::actionPerformed);
        help.addActionListener(this::actionPerformed);
        highscore.addActionListener(this::actionPerformed);
        create.addActionListener(this::actionPerformed);
        back.addActionListener(this::actionPerformed);
        quit.addActionListener(this::actionPerformed);
        start.addActionListener(this::actionPerformed);
        join.addActionListener(this::actionPerformed);
        name.addActionListener(this::actionPerformed);

        //adds button to the panel
        this.add(create);
        this.add(start);
        this.add(join);
        this.add(gamelist);
        this.add(playerlist);
        this.add(highscore);
        this.add(name);
        this.add(back);
        this.add(quit);
        this.add(help);
    }

    /**
     * This method automatically starts if a player presses a button
     * @param e1 the event that someone pressed a button
     */
    public void actionPerformed(ActionEvent e1) {
        try {
            if (e1.getSource().equals(playerlist)) {
                logger.info("asked for playerList");
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
                logger.info("created a new lobby");
                // This keyword is used to create a new lobby.
                if (profile.isInGame) {
                    // Clients cannot join a new lobby if they are already in one.
                    profile.mainFrame.messages.changeToErrorMessage(true);
                    profile.mainFrame.messages.receiveMsg(Message.inLobbyAlready);
                } else {
                    profile.mainFrame.score.makeAllCenterPanelsInvisibleExcept(0);
                    dos.writeUTF(Protocol.CRE1.name());
                    dos.writeUTF(Protocol.CHAT.name() + ":" + Message.enterLobby);
                }

            } else if (e1.getSource().equals(highscore)) {
                logger.info("asking for highscore");
                /*
                 * Sends keyword to server.
                 * Here: client asks for the high score list
                 *
                 */

                dos.writeUTF(Protocol.HSC1.name());

            } else if (e1.getSource().equals(help)) {

                /*
                 * TO DO: if a player presses the help
                 * button then a tutorial for the game
                 * and all buttons will be shown.
                 *
                 */
                System.out.println(Message.helpMessage);

            } else if (e1.getSource().equals(back)) {
                // This keyword is used to go out of a lobby.
                if (profile.isInGame) {
                    dos.writeUTF(Protocol.BACK.name());
                } else {
                    profile.mainFrame.messages.changeToErrorMessage(true);
                    profile.mainFrame.messages.receiveMsg("You have not joined a lobby yet \n"
                            + "so there is no need to go back!");
                }

            } else if (e1.getSource().equals(start)) {
                if (profile.isInGame && !profile.mainFrame.gameExists) {
                    profile.mainFrame.score.makeAllCenterPanelsInvisibleExcept(1);
                } else {
                    profile.mainFrame.messages.changeToErrorMessage(true);
                    profile.mainFrame.messages.receiveMsg(
                            "You must be in a lobby \n that is open for new players \nto start a game!\n");
                }

            } else if (e1.getSource().equals(join)) {
                if (profile.isInGame) {
                    profile.mainFrame.messages.changeToErrorMessage(true);
                    profile.mainFrame.messages.receiveMsg(Message.inLobbyAlready);
                } else {
                    profile.mainFrame.score.makeAllCenterPanelsInvisibleExcept(2);
                }

            } else if (e1.getSource().equals(name)) {
                profile.mainFrame.score.makeAllCenterPanelsInvisibleExcept(3);

            } else {
                //equals quit
                // logger.info("Quitting");
                /*
                * Informing server about his / her choice.
                * If player is not active he / she cannot write anymore.
                * */
                profile.mainFrame.closeFrame();
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

}
