package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Highscore {

    public static File file = new File("highscore.txt");
    public static int entries = 10;
    public static HighscoreList highscoreList = new HighscoreList();

    public static void getHighscores() {

        try {

            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] inputs = reader.nextLine().split(":");
                inputs[1] = inputs[1].substring(1);
                inputs[2] = inputs[2].substring(1);
                inputs[2] = inputs[2].replace( " points!", "");

                HighscorePlayer hsp = new HighscorePlayer(inputs[1], Integer.parseInt(inputs[2]));

                highscoreList.addEntryToTail(hsp);

                //System.out.println(highscoreList.writeHiscoreList());

                try {
                    FileWriter writer = new FileWriter(Highscore.file.getName());
                    writer.write(highscoreList.writeHiscoreList());
                    writer.close();
                } catch (IOException exception) {
                    System.err.println();
                }

            }

        } catch (FileNotFoundException fileE) {
            System.err.println();
        }

    }

}
