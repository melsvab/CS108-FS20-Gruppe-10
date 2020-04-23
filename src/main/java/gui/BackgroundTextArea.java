package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Melanie
 * This class is used for turtle backgrounds in the chat area.
 */

public class BackgroundTextArea extends JTextArea {

    /*
     * A buffered image that will be shown at the background
     */
    BufferedImage chatBackground;

    /**
     * Instantiates a new background text are
     * and gets the small turtle picture
     */
    public BackgroundTextArea(){
        super();
        this.setEditable(false);
        try {
            chatBackground = ImageIO.read(getClass().getResourceAsStream("/img/ChatBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paints a small turtle background
     * @param g the graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int y = 0; y < 150; y++) {
            for (int x = 0; x < 5; x++) {
                g2d.drawImage(chatBackground, null, 100 * x, 100 * y);
            }
        }
        super.paintComponent(g);
    }
}
