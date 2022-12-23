package de.reichert.springbatch00.configuration;

import de.reichert.springbatch00.listener.MyChunkListener;
import de.reichert.springbatch00.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Component
public class ListToSoutJobFactory {
    private SoutStepBuilder soutStepBuilder;
    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    public ListToSoutJobFactory(SoutStepBuilder soutStepBuilder,
                                JobRepository jobRepository,
                                PlatformTransactionManager transactionManager) {
        this.soutStepBuilder = soutStepBuilder;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    public Job createJob(List<String> list) {
        ListItemReader<String> reader = new ListItemReader<>(list);
        ItemWriter<String> writer = chunk -> chunk.forEach(System.out::println);

        TaskletStep step = new StepBuilder("ReadAndWriteStep", jobRepository)
                .<String, String>chunk(2, transactionManager)
                .faultTolerant()
                .listener(new MyChunkListener())
                .reader(reader)
                .writer(writer)
                .build();


        return new JobBuilder("IterableToSout", jobRepository)
                .start(step)
                .listener(new MyJobListener())
                .build();
    }


}
