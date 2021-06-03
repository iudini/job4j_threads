package ru.job4j.buffer;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void offer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 20; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                },
                "producer"
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 11; i++) {
                        try {
                            queue.poll();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
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