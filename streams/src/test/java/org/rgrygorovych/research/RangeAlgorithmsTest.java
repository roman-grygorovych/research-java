/*
 * @(#)RangeAlgorithmsTest.java         1.00, 2017/02/09 (09 February, 2017)
 *
 * This file is part of 'research-common' project.
 * Can not be copied and/or distributed without
 * the express permission of Segoviasoft company.
 *
 * Copyright (C) 2017 Segoviasoft
 * All Rights Reserved.
 */
package org.rgrygorovych.research;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * RangeAlgorithmsTest
 *
 * @author Roman Grygorovych
 * @version 1.00, 02/09/2017
 * @since 1.0
 */
public class RangeAlgorithmsTest {

    @Data
    static class Scores {
        final long firstScore;
        final long secondScore;

        public long total() {
            return firstScore + secondScore;
        }

        public String toString() {
            return String.format("%s:%s=%s", firstScore, secondScore, total());
        }
    }

    @Test
    public void testMiddle() {
        final List<Scores> scores = new ArrayList<>();

        scores.add(new Scores(1L, 1L));
        scores.add(new Scores(4L, 0L));
        scores.add(new Scores(4L, 1L));
        scores.add(new Scores(4L, 3L));
        scores.add(new Scores(8L, 0L));
        scores.add(new Scores(0L, 0L));
        scores.add(new Scores(0L, 1L));
        scores.add(new Scores(0L, 3L));
        scores.add(new Scores(10L, 13L));
        scores.add(new Scores(1L, 3L));

        final ArrayList<Scores> collect = scores.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Scores::getFirstScore))),
                        ArrayList::new));

        System.out.println("Initial collection: " + scores);
//        System.out.println("Find by first score...");
        System.out.println("Unique collection: " + collect);
        System.out.println("Min: " + collect.get(0));
        System.out.println("Second smallest: " + collect.get(1));
        System.out.println("Middle: " + collect.get(collect.size() / 2));
        System.out.println("Second biggest: " + collect.get(collect.size() - 2));
        System.out.println("Max: " + collect.get(collect.size() - 1));
    }

}