package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import game.Board;
import server.Protocol;
import server.Profil;


/**
 * @author Natasha
 * This class processes inputs from the keyboard.
 */
public class Keyboard extends KeyAdapter implements KeyListener {



    /**
     * The data output stream, profile and game
     */
    DataOutputStream dos;
    Profil profile;
    boolean gameExists;


    /**
     * Instantiates a new keyboard thread.
     * @param dos    the dos
     * @param profile the profile
     */
    public Keyboard(DataOutputStream dos, Profil profile) {

        this.dos = dos;
        this.profile = profile;

    }

    public void addGame() { this.gameExists = true;}

    public void deleteGame() { this.gameExists = false;}


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        myKeyEvt(e);
        try{
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Analyses what key was pressed and sends message to the server.
     */
    private void myKeyEvt(KeyEvent e) {
        int key = e.getKeyCode();

        if (profile.isInGame && !profile.isSpectator && gameExists) {

            try {

                if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT) {
                    dos.writeUTF(Protocol.LEFT.name());
                } else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT) {
                    dos.writeUTF(Protocol.RIGT.name());
                } else if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN) {
                    dos.writeUTF(Protocol.DOWN.name());
                } else if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP) {
                    dos.writeUTF(Protocol.UPPR.name());
                }
            } catch (IOException ioEx) {
                System.err.println(ioEx.toString());

            }
        }
    }



}