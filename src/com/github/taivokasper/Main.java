package com.github.taivokasper;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Main {
    private final List<Integer> fromList;
    private final List<Integer> toList;

    public Main(String fromUrl, String toUrl) {
        fromList = readFile(fromUrl, Comparator.<Integer>naturalOrder());
        toList = readFile(toUrl, Comparator.<Integer>naturalOrder());

        System.out.println(String.format("The size of the from list is %d and size of to list is %d", fromList.size(), toList.size()));

        for (Integer sum : fromList) {
            int endOfSliceIndex = 0;
            for (int i = 0; i < toList.size(); i++) {
                Integer integer = toList.get(i);
                if (integer > sum)
                    break;
                endOfSliceIndex = i;
            }

            match(sum, 0, endOfSliceIndex);
        }
    }

    private void match(Integer expectedSum, Integer from, Integer to) {
//        System.out.println(String.format("sum %s slice from %d to %d", expectedSum, from, to));
        List<Integer> sublistCollection = toList.subList(from, to + 1);
        int[] subList = sublistCollection.stream().mapToInt(i -> i).toArray();
        List<Integer> integers = SubsetSum.hasSum(subList, expectedSum);
        System.out.print(expectedSum + ";" + strJoin(integers, ","));
        System.out.println();

        // remove corresponding numbers from the original toList
        for (Integer integer : integers) {
            toList.remove(integer);
        }
    }

    private static List<Integer> readFile(String file, Comparator<Integer> orderComparator) {
        List<Integer> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new URL(file).openStream())) {
            while (scanner.hasNextLine()) {
                list.add(Integer.valueOf(scanner.nextLine()));
            }
            Collections.sort(list, orderComparator);
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String strJoin(List<Integer> aArr, String sSep) {
        StringBuilder sbStr = new StringBuilder();
        for (int i = 0, il = aArr.size(); i < il; i++) {
            if (i > 0)
                sbStr.append(sSep);
            sbStr.append(aArr.get(i));
        }
        return sbStr.toString();
    }

    public static void main(String[] args) {
        new Main(args[0], args[1]);
    }
}
