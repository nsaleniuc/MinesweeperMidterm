
/*Created by Aaron Board, Steve Suzio, and Nathan Saleniuc 7/17/2017
 */
public class MinefieldApp {
    private static InputValidator inputValidator = new InputValidator();
    private static Minefield minefield;

    public static void main(String[] args) {

        playGame();

    }

    private static void playGame() {
        do {
            int menuChoice = getDifficultyToPlay();

            //Based on the menuChoice determines size of the array
            setUpMineField(menuChoice);

            //Prints the characters for the field based on size
            minefield.printMinefield();

            keepPlayingGame();

            inputValidator.checkForUserContinue("Would you like to play again? (y/n)");
        } while (inputValidator.isUserCont());
        System.out.println("Thanks for playing!");

    }

    //Until the game is lost or won this method will keeping looping through the game
    private static void keepPlayingGame() {
        do {
            openCellOrSetFlag();

            minefield.checkIfGameWon();

            //prints updated minefield
            minefield.printMinefield();

        } while (!minefield.isGameOver());
    }

    //Asks the user for input to select a cell or set a flag
    private static void openCellOrSetFlag() {
        System.out.println("Open a cell or set a flag? (F - Flag/Unflag O - Open)");
        String userSelection = inputValidator.checkForValidChoice("F", "O");
        if (userSelection.equalsIgnoreCase("O")) {
            openSelectedCell();
        } else if (userSelection.equalsIgnoreCase("F")) {
            setFlag();
        }
    }

    //If user wants to place a flag
    private static void setFlag() {
        System.out.println("Flag or unflag.\nRow: ");
        int x = inputValidator.getValidIntBetweenTwoNumbers(1, minefield.getMineField()[0].length);
        System.out.println("Column: ");
        int y = inputValidator.getValidIntBetweenTwoNumbers(1, minefield.getMineField().length);
        minefield.setFlag(x - 1, y - 1);
    }

    //(x,y) coordinates to open cell
    private static void openSelectedCell() {
        System.out.println("\nEnter coordinates of cell: \nRow: ");
        int y = inputValidator.getValidIntBetweenTwoNumbers(1, minefield.getMineField().length);
        System.out.println("Column: ");
        int x = inputValidator.getValidIntBetweenTwoNumbers(1, minefield.getMineField()[0].length);
        minefield.checkSelectedCell(x - 1, y - 1);
    }

    //Asks the user to create a gameboard for custom game
    private static void getUserSelection() {
        System.out.println("\nSelect how many rows, columns, and bombs would like (Grid must be 3 x 3 or greater): ");
        //Grid must be 3 x 3 to keep array checks in bounds
        System.out.print("Row: ");
        int rowSelected = inputValidator.getValidPositiveInt(3);
        System.out.print("Column: ");
        int colSelected = inputValidator.getValidPositiveInt(3);
        System.out.print("Bombs: ");
        int bombs = inputValidator.getValidIntBetweenTwoNumbers(1, ((rowSelected * colSelected) - 1));
        minefield = new Minefield(bombs, rowSelected, colSelected);

    }

    //gives input from setUpMineField
    private static int getDifficultyToPlay() {
        System.out.println("Choose the level of difficulty to play: ");
        System.out.print("Beginner - 1\nIntermediate - 2\nExpert - 3\nCustom - 4\nChoose 1, 2, 3 or 4: ");
        int menuChoice = inputValidator.getValidIntBetweenTwoNumbers(1, 4);
        return menuChoice;
    }

    //Choose beginner, intermediate, expert or custom grid, receives input from getDifficultyToPlay
    private static void setUpMineField(int menuChoice) {
        switch (menuChoice) {
            case 1:
                minefield = new Minefield(10, 9, 9);
                break;
            case 2:
                minefield = new Minefield(40, 16, 16);
                break;
            case 3:
                minefield = new Minefield(99, 16, 30);
                break;
            case 4:
                getUserSelection();
            default:
                break;
        }
    }

}
