package server;
import game.Lobby;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestServerAndClient {


  Server testServer = null;
  DataInputStream dis;
  DataOutputStream dos;
  int lobbyNumber = 0;

  ServerThreadForClient[] allThreads = null;


  public void createServerInfos() {
    this.testServer = Mockito.mock(Server.class);
    Server.userThreads = new HashSet<>();
    this.lobbyNumber = Server.countGame();


    allThreads = new ServerThreadForClient[10];

    ServerThreadForClient user1 = new ServerThreadForClient(1, dis, dos);
    user1.profil.nickname = "rohail";
    allThreads[1] = user1;
    ServerThreadForClient user2 = new ServerThreadForClient(2, dis, dos);
    user2.profil.nickname = "natasha";
    allThreads[2] = user2;
    ServerThreadForClient user3 = new ServerThreadForClient(3, dis, dos);
    allThreads[3] = user3;
    user3.profil.nickname = "melanie";
    ServerThreadForClient user4 = new ServerThreadForClient(4, dis, dos);
    allThreads[4] = user4;
    user4.profil.nickname = "dennis";
    ServerThreadForClient tester = new ServerThreadForClient(5, dis, dos);
    tester.profil.nickname = "tester";

    Server.userThreads.add(user1);
    Server.userThreads.add(user2);
    Server.userThreads.add(user3);
    Server.userThreads.add(user4);
    Server.userThreads.add(tester);

    Server.clientConnections = 5;

  }



  @Test
  public void checkForUsername() throws IOException {

    ServerThreadForClient user1 = new ServerThreadForClient(1, dis, dos);
    user1.profil.nickname = "rohail";
    Server.userThreads.add(user1);
    Server.clientConnections = 2;
    ServerThreadForClient user2 = new ServerThreadForClient(2, dis, dos);
    user2.profil.nickname = Server.checkForDuplicate("rohail", user2);
    Assert.assertEquals("rohail_0", user2.profil.nickname);

  }


  @Test
  public void checkForPlayerList() throws Exception {
    createServerInfos();

    boolean check;

    try {
      check = "Players at the server are: rohail, natasha, dennis, melanie, tester".contains(Server.printPlayers());
      Assert.assertTrue(check);

    } catch (Exception expected) {
      String listOfPlayers = "Players at the server are: ";
      for (ServerThreadForClient oneOfAllUsers : Server.userThreads) {
        listOfPlayers += oneOfAllUsers.profil.nickname + ", ";
      }
      check = "Players at the server are: rohail, natasha, dennis, melanie, tester".contains(listOfPlayers);
      Assert.assertFalse(check);
    }
  }

  @Test
  public void checkDeletePlayer() {
    createServerInfos();
    Lobby lobby = new Lobby(allThreads[1], this.lobbyNumber);
    Server.games.add(lobby);
    allThreads[1].profil.lobby = lobby;
    lobby.addPlayer(allThreads[2]);
    lobby.deletePlayer(allThreads[2]);
    Assert.assertEquals(1, lobby.numberOfPlayers);
    Assert.assertFalse(allThreads[2].profil.isInGame);
    Assert.assertNull(allThreads[2].profil.lobby);
    Assert.assertFalse(allThreads[2].profil.isSpectator);
  }


  @Test
  public void checkForAutomaticSpectatorWithFivePlayers() {
    ServerThreadForClient user1 = new ServerThreadForClient(1, dis, dos);
    user1.profil.nickname = "rohail";
    Lobby lobby = new Lobby(user1, lobbyNumber+1);
    Server.games.add(lobby);
    user1.profil.lobby = lobby;
    user1.profil.isInGame = true;
    ServerThreadForClient user2 = new ServerThreadForClient(2, dis, dos);
    user2.profil.nickname = "natasha";
    ServerThreadForClient user3 = new ServerThreadForClient(3, dis, dos);
    user3.profil.nickname = "melanie";
    ServerThreadForClient user4 = new ServerThreadForClient(4, dis, dos);
    user4.profil.nickname = "dennis";
    ServerThreadForClient tester = new ServerThreadForClient(5, dis, dos);
    user4.profil.nickname = "tester";
    Server.userThreads.add(user1);
    Server.userThreads.add(user2);
    Server.userThreads.add(user3);
    Server.userThreads.add(user4);
    Server.userThreads.add(tester);
    Server.checkLobbies(1, user2, false);
    Server.checkLobbies(1, user3, false);
    Server.checkLobbies(1, user4, false);
    lobby.addPlayer(tester);
    Assert.assertFalse(user1.profil.isSpectator);
    Assert.assertTrue( user2.profil.isInGame);
    Assert.assertFalse(user2.profil.isSpectator);
    Assert.assertTrue(user3.profil.isInGame);
    Assert.assertFalse(user3.profil.isSpectator);
    Assert.assertFalse(user4.profil.isSpectator);
    Assert.assertTrue(user4.profil.isInGame);
    Assert.assertTrue(tester.profil.isSpectator);
    Assert.assertFalse(tester.profil.isInGame);

  }





}
