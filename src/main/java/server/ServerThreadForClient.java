package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServerThreadForClient implements Runnable {

    //In- & Ouputstreams for reading and sending Strings
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    /**
     * All attributes a Client requires
     */

    Profil profil;
    State state;

    //int client_ID;
    //String client_nickname; //was used before
    //String client_message;

    public ServerThreadForClient(
        int client_ID, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
            profil = new Profil(client_ID);
            state = new State();
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
    }

    public void run () {
        
        try {
            
            /**
             * Let client choose his nickname.
             */
            dataOutputStream.writeUTF("\nWould you like to use the username of your system?\n");
            dataOutputStream.writeUTF("If yes, type in >YEAH<. Otherwise type in your new nickname\n");
            /**
             * Receives nickname by Client and save it.
             */
            profil.nickname = dataInputStream.readUTF(); //to do: Server checks for duplicate

            System.out.println("\nNickname client #" + profil.client_ID + ": " + profil.nickname);

            String helloMessage = dataInputStream.readUTF();
            System.out.println(helloMessage);

            while (true) {

                synchronized (Server.message) {

                    String input = dataInputStream.readUTF();

                    if (input.equalsIgnoreCase("QUIT")) {
                        //end stream
                        break;
                    } else if (input.equalsIgnoreCase("CHAT")) {
                        //go into general chatroom
                        state.generalChat = true;
                    } else if (state.generalChat) {
                        //to do: synchronize message for chat - print chat at Client's terminal
                        Server.message += profil.nickname + ": " + input; //TO DO NICHT MESSAGE SONDER QUEUE
                        System.out.println(Server.message); // LAST IN FIRTS OUt
                        if (input.equalsIgnoreCase("BACK")) {
                            state.generalChat = false;
                        }
                    } else {
                        //Should be: Echo chat between one Client and Server only
                        dataOutputStream.writeUTF(input);

                    }
                
                }

            }

            dataInputStream.close();
            dataOutputStream.close();
            
        } catch (Exception exception) {
            System.err.println(exception.toString());
        }

    }
    
}