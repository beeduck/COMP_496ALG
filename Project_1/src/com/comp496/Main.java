package com.comp496;

import java.util.Random;

public class Main {

    private static final int randomSize = 1000000;
    private static final int[] mergeComps = {
            10,
            100,
            1000,
            10000,
            100000,
            1000000,
            5000000
    };
    private static final int[] insertionComps = {
            10,
            100,
            1000,
            10000,
            100000,
            200000,
            300000
    };


    public static void main(String[] args) {
        // write your code here

        int[] a;
        long startTime;


        // Run merge sort timer
        long mergeTotalComp, mergeTotalTime, mergeAvgComp, mergeAvgTime;
        mergeTotalComp = mergeTotalTime = mergeAvgComp = mergeAvgTime = 0;
        for(int i = 0; i < mergeComps.length; i++) {
            mergeTotalComp = mergeTotalTime = 0;
            for(int j = 0; j < 5; j++) {

                a = createList(mergeComps[i], randomSize);
                startTime = System.nanoTime();
                mergeTotalComp += Sorts.mergesort(a);
                mergeTotalTime += System.nanoTime() - startTime;
            }

            mergeAvgComp = mergeTotalComp / 5;
            mergeAvgTime = mergeTotalTime / 5;

            double logOfN = Math.log10(mergeComps[i]) / Math.log10(2);
            double comparisonComputed = mergeAvgComp / (mergeComps[i] * logOfN);
            double timeComputed = mergeAvgTime / (mergeComps[i] * logOfN);

            String format = "%-15d%-15d%-15.3f%-15d%-15.3f%n";
            System.out.printf(format, mergeComps[i], mergeAvgComp, comparisonComputed, mergeAvgTime, timeComputed);
        }

        System.out.print("\n\n");

        // Run insertion sort timer
        long insertionTotalComp, insertionTotalTime, insertionAvgComp, insertionAvgTime;
        insertionTotalComp = insertionTotalTime = insertionAvgComp = insertionAvgTime = 0;
        for(int i = 0; i < insertionComps.length; i++) {
            insertionTotalComp = insertionTotalTime = 0;
            for(int j = 0; j < 5; j++) {

                a = createList(insertionComps[i], randomSize);
                startTime = System.nanoTime();
                insertionTotalComp += Sorts.insertionsort(a);
                insertionTotalTime += System.nanoTime() - startTime;
            }

            insertionAvgComp = insertionTotalComp / insertionComps.length;
            insertionAvgTime = insertionTotalTime / insertionComps.length;

            double comparisonComputed = insertionAvgComp / (Math.pow(insertionComps[i], 2));
            double timeComputed = insertionAvgTime / (Math.pow(insertionComps[i], 2));

            String format = "%-15d%-15d%-15.3f%-15d%-15.3f%n";
            System.out.printf(format, insertionComps[i], insertionAvgComp,
                              comparisonComputed, insertionAvgTime, timeComputed);
        }

        return;
    }

    private static int[] createList(int arraySize, int randomSize) {
        int[] list = new int[arraySize];
        Random random = new Random();

        for (int i = 0; i < arraySize; i++) {
            list[i] = random.nextInt(randomSize);
        }

        return list;
    }

}
