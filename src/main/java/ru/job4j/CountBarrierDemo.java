package ru.job4j;

public class CountBarrierDemo {
    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(2);
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        countBarrier.count();
                    }
                },
                "First"
        );
        Thread second = new Thread(
                () ->
                    countBarrier.await(),
                "Second"
        );
        first.start();
        second.start();
    }
}
