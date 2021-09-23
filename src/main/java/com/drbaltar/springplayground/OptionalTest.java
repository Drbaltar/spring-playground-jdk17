package com.drbaltar.springplayground;

import java.util.Optional;

public class OptionalTest {
    public static void main(String[] args) {
        int total = 0;
        int[] intArray = new int[]{ 1,2,3,4,5,6,7,8,9,10 };
        Optional<Integer> optionalInteger = Optional.ofNullable(Integer.valueOf(150));

        System.out.println(optionalInteger.orElseGet(OptionalTest::getRandomInt));

    }

    private static int getRandomInt() {
        return (int) (Math.random() * 10);
    }


}
