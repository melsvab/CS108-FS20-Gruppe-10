package gui;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.*;


import game.Board;
import game.PlayerTurtle;

import org.slf4j.Logger;

import server.Profil;
import server.Protocol;

/**
 * The type Main frame.
 *
 * @author Melanie
 * This class puts all panel for the main frame in order
 */
public class MainFrame extends BackgroundScoreArea {

    /**
     * The frame
     */
    JFrame frame;

    /**
     * A panel for the chat
     */
    public ClientChatGUI chat;

    /**
     * A panel for messages from the server
     */
    public ClientChatGUI messages;

    /**
     * A panel filled with buttons
     */
    public ButtonsClient buttonsClient;

    /**
     * A panel with the game or our logo
     */
    public ScorePanel score;

    /**
     * A keyboard listener to react to inputs from the keyboard
     */
    public Keyboard keyboard = null;

    /**
     * A data output stream to send messages to the server
     */
    DataOutputStream dos;

    /**
     * A logger to collect data
     */
    Logger logger;

    /**
     * A profile of the client
     */
    Profil profile;

    /**
     * A boolean to check if there is a game or not
     */
    public Boolean gameExists = false;

    /**
     * Instantiates a new main frame
     *
     * @param dos     the data output stream to send messages to the server
     * @param profile the profile of the client
     * @param logger  the logger to collect data
     * @throws IOException the io exception
     */
    public MainFrame(DataOutputStream dos, Profil profile, Logger logger) throws IOException {
        this.frame = new JFrame("The Floor is Java");
        this.chat = new ClientChatGUI(dos, true);
        this.messages = new ClientChatGUI(dos, false);
        this.buttonsClient = new ButtonsClient(dos, profile, logger);
        this.score = new ScorePanel(dos);
        this.dos = dos;
        this.logger = logger;
        this.profile = profile;

        setFocusable(true);
        requestFocusInWindow();
        keyboard = new Keyboard(dos, profile);
        addKeyListener(keyboard);
        createMainFrame();

    }


    /**
     * A method to set the different panels into order and change or define the settings for the
     * frame.
     */
    public void createMainFrame() {
        this.setLayout(new GridBagLayout());
        this.setSize(1280,720);
        GridBagConstraints gbc = new GridBagConstraints();

        /*See ButtonsGame for a basic explanation of GridBagLayout
         *gbc.fill will fill the object for the whole Grid.
         *this is needed if for example the size of the chat is to small
         *so it fills the whole space.
         *gbc.insets creates distance to the other panels.
         *gbc.anchor is used if you change the size of the frame/screen
         *which appears so the chat for example stays on
         *the upper left corner
         * gbc.weightx/weighty is used so the gamePanel is larger than the other things. Why this works like that or if there is
         * a better way I dont know exactly
         */

        gbc.fill = GridBagConstraints.BOTH;

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(messages, gbc);

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(chat, gbc);

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weighty = 0;
        gbc.gridy = 2;
        this.add(buttonsClient, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        this.add(score,gbc);



        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                logger.info("Quitting");
                try {
                    dos.writeUTF(Protocol.QUIT.name());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                /*
                 * Informing server about his / her choice.
                 * If player is not active he / she cannot write anymore.
                 */
                System.out.println("\nClosing program...\n");
                profile.clientIsOnline = false;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ey) {
                    System.err.println(ey.toString());
                }
            }
        });

        //frame.getContentPane().add is used so the mainPanel gets add on the frame
        frame.getContentPane().add(this);
        frame.setSize(1280,720); //standard size of the window which opens
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //If you click on the red x in the window, the programm stops automaticaly
    }

    /**
     * Gives the board to all the classes that need it.
     *
     * @param game to give the game to the panels
     */
    public synchronized void gameIsHere(Board game) {
        score.getGame(game);
        keyboard.addGame();
        gameExists = true;

    }


    /**
     * gives the board to all the classes that need it.
     */
    public synchronized void leaveGame() {
        gameExists = false;
        score.resetGame();
        keyboard.deleteGame();
    }

    /**
    /**
     * closes chat
     */
    public void closeFrame() {
        //automatic closing you can use in code
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

    }


}