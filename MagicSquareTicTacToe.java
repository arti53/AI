import java.util.*;

public class MagicSquareTicTacToe {
    static final int[] magicSquare = {
        8, 1, 6,
        3, 5, 7,
        4, 9, 2
    };

    static int[] board = new int[9];  // 0 = blank, 3 = X, 5 = O
    static int turn = 1;              // 1 to 9

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Magic Square Tic Tac Toe (AI-style)");
        printBoard();

        while (turn <= 9) {
            if (turn % 2 == 1) {
                // X (User)
                System.out.print("Enter move (1-9) for X: ");
                int pos = sc.nextInt() - 1;
                if (pos >= 0 && pos < 9 && board[pos] == 0) {
                    GO(pos, 3);  // X = 3
                    printBoard();
                    if (hasWon(3)) {
                        System.out.println("X wins!");
                        return;
                    }
                    turn++;
                } else {
                    System.out.println("Invalid move!");
                }
            } else {
                // O (AI)
                int move = AI_O_Move();
                GO(move, 5); // O = 5
                System.out.println("O plays at position: " + (move + 1));
                printBoard();
                if (hasWon(5)) {
                    System.out.println("O wins!");
                    return;
                }
                turn++;
            }
        }

        System.out.println("Game Draw!");
    }

    // GO(n): Places mark based on player
    static void GO(int n, int player) {
        board[n] = player;
    }

    // MAKE2: If center is blank, pick it; else pick a side
    static int MAKE2() {
        if (board[4] == 0) return 4; // center
        int[] sides = {1, 3, 5, 7}; // side positions
        for (int s : sides) {
            if (board[s] == 0) return s;
        }
        return -1;
    }

    // POSSWIN(p): Return winning move index for player p (3 for X, 5 for O)
    static int POSSWIN(int player) {
        for (int i = 0; i < 9; i++) {
            if (board[i] != player) continue;
            for (int j = i + 1; j < 9; j++) {
                if (board[j] != player) continue;
                int required = 15 - (magicSquare[i] + magicSquare[j]);
                for (int k = 0; k < 9; k++) {
                    if (board[k] == 0 && magicSquare[k] == required) {
                        return k;
                    }
                }
            }
        }
        return -1;
    }

    // hasWon(p): Check if player has won using magic square rule
    static boolean hasWon(int player) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board[i] == player) {
                moves.add(magicSquare[i]);
            }
        }

        for (int i = 0; i < moves.size(); i++) {
            for (int j = i + 1; j < moves.size(); j++) {
                for (int k = j + 1; k < moves.size(); k++) {
                    if (moves.get(i) + moves.get(j) + moves.get(k) == 15) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // AI strategy for O (even turns)
    static int AI_O_Move() {
        int win = POSSWIN(5);
        if (win != -1) return win;  // Win if possible

        int block = POSSWIN(3);
        if (block != -1) return block; // Block X

        int make2 = MAKE2();
        if (make2 != -1) return make2;

        // Pick first available
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) return i;
        }
        return -1; // Should not happen
    }

    // Display board
    static void printBoard() {
        System.out.println("\nCurrent Board:");
        for (int i = 0; i < 9; i++) {
            char c = board[i] == 3 ? 'X' : board[i] == 5 ? 'O' : '.';
            System.out.print(c + " ");
            if (i % 3 == 2) System.out.println();
        }
        System.out.println();
    }
}
