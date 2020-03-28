package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import game.*;

public class ServerThreadForClient implements Runnable {

    /**
     * This Thread is created for every Client that connects
     * to the server. Represents an interface between Client & server.
     */

    /**
     * In- & Ouputstreams for reading and sending Strings.
     */

    DataInputStream dis;
    DataOutputStream dos;

    /**
     * Every Thread gets a client Profile
     */

    public ClientProfil clientProfil;
    
    //Constructor (creats a new clientProfil)
    
    public ServerThreadForClient(
        int clientID, DataInputStream dis, DataOutputStream dos) {
            this.clientProfil = new ClientProfil(clientID);
            this.dis = dis;
            this.dos = dos;
    }

    /**
     * Check if Client is in global Chat.
     */
    public boolean inGame() {
        return clientProfil.isInGame;
    }


    /*
    *
     */
    public void suddenEnding() {
        clientProfil.goesToSleep();
        sendMessage(Protocol.QUIT.name());
    }

    /**
     * Chatfunction sends a message to client.
     */
    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (Exception exception) {
        System.err.println(exception.toString());
        }
    }

    public static boolean contains(String keyword) {

        for (Protocol p : Protocol.values()) {
            if (p.name().equals(keyword)) {
                return true;
            }
        }

        return false;
    }

    public void run() {

        try {
            
            /**
             * Say Hello to client and let him choose his nickname.
             */
            
            dos.writeUTF(Protocol.WELC.name());

            /**
             * Receiving nickname by Client and checking for duplicates.
             * Let Player know his nickname.
             */
            
            String nickname = dis.readUTF();
            clientProfil.nickname = Server.checkForDublicates(nickname, this);

            System.out.println("\nNickname of client #" + clientProfil.clientID + ": "
                    + clientProfil.nickname);
            dos.writeUTF("Your nickname: " + clientProfil.nickname + "\n");
            /**Nickname chosen. */

            /**
             * Ask client what he wants to do.
             */
            dos.writeUTF(Protocol.HELP.name());

            while (clientProfil.clientIsOnline) {

                /**
                 * Get choice from Client and decide what to do.
                 */
                String original = dis.readUTF();
                int lenghtInput = original.length();
                while (lenghtInput < 4) {
                    original = dis.readUTF();
                    lenghtInput = original.length();
                }
                String clientchoice = original.toUpperCase().substring(0, 4);

                if (contains(clientchoice)) {

                    switch (Protocol.valueOf(clientchoice)) {

                        case CHAT:

                            /*if (clientProfil.checkForWord(original)
                                    && clientProfil.isInGame && clientProfil.lobby != null) {
                            */

                            String msg = "MSG0" + Protocol.CHAT.name()
                                    + ":[" + clientProfil.nickname + "]: "
                                    + original.substring(4);

                            clientProfil.lobby.writeToAll(msg);


                            /*} else {
                                System.out.println(Message.garbage);
                            } */

                            break;

                        case BRC1:
                            /*
                            * message will be send to all clients that are online
                            * (and therefore in our list of ServerThreadForClient called userThreads)
                            */
                            if(lenghtInput > 5) {
                                String message = Protocol.BRC2.name() + ":This is a message from the broadcast: \n"
                                        + "[" + clientProfil.nickname + "] " + original.substring(5);

                                Server.chat(message, Server.userThreads);
                            } else {
                                System.out.println(Message.garbage);
                            }

                            break;

                        case NAME:

                            if (clientProfil.checkForName(original)) {
                                //old name will be removed from the server list, new name is checked for dublicates
                                String oldNickname = clientProfil.nickname;
                                Server.namesOfAllClients.remove(clientProfil.nickname);
                                String desiredName = original.substring(5);
                                desiredName = Server.checkForDublicates(desiredName, this);
                                String answerToClient = Protocol.NAM1.name() +
                                        ":Your name has been changed from " + oldNickname +
                                        " to " + desiredName + "\n";
                                //write decision to Client
                                dos.writeUTF(answerToClient);

                                //server has accepted new name
                                System.out.println(
                                        "\n" + clientProfil.nickname + " changed his/her name to " + desiredName);
                                clientProfil.nickname = desiredName;

                            } else {
                                System.out.println(Message.garbage);
                            }

                            break;

                        case QUIT:

                            dos.writeUTF(clientchoice);


                            //Remove name and thread of this client form the list on the server

                            Server.removeUser(clientProfil.nickname, this);

                            //Give Feedback

                            System.out.println("\nClient #" + clientProfil.clientID + " \""
                                    + clientProfil.nickname + "\" has disconnected.");

                            clientProfil.goesToSleep();
                            break;

                        case ENDE:
                            /**
                             * Under Construction: Can stop server
                             */
                            dos.writeUTF(Protocol.QUIT.name());
                            Server.removeUser(clientProfil.nickname, this);
                            clientProfil.goesToSleep();
                            System.out.println(clientProfil.nickname + " wants to end the whole program.");
                            Server.chat("Our server goes to sleep", Server.userThreads);
                            Server.sendClientsToSleep();
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                System.err.println(e.toString());
                            }
                            Server.serverIsOnline = false;
                            break;

                        case PLL1:

                            //Take list from server and print out players name.


                            String listOfPlayers = Arrays.toString(Server.namesOfAllClients.toArray());
                            dos.writeUTF(Protocol.PLL1.name() + ":" + listOfPlayers);
                            break;

                        case GML1:

                            //Under Construction: Sends a list of open, ongoing and finished Games including their game_ID

                            dos.writeUTF("GML2");
                            break;

                        case HSC1:

                            //Under Construction: Sends the current highscore to the player

                            dos.writeUTF("HSC2" + "1000 points");
                            break;

                        case CRE1:

                            int lobbyNumber = Server.countGame();
                            Lobby lobby = new Lobby(this, lobbyNumber);
                            Server.games.add(lobby);
                            clientProfil.lobby = lobby;
                            clientProfil.isInGame = true;
                            dos.writeUTF(Protocol.CRE2.name() + ":" + lobbyNumber);

                            break;

                        case JOIN:

                            /**
                             * Under Construction: Player joins a Game with the fitting game_ID.
                             * If there is no game with the game_ID EJON will be sent.
                             */

                            if (clientProfil.checkForNumber(original)) {
                                //check if there are any games at all
                                if (Server.checkOutGames()) {

                                    String[] words = original.split(":");
                                    int lobbynumber = Integer.parseInt(words[1]);
                                    //checks for the lobbynumber and adds client if possible
                                    if (Server.checkLobbies(lobbynumber, this)) {
                                        clientProfil.isInGame = true;
                                        dos.writeUTF(Protocol.CRE2.name() + ":" + lobbynumber);
                                    } else {
                                        dos.writeUTF(Protocol.EJON.name());
                                    }
                                } else {
                                    dos.writeUTF(Protocol.EJON.name());
                                }
                            } else {
                                System.out.println(Message.garbage);
                            }
                            break;

                        case STR1:
                            if (clientProfil.checkForTwoInt(original)) {
                                String[] words = original.split(":");
                                //check if it is possible to transfer the words into numbers

                                int boardsize = Integer.parseInt(words[1]);
                                int maxPoints = Integer.parseInt(words[2]);
                                clientProfil.lobby.createGame(boardsize,maxPoints);
                                clientProfil.lobby.start();
                                clientProfil.lobby.writeToAll(Protocol.STR1.name());
                            } else {
                                System.out.println(Message.garbage);
                            }
                            break;

                        case UPPR:

                            //Under Construction: Player moves a block up ingame.

                            break;

                        case DOWN:

                            //Under Construction: Player moves a block down ingame.

                            break;

                        case LEFT:

                            //Under Construction: Player moves a block left ingame.

                            break;

                        case RIGT:

                            //Under Construction: Player moves a block right ingame.

                            break;

                        case WHP1:
                            /**
                             * Under Construction: Player sends a whisperchat. Sends WHP2
                             * when the playername exist, sends EWHP when not.
                             */
                            if (true) {
                                dos.writeUTF("WHP2" + original);
                            } else {
                                dos.writeUTF("EWHP");
                                break;
                            }

                        case BACK:
                            //TO DO: might be used for whisperchat

                            break;

                        default:

                            //This should be impossible
                            System.out.println(Message.garbage);
                            break;



                    }

                } else {
                    System.out.println(Message.garbage);
                }

            }

            dis.close();
            dos.close();
            
        } catch (Exception exception) {
            System.err.println(exception.toString());
        }

    }
    
}