package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMarginHorizontal extends BackgroundTurtles implements MouseListener {

    public boolean top;
    public GameGUI board;
    public BackgroundTurtles panelArea;

    public BackgroundScoreArea roundText;
    public BackgroundScoreArea eventIsHappening;
    public BackgroundScoreArea invalidMove;

    public Timer tmr;


    public GameMarginHorizontal(boolean top, GameGUI board){
        this.setPreferredSize(new Dimension( 60, 800) );
        this.setMinimumSize(new Dimension( 60, 800) );
        panelArea = new BackgroundTurtles();
        this.board = board;
        this.top = top;



        if (top) {

            this.roundText = new BackgroundScoreArea();
            this.eventIsHappening = new BackgroundScoreArea();

            ScorePanel.changeTextAreaProperties(roundText);
            ScorePanel.changeTextAreaProperties(eventIsHappening);

            roundText.setText("     ");
            eventIsHappening.setText("     ");

        } else {

            // bottom

            this.invalidMove = new BackgroundScoreArea();

            ScorePanel.changeTextAreaProperties(invalidMove);

            invalidMove.setText("    ");

        }


        if (top) {


            this.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.PAGE_START;
            gbc.gridx = 0;
            gbc.gridy = 0;
            this.add(roundText, gbc);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridy = 1;
            this.add(eventIsHappening, gbc);


        } else {

            invalidMove.setAlignmentX(BackgroundScoreArea.CENTER_ALIGNMENT);
            this.add(invalidMove);

            ActionListener task = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    invalidMove.setText("");
                }
            };

            tmr = new Timer(2000, task);

        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(top) {
            this.board.changeY(1);
        } else {
            this.board.changeY(-1);
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
