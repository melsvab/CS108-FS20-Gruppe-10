package gui;

import server.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.*;




/**
 * @author Melanie
 * Client Chat Gui
 */
public class ClientChatGUI extends JPanel {

    /**
     * The Chat area.
     */
    JTextArea chatArea;
    /**
     * The Message.
     */
    JTextField message;
    /**
     * The Dos.
     */
    DataOutputStream dos;
    /**
     * The Dim scroll.
     */
    Dimension dimScroll;

    /**
     * Instantiates a new Client Chat Gui.
     */
    ClientChatGUI() {
        this.chatArea = new JTextArea();
        this.message = new JTextField();
        this.dimScroll = new Dimension(240, 10);

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder(("Messages")));
        //panel.setPreferredSize(new Dimension( 500, 300 ) );

        //create TextArea
        chatArea.setEditable(false); //cannot write in the ChatArea anymore
        chatArea.setBackground(Color.LIGHT_GRAY);
        chatArea.setLineWrap(true); //prevents horizontal scrolling with long texts
        chatArea.setWrapStyleWord(true); //gets cut of by words
        this.add(chatArea);

        //create scrollpane
        JScrollPane scroll = new JScrollPane(chatArea); //chat is scrollable
        scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(240, 300));
        this.add(scroll);

        //create TextField
        message.setEditable(true);
        message.addActionListener(this::actionPerformed);
        message.requestFocusInWindow();
        this.add(message, BorderLayout.PAGE_END);
    }

    /**
     * Set dataoutputstream.
     *
     * @param dos the dos
     */
    public void setDos(DataOutputStream dos) {

        this.dos = dos;
    }

    /**
     * Action performed.
     *
     * @param e the e
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
     * Receive msg.
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
}