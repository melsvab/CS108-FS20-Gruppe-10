package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import game.*;

public class GameGUI extends JPanel {

    private static final int WIDTH = 650;
    private static final int HEIGHT = 650;
    JPanel panel;
    private BufferedImage turtle1;
    private BufferedImage turtle2;
    private BufferedImage turtle3;
    private BufferedImage turtle4;
    private BufferedImage waterField;
    private BufferedImage normalField;
    private BufferedImage usedField;
    private BufferedImage earthquake;
    private BufferedImage coin;
    Board board;

    GameGUI() throws IOException {
        this.panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT ));
        //panel.setBackground(Color.BLUE); //Placeholder to represent the field/panel. Optional: Water-background

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
     *To Do: Find out how to implement the paintComponent method or if the written paintComponent makes sense
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int widthField = WIDTH / board.boardSize;
        int heightField = HEIGHT / board.boardSize;
        g.drawString("The Game has started!",10,20);
        Graphics2D g2d = (Graphics2D) g;
        for (int y = 0; y < board.boardSize; y++) {
            for (int x = 0; x < board.boardSize; x++) {
                if (board.board[x][y].isFlood) {
                    g2d.drawImage(waterField, null, widthField * (x + 1), heightField * (y + 1));
                }
                if (board.board[x][y].isQuake) {
                    g2d.drawImage(earthquake, null, widthField * (x + 1), heightField * (y + 1));
                }
                if (board.board[x][y].steppedOn) {
                    g2d.drawImage(usedField, null, widthField * (x + 1), heightField * (y + 1));
                } else {
                    g2d.drawImage(normalField, null, widthField * (x + 1), heightField * (y + 1));
                }
            }
        }
    }

    public void setVisible(boolean b) {
        /*
         * Note: This method will be used to let the buttons only appear if the client is inGame.
         */
        panel.setVisible(b);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main (String[] args) throws IOException {
        GameGUI game = new GameGUI();
        JFrame frame = new JFrame();
        Board boardDemo = new Board(10,50);
        game.setBoard(boardDemo);
        frame.getContentPane().add(game.getPanel());
        frame.pack();
    }
}