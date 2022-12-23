package de.reichert.springbatch00.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

import java.util.Arrays;

public class MyChunkListener {
    @BeforeChunk
    public void beforeChunnk(ChunkContext chunkContext) {
        System.out.println(">> Before Chunk. Is Complete:" + chunkContext.isComplete());
    }

    @AfterChunk
    public void afterChung(ChunkContext chunkContext) {
        System.out.println("<< After Chunk. Is Complete:" + chunkContext.isComplete());
    }
}
