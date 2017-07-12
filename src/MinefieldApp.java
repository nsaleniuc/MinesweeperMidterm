import java.util.Scanner;

/*
 */
public class MinefieldApp {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        getDifficultyToPlay(scan);

        getUserSelection(scan);

    }

    private static void getUserSelection(Scanner scan) {
        System.out.println("\nSelect which row and column you would like to reveal: ");
        System.out.print("Row: ");
        int rowSelected = scan.nextInt();
        System.out.print("Column: ");
        int colSelected = scan.nextInt();
    }

    private static void getDifficultyToPlay(Scanner scan) {
        System.out.println("Welcome to Minesweeper!\nChoose the level of difficulty to play: ");
        System.out.print("Beginner - 1\nIntermediate - 2\nExpert - 3\nChoose 1, 2, or 3: ");
        int menuChoice = scan.nextInt();
    }

}
