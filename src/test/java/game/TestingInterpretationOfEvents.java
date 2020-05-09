package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.Parameter;
import server.Protocol;
import server.Server;
import server.ServerThreadForClient;


public class TestingInterpretationOfEvents {

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
  public void checkForInterpretationOfAnEarthQuake() {

    // This string will be sent from the server to all clients in the lobby
    String s = game.earthquake(3, lobby);
    s += Protocol.QUAK.name() + ":" + s;

    //Client's side
    Board gameOfAClient = new Board(10);
    Parameter quake  = new Parameter(s, 7);
    if (quake.isCorrect) {
      quake.changeBoard(gameOfAClient, 3);
    }

    boolean check = compareTwoBoards(game, gameOfAClient);
    Assert.assertTrue(check);
  }


  @Test
  public void checkForInterpretationOfAFlood() {
    // This string will be sent from the server to all clients in the lobby
    String s = game.floodBoard(3, lobby);
    s += Protocol.QUAK.name() + ":" + s;

    //Client's side
    Board gameOfAClient = new Board(10);
    Parameter flood  = new Parameter(s, 7);
    if (flood.isCorrect) {
      flood.changeBoard(gameOfAClient, 1);
    }

    boolean check = compareTwoBoards(game, gameOfAClient);
    Assert.assertTrue(check);
  }


  @Test
  public void checkForInterpretationOfCoins() {
    // This string will be sent from the server to all clients in the lobby
    String s = game.spawnRandomCoins();
    s += Protocol.COIN.name() + ":" + s;

    //Client's side
    Board gameOfAClient = new Board(10);
    Parameter coins  = new Parameter(s, 7);
    if (coins.isCorrect) {
      coins.changeBoard(gameOfAClient, 2);
    }

    boolean check = compareTwoBoards(game, gameOfAClient);
    Assert.assertTrue(check);
  }


  @Test
  public void checkForNoString() {

    /* Client's side
     * This should never happen in our program.
     */
    Parameter coins = new Parameter(null, 7);
    if (coins.isCorrect) {
      coins.changeBoard(game, 2);
    }

    Assert.assertFalse(coins.isCorrect);
  }


  public boolean compareTwoBoards(Board game, Board board) {
    if (board.boardSize == game.boardSize) {
      for (int i = 1; i < board.boardSize+1; i++) {
        for (int j = 1; j < board.boardSize+1; j++) {
          if (game.board[i][j].isQuake != game.board[i][j].isQuake
                  ||  game.board[i][j].isFlood != game.board[i][j].isFlood
                  || game.board[i][j].hasCoin != game.board[i][j].hasCoin) {
            return false;
          }
        }

      }
      return true;
    }  else {
      return false;
    }

  }




}

