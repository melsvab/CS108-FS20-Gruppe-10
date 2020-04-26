package server;
import game.Lobby;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class serverclient {

  DataInputStream dis;
  DataOutputStream dos;

  @Test
  public void checkforUsername() throws IOException {


      ServerThreadForClient user1 = new ServerThreadForClient(1, dis, dos);
      user1.profil.nickname = "rohail";
      Server.userThreads.add(user1);
      Server.clientConnections = 2;
      ServerThreadForClient user2 = new ServerThreadForClient(2, dis, dos);
      user2.profil.nickname = Server.checkForDuplicate("rohail", user2);
      Assert.assertEquals("rohail_0", user2.profil.nickname);



  }

  @Test
  public void checkforPlayerlistandConnectionlost() throws Exception {
    Server server = new Server(1111);
    server = Mockito.mock(Server.class);
    ServerThreadForClient user1 = new ServerThreadForClient(1, dis, dos);
    user1.profil.nickname = "rohail";
    user1.dos = this.dos;
    server.userThreads.add(user1);
    ServerThreadForClient user2 = new ServerThreadForClient(2, dis, dos);
    user2.profil.nickname = "natasha";
    user2.dos = this.dos;
    server.userThreads.add(user2);
    ServerThreadForClient user3 = new ServerThreadForClient(3, dis, dos);
    user3.profil.nickname = "melanie";
    user3.dos = this.dos;
    server.userThreads.add(user3);
    ServerThreadForClient user4 = new ServerThreadForClient(4, dis, dos);
    user4.profil.nickname = "dennis";
    user4.dos = this.dos;
    server.userThreads.add(user4);

    try {
      Assert.assertEquals(true, "Players at the server are: rohail, natasha, dennis, melanie, ".contains(server.printPlayers()));

    } catch (Exception expected) {
      String listOfPlayers = "Players at the server are: ";
      for (ServerThreadForClient oneOfAllUsers: server.userThreads) {
        listOfPlayers += oneOfAllUsers.profil.nickname + ", ";
      }
      Assert.assertEquals(false,"Players at the server are: rohail, natasha, dennis, melanie,".contains(listOfPlayers));
    }
    }

    @Test
    public void checkForisInGameAndisSpectator(){
      Server testserver = new Server(1111);
      testserver = Mockito.mock(Server.class);
      int lobbyNumber = testserver.countGame();
      ServerThreadForClient user1 = new ServerThreadForClient(1, dis, dos);
      user1.profil.nickname = "rohail";
      Lobby lobby = new Lobby(user1, lobbyNumber);
      testserver.games.add(lobby);
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
      testserver.userThreads.add(user1);
      testserver.userThreads.add(user2);
      testserver.userThreads.add(user3);
      testserver.userThreads.add(user4);
      testserver.userThreads.add(tester);
      testserver.checkLobbies(1,user2,false);
      testserver.checkLobbies(1,user3,false);
      testserver.checkLobbies(1,user4,false);
      lobby.addPlayer(tester);
      Assert.assertEquals(false ,user1.profil.isSpectator);
      Assert.assertEquals(true ,user2.profil.isInGame);
      Assert.assertEquals(false ,user2.profil.isSpectator);
      Assert.assertEquals(true ,user3.profil.isInGame);
      Assert.assertEquals(false ,user3.profil.isSpectator);
      Assert.assertEquals(false ,user4.profil.isSpectator);
      Assert.assertEquals(true ,user4.profil.isInGame);
      Assert.assertEquals(true ,tester.profil.isSpectator);
      Assert.assertEquals(false,tester.profil.isInGame);

    }

  }

