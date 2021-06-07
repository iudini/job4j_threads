package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ThreadPoolTest {

    @Test
    public void work() throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);
        ThreadPool pool = new ThreadPool();

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
              count.incrementAndGet();
            }
        };

        for (int i = 0; i < 100; i++) {
            pool.work(task);
        }
        Thread.sleep(1000);
        pool.shutdown();
        assertThat(count.get(), is(10000));
    }
}