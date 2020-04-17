package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundTextArea extends JTextArea {

    BufferedImage chatBackground;

    public BackgroundTextArea(){
        super();
        try {
            chatBackground = ImageIO.read(getClass().getResourceAsStream("/img/ChatBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 5; x++) {
                g2d.drawImage(chatBackground, null, 240 * x, 180 * y);
            }
        }
        super.paintComponent(g);
    }
}
