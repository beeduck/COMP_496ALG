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
    private Schedule bruteSchedule;
    public Schedule bruteForceSolution() {

        this.bruteSchedule = new Schedule();
        jobPermutations(jobs, 0);

        int startTime = 0;
        Schedule returnSchedule = new Schedule();
        for(Job job : bruteSchedule.schedule) {
            startTime = addJobToSchedule(startTime, returnSchedule, job);
        }

        return returnSchedule;
    }

    private void jobPermutations(Job[] jobs, int index) {
        if (index >= jobs.length - 1) {

            Schedule tempSchedule = new Schedule();
            for(Job job : jobs) {
                tempSchedule.add(job);
            }

            if(tempSchedule.profit >= this.bruteSchedule.profit)
                this.bruteSchedule = tempSchedule;

            return;
        }

        for (int i = index; i < jobs.length; i++) {

            // Move the scheduled job to the front of the array
            Job tempJob = jobs[index];
            jobs[index] = jobs[i];
            jobs[i] = tempJob;

            // Schedule remaining jobs
            jobPermutations(jobs, index + 1);

            // Move the scheduled job back to its original position to maintain the original array
            tempJob = jobs[index];
            jobs[index] = jobs[i];
            jobs[i] = tempJob;
        }
    }



    //earliest deadline first schedule. Schedule items contributing 0 to total profit last
    public Schedule makeScheduleEDF() {

        List<Job> jobs = Arrays.asList(this.jobs);
        jobs.sort(new Comparator<Job>() {
            @Override
            public int compare(Job job1, Job job2) {
                if(job1.deadline == job2.deadline)
                    return 0;

                return job1.deadline < job2.deadline ? -1 : 1;
            }
        });

        int startTime = 0;
        Schedule schedule = new Schedule();
        Queue<Job> unprofitableJobs = new LinkedList<>();
        for(Job job : jobs) {
            // Ensure profit is still possible - job.length + startTime <= job.deadline
            if( (job.length + startTime) > job.deadline ) {
                unprofitableJobs.add(job);
                continue;
            }

            startTime = addJobToSchedule(startTime, schedule, job);
        }

        for(Job job : unprofitableJobs) {
            startTime = addJobToSchedule(startTime, schedule, job);
        }

        return schedule;
    }

    //shortest job first schedule. Schedule items contributing 0 to total profit last
    public Schedule makeScheduleSJF() {

        List<Job> jobs = Arrays.asList(this.jobs);
        jobs.sort(new Comparator<Job>() {
            @Override
            public int compare(Job job1, Job job2) {
                if(job1.length == job2.length)
                    return 0;

                return job1.length < job2.length ? -1 : 1;
            }
        });

        int startTime = 0;
        Schedule schedule = new Schedule();
        Queue<Job> unprofitableJobs = new LinkedList<>();
        for(Job job : jobs) {
            // Ensure profit is still possible - job.length + startTime <= job.deadline
            if( (job.length + startTime) > job.deadline ) {
                unprofitableJobs.add(job);
                continue;
            }

            startTime = addJobToSchedule(startTime, schedule, job);
        }

        for(Job job : unprofitableJobs) {
            startTime = addJobToSchedule(startTime, schedule, job);
        }

        return schedule;
    }

    //highest profit first schedule. Schedule items contributing 0 to total profit last
    public Schedule makeScheduleHPF() {

        List<Job> jobs = Arrays.asList(this.jobs);
        jobs.sort(new Comparator<Job>() {
            @Override
            public int compare(Job job1, Job job2) {
                if(job1.profit == job2.profit)
                    return 0;

                return job1.profit > job2.profit ? -1 : 1;
            }
        });

        int startTime = 0;
        Schedule schedule = new Schedule();
        Queue<Job> unprofitableJobs = new LinkedList<>();
        for(Job job : jobs) {
            // Ensure profit is still possible - job.length + startTime <= job.deadline
            if( (job.length + startTime) > job.deadline ) {
                unprofitableJobs.add(job);
                continue;
            }

            startTime = addJobToSchedule(startTime, schedule, job);
        }

        for(Job job : unprofitableJobs) {
            startTime = addJobToSchedule(startTime, schedule, job);
        }

        return schedule;
    }

    public Schedule newApproxSchedule() {

        // Sort jobs with the highest profit vs length ratio first
        List<Job> jobs = Arrays.asList(this.jobs);
        jobs.sort(new Comparator<Job>() {
            @Override
            public int compare(Job job1, Job job2) {
                float profitDeadlineRatio1 = (float)job1.profit / (float)job1.length;
                float profitDeadlineRatio2 = (float)job2.profit / (float)job2.length;

                if(profitDeadlineRatio1 == profitDeadlineRatio2)
                    return 0;

                return profitDeadlineRatio1 > profitDeadlineRatio2 ? -1 : 1;
            }
        });

        int startTime = 0;
        Schedule schedule = new Schedule();
        Queue<Job> unprofitableJobs = new LinkedList<>();
        for(Job job : jobs) {
            // Ensure profit is still possible - job.length + startTime <= job.deadline
            if( (job.length + startTime) > job.deadline ) {
                unprofitableJobs.add(job);
                continue;
            }

            startTime = addJobToSchedule(startTime, schedule, job);
        }

        for(Job job : unprofitableJobs) {
            startTime = addJobToSchedule(startTime, schedule, job);
        }

        return schedule;
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
