import java.util.Random;

public class Minefield {
    /**
    Global Section
    */
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_GREY_BG = "\u001b[0m";

    // instance variables
    Cell[][] arr;
    int rows;
    int columns;
    int flags;

    // constructor that takes in number of rows, columns, and flags
    public Minefield(int rows, int columns, int flags) {
        // set the instance variables
        this.rows = rows;
        this.columns = columns;
        this.flags = flags;
        // set the board
        arr = new Cell[rows][columns];
        // loop through the board and set each cell to be blank
        for (int i = 0; i < arr.length; i++){
            for (int j = 0; j < arr.length; j++){
                arr[i][j] = new Cell(false, "-");
                arr[i][j].setRevealed(false);
            }
        }
    }

    // when a mine is found calculate the surrounding 3x3 tiles
    public void evaluateField() {
        // loop through the board
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                // if the current cell is a mine call helper method
                if (!arr[i][j].getStatus().equals("M")) {
                    // change the status of the cell
                    arr[i][j].setStatus(String.valueOf(countAdjacentMines(i, j)));
                }
            }
        }
    }

    // helper method for evaluateField()
    private int countAdjacentMines(int row, int col) {
        // count for the cell around the mine
        int count = 0;
        // checks the 3x3 surrounding mine
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                // checks if the current cell is in bounds and if it contains a mine
                if (i >= 0 && i < arr.length && j >= 0 && j < arr.length && arr[i][j].getStatus().equals("M")) {
                    count++;
                }
            }
        }
        // returns the count
        return count;
    }

    // creates mines on the board
    public void createMines(int x, int y, int mines) {
        // import random and a counter for number of mines
        Random random = new Random();
        int mineCounter = 0;
        // loop to keep adding mines until it reaches mines
        while (mineCounter != mines){
            // random cords
            int rowMine = random.nextInt(rows);
            int colMine = random.nextInt(columns);
            // checks if it is revealed, is a mine, and if it isn't the starting cords
            if (!arr[rowMine][colMine].getRevealed() && !arr[rowMine][colMine].getStatus().equals("M") && !(rowMine == x && colMine == y)){
                // change status
                arr[rowMine][colMine].setStatus("M");
                mineCounter++;
            }
        }
    }

    // helper method to check if cords are in bounds
    private boolean inBounds(int x, int y) {
        return (0 <= x && x < rows) && ((0 <= y && y < columns));
    }

    // guess or flag cells on the board
    public boolean guess(int x, int y, boolean flag) {
        // checks if it is in bounds
        if (inBounds(x, y)) {
            // if the user wants to place a flag
            if (flag) {
                // checks if there are enough flags to be placed
                if (flags > 0) {
                    flags--;
                    arr[x][y].setStatus("F");
                } else {
                    System.out.println("You have no more flags, good luck");
                }
                // if the user wants to guess
            } else {
                // if guess is a 0 cell reveal and surrounding 0's
                if (arr[x][y].getStatus().equals("0")) {
                    revealZeroes(x, y);
                }
                // if guess is a mine end game
                if (arr[x][y].getStatus().equals("M")) {
                    gameOver();
                    arr[x][y].setRevealed(true);
                    return true; // return true it has hit a mine to end the game
                }
                // reveal guessed cell
                arr[x][y].setRevealed(true);
            }
        }
        return false; // guess was successful
    }

    // game over if the user guesses a mine
    public boolean gameOver() {
        // loop through the board
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[0].length; col++) {
                // checks if there are ant remaining cells left to reveal and if there are any mines left
                if (!arr[row][col].getRevealed() && !arr[row][col].getStatus().equals("M")) {
                    return false;
                }
            }
        }
        // return true that the game is over
        return true;
    }

    // reveal 0's if user guesses a cell holding 0
    public void revealZeroes(int x, int y) {
        // create a stack
        Stack1Gen stack = new Stack1Gen();
        // add the first cell to the stack
        int[] pair = {x,y};
        stack.push(pair);
        // loops until stack is empty
        while (!stack.isEmpty()){
            // take off the first pair of cords and reveal it to the user
            int[] array = (int[]) stack.pop();
            int row = array[0];
            int col = array[1];
            arr[row][col].setRevealed(true);
            // add the cords of the top to the stack if it is 0
            if (row > 0 && !arr[row-1][col].getRevealed() && arr[row-1][col].getStatus().equals("0")){
                int[] newPair = {row-1, col};
                stack.push(newPair);
            }
            // add the cords of the below to the stack if it is 0
            if (row < arr.length-1 && !arr[row+1][col].getRevealed() && arr[row+1][col].getStatus().equals("0")){
                int[] newPair = {row+1, col};
                stack.push(newPair);
            }
            // add the cords of the left to the stack if it is 0
            if (col > 0 && !arr[row][col-1].getRevealed() && arr[row][col-1].getStatus().equals("0")){
                int[] newPair = {row, col-1};
                stack.push(newPair);
            }
            // add the cords of the right to the stack if it is 0
            if (col < arr[0].length-1 && !arr[row][col+1].getRevealed() && arr[row][col+1].getStatus().equals("0")){
                int[] newPair = {row, col+1};
                stack.push(newPair);
            }
        }
    }

    // reveal enough information for the user to get started at the beginning
    public void revealMines(int x, int y) {
        // create a queue and adds the first cells cords to it
        Q1Gen queue = new Q1Gen();
        int[] pair = {x,y};
        queue.add(pair);
        // loop until queue is empty and reveals the cells until it hits a mine
        while (queue.length() != 0){
            int[] array = (int[]) queue.remove();
            int row = array[0];
            int col = array[1];
            arr[row][col].setRevealed(true);
            if (arr[row][col].getStatus().equals("M")){
                break;
            }
            // adds the top cords to the queue
            if (row-1 >= 0 && row-1 < arr.length && !arr[row-1][col].getRevealed()){
                int[] newPair = {row-1, col};
                queue.add(newPair);
            }
            // adds the below cords to the queue
            if (row+1 >= 0 && row+1 < arr.length && !arr[row+1][col].getRevealed()){
                int[] newPair = {row+1, col};
                queue.add(newPair);
            }
            // adds the left cords to the queue
            if (col-1 >= 0 && col-1 < arr[0].length && !arr[row][col-1].getRevealed()){
                int[] newPair = {row, col-1};
                queue.add(newPair);
            }
            // adds the right cords to the queue
            if (col+1 >= 0 && col+1 < arr[0].length && !arr[row][col+1].getRevealed()){
                int[] newPair = {row, col+1};
                queue.add(newPair);
            }
        }
    }



