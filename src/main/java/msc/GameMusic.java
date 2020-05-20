package msc;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * @author Melanie
 * The  Game music.
 */
public class GameMusic {


    URL tropical;
    URL movement;
    URL coin;
    URL countDown;
    URL start;
    URL buttons;
    URL earthquake;
    URL flood;
    URL invalidMove;
    URL hit;
    MediaPlayer mediaTropical;
    MediaPlayer mediaMovement;
    MediaPlayer mediaCoin;
    MediaPlayer mediaCountDown;
    MediaPlayer mediaStart;
    MediaPlayer mediaButtons;
    MediaPlayer mediaQuake;
    MediaPlayer mediaFlood;
    MediaPlayer mediaInvalidMove;
    MediaPlayer mediaHit;

    /**
     * Instantiates a new Game music.
     */
    public GameMusic() {
        final JFXPanel fxPanel = new JFXPanel();
        tropical = this.getClass().getClassLoader().getResource("msc/Tropical-Island.mp3");
        movement = this.getClass().getClassLoader().getResource("msc/movements.mp3");
        coin = this.getClass().getClassLoader().getResource("msc/coin.mp3");
        countDown = this.getClass().getClassLoader().getResource("msc/countdown.mp3");
        start = this.getClass().getClassLoader().getResource("msc/startSound.mp3");
        buttons = this.getClass().getClassLoader().getResource("msc/buttons.mp3");
        earthquake = this.getClass().getClassLoader().getResource("msc/earthquake.mp3");
        flood = this.getClass().getClassLoader().getResource("msc/flood.mp3");
        invalidMove = this.getClass().getClassLoader().getResource("msc/invalid.mp3");
        hit = this.getClass().getClassLoader().getResource("msc/hit.mp3");
    }

    /**
     * Create tropical sound.
     */
    public void createTropicalSound() {
        Media music = new Media(String.valueOf(tropical));
        mediaTropical = new MediaPlayer(music);
        mediaTropical.setCycleCount(MediaPlayer.INDEFINITE);
        mediaTropical.play();
    }

    /**
     * Create move sound.
     */
    public void createMoveSound() {
        Media music = new Media(String.valueOf(movement));
        mediaMovement = new MediaPlayer(music);
        mediaMovement.play();
    }

    /**
     * Create count down.
     */
    public void createCountDown() {
        Media music = new Media(String.valueOf(countDown));
        mediaCountDown = new MediaPlayer(music);
        mediaCountDown.play();
    }

    /**
     * Create start sound.
     */
    public void createStartSound() {
        Media music = new Media(String.valueOf(start));
        mediaStart = new MediaPlayer(music);
        mediaStart.play();
    }

    /**
     * Create coin sound.
     */
    public void createCoinSound() {
        Media music = new Media(String.valueOf(coin));
        mediaCoin = new MediaPlayer(music);
        mediaCoin.play();
    }

    /**
     * Create button sound.
     */
    public void createButtonSound() {
        Media music = new Media(String.valueOf(buttons));
        mediaButtons = new MediaPlayer(music);
        mediaButtons.play();
    }

    /**
     * Create earthquake sound.
     */
    public void createEarthquakeSound() {
        Media music = new Media(String.valueOf(earthquake));
        mediaQuake = new MediaPlayer(music);
        mediaQuake.play();
    }

    /**
     * Create flood sound.
     */
    public void createFloodSound() {
        Media music = new Media(String.valueOf(flood));
        mediaFlood = new MediaPlayer(music);
        mediaFlood.play();
    }

    /**
     * Create invalid sound.
     */
    public void createInvalidSound() {
        Media music = new Media(String.valueOf(invalidMove));
        mediaInvalidMove = new MediaPlayer(music);
        mediaInvalidMove.play();
    }

    /**
     * Create hit sound.
     */
    public void createHitSound() {
        Media music = new Media(String.valueOf(hit));
        mediaHit = new MediaPlayer(music);
        mediaHit.play();
    }
}
