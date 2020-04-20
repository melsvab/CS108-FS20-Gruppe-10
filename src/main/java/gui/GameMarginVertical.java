package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameMarginVertical extends BackgroundTurtles implements MouseListener {

    public GameGUI board;
    boolean left;


    public GameMarginVertical(boolean left, GameGUI board){
        this.left = left;
        this.board = board;

        this.setPreferredSize(new Dimension( 600, 60) );
        this.setMinimumSize(new Dimension(600,60));
        BackgroundTurtles verticalPanel = new BackgroundTurtles();


        setLayout(new GridBagLayout());

        //this.setLayout(new GridBagLayout());
        //GridBagConstraints gbc = new GridBagConstraints();



    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (left) {
            this.board.changeX(1);
        } else {
            this.board.changeX(-1);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
