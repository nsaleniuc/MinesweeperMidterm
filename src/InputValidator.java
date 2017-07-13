import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class InputValidator {
    private boolean userCont;
    private int userInt;
    private double userDouble;
    private Scanner input = new Scanner(System.in);

    public int getValidIntBetweenTwoNumbers(int minNum, int maxNum) {
        do {
            //System.out.print("\nEnter an integer that's greater than "+minNum+" but less than "+maxNum+": ");
            getValidInt();
            checkForIntBetweenTwoNumbers(minNum, maxNum);
        } while (userInt < minNum && userInt < maxNum);
        return userInt;
    }

    public int getValidInt() {
        boolean validInt = false;
        do {
            if (input.hasNextInt()) {
                userInt = input.nextInt();
                validInt = true;
            } else {
                System.out.println("not an integer! Please enter an integer: ");
            }
            input.nextLine();
        }while (!validInt);
        return userInt;
    }

    public double getValidDouble(String errorMessage){
        boolean validInt = false;
        do {
            if (input.hasNextInt() || input.hasNextDouble()) {
                userDouble = input.nextDouble();
                validInt = true;
            } else {
                System.out.println(errorMessage);
            }
            input.nextLine();
        }while (!validInt);
        return userDouble;
    }

    public int getValidPositiveInt() {
        boolean validInt = false;
        do {
            if (input.hasNextInt()) {
                userInt = input.nextInt();
                validInt = true;
                if(userInt < 0){
                    System.out.println("Not a positive integer! Please enter a positive integer: ");
                    validInt = false;
                }
            } else {
                System.out.println("not an integer! Please enter an integer: ");
            }
            input.nextLine();
        }while (!validInt);
        return userInt;
    }

    private void checkForIntBetweenTwoNumbers(int minNumber, int maxNumber) {
        while (userInt < minNumber || userInt > maxNumber) {
            System.out.println("Not between "+ minNumber+" and "+maxNumber+"! Please enter an integer: ");
            getValidInt();
        }
    }

    public void checkForUserContinue(String message) {
        boolean validChoice = true;
        do {
            System.out.println(message);
            if (input.hasNextLine()) {
                String answer = input.next();
                validChoice = checkForValidChoice(answer);
            }
        } while (!validChoice);
        input.nextLine();
    }

    private boolean checkForValidChoice(String answer){
        boolean validChoice;
        if (answer.equalsIgnoreCase("y")) {
            userCont = true;
            validChoice = true;
        } else if (answer.equalsIgnoreCase("n")) {
            userCont = false;
            validChoice = true;
        } else {
            System.out.print("\nnot a valid choice! ");
            validChoice = false;
        }
        return validChoice;
    }

    public boolean checkForValidChoice(String answer, String choice1, String choice2){
        boolean validChoice;
        do {
            if (answer.equalsIgnoreCase(choice1)) {
                userCont = true;
                validChoice = true;
            } else if (answer.equalsIgnoreCase(choice2)) {
                userCont = false;
                validChoice = true;
            } else {
                System.out.print("\nnot a valid choice! please enter \"" + choice1 + "\" or \"" + choice2 + "\": ");
                validChoice = false;
                answer = input.nextLine();
            }
        }while (!validChoice);
        return userCont;
    }

    public String checkForValidChoice(String choice1, String choice2){
        boolean validChoice;
        String answer = input.nextLine();
        do {
            if (answer.equalsIgnoreCase(choice1)) {
                userCont = true;
                validChoice = true;
            } else if (answer.equalsIgnoreCase(choice2)) {
                userCont = false;
                validChoice = true;
            } else {
                System.out.print("\nnot a valid choice! please enter \"" + choice1 + "\" or \"" + choice2 + "\": ");
                validChoice = false;
                answer = input.nextLine();
            }
        }while (!validChoice);
        return answer;
    }

    public String getNonEmptyString(String prompt){
        String userInput;
        boolean validInput;
        do{
            userInput = input.nextLine();
            if (userInput.isEmpty()){
                System.out.println(prompt);
                validInput = false;
            }else {
                validInput = true;
            }
        }while (!validInput);
        return userInput;
    }

    public boolean isUserCont() {
        return userCont;
    }

//    public boolean checkForDuplicateCountry(String country, String pathName){
//        String[] countries;
//        boolean isDuplicate = false;
//        try {
//            countries = Files.readAllLines(new File(pathName).toPath()).toArray(new String[0]);
//            for (String country1 : countries){
//                if (country1.equalsIgnoreCase(country)){
//                    isDuplicate = true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return isDuplicate;
//    }

}
