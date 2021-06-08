package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

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
        Integer left = (Integer) leftIndex.join();
        Integer right = (Integer) rightIndex.join();
        return left > right ? left : right;
    }
}
