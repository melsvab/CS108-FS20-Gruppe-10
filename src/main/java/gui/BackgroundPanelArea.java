package gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import java.awt.image.BufferedImage;


/**
 * @author Melanie
 * This class is used for water backgrounds
 */

public class BackgroundPanelArea extends JPanel {

    /*
     * A buffered image that will be shown at the background
     */
    BufferedImage panelBackground;

    /**
     * Instantiates a new background panel
     * and gets the water background picture
     */
    public BackgroundPanelArea() {
        super();
        try {
            panelBackground = ImageIO.read(getClass().getResourceAsStream("/img/WaterBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paints a water background
     * @param g the graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(panelBackground, null, 0, 0);
    }
}
