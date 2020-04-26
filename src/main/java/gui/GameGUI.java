package gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;


import game.*;
import javafx.scene.transform.Affine;

/**
 * The type Game gui.
 *
 * @author Melanie, Natasha
 *
 * This class is used to show the nickname of a player as well as his or
 * her score during the game.
 */
public class GameGUI extends BackgroundPanelArea {

    /**
     * The width of the panel
     */
    private static final int WIDTH = 920;

    /**
     * The height of the panel
     */
    private static final int HEIGHT = 600;

    /**
     * The size of one pictures in pixels
     */
    private static final int PICTURE_SIZE = 60;

    /**
     * The picture of the blue turtle
     */
    private BufferedImage turtleBlue;

    /**
     * The picture of the green turtle
     */
    private BufferedImage turtleGreen;

    /**
     * The picture of the violet turtle
     */
    private BufferedImage turtleViolet;

    /**
     * The picture of the yellow turtle
     */
    private BufferedImage turtleYellow;

    /**
     * The picture of a water field
     */
    private BufferedImage waterField;

    /**
     * The picture of a grass field
     */
    private BufferedImage normalField;

    /**
     * The picture of a field that was stepped on
     */
    private BufferedImage usedField;

    /**
     * The picture of a field with earthquake
     */
    private BufferedImage earthquake;

    /**
     * The picture of a coin
     */
    private BufferedImage coin;

    /**
     * The picture of the logo
     */
    private BufferedImage mainScreen;

    /**
     * The picture of a field that is a start position
     */
    private BufferedImage startPosition;

    /**
     * The board that will be interpreted graphically
     */
    Board board = null;

    /**
     * An index to rescale the x values of the whole board
     */
    int rescaleX = 0;

    /**
     * An index to rescale the y values of the whole board
     */
    int rescaleY = 0;

    /**
     * The actual board size
     */
    int actualBoardSize;

    /**
     * Instantiates a new game gui.
     *
     * @throws IOException the io exception
     */
    GameGUI() throws IOException {

        this.setLayout(new BorderLayout()); //BorderLayout is chosen at the moment. Could be changed later
        this.setMaximumSize(new Dimension(WIDTH * 2, HEIGHT * 2));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        //import the files and saves them in a BufferedImage. Get resources from src/main/ressources/img
        normalField = ImageIO.read(getClass().getResourceAsStream("/img/land.png"));

        waterField = ImageIO.read(getClass().getResourceAsStream("/img/Water.png"));

        usedField = ImageIO.read(getClass().getResourceAsStream("/img/landUsed.png"));

        earthquake = ImageIO.read(getClass().getResourceAsStream("/img/earthquake.png"));

        turtleBlue = ImageIO.read(getClass().getResourceAsStream("/img/turtleBlue.png"));

        turtleGreen = ImageIO.read(getClass().getResourceAsStream("/img/turtleGreen.png"));

        turtleViolet = ImageIO.read(getClass().getResourceAsStream("/img/turtleViolett.png"));

        turtleYellow = ImageIO.read(getClass().getResourceAsStream("/img/turtleYellow.png"));

        coin = ImageIO.read(getClass().getResourceAsStream("/img/apple.png"));

        mainScreen = ImageIO.read(getClass().getResourceAsStream("/img/mainScreen.png"));

        startPosition = ImageIO.read(getClass().getResourceAsStream("/img/startPosition.png"));
    }

    /**
     * board was given by the main frame and the actual board size as well as the x and y values are
     * calculated.
     *
     * @param board the board where the game occurs
     */
    public void setBoard(Board board) {
        this.board = board;
        actualBoardSize = board.boardSize + 2;
        rescaleX = (WIDTH - board.boardSize * PICTURE_SIZE) / 2;
        rescaleY = (HEIGHT - board.boardSize * PICTURE_SIZE) / 2;
    }

    /**
     * Reset board.
     */
    public void resetBoard() {
        this.board = null;
        actualBoardSize = 0;
    }


    /**
     * Overrides paintComponent
     * and draws the whole game field on a Graphics object.
     * @param g the graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (board != null) {
            for (int y = 0; y < board.boardSize; y++) {
                for (int x = 0; x < board.boardSize; x++) {
                    int xValue = PICTURE_SIZE * x + rescaleX;
                    int yValue = PICTURE_SIZE * ((board.boardSize - 1) - y) + rescaleY;
                    if (board.board[x + 1][y + 1].isStartPosition) {
                        g2d.drawImage(startPosition, null, xValue , yValue);
                    } else if (board.board[x + 1][y + 1].isFlood) {
                        g2d.drawImage(waterField, null, xValue , yValue);
                    } else if (board.board[x + 1][y + 1].isQuake) {
                        g2d.drawImage(earthquake, null, xValue , yValue);
                    } else if (board.board[x + 1][y + 1].steppedOn) {
                        g2d.drawImage(usedField, null,  xValue , yValue);
                    } else {
                        g2d.drawImage(normalField, null,  xValue , yValue);
                    }
                    if (board.board[x + 1][y + 1].hasCoin) {
                        g2d.drawImage(coin, null,  xValue , yValue);
                    }
                    //placeholder while there is no color attributed to the turtle
                    if (board.board[x + 1][y + 1].turtle != null) {
                        int direction = board.board[x + 1][y + 1].turtle.direction;
                        switch (board.board[x + 1][y + 1].turtle.num) {
                            case 0:
                                g2d.drawImage(rotateImage(turtleBlue, direction), null,  xValue , yValue);
                                break;
                            case 1:
                                g2d.drawImage(rotateImage(turtleGreen, direction), null,  xValue , yValue);
                                break;
                            case 2:
                                g2d.drawImage(rotateImage(turtleViolet, direction), null,  xValue , yValue);
                                break;
                            case 3:
                                g2d.drawImage(rotateImage(turtleYellow, direction), null,  xValue , yValue);
                                break;

                        }

                    }
                }
            }
        } else {
            g2d.drawImage(mainScreen, null, WIDTH / 2 - (mainScreen.getWidth() / 2 + 10), 0);
        }
    }

    /**
     * This method rotates turtles into the direction that they are walking.
     *
     * @param turtle    the image of the turtle
     * @param direction the direction that the turtle is facing
     * @return the buffered image
     */
    public static BufferedImage rotateImage(BufferedImage turtle, int direction) {
        int degree = 0;
        switch(direction) {
            case 0:
                degree = 0;
                break;
            case 1:
                degree = 90;
                break;
            case 2:
                degree = 180;
                break;
            case 3:
                degree = 270;
                break;
        }
        double rotation = Math.toRadians(degree);
        int x = turtle.getWidth() / 2;
        int y = turtle.getHeight() / 2;
        AffineTransform xx = AffineTransform.getRotateInstance(rotation, x, y);
        AffineTransformOp op = new AffineTransformOp(xx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(turtle, null);
    }

    /**
     * This method changes the x values of the whole game
     *
     * @param valueX the value that changes at the x coordinates
     */
    public synchronized void changeX(int valueX) {
        for (int i = 0; i < PICTURE_SIZE / 5; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rescaleX += valueX;
            repaint();
        }
    }


    /**
     * This method changes the y values of the whole game.
     *
     * @param valueY the value that changes at the x coordinates
     */
    public synchronized void changeY(int valueY) {
        for (int i = 0; i < PICTURE_SIZE / 5; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rescaleY += valueY;
            repaint();
        }
    }


    /**
     * The entry point of application.
     * this method is used to test the game gui panel.
     * @param args the input arguments
     * @throws IOException the io exception
     */

    public static void main(String[] args) throws IOException {
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