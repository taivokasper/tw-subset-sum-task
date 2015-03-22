package com.github.taivokasper;

import java.util.ArrayList;
import java.util.List;


/**
 * Algorithm with modifications from http://comproguide.blogspot.com/2013/10/subset-sum-problem.html
 * Used dynamic programming to decrease the complexity.
 */
public class SubsetSum {

    //method based on dynamic programming
    public static List<Integer> hasSum(int[] array, int sum) {
        int len = array.length;
        boolean[][] table = new boolean[sum + 1][len + 1];

        int i;

        //If sum is zero; empty subset always has a sum 0; hence true
        for (i = 0; i <= len; i++)
            table[0][i] = true;

        //If set is empty; no way to find the subset with non zero sum; hence false
        for (i = 1; i <= sum; i++)
            table[i][0] = false;

        //calculate the table entries in terms of previous values
        for (i = 1; i <= sum; i++) {
            for (int j = 1; j <= len; j++) {
                table[i][j] = table[i][j - 1];

                if (!table[i][j] && i >= array[j - 1])
                    table[i][j] = table[i - array[j - 1]][j - 1];
            }
        }
//        prettyPrint(table);
        if (table[sum][len])
            return getSumNumbers(array, table, sum);
        return null;
    }

    private static List<Integer> getSumNumbers(int[] array, boolean[][] table, int sum) {
        int leftOver = sum;
        List<Integer> result = new ArrayList<>();
        for (int i = array.length - 1; i >= 0; i --) {
            if (leftOver > 0 && table[leftOver][i + 1]) {
                result.add(array[i]);
                leftOver -= array[i];
            }
        }

        // a fast hack that maybe works
        result.remove(result.size() - 1);
        Integer sublistSum = result.stream().reduce(0, (a, b) -> a + b);
        result.add(sum - sublistSum);
        return result;
    }

    private static void prettyPrint(boolean[][] table) {
        int i = 0;
        for (boolean[] booleans : table) {
            System.out.print(i + ": ");
            i++;
            for (boolean aBoolean : booleans) {
                System.out.print(aBoolean + " ");
            }
            System.out.println();
        }
    }

}