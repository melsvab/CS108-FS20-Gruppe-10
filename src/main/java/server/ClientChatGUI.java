package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClientChatGUI extends JFrame {

    ClientProfil clientProfil;
    JFrame frame;
    JPanel panel;
    JTextArea chatArea;
    JTextField message;
    DataOutputStream dos;

    ClientChatGUI() {
        this.frame = new JFrame("Chat");
        this.panel = new JPanel();
        this.chatArea = new JTextArea();
        this.message = new JTextField(50);
    }

    public void setDos(DataOutputStream dos){
        this.dos = dos;
    }

    public void createChat() {
        frame.add(BorderLayout.SOUTH, panel);
        panel.setLayout(new BorderLayout());

        chatArea.setPreferredSize(new Dimension(500, 500));
        chatArea.setEditable(false); //cannot write in the ChatArea anymore
        chatArea.setBackground(Color.LIGHT_GRAY);
        chatArea.setLineWrap(true); //prevents horizontal scrolling with long texts
        chatArea.setWrapStyleWord(true); //gets cut of by words
        panel.add(chatArea);

        panel.add(message, BorderLayout.PAGE_END);
        message.setEditable(true);

        /*JScrollPane scroll = new JScrollPane(chatArea); //chat is scrollable
        scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(chatArea.getPreferredSize());
        panel.add(scroll); */
        message.addActionListener(this::actionPerformed);
        frame.getContentPane().add(panel);
        frame.pack();
        message.requestFocusInWindow();
        frame.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            dos.writeUTF("CHAT" + message.getText());
        } catch (IOException f) {
            System.out.println(f);
        }
        message.setText(""); //löscht text im textfield
    }

    public void receiveMsg(String msg) {
        chatArea.append(msg + "\n");
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public void closeChat() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}



