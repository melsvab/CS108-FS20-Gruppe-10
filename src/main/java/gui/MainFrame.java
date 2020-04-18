package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import server.Profil;


public class MainFrame extends BackgroundPanelArea {

    JFrame frame;
    public ClientChatGUI chat;
    public ButtonsClient buttonsClient;
    public ScorePanel score;
    public Keyboard keyboard = null;

    public MainFrame(DataOutputStream dos, Profil profile, Logger logger) throws IOException{
        this.frame = new JFrame("Der Boden ist Java");
        this.chat = new ClientChatGUI(dos);
        this.buttonsClient = new ButtonsClient(dos, profile, logger);
        this.score = new ScorePanel(dos);

        setFocusable(true);
        requestFocusInWindow();
        keyboard = new Keyboard(dos, profile);
        addKeyListener(keyboard);
        createMainFrame();

    }


    public void createMainFrame() {
        this.setLayout(new GridBagLayout());
        this.setSize(1280,720);
        GridBagConstraints gbc = new GridBagConstraints();

        /*See ButtonsGame for a basic explanation of GridBagLayout
         *gbc.fill will fill the object for the whole Grid. this is needed if for example the size of the chat is to small
         *so it fills the whole space.
         *gbc.insets creates distance to the other panels.
         *gbc.anchor is used if you change the size of the frame/screen which appears so the chat for example stays on
         *the upper left corner
         * gbc.weightx/weighty is used so the gamePanel is larger than the other things. Why this works like that or if there is
         * a better way I dont know exactly
         */

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(chat, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        gbc.weightx = 0.03;
        gbc.weighty = 0.03;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(buttonsClient, gbc);

        gbc.insets = new Insets(15,0,0,0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        this.add(score,gbc);


        //frame.getContentPane().add is used so the mainPanel gets add on the frame
        frame.getContentPane().add(this);
        frame.setSize(1280,720); //standard size of the window which opens
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //If you click on the red x in the window, the programm stops automaticaly
    }
    /**
     * closes chat
     */
    public void closeFrame() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //automatic closing you can use in code
    }

}