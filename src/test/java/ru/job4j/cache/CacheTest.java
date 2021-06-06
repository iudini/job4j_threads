package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenUpdateTwiceThenVersionIs2() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        cache.update(new Base(1, 0));
        cache.update(new Base(1, 1));
        assertThat(cache.get(1).getVersion(), is(2));
    }

    @Test (expected = OptimisticException.class)
    public void whenUpdateWithWrongVersionThenException() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        Base base = new Base(1, 1);
        base.setName("Name");
        cache.update(base);
    }

    @Test
    public void whenUpdateTwiceThenVersionIs() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        Base base = new Base(1, 0);
        cache.delete(base);
        base = new Base(1, 10);
        cache.add(base);
        assertThat(base.getVersion(), is(10));
    }
}