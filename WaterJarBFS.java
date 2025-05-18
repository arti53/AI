import java.util.*;
public class WaterJarBFS {
    public static void main(String[] args) {
        int m = 4;
        int n = 3;
        int d = 2;
        solveWaterJug(m, n, d);
    }
    public static void solveWaterJug(int m, int n, int d) {
        Queue<int[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new int[]{0, 0});
        visited.add("0,0");
        System.out.println("Starting BFS...");
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            System.out.println("Exploring state: (" + x + ", " + y + ")");
            if (x == d || y == d) {
                System.out.println("Solution found: (" + x + ", " + y + ")");
                return;
            }
            int[][] nextStates = {
                {m, y},
                {x, n},
                {0, y},
                {x, 0},
                {x - Math.min(x, n - y), y + Math.min(x, n - y)},
                {x + Math.min(y, m - x), y - Math.min(y, m - x)}
            };
            for (int[] next : nextStates) {
                String stateKey = next[0] + "," + next[1];
                if (!visited.contains(stateKey)) {
                    visited.add(stateKey);
                    queue.add(next);
                    System.out.println("  Adding state: (" + next[0] + ", " + next[1] + ")");
                }
            }
        }
        System.out.println("No solution exists.");
    }
}
