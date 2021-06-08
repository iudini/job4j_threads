package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ParallelIndexOfTest {
    Character[] array = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars().mapToObj(c -> (char) c).toArray(Character[]::new);

    @Test
    public void indexOfZInAlphabetThen25() {
        ParallelIndexOf<Character> pio = new ParallelIndexOf<>(array, 'Z');
        assertThat(pio.compute(), is(25));
    }

    @Test
    public void indexOfCInAlphabetThen2() {
        ParallelIndexOf<Character> pio = new ParallelIndexOf<>(array, 'C');
        assertThat(pio.compute(), is(2));
    }

    @Test
    public void indexOf1InAlphabetThenMinus1() {
        ParallelIndexOf<Character> pio = new ParallelIndexOf<>(array, '1');
        assertThat(pio.compute(), is(-1));
    }
}