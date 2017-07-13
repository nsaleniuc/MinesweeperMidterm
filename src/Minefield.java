import java.awt.*;

/**
 * Created by Nathan Saleniuc on 7/12/2017.
 */
public class Minefield {
    private Cell[][] mineField;
    private int numOfMines;
    private int rows;
    private int columns;

    public Cell[][] getMineField() {
        return mineField;
    }

    public void setMineField(Cell[][] mineField) {
        this.mineField = mineField;
    }

    public int getNumOfMines() {
        return numOfMines;
    }

    public void setNumOfMines(int numOfMines) {
        this.numOfMines = numOfMines;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Minefield(int numOfMines, int rows, int columns) {
        this.numOfMines = numOfMines;
        this.rows = rows;
        this.columns = columns;
        this.mineField = new Cell[rows][columns];
        setAllCellsToUnknown();
        populateMines();
        showNumMinesNearby();
    }

    private void setAllCellsToUnknown() {
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                mineField[i][j] = new Cell(false, false, 0, false);
            }
        }
    }

    public void printMinefield() {
        int rows = 1;
        for (int i = 0; i <= columns; i++) {
            System.out.print(i +"\t");
        }
        System.out.println();
        for (int i = 0; i <= columns; i++) {
            System.out.print("____");
        }
        System.out.println();
        for (Cell[] aMineField : mineField) {
            System.out.printf("%2d|\t", rows);
            ++rows;
            for (Cell anAMineField : aMineField) {
                System.out.print(anAMineField.toString() + "\t");
            }
            System.out.println();
        }
    }

    public void populateMines() {
        for (int i = 0; i < numOfMines; i++) {
            int mineRow = (int) (Math.random() * rows);
            int mineCol = (int) (Math.random() * columns);
            if (!mineField[mineRow][mineCol].isBomb()) {
                mineField[mineRow][mineCol].setBomb(true);
            } else {
                i--;
            }

        }
    }

    public void checkCell(int row, int columns) {
        if (mineField[row][columns].isBomb()){
            gameOver();
        } else {
            mineField[row][columns].setKnown(true);
            if (mineField[row][columns].getNumOfMinesNearby() == 0){
                openAllNearbyZeros(row, columns);
            }
        }
    }

    private void showNumMinesNearby() {
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {

                //Top left Corner
                if (i==0 && j==0){
                    checkBelowForBomb(i,j);
                    checkRight(i,j);
                    checkBottomRight(i,j);
                }

                //Top
                else if(i == 0 && j > 0 && j < mineField[i].length-1){
                    checkBelowForBomb(i,j);
                    checkRight(i,j);
                    checkBottomRight(i,j);
                    checkLeft(i,j);
                    checkBottomLeft(i,j);
                }

                //top Right Corner
                else if(i == 0 && j == mineField[i].length-1){
                    checkBelowForBomb(i,j);
                    checkLeft(i,j);
                    checkBottomLeft(i,j);
                }


                // if cell is at the Right wall
                else if(i>0 && i < mineField.length-1 && j == mineField[i].length-1){
                    checkAbove(i,j);
                    checkTopLeft(i,j);
                    checkLeft(i,j);
                    checkBottomLeft(i,j);
                    checkBelowForBomb(i, j);

                }

                //Bottom Right Corner
                else if (i == mineField.length-1 && j == mineField[i].length-1){
                    checkAbove(i, j);
                    checkTopLeft(i, j);
                    checkLeft(i, j);
                }

                //Bottom
                else if (i == mineField.length-1 && j > 0 && j < mineField[i].length-1){
                    checkAbove(i, j);
                    checkLeft(i, j);
                    checkTopLeft(i,j);
                    checkTopRight(i, j);
                    checkRight(i, j);
                }
                //BottomLeft Corner
                else if (i == mineField.length-1 && j == 0){
                    checkAbove(i, j);
                    checkRight(i, j);
                    checkTopRight(i, j);
                }

                //Check Left Wall
                else if (i < mineField.length-1 && i > 0 && j ==0){
                    checkRight(i, j);
                    checkTopRight(i, j);
                    checkAbove(i, j);
                    checkBelowForBomb(i, j);
                    checkBottomRight(i, j);
                }
                else {
                    checkAll(i, j);
                }

            }
        }
    }

    private void checkAll(int i, int j) {
        checkBottomRight(i, j);
        checkBottomLeft(i, j);
        checkBelowForBomb(i, j);
        checkAbove(i, j);
        checkTopRight(i, j);
        checkRight(i, j);
        checkTopLeft(i, j);
        checkLeft(i, j);
    }

    public void openAllNearbyZeros(int i, int j) {
        if (getCellBelow(i,j).getNumOfMinesNearby() == 0){
            getCellBelow(i,j).setKnown(true);
        }
    }

    public void gameOver() {

    }

    public void checkIfGameWon() {

    }

    private Cell getCellBelow(int i, int j){
        return mineField[i+1][j];
    }

    private Cell getCellRight(int i, int j){
        return mineField[i][j+1];
    }

    private void checkBelowForBomb(int i, int j){
        if (getCellBelow(i,j).isBomb()) {
            mineField[i][j].setNumOfMinesNearby(mineField[i][j].getNumOfMinesNearby() + 1);
        }
    }
    private void checkRight(int i, int j){
        if (mineField[i][j + 1].isBomb()) {
            mineField[i][j].setNumOfMinesNearby(mineField[i][j].getNumOfMinesNearby() + 1);
        }
    }
    private void checkLeft(int i, int j){
        if (mineField[i][j - 1].isBomb()) {
            mineField[i][j].setNumOfMinesNearby(mineField[i][j].getNumOfMinesNearby() + 1);
        }

    }
    private void checkAbove(int i, int j){
        if (mineField[i - 1][j].isBomb()) {
            mineField[i][j].setNumOfMinesNearby(mineField[i][j].getNumOfMinesNearby() + 1);
        }

    }
    private void checkTopLeft(int i, int j){
        if (mineField[i - 1][j - 1].isBomb()) {
            mineField[i][j].setNumOfMinesNearby(mineField[i][j].getNumOfMinesNearby() + 1);
        }

    }
    private void checkTopRight(int i, int j){
        if (mineField[i - 1][j + 1].isBomb()) {
            mineField[i][j].setNumOfMinesNearby(mineField[i][j].getNumOfMinesNearby() + 1);
        }

    }
    private void checkBottomLeft(int i, int j){
        if (mineField[i + 1][j - 1].isBomb()) {
            mineField[i][j].setNumOfMinesNearby(mineField[i][j].getNumOfMinesNearby() + 1);
        }

    }
    private void checkBottomRight(int i, int j){
        if (mineField[i + 1][j + 1].isBomb()) {
            mineField[i][j].setNumOfMinesNearby(mineField[i][j].getNumOfMinesNearby() + 1);
        }

    }


}
