package game;

import java.util.Random;

/**
 * @author Dennis, Melanie
 * This class represents the board made out of Fields.
 */
public class Board {

    int boardSize;

    Field[][] board;
    Field[][] eventboard;

    int maxCoinsinGame;
    int coinOccurence;
    int coinsOnBoard = 0;

    /**
     * Function to copy a board (already needed by the constructor)
     *
     * @param copyThis the copy this
     * @return the field [ ] [ ]
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
     * Constructor creates a new board, fills it with fields and links the fields to each other. Also
     * a identical eventboard is created. (board for events)
     *
     * @param boardSize      the board size
     * @param maxCoinsinGame the max coinsin game
     */
    public Board(int boardSize, int maxCoinsinGame) {
        /*
        TO DO DENNIS: COINS ETC RAUSNEHMEN. eigene funktion! Clients haben eigenees Board
        STRING mit Positionen münzen an clients (Client wandelt das um dann gut) KEyWOrds benutzen! Dokumentation!
        eigene constructoren!
        1. board auf serverseite erstellen
        2. board auf clientseite erstellen
        3. serverboard macht münzen und schickt position via string (keyword)
        4. clientboard macht münzen nach string auf eigeneem board
        5. string mit turlteposition alt und neu.
        */
        //boardSize - min = 10, max = 200
        if (boardSize < 10) {
            boardSize = 10;
        } else if (boardSize > 20) {
            boardSize = 20;
        }
            this.boardSize = boardSize;

        //maxCoins possible = 500; they determine the probability for coins on the board.
        if (maxCoinsinGame > 500) {
            maxCoinsinGame = 500;
        }

        this.maxCoinsinGame = maxCoinsinGame;


        this.coinOccurence = boardSize + (maxCoinsinGame / 10);

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
        this.board[a + 1][b].resetField();
        this.board[a][b + 1].resetField();
        this.board[a + 1][b + 1].resetField();
        this.board[a][b].isStartPosition = true;
        this.board[a + 1][b].isStartPosition = true;
        this.board[a][b + 1].isStartPosition = true;
        this.board[a + 1][b + 1].isStartPosition = true;

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
     * If a earthquake happens (determined by server) the following function is used:
     *
     * @param magnitude the magnitude
     */
    public void earthquake(int magnitude) {
        for (int x = 1; x < this.boardSize; x++) { //x = 0 is border (already flooded)
            for (int y = 1; y < this.boardSize; y++) { //y = 0 is border (already flooded)
                Random random = new Random();
                int number = random.nextInt(100);
                if (number <= magnitude && !board[x][y].isStartPosition) {
                    board[x][y].isQuake = true;
                    if (this.board[x][y].isTaken) {
                        A: for (int a = 1; a < this.boardSize-3; a++) {
                            for (int b = 1; b < this.boardSize-3; b++) {
                                if (this.board[a][b].isStartPosition && !this.board[a][b].isTaken) {
                                    this.board[x][y].turtle.turtleposition = this.board[a][b];
                                    this.board[a][b].turtle = this.board[x][y].turtle;
                                    this.board[x][y].turtle = null;
                                    this.board[a][b].isTaken = true;
                                    this.board[a][b].turtle.points -= 5;
                                    this.board[a][b].turtle.wasHitByEvent = true;
                                    break A;
                                }
                            }
                        }
                        this.board[x][y].isTaken = false;
                    }
                }
            }
        }
    }

    /**
     *
     * @param timesFlood the times flood
     */
    public void floodBoard(int timesFlood) {
        Random randomFlood = new Random();

        for (int j = 0; j < timesFlood; j++) {

            int whichSide = randomFlood.nextInt(4);
            int position = randomFlood.nextInt(this.boardSize) + 1; //0 is water, so random does not include 0
            int howStrong = randomFlood.nextInt(this.boardSize - 3 + 1) + 3; //at least three fields

            switch (whichSide) {
                case 0: //unten
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[position][i].isStartPosition) {
                            this.board[position][i].isFlood = true; //TO DO: LIKED LIST - MIT KOORDINATEN
                            if (this.board[position][i].isTaken) {
                                A: for (int a = 1; a < this.boardSize-3; a++) {
                                    for (int b = 1; b < this.boardSize-3; b++) {
                                        if (this.board[a][b].isStartPosition && !this.board[a][b].isTaken) {
                                            this.board[position][i].turtle.turtleposition = this.board[a][b];
                                            this.board[a][b].turtle = this.board[position][i].turtle;
                                            this.board[position][i].turtle = null;
                                            this.board[a][b].isTaken = true;
                                            this.board[a][b].turtle.points -= 5;
                                            this.board[a][b].turtle.wasHitByEvent = true;
                                            break A;
                                        }
                                    }
                                }
                                this.board[position][i].isTaken = false;
                            }
                        }
                    }
                    break;
                case 1: //oben
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[position][this.boardSize - i].isStartPosition) {
                            this.board[position][this.boardSize - i].isFlood = true;
                            if (this.board[position][this.boardSize - i].isTaken) {
                                A: for (int a = 1; a < this.boardSize-3; a++) {
                                    for (int b = 1; b < this.boardSize-3; b++) {
                                        if (this.board[a][b].isStartPosition && !this.board[a][b].isTaken) {
                                            this.board[position][this.boardSize - i].turtle.turtleposition
                                                    = this.board[a][b];
                                            this.board[a][b].turtle = this.board[position][this.boardSize - i].turtle;
                                            this.board[position][this.boardSize - i].turtle = null;
                                            this.board[a][b].isTaken = true;
                                            this.board[a][b].turtle.points -= 5;
                                            this.board[a][b].turtle.wasHitByEvent = true;
                                            break A;
                                        }
                                    }
                                }
                                this.board[position][this.boardSize - i].isTaken = false;
                            }
                        }
                    }
                    break;

