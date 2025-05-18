public class Sudoku {
    public static void main(String[] args) {
        // Initial Sudoku board with 0 representing empty cells
        int[][] board = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        // Solve the Sudoku puzzle
        if (solveSudoku(board)) {
            System.out.println("Solved Sudoku:");
            printBoard(board);
        } else {
            System.out.println("No solution exists.");
        }
    }

    // Function to solve Sudoku using backtracking
    public static boolean solveSudoku(int[][] board) {
        // Loop through each cell
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // Check for empty cell
                if (board[row][col] == 0) {
                    // Try placing numbers 1 through 9
                    for (int num = 1; num <= 9; num++) {
                        // Check if number is safe to place
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num; // Place number

                            // Recursively attempt to solve with new board state
                            if (solveSudoku(board)) {
                                return true;
                            }

                            // If failed, backtrack
                            board[row][col] = 0;
                        }
                    }
                    // If no valid number found, return false to trigger backtracking
                    return false;
                }
            }
        }
        // If all cells filled correctly, return true
        return true;
    }

    // Function to check if placing a number at board[row][col] is valid
    public static boolean isSafe(int[][] board, int row, int col, int num) {
        // Check if number is in the current row
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) return false;
        }

        // Check if number is in the current column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) return false;
        }

        // Check if number is in the 3x3 subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) return false;
            }
        }

        // If number is not found in row, col, or subgrid, it's safe to place
        return true;
    }

    // Utility function to print the Sudoku board
    public static void printBoard(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int d = 0; d < 9; d++) {
                System.out.print(board[r][d] + " ");
            }
            System.out.println();
        }
    }
}

/*This Java program solves a Sudoku puzzle using backtracking. 
It fills empty cells (marked as 0) by trying digits 1 to 9,
 checking if placing each digit is safe based on Sudoku rules
  (no duplicates in the row, column, or 3×3 grid). If a number 
  leads to a dead end, it backtracks and tries another. Once 
  the board is filled correctly, it prints the solution.
 */
