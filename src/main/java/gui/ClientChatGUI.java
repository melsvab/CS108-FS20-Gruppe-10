package gui;

import server.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.*;




/**
 * @author Melanie
 * Client Chat Gui
 */
public class ClientChatGUI extends JPanel {

    /**
     * The chat area.
     */
    BackgroundTextArea chatArea;
    /**
     * The message.
     */
    JTextField message;
    /**
     * The data output stream to send messages to the server
     */
    DataOutputStream dos;
    /**
     * The dim scroll.
     */
    Dimension dimScroll;

    /**
     * Instantiates a new Client Chat Gui.
     */
    ClientChatGUI(DataOutputStream dos, boolean chat) {
        this.dos = dos;
        this.chatArea = new BackgroundTextArea();
        this.message = new JTextField();
        this.dimScroll = new Dimension(120, 10);

        this.setLayout(new BorderLayout());
        if(chat) {
            this.setBorder(BorderFactory.createTitledBorder(("Chat")));
        } else {
            this.setBorder(BorderFactory.createTitledBorder(("Messages")));
        }
        //panel.setPreferredSize(new Dimension( 500, 300 ) );

        //create TextArea
        chatArea.setEditable(false); //cannot write in the ChatArea anymore
        chatArea.setBackground(new Color(1,1,1, (float) 0.01));
        chatArea.setLineWrap(true); //prevents horizontal scrolling with long texts
        chatArea.setWrapStyleWord(true); //gets cut of by words
        this.add(chatArea);

        //create scroll panel
        JScrollPane scroll = new JScrollPane(chatArea); //chat is scrollable
        scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(105, 300));
        this.add(scroll);

       if (chat) {
           //create TextField
           message.setEditable(true);
           message.addActionListener(this::actionPerformed);
           message.requestFocusInWindow();
           this.add(message, BorderLayout.PAGE_END);
       }
    }

    /**
     * Action performed.
     *
     * @param e the happened event
     */
    public void actionPerformed(ActionEvent e) {
        //differentiates between normal chat-message, whisperchat and broadcast
        try {

            String msgType = message.getText() + "000"; //000 is professional bug fixing

            switch (msgType.substring(0, 3)) {
                case "/b ":
                    dos.writeUTF(Protocol.BRC1.name() + ":" + message.getText().substring(3) + " ");
                    break;
                case "/w ":
                    dos.writeUTF(Protocol.WHP1.name() + ":" + message.getText().substring(3) + " ");
                    break;
                default:
                    dos.writeUTF(Protocol.CHAT.name() + ":" + message.getText());
                    break;
            }
        } catch (IOException f) {
            System.err.println(f.toString());
        }
        message.setText(""); // deletes text in the text field
    }

    /**
     * Receives a message
     *
     * @param msg the msg
     */
    public void receiveMsg(String msg) {
        //adds the message to the TextArea and adjusts the scrollbar in relationship to the occurrence of \n
        chatArea.append(msg + "\n");
        int count = msg.length() - msg.replaceAll("\n", "").length();
        dimScroll.height += 16.5 * (count + 1);
        chatArea.setPreferredSize(dimScroll);
        chatArea.revalidate();
        chatArea.setCaretPosition(chatArea.getText().length());
    }

    /**
     * Changes the color of the text red or black.
     *
     * @param change is true if the message is an error message
     */
    public void changeToErrorMessage(boolean change) {
        if (change) {
            chatArea.setForeground(Color.RED);
        } else {
            chatArea.setForeground(Color.BLACK);
        }

    }
}