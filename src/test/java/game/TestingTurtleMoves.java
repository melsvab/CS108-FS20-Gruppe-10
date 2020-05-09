package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.Profil;
import server.Server;
import server.ServerThreadForClient;


public class TestingTurtleMoves {
  Server testServer = null;
  int lobbyNumber = 0;
  ServerThreadForClient user1 = null;
  DataOutputStream dos = null;
  DataInputStream dis = null;
  Board game = null;

  @Before
  public void createServerInfos() {
    testServer = Mockito.mock(Server.class);
    lobbyNumber = Server.countGame();
    user1 = new ServerThreadForClient(1, dis, dos);
    user1.profil.nickname = "turtle";
    Lobby lobby = new Lobby(user1, lobbyNumber);
    Server.games.add(lobby);
    user1.profil.lobby = lobby;
    game = new Board(10);
    user1.profil.lobby.board = game;
    user1.profil.myTurtle = new PlayerTurtle("turtleName");
    user1.profil.myTurtle.turtleposition = user1.profil.lobby.board.board[5][5];
    user1.profil.myTurtle.xPos = 5;
    user1.profil.myTurtle.yPos = 5;
    game.board[5][5].isTaken = true;
  }


  @Test
  public void checkMoveTurtleLeft() {
    int x = user1.profil.myTurtle.xPos;
    int y = user1.profil.myTurtle.yPos;

    user1.profil.moveTurtle(3);
    Assert.assertEquals(x-1,user1.profil.myTurtle.xPos);
    Assert.assertEquals(y,user1.profil.myTurtle.yPos);
  }


  @Test
  public void checkMoveTurtleRight() {
    int x = user1.profil.myTurtle.xPos;
    int y = user1.profil.myTurtle.yPos;

    user1.profil.moveTurtle(1);
    Assert.assertEquals(x+1,user1.profil.myTurtle.xPos);
    Assert.assertEquals(y,user1.profil.myTurtle.yPos);
  }

  @Test
  public void checkMoveTurtleUp() {
    int x = user1.profil.myTurtle.xPos;
    int y = user1.profil.myTurtle.yPos;

    user1.profil.moveTurtle(0);
    Assert.assertEquals(x,user1.profil.myTurtle.xPos);
    Assert.assertEquals(y+1,user1.profil.myTurtle.yPos);
  }

  @Test
  public void checkMoveTurtleDown() {
    int x = user1.profil.myTurtle.xPos;
    int y = user1.profil.myTurtle.yPos;

    user1.profil.moveTurtle(2);
    Assert.assertEquals(x,user1.profil.myTurtle.xPos);
    Assert.assertEquals(y-1,user1.profil.myTurtle.yPos);
  }


  @Test
  public void checkMoveTurtleNoRealDirection() {
    // This should never occur in our program because of how we wrote our code
    int x = user1.profil.myTurtle.xPos;
    int y = user1.profil.myTurtle.yPos;

    user1.profil.moveTurtle(4);
    Assert.assertEquals(x,user1.profil.myTurtle.xPos);
    Assert.assertEquals(y,user1.profil.myTurtle.yPos);
  }

  @Test
  public void checkMoveTurtleOffBoard() {
    // This should never occur in our program because of how we wrote our code
    boolean checkTheImpossibleCase = false;

    try {
      for (int i = 0; i < 10; i++) {
        user1.profil.moveTurtle(3);
      }

    } catch (Exception e) {
      checkTheImpossibleCase = true;
    }
    Assert.assertTrue(checkTheImpossibleCase);
  }

  @Test
  public void checkMoveTurtleOnOtherTurtle() {
    /* can not happen in our game due to the fact that there is an if clause around this method
     * that will make sure that this method is not used if a turtle wants to go to a field that is
     * already taken.
     */

    Profil profile2 = new Profil(2);
    profile2.myTurtle = new PlayerTurtle("secondTurtle");
    profile2.myTurtle.turtleposition = user1.profil.lobby.board.board[4][5];
    profile2.myTurtle.xPos = 5;
    profile2.myTurtle.yPos = 5;
    game.board[4][5].isTaken = true;

    user1.profil.moveTurtle(3);
    Assert.assertEquals(user1.profil.myTurtle.turtleposition, profile2.myTurtle.turtleposition);
  }

  @Test
  public void checkMoveTurtlePoints() {
    user1.profil.moveTurtle(3);
    Assert.assertEquals(1,user1.profil.myTurtle.points);
  }


  @Test
  public void checkMoveTurtleCoins() {
    user1.profil.myTurtle.turtleposition.up.steppedOn = false;
    user1.profil.myTurtle.turtleposition.up.hasCoin = true;
    user1.profil.moveTurtle(0);
    Assert.assertEquals(2,user1.profil.myTurtle.points);
  }


  @Test
  public void checkMoveTurtleToAFieldThatIsAlreadySteppedOn() {
    user1.profil.myTurtle.turtleposition.left.steppedOn = true;
    user1.profil.moveTurtle(3);
    System.out.println(user1.profil.myTurtle.points);
    Assert.assertEquals(0,user1.profil.myTurtle.points);
  }

  @Test
  public void checkMoveTurtleToAFieldThatIsAlreadySteppedOnAndHasCoins() {
    user1.profil.myTurtle.turtleposition.left.steppedOn = true;
    user1.profil.myTurtle.turtleposition.left.hasCoin = true;
    user1.profil.moveTurtle(3);
    Assert.assertEquals(2,user1.profil.myTurtle.points);
  }

}

