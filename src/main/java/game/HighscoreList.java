package game;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author  Dennis
 * The class represents the Highscore list.
 */
public class HighscoreList {

    /**
     * Pointer to the beginning of the list (1. place).
     */
    HighscorePlayer head;
    /**
     * Pointer to the last player.
     */
    HighscorePlayer tail;

    /**
     * Instantiates a new Highscore list (empty).
     */
    public HighscoreList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * This method returns the hiscore list as a string.
     *
     * @return the string
     */
    public String writeHiscoreList() {
        String higscoreListString = "";
        int counter = 0;
        HighscorePlayer hsp = this.head;
        while (hsp != null) {
            higscoreListString += ++counter
                + ": " + hsp.nicknameHS
                + ": " + hsp.pointsHS
                + " points!\n";
            if (hsp.nextPlayer == null) {
                break;
            } else {
                hsp = hsp.nextPlayer;
            }
        }
        return higscoreListString;
    }

    /**
     * Save Player to the highscorelist in correct order.
     * Also write to file or create highscore.txt
     *
     * @param hsPlayer the player who has won the game.
     */
    public void saveHighscore(HighscorePlayer hsPlayer) {

        if (this.head == null) {
            this.head = hsPlayer;
            this.tail = hsPlayer;
        } else if (this.head.pointsHS < hsPlayer.pointsHS) {
            hsPlayer.nextPlayer = this.head;
            this.head = hsPlayer;
        } else {
            HighscorePlayer prev = this.head;
            HighscorePlayer next = prev.nextPlayer;
            while (next != null && hsPlayer.pointsHS < next.pointsHS) {
                prev = prev.nextPlayer;
                next = next.nextPlayer;
            }
            if (next == null) {
                this.tail.nextPlayer = hsPlayer;
                this.tail = hsPlayer;
            } else {
                prev.nextPlayer = hsPlayer;
                hsPlayer.nextPlayer = next;
            }
        }

        try {
            FileWriter writer = new FileWriter(Highscore.file.getName());
            writer.write(writeHiscoreList());
            writer.close();
        } catch (IOException exception) {
            System.err.println();
        }

    }

    /**
     * For reading an existig highscorelist.
     * Adds an entry to the tail of the highscorelist.
     *
     * @param hsPlayer the hs player
     */
    public void addEntryToTail(HighscorePlayer hsPlayer) {
        if (this.head == null) {
            this.head = hsPlayer;
            this.tail = hsPlayer;
        } else {
            this.tail.nextPlayer = hsPlayer;
            this.tail = hsPlayer;
        }
    }

}
