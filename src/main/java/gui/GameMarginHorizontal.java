package gui;

import server.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.IOException;


/**
 * The type Game margin horizontal.
 *
 * @author Melanie, Natasha
 * This class is used for the vertical game margin.
 */
public class GameMarginHorizontal extends BackgroundTurtles implements MouseListener {

    /**
     * boolean to determine whether the object is used for the top or bottom panel next to the game.
     */
    public boolean top;

    /**
     * The panel for the game
     */
    public GameGUI board;

    /**
     * A boolean to know the game state
     */
    public boolean gameHasStarted = false;

    /**
     * The data output stream to send messages to the server
     */
    DataOutputStream dos;

    /**
     * The turtle score area for the first player's nickname
     */
    public BackgroundScoreArea firstPlayer;

    /**
     * The turtle score area for the first player's score
     */
    public BackgroundScoreArea firstScore;

    /**
     * The turtle score area for the second player's nickname
     */
    public BackgroundScoreArea secondPlayer;

    /**
     * The turtle score area for the second player's score
     */
    public BackgroundScoreArea secondScore;

    /**
     * A background with turtles
     */
    public BackgroundTurtles panelArea;

    /**
     * A background with turtles to show the current rounds
     */
    public BackgroundScoreArea roundText;

    /**
     * A background with turtles to place a message if an event is happening
     */
    public BackgroundScoreArea eventIsHappening;

    /**
     * A background with turtles to place a message if a move is invalid
     */
    public BackgroundScoreArea invalidMove;

    /**
     * This String is used to calculate the length of the invalid move text in spaces
     */
    String invalidText = "";

    /**
     * A timer to delete the text about the invalid moves after some time
     */
    public Timer tmr;


    /**
     * Instantiates a new game margin vertical.
     *
     * @param top   a boolean to determine whether this object is used on the top or bottom
     * @param board the board for the game
     */
    public GameMarginHorizontal(boolean top, GameGUI board, DataOutputStream dos) {
        this.setPreferredSize(new Dimension( 920, 60) );
        this.setMinimumSize(new Dimension( 920, 60) );
        panelArea = new BackgroundTurtles();
        this.board = board;
        this.top = top;
        this.dos = dos;



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
            for (int i = 0; i < 29; i++) {
                invalidText += " ";
            }

            ScorePanel.changeTextAreaProperties(invalidMove);
            invalidMove.setText(invalidText);

        }

        // sets background of the player and score
        this.firstPlayer = new BackgroundScoreArea();
        this.firstScore = new BackgroundScoreArea();
        this.secondPlayer = new BackgroundScoreArea();
        this.secondScore = new BackgroundScoreArea();
        ScorePanel.changeTextAreaProperties(firstPlayer);
        ScorePanel.changeTextAreaProperties(firstScore);
        ScorePanel.changeTextAreaProperties(secondPlayer);
        ScorePanel.changeTextAreaProperties(secondScore);

        // sets texts to spaces until there is another text
        firstPlayer.setText("     ");
        firstScore.setText("     ");
        secondPlayer.setText("     ");
        secondScore.setText("     ");

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.insets = new Insets(0,50,0,50);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(firstPlayer, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridy = 1;
        this.add(firstScore, gbc);


        if (top) {

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 1;
            gbc.gridy = 1;
            this.add(eventIsHappening, gbc);

            gbc.anchor = GridBagConstraints.PAGE_START;
            gbc.gridy = 0;
            this.add(roundText, gbc);

        } else {

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 1;
            gbc.gridy = 1;
            this.add(invalidMove);

            ActionListener task = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    invalidMove.setText(invalidText);
                }
            };

            tmr = new Timer(2000, task);

        }

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(secondPlayer, gbc);

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy = 1;
        this.add(secondScore, gbc);
    }


    /**
     * makes all necessary text fields (in)visible
     *
     * @param change the change
     */
    public void changeAllTextVisible(boolean change) {
        if  (!change) {
            //game has ended
            gameHasStarted = false;
            firstPlayer.setText(" ");
            firstScore.setText(" ");
            secondPlayer.setText(" ");
            secondScore.setText(" ");
        } else {
            gameHasStarted = true;

        }


        if (top && !change) {
            roundText.setText(" ");
            eventIsHappening.setText(" ");

        } else if (!top && !change) {

            invalidMove.setText(invalidText);
        }
    }


    /**
     * A method that will be activated if someone clicks on the panel
     *
     * @param e the event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            dos.writeUTF(Protocol.IDKW.name());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     *
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
        if (gameHasStarted) {
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
