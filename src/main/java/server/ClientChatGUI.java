package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Melanie
 * Client Chat Gui
 */
public class ClientChatGUI extends JFrame {

    JFrame frame;
    JPanel panel;
    JTextArea chatArea;
    JTextField message;
    DataOutputStream dos;
    Dimension dimScroll;

    /**
     * Instantiates a new Client Chat Gui.
     */
    ClientChatGUI() {
        this.frame = new JFrame("Chat");
        this.panel = new JPanel();
        this.chatArea = new JTextArea();
        this.message = new JTextField(50);
        this.dimScroll = new Dimension(500, 10);
    }

    /**
     * Set Dataoutputstream.
     *
     * @param dos the dos
     */
    public void setDos(DataOutputStream dos){
        this.dos = dos;
    }

    /**
     * Creates chat.
     */
    public void createChat() {
        frame.add(BorderLayout.SOUTH, panel);
        panel.setLayout(new BorderLayout());

        chatArea.setEditable(false); //cannot write in the ChatArea anymore
        chatArea.setBackground(Color.LIGHT_GRAY);
        chatArea.setLineWrap(true); //prevents horizontal scrolling with long texts
        chatArea.setWrapStyleWord(true); //gets cut of by words
        panel.add(chatArea);

        panel.add(message, BorderLayout.PAGE_END);
        message.setEditable(true);

        JScrollPane scroll = new JScrollPane(chatArea); //chat is scrollable
        scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(500,300));
        panel.add(scroll);

        message.addActionListener(this::actionPerformed);
        frame.getContentPane().add(panel);
        frame.pack();
        message.requestFocusInWindow();
        frame.setVisible(true);
    }

    /**
     * Action performed.
     *
     * @param e the e
     * @throws IOException
     */
    public void actionPerformed(ActionEvent e) {
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
        chatArea.append(msg + "\n");
        dimScroll.height += 16.5;
        chatArea.setPreferredSize(dimScroll);
        chatArea.revalidate();
        chatArea.setCaretPosition(chatArea.getText().length());
    }

    /**
     *
     * @param b
     */
    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    /**
     * closes chat
     */
    public void closeChat() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}



