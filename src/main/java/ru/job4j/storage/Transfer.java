package ru.job4j.storage;

public interface Transfer {
    boolean transfer(int fromId, int toId, int amount);
}
