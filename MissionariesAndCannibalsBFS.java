import java.util.*;
import java.util.LinkedList;

public class MissionariesAndCannibalsBFS {

    // State class represents a configuration of missionaries, cannibals, and boat
    static class State {
        int M_left, C_left, boat, M_right, C_right;
        State parent; // to trace the path

        public State(int M_left, int C_left, int boat, int M_right, int C_right, State parent) {
            this.M_left = M_left;
            this.C_left = C_left;
            this.boat = boat; // 1 = left side, 0 = right side
            this.M_right = M_right;
            this.C_right = C_right;
            this.parent = parent;
        }

        // To compare states uniquely
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

        // For using in HashSet
        @Override
        public int hashCode() {
            return Objects.hash(M_left, C_left, boat, M_right, C_right);
        }

        // Valid state = missionaries never outnumbered
        boolean isValid() {
            if ((M_left < C_left && M_left > 0) || (M_right < C_right && M_right > 0)) {
                return false;
            }
            return M_left >= 0 && C_left >= 0 && M_right >= 0 && C_right >= 0;
        }

        // Goal state = all on right side
        boolean isGoal() {
            return M_left == 0 && C_left == 0 && boat == 0 && M_right == 3 && C_right == 3;
        }

        String getStateString() {
            return "(" + M_left + "M, " + C_left + "C, Boat: " + (boat == 1 ? "Left" : "Right") + ")";
        }

        String getFullStateString() {
            return "(" + M_left + ", " + C_left + ", " + boat + ", " + M_right + ", " + C_right + ")";
        }
    }

    // Possible moves: {missionaries, cannibals}
    private static final int[][] MOVES = {
            {1, 0}, // 1 Missionary
            {2, 0}, // 2 Missionaries
            {0, 1}, // 1 Cannibal
            {0, 2}, // 2 Cannibals
            {1, 1}  // 1 Missionary and 1 Cannibal
    };

    public static void solve() {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        // Initial state: all on left side, boat on left
        State initialState = new State(3, 3, 1, 0, 0, null);
        queue.add(initialState);
        visited.add(initialState);

        int step = 0;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            step++;

            System.out.println("\nStep " + step + " - Checking state: " + current.getFullStateString());

            // Check if goal is reached
            if (current.isGoal()) {
                printSolutionPath(current);
                return;
            }

            // Try all valid moves from the current state
            for (int[] move : MOVES) {
                State newState = getNextState(current, move);
                if (newState != null) {
                    if (!newState.isValid()) {
                        System.out.println("  Invalid state: " + newState.getFullStateString());
                    } else if (!visited.contains(newState)) {
                        queue.add(newState);
                        visited.add(newState);
                        System.out.println("  Valid & Visited state: " + newState.getFullStateString());
                    }
                }
            }
        }

        System.out.println("No solution found!");
    }

    // Generate the next state based on the move and current boat position
    private static State getNextState(State current, int[] move) {
        int M = move[0], C = move[1];

        // Boat on left side: move from left to right
        if (current.boat == 1) {
            return new State(
                    current.M_left - M, current.C_left - C, 0, // boat moves to right
                    current.M_right + M, current.C_right + C, current
            );
        } else { // Boat on right side: move from right to left
            return new State(
                    current.M_left + M, current.C_left + C, 1, // boat moves to left
                    current.M_right - M, current.C_right - C, current
            );
        }
    }

    // Trace and print the path from initial to goal state
    private static void printSolutionPath(State goalState) {
        List<State> path = new ArrayList<>();
        State current = goalState;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);

        System.out.println("\n Solution Path Found in " + (path.size() - 1) + " moves:");
        int moveNum = 0;
        for (State state : path) {
            System.out.println(" Move " + moveNum + ": " + state.getFullStateString());
            moveNum++;
        }
    }

    // Main method
    public static void main(String[] args) {
        solve();
    }
}


/* This Java program solves the classic Missionaries and Cannibals problem 
using Breadth-First Search (BFS). The problem involves safely transferring 
3 missionaries and 3 cannibals from the left bank to the right bank of a 
river using a boat that can carry at most two people. The constraint is that 
at no point should the number of cannibals exceed the number of missionaries 
on either bank (if missionaries are present), or the missionaries may be eaten
. The program represents each state with the number of missionaries, cannibals,
and the boat's position. It explores all valid moves using BFS until it finds a 
solution and then prints the sequence of steps leading to the goal.*/
