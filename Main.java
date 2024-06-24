import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        // Create a Scanner object
        Scanner myObj = new Scanner(System.in);
        System.out.println("Choose difficulty:");
        System.out.println("Easy: Rows: 5 Columns: 5 Mines: 5 Flags: 5\n" +
                "Medium: Rows: 9 Columns: 9 Mines: 12 Flags: 12\n" +
                "Hard: Rows: 20 Columns: 20 Mines: 40 Flags: 40");
        System.out.print("Pick: ");
        // Read user input
        String option = myObj.nextLine();
        option = option.toUpperCase();
        // change to uppercase
        if (option.equals("EASY")) {
            // makes a 5x5 board with 5 mines and flags and booleans to check if mines were made and if debug mode is on
            Minefield game = new Minefield(5, 5, 5);
            boolean minesCreated = false;
            boolean debugMode = false;
            // loop until user guesses mine
            while (game.gameOver() == false) {
                int r;
                int c;
                String s;
                // creates mines and sees if user wants debug mode
                if (minesCreated == false) {
                    System.out.println("Would you like debug mode? YES or NO");
                    s = myObj.nextLine();
                    s = s.toUpperCase();
                    if (s.equals("YES")) {
                        debugMode = true;
                    }
                    if (debugMode == true){
                        game.printMinefield();
                        System.out.println("");
                    }
                    // take in user starting input
                    System.out.println(game);
                    System.out.print("Input row:");
                    r = myObj.nextInt();
                    System.out.print("Input column:");
                    c = myObj.nextInt();
                    // makes mines and reveals enough for the user
                    game.createMines(r, c, 5);
                    game.evaluateField();
                    game.revealMines(r, c);
                    minesCreated = true;
                }
                // print board if debug mode is on
                if (debugMode == true){
                    game.printMinefield();
                    System.out.println("");
                }
                // print board and updates any user guesses or flags
                System.out.println(game);
                System.out.print("Input row:");
                r = myObj.nextInt();
                System.out.print("Input column:");
                c = myObj.nextInt();
                System.out.println("Do you want to flag or guess?");
                myObj.nextLine();
                s = myObj.nextLine();
                s = s.toUpperCase();
                if (s.equals("FLAG")) {
                    game.guess(r, c, true);
                }
                if (s.equals("GUESS")) {
                    boolean result = game.guess(r, c, false);
                    if (result == true) {
                        System.out.println("You guessed a mine! Game over.");
                        game.printMinefield();
                        break;
                    }
                }
            }
        }
        if (option.equals("MEDIUM")) {
            // makes a 9x9 board with 12 mines and flags and booleans to check if mines were made and if debug mode is on
            Minefield game = new Minefield(9, 9, 12);
            boolean minesCreated = false;
            boolean debugMode = false;
            // loop until user guesses mine
            while (game.gameOver() == false) {
                int r;
                int c;
                String s;
                // creates mines and sees if user wants debug mode
                if (minesCreated == false) {
                    System.out.println("Would you like debug mode? YES or NO");
                    s = myObj.nextLine();
                    s = s.toUpperCase();
                    if (s.equals("YES")) {
                        debugMode = true;
                    }
                    if (debugMode == true){
                        game.printMinefield();
                        System.out.println("");
                    }
                    // take in user starting input
                    System.out.println(game);
                    System.out.print("Input row:");
                    r = myObj.nextInt();
                    System.out.print("Input column:");
                    c = myObj.nextInt();
                    // makes mines and reveals enough for the user
                    game.createMines(r, c, 12);
                    game.evaluateField();
                    game.revealMines(r, c);
                    minesCreated = true;
                }
                // print board if debug mode is on
                if (debugMode == true){
                    game.printMinefield();
                    System.out.println("");
                }
                // print board and updates any user guesses or flags
                System.out.println(game);
                System.out.print("Input row:");
                r = myObj.nextInt();
                System.out.print("Input column:");
                c = myObj.nextInt();
                System.out.println("Do you want to flag or guess?");
                myObj.nextLine();
                s = myObj.nextLine();
                s = s.toUpperCase();
                if (s.equals("FLAG")) {
                    game.guess(r, c, true);
                }
                if (s.equals("GUESS")) {
                    boolean result = game.guess(r, c, false);
                    if (result == true) {
                        System.out.println("You guessed a mine! Game over.");
                        game.printMinefield();
                        break;
                    }
                }
            }
        }
        if (option.equals("HARD")) {
            // makes a 20x20 board with 40 mines and flags and booleans to check if mines were made and if debug mode is on
            Minefield game = new Minefield(20, 20, 40);
            boolean minesCreated = false;
            boolean debugMode = false;
            // loop until user guesses mine
            while (game.gameOver() == false) {
                int r;
                int c;
                String s;
                // creates mines and sees if user wants debug mode
                if (minesCreated == false) {
                    System.out.println("Would you like debug mode? YES or NO");
                    s = myObj.nextLine();
                    s = s.toUpperCase();
                    if (s.equals("YES")) {
                        debugMode = true;
                    }
                    if (debugMode == true){
                        game.printMinefield();
                        System.out.println("");
                    }
                    // take in user starting input
                    System.out.println(game);
                    System.out.print("Input row:");
                    r = myObj.nextInt();
                    System.out.print("Input column:");
                    c = myObj.nextInt();
                    // makes mines and reveals enough for the user
                    game.createMines(r, c, 40);
                    game.evaluateField();
                    game.revealMines(r, c);
                    minesCreated = true;
                }
                // print board if debug mode is on
                if (debugMode == true){
                    game.printMinefield();
                    System.out.println("");
                }
                // print board and updates any user guesses or flags
                System.out.println(game);
                System.out.print("Input row:");
                r = myObj.nextInt();
                System.out.print("Input column:");
                c = myObj.nextInt();
                System.out.println("Do you want to flag or guess?");
                myObj.nextLine();
                s = myObj.nextLine();
                s = s.toUpperCase();
                if (s.equals("FLAG")) {
                    game.guess(r, c, true);
                }
                if (s.equals("GUESS")) {
                    boolean result = game.guess(r, c, false);
                    if (result == true) {
                        System.out.println("You guessed a mine! Game over.");
                        game.printMinefield();
                        break;
                    }
                }
            }
        }
    }
}