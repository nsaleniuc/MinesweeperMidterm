
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

    private final int NUM_IMAGES = 13;
    private final int CELL_SIZE = 20;

    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    private int N_MINES;
    private int N_ROWS;
    private int N_COLS;

    private Cell[][] field;
    private boolean inGame;
    private int mines_left;
    private Image[] img;
    private Minefield minefield;

    private int all_cells;
    private JLabel statusbar;


    public Board(JLabel statusbar) {

        this.statusbar = statusbar;

        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {
            img[i] = (new ImageIcon("C:\\Users\\user\\IdeaProjects\\MinesweeperMidterm\\src\\Images\\" + i + ".png")).getImage().getScaledInstance(20, 20, 0);
        }
        setDoubleBuffered(true);
        addMouseListener(new MinesAdapter());
        newGame();
    }


    private void newGame() {

        minefield = new Minefield(40, 16, 16);
        N_COLS = minefield.getColumns();
        N_ROWS = minefield.getRows();
        N_MINES = minefield.getNumOfMines();
        inGame = true;
        mines_left = N_MINES;

        all_cells = N_ROWS * N_COLS;
        field = minefield.getMineField();


        statusbar.setText(Integer.toString(mines_left));

    }


    public void find_empty_cells(int j, int i) {
        minefield.openAllNearbyZeros(j, i);
    }

    @Override
    public void paintComponent(Graphics g) {

        Cell cell;

        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {

                cell = field[i][j];

                if (inGame) {
                    setPictures(cell);
                } else {
                    if (cell.isKnown()){
                        setPictures(cell);
                    }
                }
                g.drawImage(img[cell.getPictureNum()], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);

            }
        }
    }

    private void setPictures( Cell cell){
        if (!cell.isKnown()) {
            if (cell.isFlag()){
                cell.setPictureNum(DRAW_MARK);
            }else {
                cell.setPictureNum(DRAW_COVER);
            }

        } else {
            if (cell.isBomb()) {
                cell.setPictureNum(DRAW_MINE);
            }else {
                cell.setPictureNum(cell.getNumOfMinesNearby());
            }
        }
    }


    class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean rep = false;


            if (!inGame) {
                displayBoard();
                newGame();
                repaint();
            }


            if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (!field[cRow][cCol].isKnown()) {
                        rep = true;

                        if (!field[cRow][cCol].isFlag()) {
                            if (mines_left > 0) {
                                field[cRow][cCol].setFlag(true);
                                field[cRow][cCol].setPictureNum(DRAW_MARK);
                                mines_left--;
                                statusbar.setText(Integer.toString(mines_left));
                            } else
                                statusbar.setText("No marks left");
                        } else {

                            field[cRow][cCol].setFlag(false);
                            field[cRow][cCol].setPictureNum(DRAW_COVER);
                            mines_left++;
                            statusbar.setText(Integer.toString(mines_left));
                        }
                    }

                } else {
                    if(inGame) {

                        if (field[cRow][cCol].isBomb() && !field[cRow][cCol].isKnown()) {
                            field[cRow][cCol].setKnown(true);
                            //TODO SHOW BOARD IF BOMB IS HIT
                            displayBoard();
                            rep = true;
                            inGame = false;
                            minefield.gameOver();
                            statusbar.setText("GAME OVER \u2639 (CLICK ANYWHERE TO START NEW GAME)");
                        }

                        if ((!field[cRow][cCol].isKnown()) && !field[cRow][cCol].isBomb()) {

                            field[cRow][cCol].setKnown(true);
                            rep = true;

                            if (field[cRow][cCol].getNumOfMinesNearby() == 0)
                                find_empty_cells(cRow, cCol);
                        }
                    }else {
                        displayBoard();
                    }
                }
                if(minefield.checkIfGameWon()){
                    statusbar.setText("YOU WON! \u263A (CLICK ANYWHERE TO PLAY AGAIN)");
                    inGame = false;
                }
                if (rep)
                    repaint();

            }
        }
    }

    private void displayBoard() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j].isKnown();
            }
        }
    }
}
