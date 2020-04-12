package game;

import java.util.Random;

/**
 * @author Dennis, Melanie
 * This class represents the board made out of Fields.
 */
public class Board {

    public int boardSize;
    public Field[][] board;
    public int maxCoinsInGame;
    public int coinOccurrence;
    int coinsOnBoard = 0;

    /**
     * Constructor creates a board with connected fields. Floods area around board.
     * @param desiredBoardSize size of board where players can move (between 10 and 20).
     */
    public Board(int desiredBoardSize) {

        this.boardSize = desiredBoardSize;

        //actual board
        this.board = new Field[boardSize + 1][boardSize + 1];
        //Generate Fields and flood area around (border):
        for (int x = 0; x < boardSize + 1; x++) {
            for (int y = 0; y < boardSize + 1; y++) {
                if (x == 0 || x == boardSize || y == 0 || y == boardSize) {
                    board[x][y] = new Field();
                    board[x][y].isFlood = true;
                    board[x][y].isBoundary = true;
                } else {
                    board[x][y] = new Field();
                }
            }
        }
        //Set 4 startpositions in center.
        int a = this.boardSize / 2;
        this.board[a][a].resetField();
        this.board[a + 1][a].resetField();
        this.board[a][a + 1].resetField();
        this.board[a + 1][a + 1].resetField();
        this.board[a][a].isStartPosition = true;
        this.board[a + 1][a].isStartPosition = true;
        this.board[a][a + 1].isStartPosition = true;
        this.board[a + 1][a + 1].isStartPosition = true;
        //Connect fields to each other. (UP, DOWN, LEFT, RIGHT)
        for (int x = 0; x < this.boardSize + 1; x++) {
            for (int y = 0; y < this.boardSize + 1; y++) {
                //Link UP
                if (y + 1 <= this.boardSize) {
                    this.board[x][y].up = this.board[x][y + 1];
                }
                //Link RIGHT
                if (x + 1 <= this.boardSize) {
                    this.board[x][y].right = this.board[x + 1][y];
                }
                //Link DOWN
                if (y - 1 >= 0) {
                    this.board[x][y].down = this.board[x][y - 1];
                }
                //Link LEFT
                if (x - 1 >= 0) {
                    this.board[x][y].left = this.board[x - 1][y];
                }
            }
        }

    }

    /**
     * Function creates random spawning coins on the board.
     * Occurrence according to this.CoinOccurrence
     */
    public String spawnRandomCoins() {
        String coinAt = "";
        for (int x = 0; x < this.boardSize + 1; x++) {
            for (int y = 0; y < this.boardSize + 1; y++) {
                if (!this.board[x][y].isFlood && !this.board[x][y].isBoundary
                        && !this.board[x][y].isStartPosition && !this.board[x][y].isTaken) {
                    this.board[x][y].hasCoin = this.board[x][y].coins(this.coinOccurrence);
                    //make sure, not too much coins are on the board
                    if (this.board[x][y].hasCoin && (this.coinsOnBoard <= this.maxCoinsInGame)) {
                        this.coinsOnBoard++;
                        coinAt += ":" + x + "." + y;

                    } else {
                        this.board[x][y].hasCoin = false;
                    }
                }
            }
        }
        return coinAt;
    }

    /**
     * If a turtle is hit by an event, it will be reset to a startposition.
     * @param x x-coordinate where turtle was hit
     * @param y y-coordinate where turtle was hit
     */
    public String turtleOnXYtoStart(int x, int y) {
        String turtleMove = "";
        for (int a = 1; a < this.boardSize; a++) {
            for (int b = 1; b < this.boardSize; b++) {
                if (this.board[a][b].isStartPosition && !this.board[a][b].isTaken) {
                    this.board[x][y].turtle.turtleposition = this.board[a][b];
                    this.board[a][b].turtle = this.board[x][y].turtle;
                    this.board[x][y].turtle = null;
                    this.board[a][b].isTaken = true;
                    this.board[a][b].turtle.points -= 5;
                    this.board[a][b].turtle.wasHitByEvent = true;
                    this.board[x][y].isTaken = false;
                    turtleMove += ":" + x + "." + y + "." + a + "." + b;
                    return turtleMove;
                }
            }
        }
        return turtleMove;
    }

