package game;

import java.io.FileWriter;
import java.io.IOException;

public class HighscoreList {

    HighscorePlayer head;
    HighscorePlayer tail;

    public HighscoreList() {
        this.head = null;
        this.tail = null;
    }

    public String writeHiscoreList() {
        String higscoreListString = "";
        int counter = 0;
        HighscorePlayer hsp = this.head;
        while(hsp != null) {
            higscoreListString += ++counter +
                    ": " + hsp.nicknameHS +
                    ": " + hsp.pointsHS +
                    " points!\n";
            if (hsp.nextPlayer == null) {
                break;
            } else {
                hsp = hsp.nextPlayer;
            }
        }
        return higscoreListString;
    }

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
