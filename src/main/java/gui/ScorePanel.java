package gui;

import game.Board;
import game.PlayerTurtle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

public class ScorePanel extends BackgroundTurtles {
    private DataOutputStream dos;

    public GameGUI game;
    public StartGamePanel start;
    public JoinGamePanel join;

    public GameMarginHorizontal top;
    public GameMarginVertical right;
    public GameMarginVertical left;
    public GameMarginHorizontal bottom;

    public GameCorners upLeft;
    public GameCorners upRight;
    public GameCorners downLeft;
    public GameCorners downRight;


    public Timer tmr;

    ScorePanel(DataOutputStream dos) throws IOException {
        this.dos = dos;

        //center
        this.game = new GameGUI();
        this.start = new StartGamePanel(dos);
        this.join = new JoinGamePanel(dos);

        // panel on top
        top = new GameMarginHorizontal(true, this.game);

        // panel on the right
        right = new GameMarginVertical(false, this.game);

        // panel on the left
        left = new GameMarginVertical(true, this.game);

        // panel on the bottom
        bottom = new GameMarginHorizontal(false, this.game);

        //corner up left
        upLeft = new GameCorners(0);

        //corner up right
        upRight = new GameCorners(1);

        //corner down left
        downLeft = new GameCorners(2);

        //corner down left
        downRight = new GameCorners(3);


        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(upLeft, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(left, gbc);
        left.addMouseListener(left);

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(downLeft, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(top, gbc);
        top.addMouseListener(top);


        //gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(game, gbc);
        this.add(start, gbc);
        this.add(join, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(bottom, gbc);
        bottom.addMouseListener(bottom);

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(upRight, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(right, gbc);
        right.addMouseListener(right);

        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(downRight, gbc);




        ActionListener task = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bottom.invalidMove.setText("");
            }
        };
        this.tmr = new Timer(2000, task);
    }



    public void setTextInTextArea (int... score) {

        String placeholder = "";
        for (int x = 4; x > score.length ; x--) {
            placeholder += " ";
        }

        switch (score.length) {

            case 4:
                String player4 = String.valueOf(score[3]);
                downRight.player4Score.setText(player4 + placeholder);

            case 3:
                String player3 = String.valueOf(score[2]);
                downLeft.player3Score.setText(player3 + placeholder) ;

            case 2:
                String player2 = String.valueOf(score[1]);
                upRight.player2Score.setText(player2 + placeholder);

            case 1:
                String player1 = String.valueOf(score[0]);
                upLeft.player1Score.setText(player1 + placeholder);
                break;
        }
    }



    public void setTextForRound (int rounds) {
        if (rounds <= 9) {
            top.roundText.setText("Round " + rounds);
        } else {
            top.roundText.setText("Last Round");
        }
    }

    public void getGame(Board game, PlayerTurtle[] turtles) {
        this.game.setBoard(game);
        this.game.repaint();
    }

    public static void changeTextAreaProperties(BackgroundScoreArea textArea) {
        textArea.setEditable(false);
        textArea.setBackground(new Color(1,1,1, (float) 0.01));
        Font f = textArea.getFont();
        Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize()+5);
        textArea.setFont(f2.deriveFont(Font.BOLD));
    }

    public void getGameInfos(Board game, PlayerTurtle[] turtles) {

    }
}
