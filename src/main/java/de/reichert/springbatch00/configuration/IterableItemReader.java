package de.reichert.springbatch00.configuration;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

public class IterableItemReader<T> implements ItemReader<T> {
    private final Iterator<T> iterator;

    public IterableItemReader(Iterable<T> iterable) {
        this.iterator = iterable.iterator();
    }

    public IterableItemReader(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public  T read() throws Exception,
            UnexpectedInputException,
            ParseException, NonTransientResourceException {
        return iterator.hasNext() ? iterator.next() : null;
        //alternative statt null: "any": Führt zu Endlosschleife
    }
}
