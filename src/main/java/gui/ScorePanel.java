package gui;

import game.Board;
import game.PlayerTurtle;
import msc.GameMusic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The type Score panel.
 *
 * @author Melanie
 *
 * This class has all layors of the game and puts them on the places that they are
 * supposed to be.
 */
public class ScorePanel extends BackgroundTurtles {

    /**
     * The game panel which shows the game state or the logo of the game.
     */
    public GameGUI game;

    /**
     * The start game panel will be shown after a player presses the 'start game' button.
     */
    public StartGamePanel start;

    /**
     * The join game panel will be shown after a player presses the 'join a game' button.
     */
    public JoinGamePanel join;

    /**
     * The name panel will be shown after a player presses the 'change name' button.
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

    /**
     * GameMusic
     */
    public GameMusic gmsc;

    /**
     * Instantiates a new score panel
     *
     * @param dos the data output stream
     * @throws IOException the io exception
     */
    ScorePanel(DataOutputStream dos, GameMusic gmsc) throws IOException {

        //center
        this.game = new GameGUI();
        this.start = new StartGamePanel(dos, this, gmsc);
        this.join = new JoinGamePanel(dos, this, gmsc);
        this.name = new NamePanel(dos, this, gmsc);

        // panel on top
        top = new GameMarginHorizontal(true, this.game, dos);

        // panel on the right
        right = new GameMarginVertical(false, this.game, dos);

        // panel on the left
        left = new GameMarginVertical(true, this.game, dos);

        // panel on the bottom
        bottom = new GameMarginHorizontal(false, this.game, dos);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;


        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(left, gbc);
        // board can be moved to the right by clicking on this panel
        left.addMouseListener(left);

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

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(right, gbc);
        // board can be moved to the left by clicking on this panel
        right.addMouseListener(right);


    }

    /**
     * This method updates the score of all players depending on the amount of turtles
     *
     * @param turtles the turtles
     */
    public void setTextInTextArea(PlayerTurtle[] turtles) {


        if (turtles.length >= 1) {
            if (turtles[0] != null) {
                String nickname = turtles[0].turtlename.substring(0, turtles[0].turtlename.length() - 7);
                top.firstPlayer.setText(nickname + ": ");
                top.firstScore.setText("" + turtles[0].points);
            }
        }
        if (turtles.length >= 2) {
            if (turtles[1] != null) {
                String nickname = turtles[1].turtlename.substring(0, turtles[1].turtlename.length() - 7);
                top.secondPlayer.setText(nickname + ": ");
                top.secondScore.setText("" + turtles[1].points);
            }
        }
        if (turtles.length >= 3) {
            if (turtles[2] != null) {
                String nickname = turtles[2].turtlename.substring(0, turtles[2].turtlename.length() - 7);
                bottom.firstPlayer.setText(nickname + ": ");
                bottom.firstScore.setText("" + turtles[2].points);
            }
        }

        if (turtles.length >= 4) {

            if (turtles[3] != null) {
                String nickname = turtles[3].turtlename.substring(0, turtles[3].turtlename.length() - 7);
                bottom.secondPlayer.setText(nickname + ": ");
                bottom.secondScore.setText("" + turtles[3].points);
            }
        }

    }

    /**
     * This method updates the current round while playing the game
     *
     * @param rounds the rounds
     */
    public void setTextForRound (int rounds) {
        if (rounds <= 9) {
            top.roundText.setText("Round " + rounds);
        } else {
            top.roundText.setText("Last Round");
        }
    }

    /**
     * This method gives the board to all the graphical classes that need them.
     *
     * @param game the game
     */
    public void getGame(Board game) {
        this.game.setBoard(game);
        changeVisibilityMargin(true);

        this.game.repaint();
    }

    /**
     * This method informs all other classes that the player is not playing the game anymore.
     */
    public void resetGame() {
        makeAllCenterPanelsInvisibleExcept(0);
        changeVisibilityMargin(false);

        this.game.resetBoard();
        this.game.repaint();

    }

    /**
     * This method changes the visibility of the panels around the game
     *
     * @param change the change
     */
    public void changeVisibilityMargin(boolean change) {
        top.changeAllTextVisible(change);
        bottom.changeAllTextVisible(change);
        left.gameHasStarted = change;
        right.gameHasStarted = change;
    }

    /**
     * This method changes all panels in the center of the score panel invisible except of the one
     * that we want to show
     *
     * @param aCase four different cases for four different panels that are at the same place
     */
    public void makeAllCenterPanelsInvisibleExcept(int aCase) {
        // all panels are set to invisibility first
        game.setVisible(false);
        start.setVisible(false);
        join.setVisible(false);
        name.setVisible(false);

        // then one of them is changed again
        switch (aCase) {
            case 0:
                //game is shown
                game.setVisible(true);
                break;
            case 1:
                //start panel is shown
                start.setVisible(true);
                break;
            case 2:
                //join panel is shown
                join.setVisible(true);
                break;
            case 3:
                //name panel is shown
                name.setVisible(true);
                break;
        }

    }


    /**
     * changes setting of a given text area
     *
     * @param textArea the area that needs to be changed
     */
    public static void changeTextAreaProperties(BackgroundScoreArea textArea) {
        textArea.setEditable(false);
        textArea.setBackground(new Color(1,1,1, (float) 0.01));
        Font f = textArea.getFont();
        Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize() + 5);
        textArea.setFont(f2.deriveFont(Font.BOLD));
    }

}
