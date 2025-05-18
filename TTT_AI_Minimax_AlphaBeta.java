package AI;

import java.util.*;

public class TTT_AI_Minimax_AlphaBeta {

    // Constants representing players and empty cells
    static final char HUMAN = 'O';
    static final char AI = 'X';
    static final char EMPTY = ' ';
    static final int SIZE = 3;

    public static void main(String[] args) {
        // Initialize an empty 3x3 board
        char[][] board = {
                { ' ', ' ', ' ' },
                { ' ', ' ', ' ' },
                { ' ', ' ', ' ' }
        };
        Scanner sc = new Scanner(System.in);
        printBoard(board);

        while (true) {
            // Human move input
            System.out.print("Enter your move (row and col): ");
            int row = sc.nextInt();
            int col = sc.nextInt();

            // Check if move is valid
            if (board[row][col] != EMPTY) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            // Make human move
            board[row][col] = HUMAN;
            printBoard(board);

            // Check if the game is over
            if (isGameOver(board)) break;

            // AI move using Minimax algorithm
            int[] move = findBestMove(board);
            board[move[0]][move[1]] = AI;
            System.out.println("AI moves to " + move[0] + ", " + move[1]);
            printBoard(board);

            // Check again after AI move
            if (isGameOver(board)) break;
        }
        sc.close();
    }

    // Display the current state of the board
    static void printBoard(char[][] board) {
        System.out.println();
        for (char[] row : board) {
            for (char c : row)
                System.out.print(c + " | ");
            System.out.println("\b\b\b");
            System.out.println("---------");
        }
    }

    // Determine if the game has ended (win or draw)
    static boolean isGameOver(char[][] board) {
        if (checkWin(board, HUMAN)) {
            System.out.println("You win!");
            return true;
        } else if (checkWin(board, AI)) {
            System.out.println("AI wins!");
            return true;
        } else if (isDraw(board)) {
            System.out.println("It's a draw!");
            return true;
        }
        return false;
    }

    // Check if a given player has won
    static boolean checkWin(char[][] b, char player) {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            if (b[i][0] == player && b[i][1] == player && b[i][2] == player) return true;
            if (b[0][i] == player && b[1][i] == player && b[2][i] == player) return true;
        }
        // Check diagonals
        if (b[0][0] == player && b[1][1] == player && b[2][2] == player) return true;
        if (b[0][2] == player && b[1][1] == player && b[2][0] == player) return true;
        return false;
    }

    // Check if the board is full and no winner — a draw
    static boolean isDraw(char[][] board) {
        for (char[] row : board)
            for (char cell : row)
                if (cell == EMPTY)
                    return false;
        return true;
    }

    // Find the best move for AI using the Minimax algorithm with alpha-beta pruning
    static int[] findBestMove(char[][] board) {
        int bestScore = Integer.MIN_VALUE;
        List<int[]> bestMoves = new ArrayList<>();

        // Try all possible moves
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    // Try the move
                    board[i][j] = AI;
                    // Calculate the score using minimax
                    int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    // Undo the move
                    board[i][j] = EMPTY;

                    // Track the best score and corresponding moves
                    if (score > bestScore) {
                        bestScore = score;
                        bestMoves.clear();
                        bestMoves.add(new int[] { i, j });
                    } else if (score == bestScore) {
                        bestMoves.add(new int[] { i, j });
                    }
                }
            }
        }

        // Choose a random move among the best (to add variety)
        return bestMoves.get(new Random().nextInt(bestMoves.size()));
    }

    // Minimax algorithm with alpha-beta pruning to evaluate game outcomes
    static int minimax(char[][] board, int depth, boolean isMaximizing, int alpha, int beta) {
        // Base cases
        if (checkWin(board, AI)) return 10 - depth; // Prefer quicker wins
        if (checkWin(board, HUMAN)) return depth - 10; // Prefer slower losses
        if (isDraw(board)) return 0;

        if (isMaximizing) {
            // Maximize the AI's score
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = AI;
                        int eval = minimax(board, depth + 1, false, alpha, beta);
                        board[i][j] = EMPTY;
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) return maxEval; // Beta cut-off
                    }
                }
            }
            return maxEval;
        } else {
            // Minimize the human's score
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = HUMAN;
                        int eval = minimax(board, depth + 1, true, alpha, beta);
                        board[i][j] = EMPTY;
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) return minEval; // Alpha cut-off
                    }
                }
            }
            return minEval;
        }
    }
}

/*This Java program implements a Tic Tac Toe game where a human plays against an AI.
 The game board is a 3x3 grid, and the players take turns placing their symbols 
 ('O' for the human and 'X' for the AI). The human inputs their move via the console,
  and the AI calculates its optimal move using the Minimax algorithm with alpha-beta 
  pruning to evaluate all possible game states and choose the best outcome. After each
   move, the program checks for a win or draw and ends the game if a result is found. 
   The game continues in a loop until a winner is declared or the board is full. */