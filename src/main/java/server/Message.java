package server;

public class Message {
    /**
     * This class is a database fore text which is used
     * more often in the program. Easy access.
     */
    
    public static String welcomeMessage = ("\nWelcome to the server!\n\n");
    
    public static String changeName = (
            "\nWould you like to use the username of your system?\n"
                    + "If so, please type in >YEAH<. Otherwise type in the nickname of your choice:\n");

    public static String helpMessage = (
        "\nWhat would you like to do?\n\n"
                + "enter >chat< to join the global chat.\n"
                + "enter >name< to change your nickname\n"
                + "enter >idk< to do something else.\n"
                + "enter >playerlist< to see a list of currently connected players.\n"
                + "enter >gamelist< to see a list of open, ongoing and finished games. \n"
                + "enter >highscore< to see the current highscore. \n"
                + "enter >create< to create a new game. \n"
                + "enter >join< to join a already created game. \n"
                + "enter >quit< to end this program.\n");

    public static String nameIsUsedAlready = (
        "There is someone with this name already!");

    public static String underConstruction = (
        "Under Construction! Why not try something else for the moment?");

    
}