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
                if (y - 1 < 0) {
                    this.board[x][y].down = null;
                } else {
                    this.board[x][y].down = this.board[x][y - 1];
                }
                //Link LEFT
                if (x - 1 < 0) {
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
    public void earthquake(int magnitude) { //magnitude = how many fields (probability for EQ on field)
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                if (!board[x][y].isFlood) {
                    Random random = new Random();
                    int probability = random.nextInt(500);
                    if (probability < magnitude) {
                        board[x][y].isQuake = true;
                    }
                }
            }
        }
    }


    /**
     * TO DO: Change function. Less random flood!
     */
    public void floodBoard() {
        Random randomFlood = new Random();
        boolean takeX = randomFlood.nextBoolean();
        boolean takeBottom = randomFlood.nextBoolean();
        boolean takeLeft = randomFlood.nextBoolean();
        int floodHere = randomFlood.nextInt(this.boardSize);

        if (takeX) {
            if (takeBottom) {
                for (int i = 0; i < randomFlood.nextInt(this.boardSize - 3) + 3; i++) {
                    this.board[floodHere][0 + i].isFlood = true;
                }
            } else {
                for (int i = this.boardSize; i >= randomFlood.nextInt(this.boardSize - 3) + 3; i--) {
                    this.board[floodHere][this.boardSize - i].isFlood = true;
                }
            }
        } else {
            if (takeLeft) {
                for (int i = 0; i < randomFlood.nextInt(this.boardSize - 3) + 3; i++) {
                    this.board[0 + i][floodHere].isFlood = true;
                }
            } else {
                for (int i = this.boardSize; i >= randomFlood.nextInt(this.boardSize - 3) + 3; i--) {
                    this.board[this.boardSize - i][floodHere].isFlood = true;
                }
            }
        }
    }

    public String printBoard() {
        String boardAsString = "";
        boardAsString += ("#####################################################\n"); /**STEPPED ON = |||*/
        for (int y = this.boardSize; y >= 0; y--) {
            boardAsString += ("   ");
            for (int x = 0; x <= this.boardSize; x++) {
                if (board[x][y].isFlood) {
                    boardAsString += ("WTR");
                } else if (board[x][y].isQuake) {
                    boardAsString += ("xxx");
                } else if (board[x][y].isTaken) {
                    boardAsString += (":O:"); /**HEY MACH DOOO MACH DAS NO DASS DE NAME KUNND !!!!!!!!!!*/
                } else if (board[x][y].hasCoin && !board[x][y].isFlood) {
                    boardAsString += ("$$$");
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
        System.out.println(testBoard.printBoard());

    }





}
