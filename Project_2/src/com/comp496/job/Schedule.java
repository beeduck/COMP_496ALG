package com.comp496.job;

import java.util.ArrayList;

/**
 * Created by Kristopher Morris on 3/25/2017.
 */
public class Schedule {
    ArrayList<Job> schedule;
    int profit;

    public Schedule() {
        profit = 0;
        schedule = new ArrayList<Job>();
    }

    int currentTime = 0;
    public void add(Job job) {
        schedule.add(job);

        currentTime += job.length;

        if(currentTime <= job.deadline)
            profit += job.profit;
    }


    public int getProfit() {
        return profit;
    }

    public String toString() {
        String s = "Schedule Profit = " + profit;
        for (int k = 0; k < schedule.size(); k++) {
            s = s + "\n" + schedule.get(k);

        }

        return s;
    }
}
