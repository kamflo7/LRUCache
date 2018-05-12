package pl.kflorczyk.lrucache.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Service
public class LRUCache {
    private int maxCapacity = 5;

    private Map<String, Integer> map = new HashMap<>();
    private LinkedList<String> lastUsed = new LinkedList<>();

    public void put(String key, int value) {
        if(map.containsKey(key)) {
            lastUsed.remove(key);
            lastUsed.addFirst(key);
        } else {
            if(map.size() == maxCapacity) {
                String lastKey = lastUsed.getLast();
                map.remove(lastKey);
                lastUsed.removeLast();
            }

            lastUsed.addFirst(key);
        }

        map.put(key, value);
    }
}
