package pl.kflorczyk.lrucache.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Service
public class LRUCache {
    private int maxCapacity = 0;

    private Map<String, Integer> map = new HashMap<>();
    private LinkedList<String> lastUsed = new LinkedList<>();

    public LRUCache(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

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

    public int get(String key) {
        if(map.containsKey(key)) {
            lastUsed.remove(key);
            lastUsed.addFirst(key);
            return map.get(key);
        }
        return -1;
    }

    public void changeCapactity(int capacity) {
        int currentCapacityDifference = map.size() - capacity;

        if(currentCapacityDifference > 0) {
            for(int i=0; i<currentCapacityDifference; i++) {
                String lastKey = lastUsed.removeLast();
                map.remove(lastKey);
            }
        }

        maxCapacity = capacity;
    }

    public void invalidate() {
        map.clear();
        lastUsed.clear();
    }
}
