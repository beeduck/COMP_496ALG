package com.comp496;

import com.comp496.job.JobScheduler;
import com.comp496.job.Schedule;

import java.util.Random;

/**
 * Created by Kristopher Morris on 3/25/2017.
 */
public class Main
{
    private final static int JOBS = 100;

    public static void main (String[] args)
    {

//        int[] length   = {7 , 4 , 2 , 5 };
//        int[] deadline = {7 , 16, 8 , 10};
//        int[] profit   = {10, 9 , 14, 13};

//        int[] length   = {2 , 3 , 1 , 10, 7 , 4 , 2 , 5 , 7 , 7 };
//        int[] deadline = {10, 12, 9 , 22, 10, 4 , 18, 15, 5 , 9 };
//        int[] profit   = {2 , 5 , 13, 28, 9 , 14, 2 , 7 , 3 , 10};

        int[] length = { 2,3,1,10,7,  4,6,9,3,2,  5,2,5,7,7,  6,3,7,8,4,  5,2,9,10,5};
        int[] deadline = { 10,12,15,8,10,  9,22,12,15,35,  29,32,45,41,13,
                16,10,20,10,4,  18,15,5,9, 30};
        int[] profit = { 2,5,13,28,8, 7,6,5,3,4,  9,7,6,9,14,  2,7,11,3,10,
                8,5,9,10,3 };

        length   = randomVariables(JOBS, 1, JOBS/5);
        deadline = randomVariables(JOBS, 1, JOBS);
        profit   = randomVariables(JOBS, 1, 5);

        JobScheduler js = new JobScheduler(length,deadline, profit);
        System.out.println("Jobs to be scheduled");
        System.out.println("Job format is " +
                "(length, deadline, profit, start, finish)" );
        js.printJobs();

//        //--------------------------------------------
//        System.out.println("\nOptimal Solution Using Brute Force O(n!)");
//        Schedule bestSchedule = js.bruteForceSolution();
//        System.out.println(bestSchedule);

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

/*-----------------Sample Run -----------------------

 Input: Jobs to be scheduled
 Job format is (length, deadline, profit, start, finish)
 #0:(7,7,10,-1,-1)
 #1:(4,16,9,-1,-1)
 #2:(2,8,14,-1,-1)
 #3:(5,10,13,-1,-1)

 Optimal Solution Using Brute Force O(n!)
 Schedule Profit = 36
 #3:(5,10,13,0,5)
 #2:(2,8,14,5,7)
 #1:(4,16,9,7,11)
 #0:(7,7,10,11,18)

 EDF with unprofitable jobs last
 Schedule Profit = 19
 #0:(7,7,10,0,7)
 #1:(4,16,9,7,11)
 #3:(5,10,13,11,16)
 #2:(2,8,14,16,18)


 SJF with unprofitable jobs last
 Schedule Profit = 23
 #2:(2,8,14,0,2)
 #1:(4,16,9,2,6)
 #0:(7,7,10,6,13)
 #3:(5,10,13,13,18)

 HPF with unprofitable jobs last
 Schedule Profit = 36
 #2:(2,8,14,0,2)
 #3:(5,10,13,2,7)
 #1:(4,16,9,7,11)
 #0:(7,7,10,11,18)


 Your own creative solution
 <This will have your own answers>

*/