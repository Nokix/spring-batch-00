package de.reichert.springbatch00.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MyJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Job " + jobExecution.getJobInstance().getJobName() + " started.");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Job " + jobExecution.getJobInstance().getJobName() + " ended.");
    }
}
