package com.comp496.job;

import java.util.*;

/**
 * Created by Kristopher Morris on 3/25/2017.
 */
public class JobScheduler {
    private int nJobs;
    private Job[] jobs;

    public JobScheduler(int[] joblength, int[] deadline, int[] profit)
    {
        //Set nJobs
        //Fill jobs array. The kth job entered has JobNo = k;
        nJobs = 0;
        jobs = new Job[joblength.length];

        for(int i = 0; i < joblength.length; i++) {
            jobs[nJobs] = new Job(nJobs++, joblength[i], deadline[i], profit[i]);
        }
    }

    public void printJobs() {  //prints the array jobs
        for(Job job : jobs) {
            System.out.println(job);
        }
    }

    //Brute force. Try all n! orderings. Return the schedule with the most profit
    public Schedule bruteForceSolution() {

        return new Schedule();
    }

    //earliest deadline first schedule. Schedule items contributing 0 to total profit last
    public Schedule makeScheduleEDF() {

        return new Schedule();
    }

    //shortest job first schedule. Schedule items contributing 0 to total profit last
    public Schedule makeScheduleSJF() {

        return new Schedule();
    }

    //highest profit first schedule. Schedule items contributing 0 to total profit last
    public Schedule makeScheduleHPF() {

        return new Schedule();
    }

    //Your own creation. Must be <= O(n3)
    public Schedule newApproxSchedule() {

        // Calculate latest possible start time for profit for each job
        // = (deadline - joblength)

//        int latestStartPossible[] = new int[jobs.length];

//        List<Integer> latestStartPossible = new ArrayList<>();
//        for(Job job : jobs) {
//            latestStartPossible.add()
//        }

        List<Job> jobs = Arrays.asList(this.jobs);
        Collections.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job job1, Job job2) {
                int latestStart1 = job1.deadline - job1.length;
                int latestStart2 = job2.deadline - job2.length;
                if(latestStart1 == latestStart2)
                    return 0;

                // If for some reason the job is impossible to complete from 0 start to deadline
                // then send it to the end of the sort
                if(latestStart1 <= -1)
                    return 1;
                else if(latestStart2 <= -1)
                    return -1;

                return latestStart1 < latestStart2 ? -1 : 1;
            }
        });

        int startTime = 0;
        Schedule schedule = new Schedule();
        for(Job job : jobs) {
            if( (job.deadline - job.length) < startTime)
                continue;

            startTime = addJobToSchedule(startTime, schedule, job);
        }

        return new Schedule();
    }

    /**
     * Adds job to specified schedule with supplied start time.
     * @param startTime
     * @param schedule
     * @param job
     * @return updated start time after the job has been scheduled
     */
    private int addJobToSchedule(int startTime, Schedule schedule, Job job) {
        job.start = startTime;
        job.finish = startTime += job.length;

        schedule.add(job);

        return startTime;
    }
}
