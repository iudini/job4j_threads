package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage implements Storage, Transfer {
    private final Map<Integer, User> store = new HashMap<>();

    @Override
    public synchronized boolean add(User user) {
        return store.putIfAbsent(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean update(User user) {
        return store.replace(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
        return store.remove(user.getId()) != null;
    }

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User userFrom = store.get(fromId);
        User userTo = store.get(toId);
        if (userFrom == null || userTo == null || userFrom.getAmount() < amount) {
            return false;
        }
        userFrom.setAmount(userFrom.getAmount() - amount);
        userTo.setAmount(userTo.getAmount() + amount);
        return true;
    }
}
