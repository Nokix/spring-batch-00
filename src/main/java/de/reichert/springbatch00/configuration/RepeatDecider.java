package de.reichert.springbatch00.configuration;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class RepeatDecider implements JobExecutionDecider {
    private int count = 0;
    private final int max;

    public RepeatDecider(int max) {
        this.max = max;
    }

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution,
                                      StepExecution stepExecution) {
        return new FlowExecutionStatus(count++ < max ? "REPEAT" : "STOP");
    }
}
