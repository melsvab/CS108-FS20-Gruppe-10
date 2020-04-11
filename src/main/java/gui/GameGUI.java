package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import game.*;

public class GameGUI extends JPanel {

    private static final int WIDTH = 540;
    private static final int HEIGHT = 540;
    private BufferedImage turtleBlue;
    private BufferedImage turtleGreen;
    private BufferedImage turtleViolett;
    private BufferedImage turtleYellow;
    private BufferedImage waterField;
    private BufferedImage normalField;
    private BufferedImage usedField;
    private BufferedImage earthquake;
    private BufferedImage coin;
    private BufferedImage mainScreen;
    Board board = null;

    GameGUI() throws IOException {

        this.setLayout(new BorderLayout()); //BorderLayout is chosen at the moment. Could be changed later
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        //import the files and saves them in a BufferedImage. Get resources from src/main/ressources/img
        normalField = ImageIO.read(getClass().getResourceAsStream("/img/land.png"));

        waterField = ImageIO.read(getClass().getResourceAsStream("/img/Water.png"));

        usedField = ImageIO.read(getClass().getResourceAsStream("/img/landUsed.png"));

        earthquake = ImageIO.read(getClass().getResourceAsStream("/img/earthquake.png"));

        turtleBlue = ImageIO.read(getClass().getResourceAsStream("/img/turtleBlue.png"));

        turtleGreen = ImageIO.read(getClass().getResourceAsStream("/img/turtleGreen.png"));

        turtleViolett = ImageIO.read(getClass().getResourceAsStream("/img/turtleViolett.png"));

        turtleYellow = ImageIO.read(getClass().getResourceAsStream("/img/turtleYellow.png"));

        coin = ImageIO.read(getClass().getResourceAsStream("/img/apple.png"));

        mainScreen = ImageIO.read(getClass().getResourceAsStream("/img/mainScreen.png"));
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /*
     *To Do: Find out why paintComponent does not draw anything :((((
     */
    @Override
    /*
     *Overrides paintComponent. Not Tested if the idea with g2d.drawImage works (Graphics2D is used
     *because it can drawImages apparently with BufferdImages. Not every Image is included in the method
     *at the moment until its working properly
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (board != null) {
            int widthField = WIDTH / (board.boardSize - 1);
            int heightField = HEIGHT / (board.boardSize - 1);
            for (int y = 0; y < board.boardSize - 1; y++) {
                for (int x = 0; x < board.boardSize + 1; x++) {
                    if (board.board[x+1][y+1].isFlood) {
                        g2d.drawImage(waterField, null, widthField * x, heightField * y);
                    }
                    else if (board.board[x+1][y+1].isQuake) {
                        g2d.drawImage(earthquake, null, widthField * x, heightField * y);
                    }
                    else if (board.board[x+1][y+1].steppedOn) {
                        g2d.drawImage(usedField, null, widthField * x, heightField * y);
                    } else {
                        g2d.drawImage(normalField, null, widthField * x, heightField * y);
                    }
                    if (board.board[x+1][y+1].hasCoin) {
                        g2d.drawImage(coin, null, widthField * x, heightField * y);
                    }
                    /*else if (board.board[x+1][y+1].isTaken) {
                        if (board.board[x+1][y+1].turtle.color == BLUE) {
                            g2d.drawImage(turtleBlue, null, widthField * x, heightField * y);
                        }
                        if (board.board[x+1][y+1].turtle.color == GREEN) {
                            g2d.drawImage(turtleGreen, null, widthField * x, heightField * y);
                        }
                        if (board.board[x+1][y+1].turtle.color == VIOLETT) {
                            g2d.drawImage(turtleViolett, null, widthField * x, heightField * y);
                        }
                        if (board.board[x+1][y+1].turtle.color == YELLOW) {
                            g2d.drawImage(turtleYellow, null, widthField * x, heightField * y);
                        }
                    }*/
                }
            }
        }
        else {
            g2d.drawImage(mainScreen, null, 0, 0);
        }
    }

    public static void main (String[] args) throws IOException {
        gui.GameGUI game = new gui.GameGUI();
        JFrame frame = new JFrame();
        Board boardDemo = new Board(10);
        boardDemo.coinOccurrence =  boardDemo.boardSize + (50 / 10);
        boardDemo.maxCoinsInGame = 50;
        game.setBoard(boardDemo);
        frame.getContentPane().add(game);
        frame.pack();
        frame.setVisible(true);
    }
}