package de.reichert.springbatch00.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@EnableBatchProcessing
public class JobConfiguration {

    @Bean
    public Step step1(JobRepository jobRepository,
    PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
            System.out.println("Hallo");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Job helloWorldJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("helloWorldJob", jobRepository)
                .flow(step1).end().build();
    }
}
