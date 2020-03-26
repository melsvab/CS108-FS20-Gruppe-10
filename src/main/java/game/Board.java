package game;

public class Board {  //This class represents the board and is made of Fields

    public int boardSize;
    public Field[][] board;

    //Constructor creates a new board and links fields to each other.
    public Board(int boardSize) {

        //Create a new board out of fields.
        this.board = new Field[boardSize][boardSize];

        //Connect fields to each other.
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                 //Link UP
                if (y+1 >= boardSize){
                    this.board[x][y].up = null;
                } else {
                    this.board[x][y].up = this.board[x][y+1];
                }
                //Link RIGHT
                if (x+1 >= boardSize){
                    this.board[x][y].right = null;
                } else {
                    this.board[x][y].right = this.board[x+1][y];
                }
                //Link DOWN
                if (y-1 <= boardSize){
                    this.board[x][y].down = null;
                } else {
                    this.board[x][y].down = this.board[x][y-1];
                }
                //Link LEFT
                if (x-1 <= boardSize){
                    this.board[x][y].left = null;
                } else {
                    this.board[x][y].left = this.board[x-1][y];
                }

        //All fields connected to eachother.
            }
        }
    }





}
