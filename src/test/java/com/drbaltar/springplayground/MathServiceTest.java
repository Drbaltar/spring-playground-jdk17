package com.drbaltar.springplayground;

import com.drbaltar.springplayground.textprocessing.MathService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathServiceTest {
    @ParameterizedTest
    @CsvSource({
            "add, 4, 6, '4 + 6 = 10'",
            "subtract, 15, 10, '15 - 10 = 5'",
            "multiply, 5, 6, '5 * 6 = 30'",
            "divide, 10, 5, '10 / 5 = 2'"
    })
    public void MathServiceGetCalculate(String operation, int x, int y, String expected) throws Exception {
        String actual = MathService.getCalculate(operation, x, y);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 5, '3 + 4 + 5 = 12'",
            "3, 7, 5, '3 + 7 + 5 = 15'"
    })
    public void MathServiceAddAll(String x, String y, String z, String expected) {
        List<String> myList = new ArrayList(Arrays.asList(x, y, z));
        String actual = MathService.addAll(myList);

        assertEquals(expected, actual);
    }

}