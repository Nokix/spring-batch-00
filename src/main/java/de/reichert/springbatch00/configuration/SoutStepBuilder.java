package de.reichert.springbatch00.configuration;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class SoutStepBuilder {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public SoutStepBuilder(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    public Step getStep(String name, String output) {
        return new StepBuilder(name, jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(output);
                    System.out.println(chunkContext.getStepContext().getStepName()
                            + " ist ausgeführt auf Thread " + Thread.currentThread().getName());
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
