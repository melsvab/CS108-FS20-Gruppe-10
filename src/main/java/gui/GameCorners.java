package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameCorners extends BackgroundTurtles {

    public BackgroundTurtles panelArea;

    public BackgroundScoreArea player1Score;
    public BackgroundScoreArea player2Score;
    public BackgroundScoreArea player3Score;
    public BackgroundScoreArea player4Score;

    public GameCorners(int player){
        this.setPreferredSize(new Dimension( 60, 60) );
        this.setMinimumSize(new Dimension( 60, 60) );
        panelArea = new BackgroundTurtles();

        switch (player) {
            case 0:
                this.player1Score = new BackgroundScoreArea();
                ScorePanel.changeTextAreaProperties(player1Score);

                player1Score.setText("     ");

                break;
            case 1:
                this.player2Score = new BackgroundScoreArea();
                ScorePanel.changeTextAreaProperties(player2Score);
                player2Score.setText("     ");
                break;
            case 2:
                this.player3Score = new BackgroundScoreArea();
                ScorePanel.changeTextAreaProperties(player3Score);


                player3Score.setText("     ");

                break;
            case 3:
                this.player4Score = new BackgroundScoreArea();
                ScorePanel.changeTextAreaProperties(player4Score);
                player4Score.setText("     ");
                break;
        }

        this.setLayout(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();


            gbc.insets = new Insets(0,0,0,0);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            gbc.gridy = 0;

            switch (player) {
                case 0:
                    this.add(player1Score, gbc);
                    break;
                case 1:
                    this.add(player2Score, gbc);
                    break;
                case 2:
                    this.add(player3Score, gbc);
                    break;
                case 3:
                    this.add(player4Score, gbc);
                    break;
            }


    }

}
