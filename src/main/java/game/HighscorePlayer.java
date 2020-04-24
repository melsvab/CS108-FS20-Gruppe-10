package game;

public class HighscorePlayer {

    public String nicknameHS;
    public int pointsHS;

    public HighscorePlayer nextPlayer = null;

    public HighscorePlayer(String nicknameHS, int pointsHS) {
        this.nicknameHS = nicknameHS;
        this.pointsHS = pointsHS;
    }

}
