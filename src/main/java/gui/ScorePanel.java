package gui;

import game.Board;
import game.PlayerTurtle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Melanie
 * This class has all layors of the game
 * and puts them on the places that they are supposed to be.
 */

public class ScorePanel extends BackgroundTurtles {

    /**
     * The game panel which shows the game state
     * or the logo of the game.
     */
    public GameGUI game;

    /**
     * The start game panel will be shown
     * after a player presses the 'start game' button.
     */
    public StartGamePanel start;

    /**
     * The join game panel will be shown after
     * a player presses the 'join a game' button.
     */
    public JoinGamePanel join;

    /**
     * The name panel will be shown after
     * a player presses the 'change name' button.
     */
    public NamePanel name;

    /**
     * The panel over the game
     */
    public GameMarginHorizontal top;

    /**
     * The panel on the right side of the game
     */
    public GameMarginVertical right;

    /**
     * The panel on the left side of the game
     */
    public GameMarginVertical left;

    /**
     * The panel under the game
     */
    public GameMarginHorizontal bottom;

    /*
    * The corner panel up left
    */
    public GameCorners upLeft;

    /*
     * The corner panel up right
     */
    public GameCorners upRight;

    /*
     * The corner panel down right
     */
    public GameCorners downLeft;

    /*
     * The corner panel down right
     */
    public GameCorners downRight;

    /**
     * Instantiates a new score panel
     *
     * @param dos  the data output stream
     */
    ScorePanel(DataOutputStream dos) throws IOException {

        //center
        this.game = new GameGUI();
        this.start = new StartGamePanel(dos, game);
        this.join = new JoinGamePanel(dos, game);
        this.name = new NamePanel(dos, game);

        // panel on top
        top = new GameMarginHorizontal(true, this.game);

        // panel on the right
        right = new GameMarginVertical(false, this.game);

        // panel on the left
        left = new GameMarginVertical(true, this.game);

        // panel on the bottom
        bottom = new GameMarginHorizontal(false, this.game);

        //corner up left
        upLeft = new GameCorners();

        //corner up right
        upRight = new GameCorners();

        //corner down left
        downLeft = new GameCorners();

        //corner down left
        downRight = new GameCorners();


        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(upLeft, gbc);

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridy = 1;
        this.add(left, gbc);
        // board can be moved to the right by clicking on this panel
        left.addMouseListener(left);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(downLeft, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(top, gbc);
        // board can be moved down by clicking on this panel
        top.addMouseListener(top);


        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 1;
        this.add(game, gbc);
        this.add(start, gbc);
        this.add(join, gbc);
        this.add(name, gbc);


        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.gridy = 2;
        this.add(bottom, gbc);
        // board can be moved up by clicking on this panel
        bottom.addMouseListener(bottom);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(upRight, gbc);

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridy = 1;
        this.add(right, gbc);
        // board can be moved to the left by clicking on this panel
        right.addMouseListener(right);

        gbc.gridy = 2;
        this.add(downRight, gbc);


    }

    /**
     * This method updates the score of all players depending on the amount of turtles
     */

    public void setTextInTextArea (PlayerTurtle[] turtles) {


        if (turtles.length > 1) {
            if (turtles[0] != null) {
                String nickname = turtles[0].turtlename.substring(0, turtles[0].turtlename.length() - 7);
                upLeft.player.setText(nickname + ": ");
                upLeft.score.setText("" + turtles[0].points);
            }
        }
        if (turtles.length > 2) {
            if (turtles[1] != null) {
                String nickname = turtles[1].turtlename.substring(0, turtles[1].turtlename.length() - 7);
                upRight.player.setText(nickname + ": ");
                upRight.score.setText("" + turtles[1].points);
            }
        }
        if (turtles.length > 3) {
            if (turtles[2] != null) {
                String nickname = turtles[2].turtlename.substring(0, turtles[2].turtlename.length() - 7);
                upRight.player.setText(nickname + ": ");
                upRight.score.setText("" + turtles[2].points);
            }
        }

        if (turtles.length > 4) {

            if (turtles[3] != null) {
                String nickname = turtles[3].turtlename.substring(0, turtles[3].turtlename.length() - 7);
                upRight.player.setText(nickname + ": ");
                upRight.score.setText("" + turtles[3].points);
            }
        }

    }

    /**
     * This method updates the current round while playing the game
     */
    public void setTextForRound (int rounds) {
        if (rounds <= 9) {
            top.roundText.setText("Round " + rounds);
        } else {
            top.roundText.setText("Last Round");
        }
    }

    /**
     * changes visibility of the corners where
     * the names of the players and their score is shown
     */
    public void changeVisibilityCorners(boolean change) {
        upLeft.setVisible(change);
        upRight.setVisible(change);
        downLeft.setVisible(change);
        downRight.setVisible(change);

    }

    /**
     * This method gives the board to all the graphical classes that need them.
     */
    public void getGame(Board game) {
        this.game.setBoard(game);
        changeVisibilityCorners(true);
        this.game.repaint();
    }

    /**
     * This method informs all other classes
     * that the player is not playing the game anymore.
     */
    public void resetGame() {
        this.game.resetBoard();
        top.roundText.setText(" ");
        changeVisibilityCorners(false);
        this.game.repaint();


    }

    /**
     * changes setting of a given text area
     */
    public static void changeTextAreaProperties(BackgroundScoreArea textArea) {
        textArea.setEditable(false);
        textArea.setBackground(new Color(1,1,1, (float) 0.01));
        Font f = textArea.getFont();
        Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize()+5);
        textArea.setFont(f2.deriveFont(Font.BOLD));
    }

}
