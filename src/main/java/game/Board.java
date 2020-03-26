package game;

import java.util.Random;

public class Board {  //This class represents the board and is made of Fields

    int boardSize;
    Field[][] board;

    int maxCoinsinGame;
    int coinsOnBoard = 0;


    //Constructor creates a new board and links fields to each other.
    public Board(int boardSize, int maxCoinsinGame) {

        Random random = new Random();

        //Create a new board out of fields.
        this.boardSize = boardSize;
        this.maxCoinsinGame = maxCoinsinGame;
        this.board = new Field[boardSize + 1][boardSize + 1];

        //Generate random fields and an island (flooded around)
        for (int x = 0; x < boardSize + 1; x++) {
            for (int y = 0; y < boardSize + 1; y++) {
                if (x == 0 || x == boardSize || y == 0 || y == boardSize) {
                    board[x][y] = new Field(false);
                    board[x][y].isFlood = true;
                    board[x][y].isBoundary = true;
                } else {
                    //every Field is either empty or randomly generated.
                    board[x][y] = new Field(random.nextBoolean());
                    //make sure, not too much coins on one board
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

        //Connect fields to each other.
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                 //Link UP
                if (y+1 >= this.boardSize){
                    this.board[x][y].up = null;
                } else {
                    this.board[x][y].up = this.board[x][y+1];
                }
                //Link RIGHT
                if (x+1 >= this.boardSize){
                    this.board[x][y].right = null;
                } else {
                    this.board[x][y].right = this.board[x+1][y];
                }
                //Link DOWN
                if (y-1 <= 0){
                    this.board[x][y].down = null;
                } else {
                    this.board[x][y].down = this.board[x][y-1];
                }
                //Link LEFT
                if (x-1 <= 0){
                    this.board[x][y].left = null;
                } else {
                    this.board[x][y].left = this.board[x-1][y];
                }
        //All fields connected to eachother (up, down, left, right).
            }
        }
    }

    //Funtion to Randomly flood areas
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

    public void printBoard() {
        System.out.println("#####################################################");
        for (int y = this.boardSize; y >= 0; y--) {
            System.out.print("   ");
            for (int x = 0; x <= this.boardSize; x++) {
                if (board[x][y].isFlood) {
                    System.out.print("WTR");
                } else if (board[x][y].isQuake) {
                    System.out.print("xxx");
                } else if (board[x][y].hasCoin && !board[x][y].isFlood) {
                    System.out.print("$$$");
                } else {
                    System.out.print("___");
                }
                System.out.print("   ");
            }
            System.out.println("\n");
        }
        System.out.println("#####################################################");
    }

    // TESTING CODE:
    public static void main(String[] args) {
        Board testBoard = new Board(10, 5);
        testBoard.printBoard();
        for(int i = 0; i < 5; i++) {
            testBoard.floodBoard();
            testBoard.printBoard();
        }
    }





}
