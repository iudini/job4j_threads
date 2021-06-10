package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static ru.job4j.pool.RolColSum.asyncSum;
import static ru.job4j.pool.RolColSum.sum;

public class RolColSumTest {

    @Test
    public void plainSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] expected = {new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)};
        assertThat(sum(matrix), is(expected));
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        RolColSum.Sums[] expected = {new RolColSum.Sums(10, 28),
                new RolColSum.Sums(26, 32),
                new RolColSum.Sums(42, 36),
                new RolColSum.Sums(58, 40)};
        assertThat(asyncSum(matrix), is(expected));
    }
}