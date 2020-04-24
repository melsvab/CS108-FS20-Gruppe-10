package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Melanie, Natasha
 * This class is used for the vertical game margin.
 */

public class GameMarginHorizontal extends BackgroundTurtles implements MouseListener {

    /**
     * boolean to determine whether the object is used
     * for the top or bottom panel next to the game.
     */
    public boolean top;

    /**
     * The panel for the game
     */
    public GameGUI board;

    /**
     * A background with turtles
     */
    public BackgroundTurtles panelArea;

    /**
     * A background with turtles to show the current rounds
     */
    public BackgroundScoreArea roundText;

    /**
     * A background with turtles to place a message
     * if an event is happening
     */
    public BackgroundScoreArea eventIsHappening;

    /**
     * A background with turtles to place a message
     * if a move is invalid
     */
    public BackgroundScoreArea invalidMove;

    /**
     * A timer to delete the text
     * about the invalid moves after some time
     */
    public Timer tmr;


    /**
     * Instantiates a new game margin vertical.
     * @param top a boolean to determine whether this object is used on the top or bottom
     * @param board the board for the game
     */
    public GameMarginHorizontal(boolean top, GameGUI board){
        this.setPreferredSize(new Dimension( 60, 800) );
        this.setMinimumSize(new Dimension( 60, 800) );
        panelArea = new BackgroundTurtles();
        this.board = board;
        this.top = top;



        if (top) {

            this.roundText = new BackgroundScoreArea();
            this.eventIsHappening = new BackgroundScoreArea();

            ScorePanel.changeTextAreaProperties(roundText);
            ScorePanel.changeTextAreaProperties(eventIsHappening);

            roundText.setText("     ");
            eventIsHappening.setText("     ");

        } else {

            // bottom

            this.invalidMove = new BackgroundScoreArea();

            ScorePanel.changeTextAreaProperties(invalidMove);

            invalidMove.setText("    ");

        }


        if (top) {


            this.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.PAGE_START;
            gbc.gridx = 0;
            gbc.gridy = 0;
            this.add(roundText, gbc);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridy = 1;
            this.add(eventIsHappening, gbc);


        } else {

            invalidMove.setAlignmentX(BackgroundScoreArea.CENTER_ALIGNMENT);
            this.add(invalidMove);

            ActionListener task = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    invalidMove.setText("");
                }
            };

            tmr = new Timer(2000, task);

        }
    }


    /**
     * A method that will be activated if someone clicks on the panel
     * TO DO: can turn on/off animation that will be seen at the game
     * @param e the event
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * TO DO: Will be used to start music if there is no game.
     * @param e the event
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * A method that will be activated if the mouse is released
     * @param e the event
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * A method that will be activated if the mouse enters the panel.
     * rescales y to the right direction depending on
     * the fact whether this object is used
     * for the panel next on top or on the bottom to the game.
     * @param e the event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (board != null) {
            if (top) {
                this.board.changeY(5);
            } else {
                this.board.changeY(-5);
            }
        }

    }

    /**
     * A method that will be activated if the mouse exits the panel.
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

}
