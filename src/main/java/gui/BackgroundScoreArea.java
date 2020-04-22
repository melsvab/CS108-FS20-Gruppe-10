package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundScoreArea extends JTextArea {

    BufferedImage waterBackground;

    public BackgroundScoreArea(){
        super();
        this.setEditable(false);
        try {
            waterBackground = ImageIO.read(getClass().getResourceAsStream("/img/WaterBackground2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(waterBackground, null, 0,0);
        super.paintComponent(g);
    }
}
