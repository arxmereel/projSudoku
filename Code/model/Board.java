package model;

public class Board {
    protected Cell[][] board;

    public Board(){
        this.board = new Cell[9][9];
        for (int i = 0; i <9; i++) {
            for (int j = 0; j <9; j++) {
                this.board[i][j] = new Cell();
            }
        }
    }

    // Clone constructor
    public Board(Board otherBoard) {
        this.board = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = new Cell(otherBoard.board[i][j]);
        }
    }
}

    public Cell getCell(int row, int col){
        return board[row][col];
    }

    public Cell[][] getRow(int row){

    }

    public Cell[][] getCol(int col){

    }
}
