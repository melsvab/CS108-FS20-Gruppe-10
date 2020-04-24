package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Melanie, Natasha
 * This class is used to show the nickname of a player
 * as well as his or her score during the game.
 */

public class GameCorners extends JPanel {

    /**
     * The turtle background
     */
    public BackgroundTurtles panelArea;

    /**
     * The turtle score area
     * for the player's nickname
     */
    public BackgroundScoreArea player;

    /**
     * The turtle score area
     * for the player's score
     */
    public BackgroundScoreArea score;


    /**
     * Instantiates a new game corner
     */
    public GameCorners(){
        // sets sizes for the panel
        this.setPreferredSize(new Dimension( 120, 60) );
        this.setMinimumSize(new Dimension( 120, 60) );
        // sets background
        panelArea = new BackgroundTurtles();
        // sets background of the player and score
        this.player = new BackgroundScoreArea();
        this.score = new BackgroundScoreArea();
        ScorePanel.changeTextAreaProperties(player);
        ScorePanel.changeTextAreaProperties(score);

        // sets texts to spaces until there is another text
        player.setText("     ");
        score.setText("     ");

        // sets layout
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // the text with the player's nickname is on top
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(player, gbc);

        // the text with the player's score is on the bottom
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridy = 1;
        this.add(score, gbc);

        this.setVisible(true);


    }

}
