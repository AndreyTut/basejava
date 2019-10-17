package javawebinar.basejava;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        AtomicInteger pow = new AtomicInteger(0);
        Double res = Arrays.stream(values)
                .boxed()
                .collect(Collectors.toSet())
                .stream()
                .sorted((i1, i2) -> i2 - i1)
                .map(val -> val * Math.pow(10, pow.getAndIncrement()))
                .reduce((val1, val2) -> val1 + val2).get();
        return res.intValue();
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> odds = new ArrayList<>();
        List<Integer> evens = new ArrayList<>();
        int sum = integers.stream()
                .peek(val -> {
                    if (val % 2 == 0) evens.add(val);
                    else odds.add(val);
                })
                .reduce((val1, val2) -> val1 + val2).get();
        return sum % 2 == 0 ? evens : odds;
    }
}
