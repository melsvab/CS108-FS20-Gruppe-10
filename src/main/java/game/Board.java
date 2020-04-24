package game;

import server.Protocol;

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

        desiredBoardSize = desiredBoardSize + 2;

        //actual board
        this.board = new Field[desiredBoardSize][desiredBoardSize];
        //Generate fields and flood area around (border)

        int middle = desiredBoardSize / 2;

        for (int x = 0; x < desiredBoardSize; x++) {
            for (int y = 0; y < desiredBoardSize; y++) {
                if (x == 0 || x == desiredBoardSize - 1 || y == 0 || y == desiredBoardSize - 1) {
                    board[x][y] = new Field(1);
                } else if ((x == middle && y == middle)){
                    // start position in the right top
                    board[x][y] = new Field(2);
                } else if ((x == middle - 1 && y == middle)){
                    // start position in the left top
                    board[x][y] = new Field(2);
                } else if ((x == middle && y == middle - 1)){
                    // start position in the right bottom
                    board[x][y] = new Field(2);
                } else if ((x == middle - 1 && y == middle - 1)){
                    // start position in the left bottom
                    board[x][y] = new Field(2);
                } else {
                    board[x][y] = new Field(0);
                }
            }
        }

        //Connect fields to each other. (UP, DOWN, LEFT, RIGHT)
        for (int x = 1; x < desiredBoardSize - 1; x++) {
            for (int y = 1; y < desiredBoardSize - 1; y++) {
                //Link UP
                this.board[x][y].up = this.board[x][y + 1];

                //Link RIGHT
                this.board[x][y].right = this.board[x + 1][y];

                //Link DOWN
                this.board[x][y].down = this.board[x][y - 1];

                //Link LEFT
                this.board[x][y].left = this.board[x - 1][y];

            }
        }

    }

    /**
     * Function creates random spawning coins on the board.
     * Occurrence according to this.CoinOccurrence
     */

    public String spawnRandomCoins() {
        String coinAt = "";
        for (int x = 1; x < this.boardSize + 1; x++) { //x = 0 is border (already flooded)
            for (int y = 1; y < this.boardSize + 1; y++) { //y = 0 is border (already flooded)
                Random random = new Random();
                int number = random.nextInt(100) + 3;
                if (number <= this.coinOccurrence && !this.board[x][y].isStartPosition) {
                    this.board[x][y].hasCoin = true;
                    coinAt += ":" + x + "-" + y;

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
    public void turtleOnXYtoStart(int x, int y, Lobby lobby) {
        String turtleMove = Protocol.TUST.name();
        for (int a = 1; a < this.boardSize + 1; a++) {
            for (int b = 1; b < this.boardSize + 1; b++) {
                if (this.board[a][b].isStartPosition && !this.board[a][b].isTaken) {
                    this.board[x][y].turtle.turtleposition = this.board[a][b];
                    this.board[a][b].turtle = this.board[x][y].turtle;
                    this.board[x][y].turtle = null;
                    this.board[a][b].isTaken = true;
                    this.board[a][b].turtle.points -= 5;
                    this.board[a][b].turtle.wasHitByEvent = true;
                    this.board[x][y].isTaken = false;
                    this.board[a][b].turtle.xPos = a;
                    this.board[a][b].turtle.yPos = b;
                    turtleMove += ":" + this.board[a][b].turtle.num + ":" + a + "-" + b;
                    lobby.writeToAll(turtleMove);
                    return;
                }
            }
        }

    }

    /**
     * Function for an earthquake hitting the board.
     * @param magnitude how strong (how many fields are effected by) is the earthquake
     */
    public String earthquake(int magnitude, Lobby lobby) {
        String quake = "";
        for (int x = 1; x < this.boardSize + 1; x++) { //x = 0 is border (already flooded)
            for (int y = 1; y < this.boardSize + 1; y++) { //y = 0 is border (already flooded)
                Random random = new Random();
                int number = random.nextInt(100);
                if (number <= magnitude && !this.board[x][y].isStartPosition) {
                    this.board[x][y].isQuake = true;
                    quake += ":" + x + "-" + y;
                    if (this.board[x][y].isTaken) {
                        turtleOnXYtoStart(x,y, lobby);
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
    public String floodBoard(int timesFlood, Lobby lobby) {
        String flood = "";
        Random randomFlood = new Random();

        for (int j = 0; j < timesFlood; j++) {

            int whichSide = randomFlood.nextInt(4);
            int position = randomFlood.nextInt(this.boardSize) + 1; //0 is water, so random does not include 0
            int howStrong = randomFlood.nextInt(this.boardSize - 3 + 1) + 3; //at least three fields

            switch (whichSide) {
                case 0: //down
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[position][i].isStartPosition) {
                            this.board[position][i].isFlood = true;
                            flood += ":" + position + "-" + i;
                            if (this.board[position][i].isTaken) {
                                turtleOnXYtoStart(position, i, lobby);
                            }
                        }
                    }
                    break;

                case 1: //up
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[position][this.boardSize + 1 - i].isStartPosition) {
                            this.board[position][this.boardSize + 1 - i].isFlood = true;
                            flood += ":" + position + "-" + (this.boardSize + 1 - i);
                            if (this.board[position][this.boardSize + 1 - i].isTaken) {
                                turtleOnXYtoStart(position, this.boardSize + 1 - i, lobby);
                            }
                        }
                    }
                    break;

                case 2: //left
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[i][position].isStartPosition) {
                            this.board[i][position].isFlood = true;
                            flood += ":" + i + "-" + position;
                            if (this.board[i][position].isTaken) {
                                turtleOnXYtoStart(i, position, lobby);
                            }
                        }
                    }
                    break;

                case 3: //right
                    for (int i = 0; i < howStrong; i++) {
                        if (!this.board[this.boardSize + 1 - i][position].isStartPosition) {
                            this.board[this.boardSize + 1 - i][position].isFlood = true;
                            flood += ":" + (this.boardSize + 1 - i) + "-" + position;
                            if (this.board[this.boardSize + 1 - i][position].isTaken) {
                                turtleOnXYtoStart(this.boardSize + 1 - i, position, lobby);
                            }
                        }
                    }
                    break;
            }
        }
        return secondWave(flood,lobby);
    }


    /**
     * Function calculates neighbors of a field
     * and decides to change field to isFlood if there are many neighbors.
     */

    public String secondWave(String firstWave, Lobby lobby) {
        boolean[][] copyBoard = new boolean[boardSize + 2][boardSize + 2];
        for (int x = 1; x < boardSize + 1; x++) {
            for (int y = 1; y < boardSize + 1; y++) {
                if (!board[x][y].isStartPosition) {
                    boolean getsFlooded = false;
                    int neighbors = getWaterNeighbors(x, y);

                    Random random = new Random();
                    int number = random.nextInt(10);
                    if (neighbors >= 7) {
                        if (number < 9) {
                            getsFlooded = true;
                        }
                    } else if (neighbors >= 6) {
                        if (number < 8) {
                            getsFlooded = true;
                        }
                    } else if (neighbors >= 5) {
                        if (number < 7) {
                            getsFlooded = true;
                        }
                    } else if (neighbors >= 3) {
                        // fields next to the beach
                        if (number < 8) {
                            getsFlooded = true;
                        }
                    }

                    if (getsFlooded) {
                        if (this.board[x][y].isTaken) {
                            turtleOnXYtoStart(x, y, lobby);
                            copyBoard[x][y] = true;
                        }
                    }
                }
            }
        }

        for (int x = 1; x < boardSize + 1; x++) {
            for (int y = 1; y < boardSize + 1; y++) {
                if (copyBoard[x][y]) {
                    board[x][y].isFlood = true;
                    firstWave += ":" + x + "-" + y;
                }

            }
        }
        return firstWave;
    }

    /**
     * Function calculates neighbors of a field
     * and decides to change field to isFlood if there are many neighbors.
     */

    public int getWaterNeighbors(int x, int y) {
        int neighbors = 0;

        if (board[x][y].isFlood) {
            //There is no need to calculate for a field that was already flooded.
            return 0;
        }

        for (int i = -1; i < 2; i++) {
            //upper three fields
            if (board[x+i][y-1].isFlood) {
                neighbors++;
            }
            // three fields under
            if (board[x+i][y+1].isFlood) {
                neighbors++;
            }
        }

        if (board[x-1][y].isFlood) {
            neighbors++;
        }
        if (board[x+1][y].isFlood) {
            neighbors++;
        }

        return neighbors;
    }

    /**
     * After every event, field which were hit by it, are reset.
     */
    public void afterEvent() {
        for (int x = 1; x < this.boardSize + 1; x++) {
            for (int y = 1; y < this.boardSize + 1; y++) {
                if ((board[x][y].isFlood || this.board[x][y].isQuake)
                        && !this.board[x][y].isStartPosition) {
                    this.board[x][y].resetField();
                }
            }
        }
    }

    /**
     * Returns the board (Field[][]) as a complete String. Each field is represented differently.
     * @return board and its field as a String.
     */
    public String printBoard() {
        String boardAsString = "";
        boardAsString += ("#####################################################\n");
        for (int y = this.boardSize + 1; y >= 0; y--) {
            boardAsString += ("   ");
            for (int x = 0; x <= this.boardSize + 1; x++) {
                if (board[x][y].isFlood) {
                    boardAsString += ("WTR");
                } else if (board[x][y].isQuake) {
                    boardAsString += ("xxx");
                } else if (board[x][y].isTaken || board[x][y].turtle != null) {
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
        testBoard.floodBoard(2, null);
        System.out.println("BOARD WAS FLOOD TIMES 2 \n\n" + testBoard.printBoard());
        testBoard.afterEvent();
        System.out.println("BOARD WAS KINDA RESET (COINS DISAPPEAR ETC) : \n\n " + testBoard.printBoard());
        testBoard.earthquake(20, null);
        System.out.println("BOARD WAS QUAKED MAGNITUDE 20 : \n\n " + testBoard.printBoard());
        testBoard.afterEvent();
        System.out.println("BOARD WAS KINDA RESET (COINS DISAPPEAR ETC) : \n\n " + testBoard.printBoard());
    }





}
