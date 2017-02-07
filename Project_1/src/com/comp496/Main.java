package com.comp496;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int randomSize = 1000000;
    private static final int runCount = 5;
    private static final int[] comps = {
            10,
            100,
            1000,
            10000,
            100000,
            1000000,
            5000000
    };




    public static void main(String[] args) {
	// write your code here

        List<ResultsOutput> resultsOutputList = new ArrayList<>();
        ResultsOutput resultsOutput;
        for(int i = 0; i < comps.length; i++) {
            resultsOutput = new ResultsOutput(comps[i]);
            resultsOutput.runSort();
            resultsOutputList.add(resultsOutput);
        }

        resultsOutputList.forEach(s -> System.out.println(s));

        return;
    }





    private static class ResultsOutput {
        public int n;
        public long averageComp;
        public long averageTime;
        private static long totalTime = 0;
        private static long totalComp = 0;

        public ResultsOutput(int n) {
            this.n = n;
        }

        public void runSort() {
            totalTime = totalComp = 0;
            for (int i = 0; i < runCount; i++) {
                int[] a = createList(n, randomSize);
                runSortTimer(a);
            }
            averageComp = totalComp / runCount;
            averageTime = totalTime / runCount;
        }

        private static void runSortTimer(int[] a) {
            long startTime = System.nanoTime();
            totalComp += Sorts.mergesort(a);
            totalTime += System.nanoTime() - startTime;
        }

        private int[] createList(int arraySize, int randomSize) {
            int[] list = new int[arraySize];
            Random random = new Random();

            for (int i = 0; i < arraySize; i++) {
                list[i] = random.nextInt(randomSize);
            }

            return list;
        }

        @Override
        public String toString() {
            return Integer.toString(n) + "\t\t\t" + averageComp + "\t\t\t" + "todo" + "\t\t\t" + averageTime + "\t\t\t" + "todo";
        }
    }
}
