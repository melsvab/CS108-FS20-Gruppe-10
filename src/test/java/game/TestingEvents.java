package game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


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
    // It should be impossible to get a negative number for the magnitude.
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
    // There will always be a flood no matter what.
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
    // There will always be a coins no matter what.
    game.spawnRandomCoins();
    boolean check = checkForCoinsOnBoard();
    Assert.assertTrue(check);
  }


  @Test
  public void checkCoinsAfterEvent() {
    //Coins should stay
    game.spawnRandomCoins();
    game.afterEvent();
    boolean check = checkForCoinsOnBoard();
    Assert.assertTrue(check);

  }


  @Test
  public void checkCoinsAfterFlood() {
    //Coins disappear with floods
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
    //Coins disappear with earth quakes
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
    //Fields that are stepped on should stay on board after event
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
    // Fields that are stepped on disappear with floods
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
    // Fields that are stepped on disappear with earth quakes
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

  public void checkForFieldsThatAreSteppedOn() {
    countSteppedOnFields = 0;
    for (int i = 1; i < game.boardSize + 1; i++) {
      for (int j = 1; j < game.boardSize + 1; j++) {
        if (game.board[i][j].steppedOn) {
          countSteppedOnFields++;
        }
      }
    }
  }



}

