package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Melanie, Natasha
 * This class is used for the vertical game margin.
 */

public class GameMarginVertical extends BackgroundTurtles implements MouseListener {

    /**
     * board to rescale x values
     */
    public GameGUI board;

    /**
     * This is a boolean to determine whether
     * this class is for the left or right side
     * next to the game
     */
    boolean left;


    /**
     * Instantiates a new game margin vertical.
     */
    public GameMarginVertical(boolean left, GameGUI board){
        this.left = left;
        this.board = board;

        this.setPreferredSize(new Dimension( 600, 60) );
        this.setMinimumSize(new Dimension(600,60));
        BackgroundTurtles verticalPanel = new BackgroundTurtles();


        setLayout(new GridBagLayout());

    }


    /**
     * A method that will be activated if someone clicks on the panel
     * TO DO: can turn on/off animation that will be seen at the game
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * TO DO: Will be used to start music if there is no game.
     */
    @Override
    public void mousePressed(MouseEvent e) {

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
        if(board != null) {
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
