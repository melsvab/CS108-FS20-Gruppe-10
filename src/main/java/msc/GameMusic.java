package msc;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class GameMusic {

    URL tropical;
    URL movement;
    URL coin;
    URL countDown;
    MediaPlayer mediaTropical;
    MediaPlayer mediaMovement;
    MediaPlayer mediaCoin;
    MediaPlayer mediaCountDown;

    public GameMusic() {
        final JFXPanel fxPanel = new JFXPanel();
        tropical = this.getClass().getClassLoader().getResource("msc/Tropical-Island.mp3");
        movement = this.getClass().getClassLoader().getResource("msc/movements.mp3");
        coin = this.getClass().getClassLoader().getResource("msc/coin.mp3");
        countDown = this.getClass().getClassLoader().getResource("msc/countDown.mp3");
    }
    public void createTropicalSound() {
        Media music = new Media(String.valueOf(tropical));
        mediaTropical = new MediaPlayer(music);
        mediaTropical.setCycleCount(MediaPlayer.INDEFINITE);
        mediaTropical.play();
    }

    public void createMoveSound() {
        Media music = new Media(String.valueOf(movement));
        mediaMovement = new MediaPlayer(music);
        mediaMovement.play();
    }

    public void createCountDown() {
        Media music = new Media(String.valueOf(countDown));
        mediaCountDown = new MediaPlayer(music);
        mediaCountDown.play();
    }

    public void createCoinSound() {
        Media music = new Media(String.valueOf(coin));
        mediaCoin = new MediaPlayer(music);
        mediaCoin.play();
    }
}
