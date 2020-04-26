package gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;




/**
 * @author Melanie
 * This class is used for turtle backgrounds
 */
public class BackgroundScoreArea extends JTextArea {

    /*
     * A buffered image that will be shown at the background
     */
    BufferedImage waterBackground;

    /**
     * Instantiates a background score area
     * and gets the turtle image.
     */
    public BackgroundScoreArea() {
        super();
        this.setEditable(false);
        try {
            waterBackground = ImageIO.read(getClass().getResourceAsStream("/img/turtles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paints a big turtle background
     * @param g the graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(waterBackground, null, 0,0);
        super.paintComponent(g);
    }
}
