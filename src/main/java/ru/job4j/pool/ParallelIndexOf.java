package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

/**
 * Parallel searching index of any value in array.
 * @param <T>
 */
public class ParallelIndexOf<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T value;

    public ParallelIndexOf(T[] array, T value) {
        this.array = array;
        from = 0;
        to = array.length;
        this.value = value;
    }

    private ParallelIndexOf(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    /**
     * Check if array size is 10 or less, then stop forking and search index.
     * Bring the middle index of array.
     * Splits array on 2 arrays by middle index of initial array and start compute again.
     * @return index of value in array, if not found then return -1.
     */
    @Override
    protected Integer compute() {
        if (from - to <= 10) {
            for (int i = from; i < to; i++) {
                if (array[i].equals(value)) {
                    return i;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelIndexOf<T> leftIndex = new ParallelIndexOf<>(array, from, mid,  value);
        ParallelIndexOf<T> rightIndex = new ParallelIndexOf<>(array, mid, to, value);
        leftIndex.fork();
        rightIndex.fork();
        Integer left = leftIndex.join();
        Integer right = rightIndex.join();
        return Math.max(left, right);
    }
}
