package javawebinar.basejava;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MainStream {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 2, 3};
        int[] arr1 = {9, 8};
        System.out.println(minValue(arr));
        System.out.println(minValue(arr1));
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add(i);
        }
        System.out.println(oddOrEven(list));
    }

    static int minValue(int[] values) {
        return IntStream.of(values)
                .sorted()
                .distinct()
                .reduce((i1, i2) -> i1 * 10 + i2).orElse(-1);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> odds = new ArrayList<>();
        List<Integer> evens = new ArrayList<>();
        int sum = integers.stream()
                .peek(val -> {
                    if (val % 2 == 0) evens.add(val);
                    else odds.add(val);
                })
                .reduce((val1, val2) -> val1 + val2).orElse(-1);
        return sum % 2 == 0 ? evens : odds;
    }
}
