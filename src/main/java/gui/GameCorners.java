package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameCorners extends BackgroundTurtles {

    public BackgroundTurtles panelArea;

    public BackgroundScoreArea player;
    public BackgroundScoreArea score;


    public GameCorners(){
        this.setPreferredSize(new Dimension( 60, 60) );
        this.setMinimumSize(new Dimension( 60, 60) );
        panelArea = new BackgroundTurtles();


        this.player = new BackgroundScoreArea();
        this.score = new BackgroundScoreArea();
        ScorePanel.changeTextAreaProperties(player);
        ScorePanel.changeTextAreaProperties(score);

        player.setText("     ");
        score.setText("     ");


        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();


        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(player, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(score, gbc);


    }

}
