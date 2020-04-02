package game;

import java.util.Random;

public class Board {
    /**
     * This class represents the board made out of Fields.
     */
    int boardSize;
    Field[][] board;
    Field[][] eventboard;

    int maxCoinsinGame;
    int coinOccurence;
    int coinsOnBoard = 0;

    /**
     * Function to copy a board (already needed the constructor)
     */
    public Field[][] copyBoard(Field[][] copyThis) {
        Field[][] copy = new Field[this.boardSize + 1][this.boardSize + 1];
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                copy[x][y] = Field.copyField(copyThis[x][y]);
            }
        }
        return copy;
    }

    /**
     * Constructor creates a new board, fills it with fields
     * and links the fields to each other.
     * Also a identical eventboard is created. (board for events)
     */
    public Board(int boardSize, int maxCoinsinGame) {
        //boardSize - min = 10, max = 200
        if (boardSize < 10) { boardSize = 10; }
        else if (boardSize > 20) { boardSize = 20; }
        this.boardSize = boardSize;
        //maxCoins possible = 500; they determine the probability for coins on the board.
        if (maxCoinsinGame > 500) { maxCoinsinGame = 500; }
        this.maxCoinsinGame = maxCoinsinGame;

        this.coinOccurence = boardSize + (maxCoinsinGame/10);

        this.board = new Field[boardSize + 1][boardSize + 1];
        this.eventboard = new Field[boardSize + 1][boardSize + 1];

        //Generate random fields on the board and water around it.
        for (int x = 0; x < boardSize + 1; x++) {
            for (int y = 0; y < boardSize + 1; y++) {
                //if boarder = flood area!
                if (x == 0 || x == boardSize || y == 0 || y == boardSize) { //MAYBE STARTPOSITION HERE
                    board[x][y] = new Field(-1);
                    board[x][y].isFlood = true;
                    board[x][y].isBoundary = true;
                } else {
                    //every field gets a coin with a probability of coinOccurence
                    board[x][y] = new Field(coinOccurence);
                    //make sure, not too much coins are on one board
                    if (board[x][y].hasCoin) {
                        coinsOnBoard++;
                        if (coinsOnBoard > maxCoinsinGame) {
                            board[x][y].hasCoin = false;
                            coinsOnBoard--;
                        }
                    }
                }
            }
        }
        //Set startpositions in center.
        int a = this.boardSize/2;
        int b = this.boardSize/2;
        this.board[a][b].resetField();
        this.board[a+1][b].resetField();
        this.board[a][b+1].resetField();
        this.board[a+1][b+1].resetField();
        this.board[a][b].isStartPosition = true;
        this.board[a+1][b].isStartPosition = true;
        this.board[a][b+1].isStartPosition = true;
        this.board[a+1][b+1].isStartPosition = true;

        //Connect fields to each other. (UP, DOWN, LEFT, RIGHT)
        for (int x = 0; x <= this.boardSize; x++) {
            for (int y = 0; y <= this.boardSize; y++) {
                //Link UP
                if (y + 1 > this.boardSize) {
                    this.board[x][y].up = null;
                } else {
                    this.board[x][y].up = this.board[x][y + 1];
                }
                //Link RIGHT
                if (x + 1 > this.boardSize) {
                    this.board[x][y].right = null;
                } else {
                    this.board[x][y].right = this.board[x + 1][y];
                }
                //Link DOWN
                if (y - 1 < 1) { //0 is already water
                    this.board[x][y].down = null;
                } else {
                    this.board[x][y].down = this.board[x][y - 1];
                }
                //Link LEFT
                if (x - 1 < 1) { //0 is already water
                    this.board[x][y].left = null;
                } else {
                    this.board[x][y].left = this.board[x - 1][y];
                }
            }
        }
        //Copy board into eventboard.
        eventboard = copyBoard(board);
    }

    /**
     * If a earthquake happens (determined by server)
     * do the following function.
     */
    public void earthquake(int magnitude) { //magnitude = percentage a earthquake could happen on the field.
        for (int x = 1; x < this.boardSize; x++) { //x = 0 is border (already flooded)
            for (int y = 1; y < this.boardSize; y++) { //y = 0 is border (already flooded)
                Random random = new Random();
                int number = random.nextInt(100);
                if (number <= magnitude && !board[x][y].isStartPosition) {
                    board[x][y].isQuake = true;
                }
            }
        }
    }


    /**
     * TO DO: Change function. Less random flood!
     */
    public void floodBoard(int timesFlood) { //more than one flood for a crazy time
        Random randomFlood = new Random();

        for (int j = 0; j < timesFlood; j++) {

            int whichSide = randomFlood.nextInt(4);
            int position = randomFlood.nextInt(this.boardSize) + 1; //0 is water, so random does not include 0
            int howStrong = randomFlood.nextInt(this.boardSize - 3 + 1) + 3; //at least three fields

            switch (whichSide) {
                case 0: //unten
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[position][0 + i].isStartPosition) {
                            this.board[position][0 + i].isFlood = true;
                        }
                    }
                    break;
                case 1: //oben
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[position][this.boardSize - i].isStartPosition) {
                            this.board[position][this.boardSize - i].isFlood = true;
                        }
                    }
                    break;

                case 2: //links
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[0 + i][position].isStartPosition) {
                            this.board[0 + i][position].isFlood = true;
                        }
                    }
                    break;

                case 3: //rechts
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[this.boardSize - i][position].isStartPosition) {
                            this.board[this.boardSize - i][position].isFlood = true;
                        }
                    }
                    break;
            }
        }
    }

    public void afterEvent() {
        for (int x = 1; x < this.boardSize; x++) {
            for (int y = 1; y < this.boardSize; y++) {
                if (!this.board[x][y].isStartPosition && (board[x][y].isFlood == true || this.board[x][y].isQuake == true)) {
                    this.board[x][y].resetField();
                }
            }
        }
    }



    public String printBoard() {
        String boardAsString = "";
        boardAsString += ("#####################################################\n");
        for (int y = this.boardSize; y >= 0; y--) {
            boardAsString += ("   ");
            for (int x = 0; x <= this.boardSize; x++) {
                if (board[x][y].isFlood) {
                    boardAsString += ("WTR");
                } else if (board[x][y].isQuake) {
                    boardAsString += ("xxx");
                } else if (board[x][y].isTaken) {
                    boardAsString += (":O:");
                } else if (board[x][y].hasCoin && !board[x][y].isFlood) {
                    boardAsString += ("$$$");
                } else if (board[x][y].steppedOn) {
                    boardAsString += ("~~~");
                } else {
                    boardAsString += ("___");
                }
                boardAsString += ("   ");
            }
            boardAsString += ("\n\n");
        }
        boardAsString += ("#####################################################\n");
        return boardAsString;
    }

    public void resetEventboard() {
        eventboard = copyBoard(board);
    }

    /*public void setPlayerStartpositions(int HowManyPlayers) {
        int a = this.boardSize/2;
        int b = this.boardSize/2;
        for(int i = 0; i < HowManyPlayers; i++) {
            board[a][b].isTaken = true;
            a++; b++;
        }

    }*/

    // TESTING CODE:
    public static void main(String[] args) {
        Board testBoard = new Board(10, 15);
        System.out.println("NEW BOARD CREATED: \n\n" + testBoard.printBoard());
        testBoard.floodBoard(2);
        System.out.println("BOARD WAS FLOOD TIMES 2 \n\n" + testBoard.printBoard());
        testBoard.afterEvent();
        System.out.println("BOARD WAS KINDA RESET (COINS DISAPEAR ETC) : \n\n " + testBoard.printBoard());
        testBoard.earthquake(20);
        System.out.println("BOARD WAS QUAKED MAGNITUDE 20 : \n\n " + testBoard.printBoard());
        testBoard.afterEvent();
        System.out.println("BOARD WAS KINDA RESET (COINS DISAPEAR ETC) : \n\n " + testBoard.printBoard());
    }





}
