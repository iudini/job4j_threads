package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {
    private static class CASCountThread extends Thread {
        private final CASCount count;

        private CASCountThread(final CASCount count) {
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                this.count.increment();
            }
        }
    }

    @Test
    public void increment() throws InterruptedException {
        final CASCount count = new CASCount();
        Thread first = new CASCountThread(count);
        Thread second = new CASCountThread(count);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(200000));
    }
}