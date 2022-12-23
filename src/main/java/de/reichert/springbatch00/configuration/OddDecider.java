package de.reichert.springbatch00.configuration;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

public class OddDecider implements JobExecutionDecider {
    private int count = 0;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution,
                                      StepExecution stepExecution) {
        count++;
        return new FlowExecutionStatus(count % 2 == 0 ? "EVEN" : "ODD");
    }
}
