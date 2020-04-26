package game;

/**
 * This class represents a player which will be
 * saved in the highscorelist.
 */
public class HighscorePlayer {

    public String nicknameHS;
    public int pointsHS;

    /**
     * Each HSPlayer has a pointer to the next lower player.
     * This is important for the correct order of the list.
     */
    public HighscorePlayer nextPlayer = null;

    /**
     * Instantiates a new Highscore player.
     *
     * @param nicknameHS the nickname of the player
     * @param pointsHS the points of the player
     */
    public HighscorePlayer(String nicknameHS, int pointsHS) {
        this.nicknameHS = nicknameHS;
        this.pointsHS = pointsHS;
    }

}