                case 2: //links
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[i][position].isStartPosition) {
                            this.board[i][position].isFlood = true;
                            if (this.board[i][position].isTaken) {
                                A: for (int a = 1; a < this.boardSize-3; a++) {
                                    for (int b = 1; b < this.boardSize-3; b++) {
                                        if (this.board[a][b].isStartPosition && !this.board[a][b].isTaken) {
                                            this.board[i][position].turtle.turtleposition = this.board[a][b];
                                            this.board[a][b].turtle = this.board[i][position].turtle;
                                            this.board[i][position].turtle = null;
                                            this.board[a][b].isTaken = true;
                                            this.board[a][b].turtle.points -= 5;
                                            this.board[a][b].turtle.wasHitByEvent = true;
                                            break A;
                                        }
                                    }
                                }
                                this.board[i][position].isTaken = false;
                            }
                        }
                    }
                    break;

                case 3: //rechts
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[this.boardSize - i][position].isStartPosition) {
                            this.board[this.boardSize - i][position].isFlood = true;
                            if (this.board[this.boardSize - i][position].isTaken) {
                                A: for (int a = 1; a < this.boardSize-3; a++) {
                                    for (int b = 1; b < this.boardSize-3; b++) {
                                        if (this.board[a][b].isStartPosition && !this.board[a][b].isTaken) {
                                            this.board[this.boardSize - i][position].turtle.turtleposition =
                                                    this.board[a][b];
                                            this.board[a][b].turtle = this.board[this.boardSize - i][position].turtle;
                                            this.board[this.boardSize - i][position].turtle = null;
                                            this.board[a][b].isTaken = true;
                                            this.board[a][b].turtle.points -= 5;
                                            this.board[a][b].turtle.wasHitByEvent = true;
                                            break A;
                                        }
                                    }
                                }
                                this.board[this.boardSize - i][position].isTaken = false;
                            }
                        }
                    }
                    break;
            }
        }
    }

    /**
     * After event.
     */
    public void afterEvent() {
        for (int x = 1; x < this.boardSize; x++) {
            for (int y = 1; y < this.boardSize; y++) {
                if (!this.board[x][y].isStartPosition && (board[x][y].isFlood || this.board[x][y].isQuake)) {
                    this.board[x][y].resetField();
                }
            }
        }
    }


    /**
     * Print board string.
     *
     * @return the string
     */
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

    /**
     * Reset eventboard.
     */
    public void resetEventboard() {
        eventboard = copyBoard(board);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
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
