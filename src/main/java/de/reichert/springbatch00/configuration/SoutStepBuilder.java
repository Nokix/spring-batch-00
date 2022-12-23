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
    private JobRepository jobRepository;
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    public SoutStepBuilder(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    public Step getStep(String name, String output) {
        return new StepBuilder(name, jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(output);
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

}
