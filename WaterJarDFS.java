import java.util.*;
public class WaterJarDFS {
    static Set<String> visited = new HashSet<>();
    public static void main(String[] args) {
        int m = 4;
        int n = 3;
        int d = 2;
        System.out.println("Starting DFS...");
        if (solveWaterJugDFS(0, 0, m, n, d, "")) {
            System.out.println("Solution found!");
        } else {
            System.out.println("No solution exists.");
        }
    }

    public static boolean solveWaterJugDFS(int x, int y, int m, int n, int d, String path) {
        System.out.println("Exploring state: (" + x + ", " + y + ")");

        if (x == d || y == d) {
            System.out.println("Solution path: " + path + " -> (" + x + ", " + y + ")");
            return true;
        }

        visited.add(x + "," + y);

        int[][] nextStates = {
                {m, y},
                {x, n},
                {0, y},
                {x, 0},
                {x - Math.min(x, n - y), y + Math.min(x, n - y)},
                {x + Math.min(y, m - x), y - Math.min(y, m - x)}
        };

        for (int[] next : nextStates) {
            int nextX = next[0];
            int nextY = next[1];
            String stateKey = nextX + "," + nextY;

            if (!visited.contains(stateKey)) {
                System.out.println("  Adding state: (" + nextX + ", " + nextY + ")");
                if (solveWaterJugDFS(nextX, nextY, m, n, d, path + " -> (" + x + ", " + y + ")")) {
                    return true;
                }
            }
        }

        visited.remove(x + "," + y);
        return false;
    }
}
