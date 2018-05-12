package pl.kflorczyk.lrucache.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Service
public class LRUCacheService {
    private int maxCapacity = 0;

    private Map<String, String> map = new HashMap<>();
    private LinkedList<String> lastUsed = new LinkedList<>();

    public LRUCacheService(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LRUCacheService() {
        this(5);
    }

    public void put(String key, String value) {
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

    public String get(String key) {
        if(map.containsKey(key)) {
            lastUsed.remove(key);
            lastUsed.addFirst(key);
            return map.get(key);
        }
        return null;
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
