import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

class MissionariesAndCannibalsDFS {

    // Inner class to represent a state in the problem
    static class State {
        int M_left, C_left, boat, M_right, C_right;
        State parent;

        // Constructor to initialize the state
        public State(int M_left, int C_left, int boat, int M_right, int C_right, State parent) {
            this.M_left = M_left;
            this.C_left = C_left;
            this.boat = boat;
            this.M_right = M_right;
            this.C_right = C_right;
            this.parent = parent;
        }

        // Override equals to compare states based on their values
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof State) {
                State other = (State) obj;
                return (this.M_left == other.M_left && this.C_left == other.C_left &&
                        this.boat == other.boat && this.M_right == other.M_right &&
                        this.C_right == other.C_right);
            }
            return false;
        }

        // Hash function for using state in hash-based collections
        @Override
        public int hashCode() {
            return Objects.hash(M_left, C_left, boat, M_right, C_right);
        }

        // String representation for debugging and printing
        @Override
        public String toString() {
            return "(" + M_left + ", " + C_left + ", " + boat + ", " + M_right + ", " + C_right + ")";
        }

        // Check if the current state is valid (i.e., missionaries are not outnumbered)
        boolean isValid() {
            return !((M_left < C_left && M_left > 0) || (M_right < C_right && M_right > 0)) &&
                    M_left >= 0 && C_left >= 0 && M_right >= 0 && C_right >= 0;
        }

        // Check if the goal state is reached
        boolean isGoal() {
            return M_left == 0 && C_left == 0 && boat == 0 && M_right == 3 && C_right == 3;
        }
    }

    // All possible moves of missionaries and cannibals in the boat
    private static final int[][] MOVES = {
            {1, 0},  // 1 missionary
            {2, 0},  // 2 missionaries
            {0, 1},  // 1 cannibal
            {0, 2},  // 2 cannibals
            {1, 1}   // 1 missionary and 1 cannibal
    };

    // Main function to solve the problem using DFS
    public static void solve() {
        Stack<State> stack = new Stack<>();          // DFS stack
        Set<State> visited = new HashSet<>();        // Track visited states

        // Initial state: all missionaries and cannibals on the left bank, boat also on the left
        State initialState = new State(3, 3, 1, 0, 0, null);
        stack.push(initialState);
        visited.add(initialState);

        System.out.println("Starting DFS to solve Missionaries and Cannibals Problem...\n");

        // DFS loop
        while (!stack.isEmpty()) {
            State current = stack.pop();

            System.out.println("Visited: " + current);

            // Check for goal state
            if (current.isGoal()) {
                System.out.println("\n Solution Found!");
                printSolutionPath(current);
                return;
            }

            // Try all possible valid moves from current state
            for (int[] move : MOVES) {
                State newState = getNextState(current, move);
                if (newState != null) {
                    System.out.println("→ Checking: " + newState);
                    if (!visited.contains(newState)) {
                        visited.add(newState);
                        if (newState.isValid()) {
                            System.out.println(" Valid state: " + newState);
                            stack.push(newState);  // Add valid and unvisited state to stack
                        } else {
                            System.out.println("Invalid state(dead-end): " + newState);
                        }
                    }
                }
            }

            System.out.println();
        }

        System.out.println("No solution found!");
    }

    // Generate the next state based on the move and current boat position
    private static State getNextState(State current, int[] move) {
        int M = move[0], C = move[1];

        // Boat on the left bank → move to right
        if (current.boat == 1) {
            return new State(
                    current.M_left - M, current.C_left - C, 0,
                    current.M_right + M, current.C_right + C, current
            );
        } else {  // Boat on the right bank → move to left
            return new State(
                    current.M_left + M, current.C_left + C, 1,
                    current.M_right - M, current.C_right - C, current
            );
        }
    }

    // Print the path from initial state to goal state
    private static void printSolutionPath(State goalState) {
        List<State> path = new ArrayList<>();
        State current = goalState;

        // Backtrack from goal to initial state using parent links
        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        // Reverse the path to show in order
        Collections.reverse(path);

        System.out.println("\n Solution Path:");
        for (int i = 0; i < path.size(); i++) {
            System.out.println("Step " + i + ": " + path.get(i));
        }
    }

    // Main method
    public static void main(String[] args) {
        solve();
    }
}

/*This Java program solves the classic Missionaries and Cannibals problem
using Depth-First Search (DFS). It explores all valid states by simulating boat 
movements across a river, transferring missionaries and cannibals while ensuring that
cannibals never outnumber missionaries on either bank. Each state is validated,
tracked to avoid revisits, and linked to its parent to reconstruct the solution path 
once the goal is reached. The program prints each visited state, highlights valid
transitions, and finally displays the step-by-step solution from the initial to the 
goal state.*/