//    /**
//     * revealStart
//     *
//     * @param x       The x value the user entered.
//     * @param y       The y value the user entered.
//     */
//    public void revealStart(int x, int y) {
//        arr[x][y].setRevealed(true);
//    }

    // This method prints the entire minefield regardless of the cell has been revealed yet
    public void printMinefield() {
        // Print the top row of column numbers
        System.out.print("   ");
        for (int i = 0; i < arr.length; i++) {
            if (i < 10){
                System.out.print("\033[0;37m" + i + "   ");
            } else {
                System.out.print("\033[0;37m" + i + "  ");
            }
        }
        System.out.print("\n");

        // print the rows of cells
        for (int i = 0; i < arr.length; i++) {
            // print the leftmost column of row numbers
            if (i < 10){
                System.out.print("\033[0;37m" + i + "  ");
            } else{
                System.out.print("\033[0;37m" + i + " ");
            }
            for (int j = 0; j < arr[i].length; j++) {
                // set the color for the cell based on its status
                String color = "\033[0;37m";
                if (arr[i][j].getStatus().equals("F")){
                    color = "\u001B[33m"; // yellow
                } else if (arr[i][j].getStatus().equals("M") || (arr[i][j].getStatus().equals("-"))){
                    color = "\u001b[31;1m"; // red
                } else {
                    int colorNum = Integer.parseInt(arr[i][j].getStatus());
                    switch (colorNum) {
                        case 0:
                            color = "\033[1;34m"; // blue
                            break;
                        case 1:
                            color = "\u001b[32m"; // green
                            break;
                        case 2:
                            color = "\u001B[35m"; // magenta
                            break;
                        case 3:
                            color = "\u001B[36m"; // cyan
                            break;
                        case 4:
                            color = "\u001b[34;1m"; // blue
                            break;
                        case 5:
                            color = "\033[1;32m"; //  green
                            break;
                        case 6:
                            color = "\033[1;34m"; // blue
                            break;
                        case 7:
                            color = "\033[1;35m"; // purple
                            break;
                        case 8:
                            color = "\033[1;36m"; // teal
                            break;
                        case 9:
                            color = "\033[1;32m"; // bright green
                            break;
                        default:
                            color = "\033[0;37m"; // default color
                            break;
                    }
                }
                // print the cell with the correct color
                System.out.print(color + arr[i][j].getStatus() + "   ");
            }
            // print a newline after each row
            System.out.print("\n");
        }
    }

    // the main board with the hidden cells
    public String toString() {
        String s = "";
        s += "   ";

        // print column numbers
        for (int i = 0; i < arr.length; i++) {
            if (i < 10) {
                s += "\033[0;37m" + i + "   ";
            } else {
                s += "\033[0;37m" + i + "  ";
            }
        }
        s += "\n";

        // print each row
        for (int i = 0; i < arr.length; i++) {
            if (i < 10) {
                s += "\033[0;37m" + i + "  ";
            } else {
                s += "\033[0;37m" + i + " ";
            }

            // print each cell
            for (int j = 0; j < arr[i].length; j++) {
                String color = "\033[0;37m";
                // flagged cell
                if (arr[i][j].getStatus().equals("F")) {
                    color = "\u001B[33;1m";
                    s += color + arr[i][j].getStatus() + "   ";
                    // cover mine cell
                } else if (arr[i][j].getStatus().equals("M")) {
                    color = "\033[0;37m";
                    s += color + "-" + "   ";
                    // empty cell
                } else if ((arr[i][j].getStatus().equals("-"))){
                    s += "\033[0;37m" + arr[i][j].getStatus() + "   ";
                } else {
                    // numbered cell and checks if it has been revealed
                    if (arr[i][j].getRevealed()) {
                        int colorNum = Integer.parseInt(arr[i][j].getStatus());
                        // choose color based on number
                        switch (colorNum) {
                            case 0:
                                color = "\033[1;34m";
                                break;
                            case 1:
                                color = "\u001b[32m";
                                break;
                            case 2:
                                color = "\u001B[35m";
                                break;
                            case 3:
                                color = "\u001B[36m";
                                break;
                            case 4:
                                color = "\u001b[34;1m";
                                break;
                            case 5:
                                color = "\033[1;32m";
                                break;
                            case 6:
                                color = "\033[1;34m";
                                break;
                            case 7:
                                color = "\033[1;35m";
                                break;
                            case 8:
                                color = "\033[1;36m";
                                break;
                            case 9:
                                color = "\033[1;32m";
                                break;
                            default:
                                color = "\033[0;37m";
                                break;
                        }
                        // add to the string
                        s += color + arr[i][j].getStatus() + "   ";
                    } else {
                        // cell has not been revealed
                        s += color + "-" + "   ";
                    }
                }
            }
            s += "\n";
        }
        return s;
    }
}