    /**
     * Function for an earthquake hitting the board.
     * @param magnitude how strong (how many fields are effected by) is the earthquake
     */
    public String earthquake(int magnitude) {
        String quake = "";
        for (int x = 1; x < this.boardSize; x++) { //x = 0 is border (already flooded)
            for (int y = 1; y < this.boardSize; y++) { //y = 0 is border (already flooded)
                Random random = new Random();
                int number = random.nextInt(100);
                if (number <= magnitude && !this.board[x][y].isStartPosition) {
                    this.board[x][y].isQuake = true;
                    if (this.board[x][y].isTaken) {
                        String s = turtleOnXYtoStart(x, y);
                    }
                }
            }
        }
        return quake;
    }

    /**
     * Function for a flood hitting the board
     * @param timesFlood more than one flood for a crazy time!
     */
    public String floodBoard(int timesFlood) {
        String flood = "";
        Random randomFlood = new Random();

        for (int j = 0; j < timesFlood; j++) {

            int whichSide = randomFlood.nextInt(4);
            int position = randomFlood.nextInt(this.boardSize) + 1; //0 is water, so random does not include 0
            int howStrong = randomFlood.nextInt(this.boardSize - 3 + 1) + 3; //at least three fields

            switch (whichSide) {
                case 0: //unten
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[position][i].isStartPosition) {
                            this.board[position][i].isFlood = true;
                            flood += ":" + position + "." + i;
                            if (this.board[position][i].isTaken) {
                                String turtleMove = turtleOnXYtoStart(position, i);
                            }
                        }
                    }
                    break;

                case 1: //oben
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[position][this.boardSize - i].isStartPosition) {
                            this.board[position][this.boardSize - i].isFlood = true;
                            flood += ":" + position + "." + i;
                            if (this.board[position][this.boardSize - i].isTaken) {
                                String turtleMove = turtleOnXYtoStart(position, this.boardSize - i);
                            }
                        }
                    }
                    break;

                case 2: //links
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[i][position].isStartPosition) {
                            this.board[i][position].isFlood = true;
                            flood += ":" + position + "." + i;
                            if (this.board[i][position].isTaken) {
                                String turtleMove = turtleOnXYtoStart(i, position);
                            }
                        }
                    }
                    break;

                case 3: //rechts
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[this.boardSize - i][position].isStartPosition) {
                            this.board[this.boardSize - i][position].isFlood = true;
                            flood += ":" + position + "." + i;
                            if (this.board[this.boardSize - i][position].isTaken) {
                                String turtleMove = turtleOnXYtoStart(this.boardSize - i, position);
                            }
                        }
                    }
                    break;
            }
        }
        return flood;
    }

    /**
     * After every event, field which were hit by it, are reset.
     */
    public String afterEvent() {
        String reset = "";
        for (int x = 1; x < this.boardSize; x++) {
            for (int y = 1; y < this.boardSize; y++) {
                if ((board[x][y].isFlood || this.board[x][y].isQuake)
                        && !this.board[x][y].isStartPosition) {
                    reset += ":" + x + "." + y;
                    this.board[x][y].resetField();
                }
            }
        }
        return reset;
    }

    /**
     * Returns the board (Field[][]) as a complete String. Each field is represented differently.
     * @return board and its field as a String.
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
     * main function only for testing reasons.
     * @param args not used.
     */
    public static void main(String[] args) {
        Board testBoard = new Board(10);
        testBoard.maxCoinsInGame = 15;
        testBoard.coinOccurrence = testBoard.boardSize + (testBoard.maxCoinsInGame / 10);
        System.out.println("NEW BOARD CREATED: \n\n" + testBoard.printBoard());
        testBoard.floodBoard(2);
        System.out.println("BOARD WAS FLOOD TIMES 2 \n\n" + testBoard.printBoard());
        testBoard.afterEvent();
        System.out.println("BOARD WAS KINDA RESET (COINS DISAPPEAR ETC) : \n\n " + testBoard.printBoard());
        testBoard.earthquake(20);
        System.out.println("BOARD WAS QUAKED MAGNITUDE 20 : \n\n " + testBoard.printBoard());
        testBoard.afterEvent();
        System.out.println("BOARD WAS KINDA RESET (COINS DISAPPEAR ETC) : \n\n " + testBoard.printBoard());
    }





}
