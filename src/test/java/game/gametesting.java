package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import server.Server;
import server.ServerThreadForClient;

public class gametesting {
DataOutputStream dos;
DataInputStream dis;
  @Test
  public void checkDeletePlayer(){
    Server testdeletion = new Server(1111);
    testdeletion = Mockito.mock(Server.class);
    int lobbyNumber = testdeletion.countGame();
    ServerThreadForClient user1 = new ServerThreadForClient(1, dis, dos);
    user1.profil.nickname = "rohail";
    Lobby lobby = new Lobby(user1, lobbyNumber);
    testdeletion.games.add(lobby);
    user1.profil.lobby = lobby;
    ServerThreadForClient user2 = new ServerThreadForClient(2, dis, dos);
    lobby.addPlayer(user2);
    lobby.deletePlayer(user2);
    Assert.assertEquals(1,lobby.numberOfPlayers);
    Assert.assertEquals(false,user2.profil.isInGame);
    Assert.assertEquals(null,user2.profil.lobby);
    Assert.assertEquals(false,user2.profil.isSpectator);
  }
  @Test
  public void checkMoveTurtle(){
    Server testserver = new Server(1111);
    testserver = Mockito.mock(Server.class);
    int lobbyNumber = testserver.countGame();
    ServerThreadForClient user1 = new ServerThreadForClient(1, dis, dos);
    user1.profil.nickname = "rohail";
    Lobby lobby = new Lobby(user1, 1);
    testserver.games.add(lobby);
    user1.profil.lobby = lobby;
    Board board = new Board(10);
    user1.profil.lobby.board = board;
    PlayerTurtle tester = new PlayerTurtle("rohail");
    user1.profil.myTurtle = tester;
    user1.profil.myTurtle.turtleposition = user1.profil.lobby.board.board[5][5];
    user1.profil.myTurtle.xPos = 5;
    user1.profil.myTurtle.yPos = 5;
    board.board[5][5].isTaken = true;
    user1.profil.moveTurtle(3);
    user1.profil.moveTurtle(3);
    user1.profil.moveTurtle(3);
    user1.profil.moveTurtle(3);
    Assert.assertEquals(4,user1.profil.myTurtle.points);
    user1.profil.myTurtle.turtleposition.right.hasCoin = true;
    user1.profil.moveTurtle(1);
    Assert.assertEquals(6,user1.profil.myTurtle.points);
    user1.profil.myTurtle.turtleposition.up.hasCoin = true;
    user1.profil.moveTurtle(0);
    Assert.assertEquals(9,user1.profil.myTurtle.points);


  }

}

