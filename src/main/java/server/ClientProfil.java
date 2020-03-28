package server;

import game.Lobby;
import game.PlayerTurtle;

public class ClientProfil {

    /**This class is for the server and the client
     * to know about the states and about specific
     * information about a client.
     */

    int clientID;
    public String nickname;
    Lobby lobby;
    public PlayerTurtle myTurtle;

    boolean isInWhisperChat; //example for later
    boolean isInGame; //example for later
    boolean clientIsOnline;
    ClientChatGUI ccg;


    //constructor for serverthread

    public ClientProfil(int clientID) {
        this.clientID = clientID;
        this.clientIsOnline = true;
        lobby = null;
    }


    //constructor for client

    public ClientProfil() {
        this.clientIsOnline = true;
        lobby = null;
        this.ccg = new ClientChatGUI();
    }

    public void goesToSleep() {
        isInGame = false;
        clientIsOnline = false;
    }

    public boolean checkForTwoInt(String original) {
        //an example of an input:
        //KEYW:5:355

        if(checkForTwoWords(original)) {
            String[] words = original.split(":");
            //check if it is possible to transfer the words into numbers
            try {
                int num1 = Integer.parseInt(words[1]);
                int num2 = Integer.parseInt(words[2]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }


    public boolean checkForTwoWords(String original) {
        //an example of an input:
        //KEYW:word:secondWord
        int lenghtInput = original.length();

        //check for usage of ":" and minimum input of KEYW:a:b (without checking details)
        if (lenghtInput > 7 && original.contains(":")) {
            String[] words = original.split(":");

            //check if there are two words (or at least letters) in between ":"
            if (words.length > 2) {
               if(words[1].length() > 0 && words[2].length() > 0) {
                   return true;
               } else {
                   return false;
               }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean checkForName(String original) {
        //an example of an input:
        //KEYW:name (without any further ":")

        if (checkForWord(original)) {

            String[] words = original.split(":");

            //check if there is only one ":"
            // (so if you split the string at ":", there will be two substrings)
            // why? names cannot have a ":" in them.
            if (words.length == 2) {
                return true;

            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public boolean checkForWord(String original) {
        //an example of an input:
        //KEYW:word
        int lenghtInput = original.length();

        ////check for usage of ":" and minimum input of KEYW:a
        if (lenghtInput > 5 && original.contains(":")) {
            //check if ":" is after the keyword
            if (original.indexOf(':') == 4) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean checkForNumber(String original) {
        if (checkForWord(original)) {
            String[] words = original.split(":");

            try {
                int num1 = Integer.parseInt(words[1]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            return true;
        }
    }


}