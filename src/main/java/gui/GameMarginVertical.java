package gui;
import server.Protocol;

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.*;

/**
 * The type Game margin vertical.
 *
 * @author Melanie, Natasha
 * This class is used for the vertical game margin.
 */
public class GameMarginVertical extends BackgroundTurtles implements MouseListener {

    /**
     * board to rescale x values
     */
    public GameGUI board;

    /**
     * This is a boolean to determine whether this class is for the left or right side next to the
     * game
     */
    boolean left;


    /**
     * This is a boolean to determine whether there is a game or not
     */
    public boolean gameHasStarted = false;


    /**
     * The data output stream to send messages to the server
     */
    DataOutputStream dos;


    /**
     * Instantiates a new game margin vertical.
     *
     * @param left  the left
     * @param board the board
     */
    public GameMarginVertical(boolean left, GameGUI board, DataOutputStream dos) {
        this.left = left;
        this.board = board;
        this.dos = dos;

        this.setPreferredSize(new Dimension( 60, 650) );
        this.setMinimumSize(new Dimension(60,600));
        BackgroundTurtles verticalPanel = new BackgroundTurtles();


        setLayout(new GridBagLayout());

    }


    /**
     * A method that will be activated if someone clicks on the panel
     * @param e the event
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * A method that will be activated if someone keeps the moused pressed on the panel
     * @param e the event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        try {
            dos.writeUTF(Protocol.IDKW.name());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * A method that will be activated if the mouse is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * A method that will be activated if the mouse enters the panel.
     * rescales x to the right direction depending on
     * the fact whether this object is used
     * for the left or right panel next to the game
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (gameHasStarted) {
            if (left) {
                this.board.changeX(5);
            } else {
                this.board.changeX(-5);
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
