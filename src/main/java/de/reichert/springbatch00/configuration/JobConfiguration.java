package de.reichert.springbatch00.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
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
    @Autowired
    private SoutStepBuilder soutStepBuilder;
    @Autowired
    private JobRepository jobRepository;

    @Bean
    public Step step1(JobRepository jobRepository,
    PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
            System.out.println("Hallo");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    //@Bean
    public Job helloWorldJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("helloWorldJob", jobRepository)
                .start(step1).on("COMPLETED").to(step1).end().build();
    }

//    @Bean
    public Job secondJob() {
        Step step0 = soutStepBuilder.getStep("s0", "hallo");
        Step step1 = soutStepBuilder.getStep("s1", "du");
        return new JobBuilder("secondJob", jobRepository).start(step0).next(step1).build();
    }

    @Bean
    public Job flowJob() {
        Step step0 = soutStepBuilder.getStep("s0", "hallo");
        Step step1 = soutStepBuilder.getStep("s1", "du");
        Flow flow = new FlowBuilder<Flow>("foo").start(step0).next(step1).build();
        return new JobBuilder("flowJob", jobRepository).start(flow).end().build();

    }
}
