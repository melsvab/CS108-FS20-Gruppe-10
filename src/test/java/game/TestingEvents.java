package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.Server;
import server.ServerThreadForClient;


public class TestingEvents {

  Board game = null;
  Lobby lobby = null;
  int fieldsWithCoins = 0;
  int countSteppedOnFields = 0;

  @Before
  public void createBoard() {
    game = new Board(10);
    game.coinOccurrence = 100;
    lobby = Mockito.mock(Lobby.class);
    fieldsWithCoins = 0;
    countSteppedOnFields = 0;
  }

  @Test
  public void checkForAnEarthQuake() {

    game.earthquake(3, lobby);
    boolean check = checkForQuake();
    Assert.assertTrue(check);
  }

  @Test
  public void checkForNoEarthQuake() {
    game.earthquake(-1, lobby);
    boolean check = checkForQuake();
    Assert.assertFalse(check);
  }

  @Test
  public void checkAfterQuake() {
    game.earthquake(29, lobby);
    game.afterEvent();
    boolean check = checkForQuake();
    Assert.assertFalse(check);

  }

  public boolean checkForQuake() {
    boolean atLeastOneFieldWithEarthQuake = false;
    for (int i = 1; i < game.boardSize + 1; i++) {
      for (int j = 1; j < game.boardSize + 1; j++) {
        if (game.board[i][j].isQuake) {
          atLeastOneFieldWithEarthQuake = true;
          break;
        }
      }
    }
    return atLeastOneFieldWithEarthQuake;
  }


  @Test
  public void checkForAFlood() {
    //is always possible
    game.floodBoard(-30,lobby);
    boolean check = checkForFlood();
    Assert.assertTrue(check);
  }


  @Test
  public void checkAfterFlood() {
    game.floodBoard(29, lobby);
    game.afterEvent();
    boolean check = checkForFlood();
    Assert.assertFalse(check);

  }

  public boolean checkForFlood() {
    boolean atLeastOneFieldFlooded = false;
    for (int i = 1; i < game.boardSize + 1; i++) {
      for (int j = 1; j < game.boardSize + 1; j++) {
        if (game.board[i][j].isFlood) {
          atLeastOneFieldFlooded = true;
          break;
        }
      }
    }
    return atLeastOneFieldFlooded;
  }


  @Test
  public void checkForCoins() {
    //is always possible
    game.spawnRandomCoins();
    boolean check = checkForCoinsOnBoard();
    Assert.assertTrue(check);
  }


  @Test
  public void checkCoinsAfterEvent() {
    //coins should stay
    game.spawnRandomCoins();
    game.afterEvent();
    boolean check = checkForCoinsOnBoard();
    Assert.assertTrue(check);

  }


  @Test
  public void checkCoinsAfterFlood() {
    //coins disappear with floods
    boolean lessCoins = false;
    for (int i = 0; i < 4; i++) {
      game.spawnRandomCoins();
    }
    checkForCoinsOnBoard();
    int coinsBeforeEvent = fieldsWithCoins;
    game.floodBoard(100, lobby);
    game.afterEvent();
    checkForCoinsOnBoard();
    if (coinsBeforeEvent > fieldsWithCoins) {
      lessCoins = true;
    }
    Assert.assertTrue(lessCoins);

  }

  @Test
  public void checkCoinsAfterEarthQuake() {
    //coins disappear with earth quakes
    boolean lessCoins = false;
    for (int i = 0; i < 4; i++) {
      game.spawnRandomCoins();
    }
    checkForCoinsOnBoard();
    int coinsBeforeEvent = fieldsWithCoins;
    game.earthquake(100, lobby);
    game.afterEvent();
    checkForCoinsOnBoard();
    if (coinsBeforeEvent > fieldsWithCoins) {
      lessCoins = true;
    }
    Assert.assertTrue(lessCoins);
  }


  public boolean checkForCoinsOnBoard() {
    fieldsWithCoins = 0;
    boolean atLeastOneCoin = false;
    for (int i = 1; i < game.boardSize + 1; i++) {
      for (int j = 1; j < game.boardSize + 1; j++) {
        if (game.board[i][j].hasCoin) {
          atLeastOneCoin  = true;
          fieldsWithCoins++;
        }
      }
    }
    return atLeastOneCoin;
  }


  @Test
  public void checkSteppedOnFieldsAfterEvent() {
    //steppedOnFields should stay
    changeFieldsToSteppedOn();
    checkForFieldsThatAreSteppedOn();
    int steppedOnFieldsBefore = countSteppedOnFields;
    game.afterEvent();

    boolean check = false;
    if (steppedOnFieldsBefore == countSteppedOnFields) {
      check = true;
    }

    Assert.assertTrue(check);

  }


  @Test
  public void checkSteppedOnFieldsAfterFlood() {
    //coins disappear with floods
    changeFieldsToSteppedOn();
    checkForFieldsThatAreSteppedOn();
    int steppedOnFieldsBefore = countSteppedOnFields;
    game.floodBoard(10,lobby);
    game.afterEvent();

    checkForFieldsThatAreSteppedOn();
    boolean check = false;
    if (steppedOnFieldsBefore > countSteppedOnFields) {
      check = true;
    }

    Assert.assertTrue(check);

  }

  @Test
  public void checkSteppedOnFieldsAfterEarthQuake() {
    //coins disappear with earth quakes
    changeFieldsToSteppedOn();
    checkForFieldsThatAreSteppedOn();
    int steppedOnFieldsBefore = countSteppedOnFields;
    game.earthquake(10,lobby);
    game.afterEvent();

    checkForFieldsThatAreSteppedOn();
    boolean check = false;
    if (steppedOnFieldsBefore > countSteppedOnFields) {
      check = true;
    }
    Assert.assertTrue(check);
  }

  public void changeFieldsToSteppedOn() {
    for (int i = 1; i < game.boardSize + 1; i++) {
      for (int j = 1; j < game.boardSize + 1; j++) {
        game.board[i][j].steppedOn = true;
      }
    }
  }

  public boolean checkForFieldsThatAreSteppedOn() {
    countSteppedOnFields = 0;
    boolean atLeastOneField = false;
    for (int i = 1; i < game.boardSize + 1; i++) {
      for (int j = 1; j < game.boardSize + 1; j++) {
        if (game.board[i][j].steppedOn) {
          atLeastOneField  = true;
          countSteppedOnFields++;
        }
      }
    }
    return atLeastOneField;
  }



}

