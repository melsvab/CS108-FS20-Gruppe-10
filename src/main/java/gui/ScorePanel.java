package gui;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;

public class ScorePanel extends BackgroundPanelArea {
    private DataOutputStream dos;

    public GameGUI game;
    public StartGamePanel start;
    public JoinGamePanel join;
    public ButtonsGame buttonsGame;
    public BackgroundScoreArea player1Score;
    public BackgroundScoreArea player2Score;
    public BackgroundScoreArea player3Score;
    public BackgroundScoreArea player4Score;
    public BackgroundScoreArea roundText;

    ScorePanel(DataOutputStream dos) throws IOException {
        this.dos = dos;

        this.game = new GameGUI();
        this.start = new StartGamePanel(dos);
        this.join = new JoinGamePanel(dos);
        this.buttonsGame = new ButtonsGame(dos);

        this.player1Score = new BackgroundScoreArea();
        this.player2Score = new BackgroundScoreArea();
        this.player3Score = new BackgroundScoreArea();
        this.player4Score = new BackgroundScoreArea();
        this.roundText = new BackgroundScoreArea();

        this.changeTextAreaProperties(player1Score);
        this.changeTextAreaProperties(player2Score);
        this.changeTextAreaProperties(player3Score);
        this.changeTextAreaProperties(player4Score);
        this.changeTextAreaProperties(roundText);

        player4Score.setText("     ");
        player3Score.setText("     ");
        player2Score.setText("     ");
        player1Score.setText("     ");
        roundText.setText("    ");

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weightx = 1.3;
        gbc.weighty = 1.3;
        gbc.gridx = 1;
        gbc.gridy = 1;
        BackgroundPanelArea temp = new BackgroundPanelArea();
        temp.add(game);
        this.add(temp, gbc);
        BackgroundPanelArea temp2= new BackgroundPanelArea();
        temp2.add(start);
        this.add(temp2, gbc);
        BackgroundPanelArea temp3 = new BackgroundPanelArea();
        temp3.add(join);
        this.add(temp3, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 0.03;
        gbc.weighty = 0.03;
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(buttonsGame, gbc);

        gbc.fill = GridBagConstraints.NONE;

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(roundText, gbc);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(player1Score, gbc);

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(player2Score, gbc);

        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(player3Score, gbc);

        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(player4Score, gbc);
    }

    public void changeTextAreaProperties(BackgroundScoreArea textArea) {
        textArea.setEditable(false);
        textArea.setBackground(new Color(1,1,1, (float) 0.01));
        Font f = textArea.getFont();
        Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize()+5);
        textArea.setFont(f2.deriveFont(Font.BOLD));
    }

    public void setTextInTextArea (int... score) {

        String placeholder = "";
        for (int x = 4; x > score.length ; x--) {
            placeholder += " ";
        }

        switch (score.length) {

            case 4:
                String player4 = String.valueOf(score[3]);
                player4Score.setText(player4 + placeholder);

            case 3:
                String player3 = String.valueOf(score[2]);
                player3Score.setText(player3 + placeholder) ;

            case 2:
                String player2 = String.valueOf(score[1]);
                player2Score.setText(player2 + placeholder);

            case 1:
                String player1 = String.valueOf(score[0]);
                player1Score.setText(player1 + placeholder);
                break;
        }
    }

    public void setTextForRound (int rounds) {
        if (rounds <= 9) {
            roundText.setText("Round " + rounds);
        } else {
            roundText.setText("Last Round");
        }
    }
}
