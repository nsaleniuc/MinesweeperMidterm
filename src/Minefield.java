/**
 * Created by Nathan Saleniuc on 7/12/2017.
 */
public class Minefield {
    public Cell[][] mineField;
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
    }

    private void setAllCellsToUnknown(){
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                mineField[i][j] = new Cell(false,false,0,false);
            }
        }
    }

    private void printMinefield() {
        for (Cell[] aMineField : mineField) {
            System.out.println();
            for (Cell anAMineField : aMineField) {
                System.out.print(anAMineField.toString() + "\t");
            }
        }
    }

    public void populateMines() {
        for (int i = 0; i < numOfMines; i++) {

            int mineRow = (int) Math.random()*rows;
            int mineCol = (int) Math.random()*columns;
            if(!mineField[mineRow][mineCol].isBomb()) {
                mineField[mineRow][mineCol].setBomb(true);
            } else {
                i--;
            }

        }
    }

    public void checkCell(int row, int columns) {

    }

    public void showNumMinesNearby() {

    }

    public void openAllNearbyZeros() {

    }

    public void gameOver() {

    }

    public void checkIfGameWon() {

    }

}
