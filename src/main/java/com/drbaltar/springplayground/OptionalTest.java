package com.drbaltar.springplayground;

import java.util.ArrayList;
import java.util.Arrays;

public class OptionalTest {

    public static void main(String[] args) {
//        int total = 0;
//        int[] intArray = new int[]{ 1,2,3,4,5,6,7,8,9,10 };
//        Optional<Integer> optionalInteger = Optional.ofNullable(Integer.valueOf(150));
//
//        System.out.println(optionalInteger.orElseGet(OptionalTest::getRandomInt));

        ArrayList<String> testList = new ArrayList<>(Arrays.asList("test", "Basic", "by", "Explained"));

        System.out.println("testList.stream().filter(input -> isFourLettersLong(input)).toList() = " + testList.stream().filter(input -> isFourLettersLong(input)).toList());

        System.out.println("testList = " + testList);

    }

    private static boolean isFourLettersLong(String input) {
        return input.length() == 4;
    }


    private static int getRandomInt() {
        return (int) (Math.random() * 10);
    }


}
