import com.sun.xml.internal.bind.v2.TODO;

import java.util.Scanner;

/*
 */
public class MinefieldApp {
    private static InputValidator inputValidator = new InputValidator();
    private static Minefield minefield;
    public static void main(String[] args) {



        int menuChoice = getDifficultyToPlay();
        setUpMineField(menuChoice);
        minefield.printMinefield();
        do {
            System.out.println("Would you like to open a cell or set a flag? (F - Flag O - Open)");
            String userSelection = inputValidator.checkForValidChoice("F", "O");
            if (userSelection.equalsIgnoreCase("O")){
                openSelectedCell();
            }else if (userSelection.equalsIgnoreCase("F")){
                setFlag();
            }
            minefield.checkIfGameWon();

            minefield.printMinefield();
        }while(!minefield.isGameOver());
    }
private static void setFlag(){
    System.out.println("Where would you like to set a flag?\ncolumn: ");
    int x = inputValidator.getValidIntBetweenTwoNumbers(1, minefield.getMineField()[0].length);
    System.out.println("row: ");
    int y = inputValidator.getValidIntBetweenTwoNumbers(1, minefield.getMineField().length);
    minefield.setFlag(x-1,y-1);
}
    private static void openSelectedCell() {

        System.out.println("\nenter coordinates of desired cell: \ncolumn: ");
        int x = inputValidator.getValidIntBetweenTwoNumbers(1, minefield.getMineField()[0].length);
        System.out.println("row: ");
        int y = inputValidator.getValidIntBetweenTwoNumbers(1, minefield.getMineField().length);
        minefield.checkCell(x-1,y-1);
    }

//    private static boolean checkIfUserWantsCustomGame() {
//        System.out.println("Welcome to Minesweeper!\nWould you like to set the grid size and number of bombs?\n" +
//                "'y' to create your own grid, 'n' to choose from pre-set difficulty.");
//        return inputValidator.checkForValidChoice("y", "n");
//    }

    private static void getUserSelection() {
        System.out.println("\nSelect how many rows, columns, and bombs would like: ");
        System.out.print("Row: ");
        int rowSelected = inputValidator.getValidPositiveInt();
        System.out.print("Column: ");
        int colSelected = inputValidator.getValidPositiveInt();
        System.out.print("Bombs: ");
        int bombs = inputValidator.getValidPositiveInt();
        //TODO add a check so that the bombs cant exceed the number of cells
        minefield = new Minefield(bombs, rowSelected, colSelected);

    }

    private static int getDifficultyToPlay() {
        System.out.println("Choose the level of difficulty to play: ");
        System.out.print("Beginner - 1\nIntermediate - 2\nExpert - 3\nCustom - 4\nChoose 1, 2, 3 or 4: ");
        int menuChoice = inputValidator.getValidIntBetweenTwoNumbers(1,4);
        return menuChoice;
    }

    private static void setUpMineField(int menuChoice){
        switch (menuChoice){
            case 1:
                minefield = new Minefield(10,9,9);
                break;
            case 2:
                minefield = new Minefield(40,16,16);
                break;
            case 3:
                minefield = new Minefield(99,16,30);
                break;
            case 4:
                getUserSelection();
            default:
                break;
        }
    }

}
