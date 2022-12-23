package de.reichert.springbatch00.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

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

//    @Bean
    public Job flowJob() {
        Step step0 = soutStepBuilder.getStep("s0", "hallo");
        Step step1 = soutStepBuilder.getStep("s1", "du");
        Flow flow = new FlowBuilder<Flow>("foo").start(step0).next(step1).build();
        Step mystep = soutStepBuilder.getStep("s2", "Viktor");
        return new JobBuilder("flowJob", jobRepository).start(flow).next(mystep).end().build();
//        return new JobBuilder("flowJob", jobRepository).start(mystep).on("COMPLETED").to(flow).end().build();
    }

//    @Bean
    public Job parallelFlows() {
        Step step0 = soutStepBuilder.getStep("s0", "hallo");
        Step step1 = soutStepBuilder.getStep("s1", "du");
        Flow flow = new FlowBuilder<Flow>("foo").start(step0).next(step1).build();

        Step myStep0 = soutStepBuilder.getStep("s2", "Viktor");
        Step myStep1 = soutStepBuilder.getStep("s3", "Reichert");
        Flow myFlow = new FlowBuilder<Flow>("foo2").start(myStep0).next(myStep1).build();

        return new JobBuilder("flowJob", jobRepository)
                .start(flow)
                .split(new SimpleAsyncTaskExecutor()).add(myFlow)
                .end().build();
    }

//    @Bean
    public Job decideFlow() {
        Step stepStart = soutStepBuilder.getStep("s0", "start");
        Step stepOdd = soutStepBuilder.getStep("s0", "odd");
        Step stepEven = soutStepBuilder.getStep("s1", "even");
        JobExecutionDecider decider = new OddDecider();

        return new JobBuilder("deciderJob", jobRepository)
                .start(stepStart)
                .next(decider)
                .from(decider).on("ODD").to(stepOdd)
                .from(decider).on("EVEN").to(stepEven)
                .from(stepOdd).on("*").to(decider)
                .end().build();
    }

    //@Bean
    public Job decideFlowRepeat() {
        Step stepStart = soutStepBuilder.getStep("start", "start");
        Step step = soutStepBuilder.getStep("repeat", "repeating");
        Step finished = soutStepBuilder.getStep("finished", "finished");
        JobExecutionDecider decider = new RepeatDecider(3);

        return new JobBuilder("deciderJob", jobRepository)
                .start(stepStart)
                .next(decider)
                .from(decider).on("REPEAT").to(step)
                .from(step).on("*").to(decider)
                .from(decider).on("STOP").to(finished)
                .end().build();
    }

    @Bean
    public Job listenerJob(ListToSoutJobFactory listToSoutJobFactory) {
        return listToSoutJobFactory.createJob(List.of("a", "b", "c", "d", "e"));
    }


}
