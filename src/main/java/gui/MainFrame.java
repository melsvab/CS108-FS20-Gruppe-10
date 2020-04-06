package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;

import game.*;

public class MainFrame extends JFrame {

    JFrame frame;
    JPanel panelChat;
    JPanel panelButtonsGame;
    JPanel panelGame;
    JPanel panelButtonsClient;
    JPanel mainPanel;
    public ClientChatGUI chat;
    public ButtonsGame buttons;
    public ButtonsClient buttons2;
    public GameGUI game;

    public MainFrame() throws IOException {
        this.frame = new JFrame("Der Boden ist Java");
        this.mainPanel = new JPanel();
        this.chat = new ClientChatGUI();
        this.buttons = new ButtonsGame();
        this.buttons2 = new ButtonsClient();
        this.game = new GameGUI();
        this.panelChat = chat.getPanel();
        this.panelButtonsGame = buttons.getPanel();
        this.panelGame = game.getPanel();
        this.panelButtonsClient = buttons2.getPanel();
        createMainFrame();
    }

    public void createMainFrame() {
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.BLUE); //Optional: Background with the used Water-Design for the game
        mainPanel.setSize(1280,720);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(panelChat, gbc);

        gbc.insets = new Insets(20,20,20,20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 2.5;
        gbc.weighty = 2.5;
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(panelGame, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        gbc.weightx = 0.03;
        gbc.weighty = 0.03;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(panelButtonsClient, gbc);

        gbc.insets = new Insets(50,50,50,50);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.03;
        gbc.weighty = 0.03;
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(panelButtonsGame, gbc);

        frame.getContentPane().add(mainPanel);
        frame.setSize(1280,720);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    /**
     * closes chat
     */
    public void closeChat() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }


    //TestCode
    public static void main (String args[]) throws IOException {
        MainFrame demo = new MainFrame();
        demo.buttons.setVisible(true);
        Board boardDemo = new Board(10,50);
        demo.game.setBoard(boardDemo);
        demo.revalidate();
    }
}