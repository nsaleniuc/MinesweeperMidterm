import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


class Minefield {
    private Cell[][] mineField;
    private int numOfMines;
    private int rows;
    private int columns;
    private boolean isGameOver;

    int getNumOfMines() {
        return numOfMines;
    }

    int getRows() {
        return rows;
    }

    int getColumns() {
        return columns;
    }

    boolean isGameOver() {
        return isGameOver;
    }

    Cell[][] getMineField() {
        return mineField;
    }

    //Constructor for preselected and custom gameboards
    Minefield(int numOfMines, int rows, int columns) {
        this.numOfMines = numOfMines;
        this.rows = rows;
        this.columns = columns;
        checkIfMinesExceedCells(numOfMines, rows, columns);
        createMinefield(rows, columns);
    }

    private void checkIfMinesExceedCells(int numOfMines, int rows, int columns) {
        if (numOfMines > rows * columns) {
            this.numOfMines = (rows * columns) - 1;
        }
    }

    private void createMinefield(int rows, int columns) {
        this.mineField = new Cell[rows][columns];
        createCells();
        populateMines();
        setNumOfMinesNearby();
    }

    //Sets the board at beginning of game to be unknown
    private void createCells() {
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                mineField[i][j] = new Cell(false, false, 0, false, j, i);
            }
        }
    }

    ///Prints the formatted minefield
    void printMinefield() {
        int rows = 1;
        for (int i = 0; i <= columns; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();
        for (int i = 0; i <= columns; i++) {
            System.out.print("____");
        }
        System.out.println();
        for (Cell[] cellArray : mineField) {
            System.out.printf("%2d|\t", rows);
            ++rows;
            for (Cell cell : cellArray) {
                System.out.print(cell.toString() + "\t");
            }
            System.out.println();
        }
    }

    // generate random (x,y) coordinates to set mines.
    private void populateMines() {
        for (int minesPlaced = 0; minesPlaced < numOfMines; minesPlaced++) {
            minesPlaced = maybePlaceRandomMine(minesPlaced);
        }
    }

    private int maybePlaceRandomMine(int minesPlaced) {
        int mineRow = (int) (Math.random() * rows);
        int mineCol = (int) (Math.random() * columns);
        if (!isCellBomb(mineField[mineRow][mineCol])) {
            mineField[mineRow][mineCol].setBomb(true);
        } else {
            minesPlaced--;
        }
        return minesPlaced;
    }

    void checkSelectedCell(int row, int columns) {
        if (isCellBomb(mineField[columns][row])) {
            gameOver();
        } else {
            mineField[columns][row].setKnown(true);
            maybeOpenZeroesNearby(row, columns);
        }
    }

    private void maybeOpenZeroesNearby(int row, int columns) {
        if (mineField[columns][row].getNumOfMinesNearby() == 0) {
            openAllNearbyZeros(columns, row);
        }
    }

    private boolean isCellBomb(Cell cell) {
        return cell.isBomb();
    }

    //check around each individual cell for mines or empty cells and put results in an ArrayList
    private ArrayList<Cell> getListOfCellsNearby(int row, int column) {
        ArrayList<Cell> listOfCellsNearby = new ArrayList<>();
        //Top left Corner
        if (row == 0 && column == 0) {
            listOfCellsNearby.add(getCellBelow(row, column));
            listOfCellsNearby.add(getCellRight(row, column));
            listOfCellsNearby.add(getCellBottomRight(row, column));
        }

        //Top
        else if (row == 0 && column > 0 && column < mineField[row].length - 1) {
            listOfCellsNearby.add(getCellBelow(row, column));
            listOfCellsNearby.add(getCellRight(row, column));
            listOfCellsNearby.add(getCellBottomRight(row, column));
            listOfCellsNearby.add(getCellLeft(row, column));
            listOfCellsNearby.add(getCellBottomLeft(row, column));
        }

        //top Right Corner
        else if (row == 0 && column == mineField[row].length - 1) {
            listOfCellsNearby.add(getCellBelow(row, column));
            listOfCellsNearby.add(getCellLeft(row, column));
            listOfCellsNearby.add(getCellBottomLeft(row, column));
        }


        // if cell is at the Right wall
        else if (row > 0 && row < mineField.length - 1 && column == mineField[row].length - 1) {
            listOfCellsNearby.add(getCellAbove(row, column));
            listOfCellsNearby.add(getCellTopLeft(row, column));
            listOfCellsNearby.add(getCellLeft(row, column));
            listOfCellsNearby.add(getCellBottomLeft(row, column));
            listOfCellsNearby.add(getCellBelow(row, column));

        }

        //Bottom Right Corner
        else if (row == mineField.length - 1 && column == mineField[row].length - 1) {
            listOfCellsNearby.add(getCellAbove(row, column));
            listOfCellsNearby.add(getCellTopLeft(row, column));
            listOfCellsNearby.add(getCellLeft(row, column));
        }

        //Bottom
        else if (row == mineField.length - 1 && column > 0 && column < mineField[row].length - 1) {
            listOfCellsNearby.add(getCellAbove(row, column));
            listOfCellsNearby.add(getCellLeft(row, column));
            listOfCellsNearby.add(getCellTopLeft(row, column));
            listOfCellsNearby.add(getCellTopRight(row, column));
            listOfCellsNearby.add(getCellRight(row, column));
        }
        //BottomLeft Corner
        else if (row == mineField.length - 1 && column == 0) {
            listOfCellsNearby.add(getCellAbove(row, column));
            listOfCellsNearby.add(getCellRight(row, column));
            listOfCellsNearby.add(getCellTopRight(row, column));
        }

        //Check Left Wall
        else if (row < mineField.length - 1 && row > 0 && column == 0) {
            listOfCellsNearby.add(getCellRight(row, column));
            listOfCellsNearby.add(getCellTopRight(row, column));
            listOfCellsNearby.add(getCellAbove(row, column));
            listOfCellsNearby.add(getCellBelow(row, column));
            listOfCellsNearby.add(getCellBottomRight(row, column));
        }

        //Check all cells around selected cell
        else {
            return getAllCellsNearby(row, column);
        }
        return listOfCellsNearby;
    }

    //Displays a number to the board based on the number of found bombs
    private void setNumOfMinesNearby() {
        for (int row = 0; row < mineField.length; row++) {
            for (int column = 0; column < mineField[row].length; column++) {
                maybeIncrementNumOfMinesNearby(row, column);
            }
        }
    }

    private void maybeIncrementNumOfMinesNearby(int row, int column) {
        ArrayList<Cell> listOfCellsNearby = getListOfCellsNearby(row, column);
        for (Cell cell : listOfCellsNearby) {
            if (isCellBomb(cell)) {
                mineField[row][column].setNumOfMinesNearby(mineField[row][column].getNumOfMinesNearby() + 1);
            }
        }
    }

    //used recursion to open all empty cells until it finds a cell with a number.
    void openAllNearbyZeros(int row, int column) {
        ArrayList<Cell> cellsNearby = getListOfCellsNearby(row, column);
        for (Cell aCellsNearby : cellsNearby) {
            if (isCellKnown(aCellsNearby)) {
                maybeOpenCellsNearby(aCellsNearby);
            }
        }
    }

    private void maybeOpenCellsNearby(Cell cell) {
        if (isThereMinesNearby(cell)) {
            maybeOpenSurroundingCells(cell);
        } else if (isCellANumber(cell)) {
            cell.setKnown(true);
        }
    }

    private boolean isCellKnown(Cell cell) {
        return !cell.isKnown();
    }

    private boolean isCellANumber(Cell cell) {
        return !isCellBomb(cell) & !isCellFlag(cell);
    }

    private boolean isThereMinesNearby(Cell cell) {
        return cell.getNumOfMinesNearby() == 0;
    }

    private void maybeOpenSurroundingCells(Cell cell) {
        if (!isCellFlag(cell)) {
            cell.setKnown(true);
            //calls itself to keep opening cells that are zeros
            openAllNearbyZeros(cell.getY(), cell.getX());
        }
    }

    void setFlag(int i, int j) {
        if (isCellFlag(mineField[i][j])) {
            mineField[i][j].setFlag(false);
        } else {
            mineField[i][j].setFlag(true);
        }
    }

    private boolean isCellFlag(Cell cell) {
        return cell.isFlag();
    }

    //show where all mines are after you lose
    void gameOver() {
        System.out.println("YOU LOSE! \u2639");
        playSound("bomb_x.wav");
        for (Cell[] cellArray : mineField) {
            for (Cell cell : cellArray) {
                cell.setKnown(true);
            }
        }
        isGameOver = true;
    }

    private static void playSound(final String url) {
        InputStream in;
        try {
            in = new FileInputStream(url);
            AudioStream as = new AudioStream(in);
            AudioPlayer.player.start(as);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //if number of uncovered cells(mines) equals amount of bombs, you win
    boolean checkIfGameWon() {
        int numUnknownCells = getNumUnknownCells();
        boolean isGameWon;
        isGameWon = isMinesEqualToUnknownCells(numUnknownCells);
        return isGameWon;
    }

    private boolean isMinesEqualToUnknownCells(int numUnknownCells) {
        boolean isGameWon;
        if (numUnknownCells == numOfMines) {
            gameIsWon();
            isGameWon = true;
        } else {
            isGameWon = false;
        }
        return isGameWon;
    }

    private void gameIsWon() {
        System.out.println("YOU WIN \u263A");
        playSound("smb_stage_clear.wav");
        isGameOver = true;
        openMinefield();
    }

    private void openMinefield() {
        for (Cell[] cellArray : mineField) {
            for (Cell cell : cellArray) {
                maybeSetCellToFlag(cell);
            }
        }
    }

    private void maybeSetCellToFlag(Cell cell) {
        if (isCellBomb(cell)) {
            cell.setFlag(true);
        } else {
            cell.setKnown(true);
        }
    }

    private int getNumUnknownCells() {
        int numUnknownCells = 0;
        for (Cell[] cellArray : mineField) {
            for (Cell cell : cellArray) {
                if (isCellKnown(cell)) {
                    numUnknownCells++;
                }
            }
        }
        return numUnknownCells;
    }


    private Cell getCellBelow(int i, int j) {
        return mineField[i + 1][j];
    }

    private Cell getCellRight(int i, int j) {
        return mineField[i][j + 1];
    }

    private Cell getCellAbove(int i, int j) {
        return mineField[i - 1][j];
    }

    private Cell getCellLeft(int i, int j) {
        return mineField[i][j - 1];
    }

    private Cell getCellTopRight(int i, int j) {
        return mineField[i - 1][j + 1];
    }

    private Cell getCellTopLeft(int i, int j) {
        return mineField[i - 1][j - 1];
    }

    private Cell getCellBottomRight(int i, int j) {
        return mineField[i + 1][j + 1];
    }

    private Cell getCellBottomLeft(int i, int j) {
        return mineField[i + 1][j - 1];
    }

    private ArrayList<Cell> getAllCellsNearby(int i, int j) {
        ArrayList<Cell> cellsNearby = new ArrayList<>();
        cellsNearby.add(getCellAbove(i, j));
        cellsNearby.add(getCellBelow(i, j));
        cellsNearby.add(getCellTopLeft(i, j));
        cellsNearby.add(getCellTopRight(i, j));
        cellsNearby.add(getCellBottomLeft(i, j));
        cellsNearby.add(getCellBottomRight(i, j));
        cellsNearby.add(getCellRight(i, j));
        cellsNearby.add(getCellLeft(i, j));
        return cellsNearby;
    }


}
