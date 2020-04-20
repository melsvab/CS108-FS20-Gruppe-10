package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundTurtles extends JPanel {

    BufferedImage panelBackground;

    public BackgroundTurtles(){
        super();
        try {
            panelBackground = ImageIO.read(getClass().getResourceAsStream("/img/WaterBackground2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(panelBackground, null, 0, 0);
    }
}