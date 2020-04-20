package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameMarginVertical extends JTextArea {


    public GameMarginVertical(){

        BackgroundPanelArea verticalPanel = new BackgroundPanelArea();

        this.setLayout(new BorderLayout());

        this.setPreferredSize(new Dimension( 60, 720) );

        //create TextArea
        verticalPanel.setBackground(new Color(1,1,1, (float) 0.01));
        this.add(verticalPanel);
    }

}
