import java.util.Scanner;

public class NQueens {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for the size of the chessboard
        System.out.print("Enter the size of the board (N for N x N): ");
        int N = scanner.nextInt();

        solveNQueens(N);
    }

    // Function to solve the N-Queens problem
    public static void solveNQueens(int n) {
        int[][] board = new int[n][n]; // Create an NxN chessboard

        // Try to place queens
        if (placeQueens(board, 0, n)) {
            printBoard(board, n);
        } else {
            System.out.println("No solution exists for " + n + " queens.");
        }
    }

    // Recursive function to place queens column by column
    public static boolean placeQueens(int[][] board, int col, int n) {
        // Base case: all queens are placed
        if (col >= n) {
            return true;
        }

        // Try placing the queen in all rows of the current column
        for (int row = 0; row < n; row++) {
            if (isSafe(board, row, col, n)) {
                board[row][col] = 1; // Place queen

                // Recur to place the rest of the queens
                if (placeQueens(board, col + 1, n)) {
                    return true;
                }

                // Backtrack: remove queen
                board[row][col] = 0;
            }
        }

        // If queen can't be placed in any row of this column
        return false;
    }

    // Function to check if it's safe to place a queen at board[row][col]
    public static boolean isSafe(int[][] board, int row, int col, int n) {
        // Check row on left side
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) return false;
        }

        // Check upper diagonal on left side
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }

        // Check lower diagonal on left side
        for (int i = row, j = col; i < n && j >= 0; i++, j--) {
            if (board[i][j] == 1) return false;
        }

        return true; // Safe to place queen
    }

    // Function to print the board
    public static void printBoard(int[][] board, int n) {
        System.out.println("Solution for " + n + "-Queens:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print((board[i][j] == 1 ? "Q " : ". "));
            }
            System.out.println();
        }
    }
}

/*This Java program solves the N-Queens problem using backtracking.
 It asks the user for the board size N and then tries to place N 
 queens on an N×N chessboard such that no two queens attack each 
 other (i.e., no two are in the same row, column, or diagonal).
  It recursively places queens column by column, checks if each 
  position is safe, and backtracks if needed. If a solution is 
  found, it prints the board; otherwise, it states that no solution exists. */
