package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameMarginHorizontal extends JTextArea {

    BufferedImage waterBackground;

    public GameMarginHorizontal(){
        BackgroundPanelArea horizontalPanel = new BackgroundPanelArea();

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension( 1040, 60) );

        //create TextArea
        horizontalPanel.setBackground(new Color(1,1,1, (float) 0.01));
        this.add(horizontalPanel);
    }


}
