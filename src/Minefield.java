import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Nathan Saleniuc on 7/12/2017.
 */
public class Minefield {
    private Cell[][] mineField;
    private int numOfMines;
    private int rows;
    private int columns;
    private boolean isGameOver;
    private boolean isGameWon;

    public int getNumOfMines() {
        return numOfMines;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Cell[][] getMineField() {
        return mineField;
    }

    //Constructor for preselected and custom gameboards
    public Minefield(int numOfMines, int rows, int columns) {
        this.numOfMines = numOfMines;
        this.rows = rows;
        this.columns = columns;
        if (numOfMines > rows * columns) {
            this.numOfMines = (rows * columns) - 1;
        }
        this.mineField = new Cell[rows][columns];
        setAllCellsToUnknown();
        populateMines();
        showNumMinesNearby();
    }

    //Sets the board at beginning of game to be unknown
    private void setAllCellsToUnknown() {
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                mineField[i][j] = new Cell(false, false, 0, false, j, i);
            }
        }
    }

    ///Prints the formatted minefield
    public void printMinefield() {
        int rows = 1;
        for (int i = 0; i <= columns; i++) {
            System.out.print(i + "\t");
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

    // generate random (x,y) coordinates to set mines.
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
        if (mineField[columns][row].isBomb()) {
            gameOver();
        } else {
            mineField[columns][row].setKnown(true);
            if (mineField[columns][row].getNumOfMinesNearby() == 0) {
                openAllNearbyZeros(columns, row);
            }
        }
    }

    //check around each individual cell for mines or empty cells and put results in an ArrayList
    private ArrayList<Cell> getListOfCellsNearby(int i, int j) {
        ArrayList<Cell> listOfCellsNearby = new ArrayList<>();
        //Top left Corner
        if (i == 0 && j == 0) {
            listOfCellsNearby.add(getCellBelow(i, j));
            listOfCellsNearby.add(getCellRight(i, j));
            listOfCellsNearby.add(getCellBottomRight(i, j));
        }

        //Top
        else if (i == 0 && j > 0 && j < mineField[i].length - 1) {
            listOfCellsNearby.add(getCellBelow(i, j));
            listOfCellsNearby.add(getCellRight(i, j));
            listOfCellsNearby.add(getCellBottomRight(i, j));
            listOfCellsNearby.add(getCellLeft(i, j));
            listOfCellsNearby.add(getCellBottomLeft(i, j));
        }

        //top Right Corner
        else if (i == 0 && j == mineField[i].length - 1) {
            listOfCellsNearby.add(getCellBelow(i, j));
            listOfCellsNearby.add(getCellLeft(i, j));
            listOfCellsNearby.add(getCellBottomLeft(i, j));
        }


        // if cell is at the Right wall
        else if (i > 0 && i < mineField.length - 1 && j == mineField[i].length - 1) {
            listOfCellsNearby.add(getCellAbove(i, j));
            listOfCellsNearby.add(getCellTopLeft(i, j));
            listOfCellsNearby.add(getCellLeft(i, j));
            listOfCellsNearby.add(getCellBottomLeft(i, j));
            listOfCellsNearby.add(getCellBelow(i, j));

        }

        //Bottom Right Corner
        else if (i == mineField.length - 1 && j == mineField[i].length - 1) {
            listOfCellsNearby.add(getCellAbove(i, j));
            listOfCellsNearby.add(getCellTopLeft(i, j));
            listOfCellsNearby.add(getCellLeft(i, j));
        }

        //Bottom
        else if (i == mineField.length - 1 && j > 0 && j < mineField[i].length - 1) {
            listOfCellsNearby.add(getCellAbove(i, j));
            listOfCellsNearby.add(getCellLeft(i, j));
            listOfCellsNearby.add(getCellTopLeft(i, j));
            listOfCellsNearby.add(getCellTopRight(i, j));
            listOfCellsNearby.add(getCellRight(i, j));
        }
        //BottomLeft Corner
        else if (i == mineField.length - 1 && j == 0) {
            listOfCellsNearby.add(getCellAbove(i, j));
            listOfCellsNearby.add(getCellRight(i, j));
            listOfCellsNearby.add(getCellTopRight(i, j));
        }

        //Check Left Wall
        else if (i < mineField.length - 1 && i > 0 && j == 0) {
            listOfCellsNearby.add(getCellRight(i, j));
            listOfCellsNearby.add(getCellTopRight(i, j));
            listOfCellsNearby.add(getCellAbove(i, j));
            listOfCellsNearby.add(getCellBelow(i, j));
            listOfCellsNearby.add(getCellBottomRight(i, j));
        }

        //Check all cells around selected cell
        else {
            return getAll(i, j);
        }
        return listOfCellsNearby;
    }

    //Displays a number to the board based on the number of found bombs
    private void showNumMinesNearby() {
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                ArrayList<Cell> listOfCellsNearby = getListOfCellsNearby(i, j);
                for (int k = 0; k < listOfCellsNearby.size(); k++) {
                    if (listOfCellsNearby.get(k).isBomb()) {
                        mineField[i][j].setNumOfMinesNearby(mineField[i][j].getNumOfMinesNearby() + 1);
                    }
                }
            }
        }
    }

    //used recursion to open all empty cells until it finds a cell with a number.
    public void openAllNearbyZeros(int i, int j) {
        ArrayList<Cell> cellsNearby = getListOfCellsNearby(i, j);
        for (int k = 0; k < cellsNearby.size(); k++) {
            if (!cellsNearby.get(k).isKnown()) {
                if (cellsNearby.get(k).getNumOfMinesNearby() == 0) {
                    if (!cellsNearby.get(k).isFlag()) {
                        cellsNearby.get(k).setKnown(true);
                        //calls itself to keep opening cells that are zeros
                        openAllNearbyZeros(cellsNearby.get(k).getY(), cellsNearby.get(k).getX());
                    }
                } else if (!cellsNearby.get(k).isBomb()) {
                    if (!cellsNearby.get(k).isFlag()) {
                        cellsNearby.get(k).setKnown(true);
                    }
                }
            }
        }
    }

    public void setFlag(int i, int j) {
        if (mineField[i][j].isFlag()) {
            mineField[i][j].setFlag(false);
        } else {
            mineField[i][j].setFlag(true);
        }
    }

    //show where all mines are after you lose
    public void gameOver() {
        System.out.println("YOU LOSE! \u2639");
        playSound("bomb_x.wav");
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                mineField[i][j].setKnown(true);
            }
        }
        isGameOver = true;
    }

    public static void playSound(final String url) {
        InputStream in = null;
        try {
            in = new FileInputStream(url);
            AudioStream as = new AudioStream(in);
            AudioPlayer.player.start(as);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //if number of uncovered cells(mines) equals amount of bombs, you win
    public boolean checkIfGameWon() {
        int numUnknownCells = 0;
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                if (!mineField[i][j].isKnown()) {
                    numUnknownCells++;
                }
            }
        }
        if (numUnknownCells == numOfMines) {
            System.out.println("YOU WIN \u263A");
            playSound("smb_stage_clear.wav");
            isGameOver = true;
            isGameWon = true;
            for (int i = 0; i < mineField.length; i++) {
                for (int j = 0; j < mineField[i].length; j++) {
                    if (mineField[i][j].isBomb()) {
                        mineField[i][j].setFlag(true);
                    } else {
                        mineField[i][j].setKnown(true);
                    }
                }
            }
        } else {
            isGameWon = false;
        }
        return isGameWon;
    }

    public String displayWinMessage() {
        return "YOU WIN \u263A";
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

    private ArrayList<Cell> getAll(int i, int j) {
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
