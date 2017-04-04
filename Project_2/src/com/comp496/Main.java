package com.comp496;

import com.comp496.job.JobScheduler;
import com.comp496.job.Schedule;

import java.util.Random;

/**
 * Created by Kristopher Morris on 3/25/2017.
 */
public class Main
{
    public static void main (String[] args) {

        int[] length   = {7 , 4 , 2 , 5 };
        int[] deadline = {7 , 16, 8 , 10};
        int[] profit   = {10, 9 , 14, 13};

        JobScheduler js = new JobScheduler(length, deadline, profit);
        runScheduler(js, true);


        length   = new int[] {2 , 3 , 1 , 10, 7 , 4 , 2 , 5 , 7 , 7 };
        deadline = new int[] {10, 12, 9 , 22, 10, 4 , 18, 15, 5 , 9 };
        profit   = new int[] {2 , 5 , 13, 28, 9 , 14, 2 , 7 , 3 , 10};

        js = new JobScheduler(length, deadline, profit);
        runScheduler(js, true);



        length = new int[] { 2,3,1,10,7,  4,6,9,3,2,  5,2,5,7,7,  6,3,7,8,4,  5,2,9,10,5};
        deadline = new int[] { 10,12,15,8,10,  9,22,12,15,35,  29,32,45,41,13,
                16,10,20,10,4,  18,15,5,9, 30};
        profit = new int[] { 2,5,13,28,8, 7,6,5,3,4,  9,7,6,9,14,  2,7,11,3,10,
                8,5,9,10,3 };

        js = new JobScheduler(length, deadline, profit);
        runScheduler(js, false);



        length   = randomVariables(4, 1, 8);
        deadline = randomVariables(4, 5, 15);
        profit   = randomVariables(4, 10, 15);

        js = new JobScheduler(length, deadline, profit);
        runScheduler(js, false);


        length   = randomVariables(50, 1, 8);
        deadline = randomVariables(50, 5, 15);
        profit   = randomVariables(50, 10, 15);

        js = new JobScheduler(length, deadline, profit);
        runScheduler(js, false);


    }

    private static void runScheduler(JobScheduler js, boolean bruteForce) {

        System.out.println("Jobs to be scheduled");
        System.out.println("Job format is " +
                "(length, deadline, profit, start, finish)" );
        js.printJobs();

        if(bruteForce) {
            //--------------------------------------------
            System.out.println("\nOptimal Solution Using Brute Force O(n!)");
            Schedule bestSchedule = js.bruteForceSolution();
            System.out.println(bestSchedule);
        }

        //---------------------------------------
        System.out.println("\nEDF with unprofitable jobs last ");
        Schedule EDFPSchedule = js.makeScheduleEDF();
        System.out.println(EDFPSchedule);

        //-------------------------------------
        System.out.println("\nSJF with unprofitable jobs last");
        Schedule SJFPSchedule = js.makeScheduleSJF();
        System.out.println(SJFPSchedule);

        //--------------------------------------------
        System.out.println("\nHPF with unprofitable jobs last");
        Schedule HPFSchedule = js.makeScheduleHPF();
        System.out.println(HPFSchedule);

        // ------------------------------
        System.out.println("\nYour own creative solution");
        Schedule NASSchedule = js.newApproxSchedule();
        System.out.println(NASSchedule);
    }

    private static int[] randomVariables(int variableCount, int min, int max) {
        int[] ints = new int[variableCount];
        Random random = new Random();

        for(int i = 0; i < variableCount; i++)
            ints[i] = random.nextInt((max - min) + 1) + min;

        return ints;
    }
}