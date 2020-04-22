package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Melanie, Natasha
 * This class is used for turtle backgrounds.
 */
public class BackgroundTurtles extends JPanel {

    /*
     * A buffered image that will be shown at the background
     */
    BufferedImage panelBackground;

    /**
     * Instantiates a new background turtle panel
     * and gets the turtles background picture
     */
    public BackgroundTurtles(){
        super();
        try {
            panelBackground = ImageIO.read(getClass().getResourceAsStream("/img/turtles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * paints a turtle background
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(panelBackground, null, 0, 0);
    }
}
