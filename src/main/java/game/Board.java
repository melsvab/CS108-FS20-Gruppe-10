package game;

import java.util.Random;

public class Board {  //This class represents the board and is made of Fields

    int boardSize;
    Field[][] board;

    int maxCoins;
    int maxApples;
    int applesOnField = 0;
    int coinsOnField = 0;


    //Constructor creates a new board and links fields to each other.
    public Board(int boardSize, int maxApples, int maxCoins) {

        //Create a new board out of fields.
        this.boardSize = boardSize;
        this.maxApples = maxApples;
        this.maxCoins = maxCoins;
        this.board = new Field[boardSize][boardSize];
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                Random random = new Random();
                boolean truefalse = random.nextBoolean();
                board[x][y] = new Field(truefalse);
                if (board[x][y].hasApple && board[x][y].hasCoin) {
                    board[x][y].hasCoin = false;
                }
                if (board[x][y].hasApple) {
                    applesOnField++;
                    if (applesOnField > maxApples) {
                        board[x][y].hasApple = false;
                        applesOnField--;
                    }
                }
                if (board[x][y].hasCoin) {
                    coinsOnField++;
                    if (coinsOnField > maxCoins) {
                        board[x][y].hasCoin = false;
                        coinsOnField--;
                    }
                }
            }
        }

        //Connect fields to each other.
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                 //Link UP
                if ((y+1) >= this.boardSize){
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
                if (y-1 <= this.boardSize){
                    this.board[x][y].down = null;
                } else {
                    this.board[x][y].down = this.board[x][y-1];
                }
                //Link LEFT
                if (x-1 <= this.boardSize){
                    this.board[x][y].left = null;
                } else {
                    this.board[x][y].left = this.board[x-1][y];
                }

        //All fields connected to eachother.
            }
        }
    }

    //Funtion to Randomly flood areas
    public void floodBoard() {
        Random randomFlood = new Random();
        int floodStartXY = randomFlood.nextInt(this.boardSize);
        int floodStartYX = randomFlood.nextInt(1);
        boolean xORy = randomFlood.nextBoolean();
        int floodStartX = 0;
        int floodStartY = 0;
        if (xORy) {
            floodStartX = floodStartXY;
            floodStartY = floodStartYX;
        } else {
            floodStartX = floodStartYX;
            floodStartY = floodStartXY;
        }

        this.board[floodStartX][floodStartY].isFlood = true;

    }

    public void printBoard() {
        System.out.println("__________________________________________________");
        for (int y = this.boardSize-1; y >= 0; y--) {
            System.out.print("   ");
            for (int x = 0; x < this.boardSize; x++) {
                if (board[x][y].isFlood) {
                    System.out.print("~~~");
                } else if (board[x][y].isQuake) {
                    System.out.print("xxx");
                } else if (board[x][y].hasApple) {
                    System.out.print("Apl");
                } else if (board[x][y].hasCoin) {
                    System.out.print("$$$");
                } else {
                    System.out.print("___");
                }
                System.out.print("   ");
            }
            System.out.println("\n");
        }
        System.out.println("__________________________________________________");
    }

    // TESTING CODE:
    public static void main(String[] args) {
        Board testBoard = new Board(10, 5, 4);

        testBoard.printBoard();

        testBoard.floodBoard();

        testBoard.printBoard();

        testBoard.floodBoard();

        testBoard.printBoard();

    }





}
