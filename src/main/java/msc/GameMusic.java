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
    URL start;
    URL buttons;
    URL earthquake;
    URL flood;
    MediaPlayer mediaTropical;
    MediaPlayer mediaMovement;
    MediaPlayer mediaCoin;
    MediaPlayer mediaCountDown;
    MediaPlayer mediaStart;
    MediaPlayer mediaButtons;
    MediaPlayer mediaQuake;
    MediaPlayer mediaFlood;

    public GameMusic() {
        final JFXPanel fxPanel = new JFXPanel();
        tropical = this.getClass().getClassLoader().getResource("msc/Tropical-Island.mp3");
        movement = this.getClass().getClassLoader().getResource("msc/movements.mp3");
        coin = this.getClass().getClassLoader().getResource("msc/coin.mp3");
        countDown = this.getClass().getClassLoader().getResource("msc/countDown.mp3");
        start = this.getClass().getClassLoader().getResource("msc/startSound.mp3");
        buttons = this.getClass().getClassLoader().getResource("msc/buttons.mp3");
        earthquake = this.getClass().getClassLoader().getResource("msc/earthquake.mp3");
        flood = this.getClass().getClassLoader().getResource("msc/flood.mp3");
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

    public void createStartSound() {
        Media music = new Media(String.valueOf(start));
        mediaStart = new MediaPlayer(music);
        mediaStart.play();
    }

    public void createCoinSound() {
        Media music = new Media(String.valueOf(coin));
        mediaCoin = new MediaPlayer(music);
        mediaCoin.play();
    }

    public void createButtonSound() {
        Media music = new Media(String.valueOf(buttons));
        mediaButtons = new MediaPlayer(music);
        mediaButtons.play();
    }

    public void createEarthquakeSound() {
        Media music = new Media(String.valueOf(earthquake));
        mediaQuake = new MediaPlayer(music);
        mediaQuake.play();
    }

    public void createFloodSound() {
        Media music = new Media(String.valueOf(flood));
        mediaFlood = new MediaPlayer(music);
        mediaFlood.play();
    }
}
