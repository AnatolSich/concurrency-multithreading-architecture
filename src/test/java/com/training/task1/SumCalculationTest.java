package com.training.task1;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ConcurrentModificationException;
import java.util.Date;

class SumCalculationTest {

    private final static String REPORT_PATH = "src/test/java/com/training/task1/durationTime";
    private static final SumCalculation sumCalculation = new SumCalculation();

    private long currentTime(){
        return System.currentTimeMillis();
    }

    @Test
    void goHashMapTest() {
        long startTime = currentTime();
        Assertions.assertThrows(ConcurrentModificationException.class, sumCalculation::goHashMap);
        long endTime = currentTime();

        appendTimeDuration("Duration goHashMapTest: " + (endTime - startTime) + " milliseconds : " + new Date());
    }

    @Test
    void goConcurrentHashMapTest() {
        long startTime = currentTime();
        sumCalculation.goConcurrentHashMap();
        long endTime = currentTime();

        appendTimeDuration("Duration goConcurrentHashMapTest: " + (endTime - startTime) + " milliseconds : " + new Date());
    }

    @Test
    void goSynchronizedMapTest() {
        long startTime = currentTime();
        Assertions.assertThrows(ConcurrentModificationException.class, sumCalculation::goCollectionSynchronizedMap);
        long endTime = currentTime();

        appendTimeDuration("Duration goCollectionSynchronizedMapTest: " + (endTime - startTime) + " milliseconds : " + new Date());
    }

    @Test
    void goThreadSafeMapTest() {
        long startTime = currentTime();
        sumCalculation.goThreadSafeMap();
        long endTime = currentTime();

        appendTimeDuration("Duration goThreadSafeMapTest: " + (endTime - startTime) + " milliseconds : " + new Date());
    }

    @Test
    void goCustomSynchronizedThreadSafeMapTest() {
        long startTime = currentTime();
        sumCalculation.goSynchronizedThreadSafeMap();
        long endTime = currentTime();

        appendTimeDuration("Duration goSynchronizedThreadSafeMapTest: " + (endTime - startTime) + " milliseconds : " + new Date());
    }

    @SneakyThrows
    private void appendTimeDuration(String message) {
        System.out.println(message);
        Files.write(Paths.get(REPORT_PATH), (message + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
    }
}
