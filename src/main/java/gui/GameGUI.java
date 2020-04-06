package gui;

import javax.swing.*;
import java.awt.*;

public class GameGUI {
    /*
     * To Do: Find out how you can load images to create the Board and how to change it when something
     * is happening.
     */

    //private board board;
    private static final int WIDTH = 650;
    private static final int HEIGHT = 650;
    JPanel panel;
    private Image turtle1;
    private Image turtle2;
    private Image turtle3;
    private Image turtle4;
    private Image waterField;
    private Image normalField;
    private Image usedField;
    private Image flood;
    private Image earthquake;
    private Image coin;

    GameGUI() {
        this.panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT ));
        panel.setBackground(Color.BLUE); //Placeholder to represent the field/panel. Optional: Water-background
        /*ImageIcon ii = new ImageIcon("src/GUI/Boden4.png");
        normalField = ii.getImage();
        ImageIcon ii2 = new ImageIcon("src/GUI/BodenBenutzt.png");
        usedField = ii2.getImage();
        ImageIcon ii3 = new ImageIcon("src/GUI/Wasser.png");
        flood = ii3.getImage();
        ImageIcon ii4 = new ImageIcon("src/GUI/Schildkröte1.png");
        turtle1 = ii4.getImage();*/
    }

    public void setVisible(boolean b) {
        /*
         * Note: This method will be used to let the buttons only appear if the client is inGame.
         */
        panel.setVisible(b);
    }

    public JPanel getPanel() {
        return panel;
    }

    /*@Override
    public void paintComponent(Graphics g) {
        ImageIcon ll = new ImageIcon(this.getClass().getResource("Boden4.png"));
        normalField = ll.getImage();

        Graphics2D g2D = (Graphics2D) g;
        g2D.draw(normalField);
    }*/
}