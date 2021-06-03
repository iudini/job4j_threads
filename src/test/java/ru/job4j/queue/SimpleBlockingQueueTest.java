package ru.job4j.queue;

import org.junit.Test;
import ru.job4j.synch.SingleLockList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void offer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 20; i++) {
                        queue.offer(i);
                    }
                },
                "producer"
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 11; i++) {
                        queue.poll();
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(11, is(queue.poll()));
    }
}