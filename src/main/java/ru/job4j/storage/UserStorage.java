package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage implements Storage, Transfer {
    private final Map<Integer, User> store = new HashMap<>();

    @Override
    public synchronized boolean add(User user) {
        store.put(user.getId(), user);
        return true;
    }

    @Override
    public synchronized boolean update(User user) {
        if (!store.containsKey(user.getId())) {
            return false;
        }
        store.put(user.getId(), user);
        return true;
    }

    @Override
    public synchronized boolean delete(User user) {
        return store.remove(user.getId()) != null;
    }

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (!store.containsKey(fromId) || !store.containsKey(toId)) {
            return false;
        }
        User userFrom = store.get(fromId);
        User userTo = store.get(toId);
        if (userFrom.getAmount() < amount) {
            return false;
        }
        userFrom.setAmount(userFrom.getAmount() - amount);
        userTo.setAmount(userTo.getAmount() + amount);
        return true;
    }
}
