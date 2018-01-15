package utils;

/**
 * Created by iulian_popescu
 * Computes the Levenshtein distance between two strings
 */
public class LevenshteinDistanceHelper {
    private LevenshteinDistanceHelper() {}

    /**
     * Computes the min value between three values
     * @param a first value
     * @param b second value
     * @param c third value
     * @return The minimum value
     */
    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * Computes the Levenshtein distance between two strings
     * If any of the strings is NULL, Integer.MAX_VALUE will return
     * @param lhs first string
     * @param rhs second string
     * @return an integer representing the Levenshtein distance between given strings
     */
    public static int computeLevenshteinDistance(String lhs, String rhs) {
        if (lhs == null || rhs == null) {
            return Integer.MAX_VALUE;
        }
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

        for (int i = 0; i <= lhs.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= rhs.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= lhs.length(); i++)
            for (int j = 1; j <= rhs.length(); j++)
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));

        return distance[lhs.length()][rhs.length()];
    }
}
