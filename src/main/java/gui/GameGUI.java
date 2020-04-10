package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import game.*;

public class GameGUI extends JPanel {

    private static final int WIDTH = 540;
    private static final int HEIGHT = 540;
    private BufferedImage turtle1;
    private BufferedImage turtle2;
    private BufferedImage turtle3;
    private BufferedImage turtle4;
    private BufferedImage waterField;
    private BufferedImage normalField;
    private BufferedImage usedField;
    private BufferedImage earthquake;
    private BufferedImage coin;
    Board board = null;

    GameGUI() throws IOException {

        this.setLayout(new BorderLayout()); //BorderLayout is chosen at the moment. Could be changed later
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        //The panel is a square at the moment. Probably because of the other panels the GamePanel is shown to be rectangular in the mainFrame

        //import the files and saves them in a BufferedImage. Get resources from src/main/ressources/img
        normalField = ImageIO.read(getClass().getResourceAsStream("/img/land.png"));

        waterField = ImageIO.read(getClass().getResourceAsStream("/img/Water.png"));

        usedField = ImageIO.read(getClass().getResourceAsStream("/img/landUsed.png"));

        earthquake = ImageIO.read(getClass().getResourceAsStream("/img/earthquake.png"));

        turtle1 = ImageIO.read(getClass().getResourceAsStream("/img/turtleBlue.png"));

        turtle2 = ImageIO.read(getClass().getResourceAsStream("/img/turtleGreen.png"));

        turtle3 = ImageIO.read(getClass().getResourceAsStream("/img/turtleViolett.png"));

        turtle4 = ImageIO.read(getClass().getResourceAsStream("/img/turtleYellow.png"));

        coin = ImageIO.read(getClass().getResourceAsStream("/img/apple.png"));
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
            int widthField = WIDTH / board.boardSize;
            int heightField = HEIGHT / board.boardSize;
            for (int y = 0; y < board.boardSize; y++) {
                for (int x = 0; x < board.boardSize; x++) {
                    if (board.board[x][y].isFlood) {
                        g2d.drawImage(waterField, null, widthField * x, heightField * y);
                    }
                    else if (board.board[x][y].isQuake) {
                        g2d.drawImage(earthquake, null, widthField * x, heightField * y);
                    }
                    else if (board.board[x][y].steppedOn) {
                        g2d.drawImage(usedField, null, widthField * x, heightField * y);
                    } else {
                        g2d.drawImage(normalField, null, widthField * x, heightField * y);
                    }
                    if (board.board[x][y].hasCoin) {
                        g2d.drawImage(coin, null, widthField * x, heightField * y);
                    }
                    else if (board.board[x][y].isTaken) {
                        g2d.drawImage(turtle1, null, widthField * x, heightField * y);
                    }
                }
            }
        }
        else {
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    g2d.drawImage(waterField, null, (WIDTH/10) * x, (HEIGHT / 10) * y);
                }
            }
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