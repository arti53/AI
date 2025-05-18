import java.util.*;

class CryptArithmetic {
    static boolean solved = false;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String word1 = "", word2 = "", result = "";

        System.out.println("\nCRYPTARITHMETIC PUZZLE SOLVER");
        System.out.println("Format: WORD1 + WORD2 = RESULT");

        // Input words
        System.out.print("Enter WORD1: ");
        word1 = in.next().toUpperCase();
        System.out.print("Enter WORD2: ");
        word2 = in.next().toUpperCase();
        System.out.print("Enter RESULT: ");
        result = in.next().toUpperCase();

        // Validate input (only uppercase letters allowed)
        if (!word1.matches("^[A-Z]+$") || !word2.matches("^[A-Z]+$") || !result.matches("^[A-Z]+$")) {
            System.out.println("\nOnly letters A-Z allowed.");
        } else if (result.length() > Math.max(word1.length(), word2.length()) + 1) {
            // Result can't be longer than max(word1, word2) + 1
            System.out.println("\nNo solutions possible!");
        } else {
            // Store all unique characters
            Set<Character> set = new HashSet<>();
            for (char c : (word1 + word2 + result).toCharArray())
                set.add(c);

            // If more than 10 unique letters, solution is impossible (digits 0-9)
            if (set.size() > 10) {
                System.out.println("\nNo solutions possible (more than 10 unique letters)!");
                System.exit(0);
            }

            List<Character> letters = new ArrayList<>(set);
            System.out.println("\nSolutions:");
            solve(letters, new ArrayList<>(), new boolean[10], new String[]{word1, word2, result});

            if (!solved)
                System.out.println("\nNo solutions found!");
        }
        in.close();
    }

    // Recursive backtracking function to try all digit mappings
    static void solve(List<Character> letters, List<Integer> values, boolean[] visited, String[] words) {
        if (letters.size() == values.size()) {
            // Create mapping from letters to digits
            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < letters.size(); i++)
                map.put(letters.get(i), values.get(i));

            // Avoid leading zero in any word
            if (map.get(words[0].charAt(0)) == 0 || map.get(words[1].charAt(0)) == 0 || map.get(words[2].charAt(0)) == 0)
                return;

            // Convert words to numbers based on current map
            String w1 = "", w2 = "", res = "";
            for (char c : words[0].toCharArray()) w1 += map.get(c);
            for (char c : words[1].toCharArray()) w2 += map.get(c);
            for (char c : words[2].toCharArray()) res += map.get(c);

            // Check if it satisfies the arithmetic condition
            if (Integer.parseInt(w1) + Integer.parseInt(w2) == Integer.parseInt(res)) {
                System.out.println(w1 + " + " + w2 + " = " + res + "\t" + map);
                solved = true;
            }
            return;
        }

        // Try assigning digits 0-9 to each letter (backtracking)
        for (int i = 0; i < 10; i++) {
            if (!visited[i]) {
                visited[i] = true;
                values.add(i);
                solve(letters, values, visited, words);
                values.remove(values.size() - 1);
                visited[i] = false;
            }
        }
    }

    
}

/* This Java program solves Cryptarithmetic puzzles,
 where each letter in a word represents a unique digit (0–9).
  It takes three words as input in the format WORD1 + WORD2 = RESULT,
   checks all valid digit-letter assignments using backtracking, 
   and prints solutions that satisfy the equation. The program ensures 
   no leading zeroes and restricts the total unique letters to ten,
    making it efficient and accurate for classic puzzles like SEND + MORE = MONEY. */
