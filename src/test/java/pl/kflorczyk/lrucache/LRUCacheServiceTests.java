package pl.kflorczyk.lrucache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import pl.kflorczyk.lrucache.services.LRUCache;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.Map;

@RunWith(SpringRunner.class)
public class LRUCacheServiceTests {

    @Test
    public void whenLruCachePutTheMechanismIsCorrect() {
        LRUCache lruCache = new LRUCache(5);

        LinkedList<String> lastUsed = getPropertyLastUsedByReflection(lruCache);
        Map<String, Integer> map = getPropertyMapByReflection(lruCache);

        lruCache.put("key1", 1);
        lruCache.put("key2", 1);
        lruCache.put("key3", 1);
        lruCache.put("key4", 1);
        lruCache.put("key5", 1);
        if(!listValuesAreEqualTo(lastUsed, "key5", "key4", "key3", "key2", "key1")) fail("LRUCache::put mechanism is not correct");

        lruCache.put("key2", 1);
        lruCache.put("key5", 1);
        lruCache.put("key3", 1);
        if(!listValuesAreEqualTo(lastUsed, "key3", "key5", "key2", "key4", "key1")) fail("LRUCache::put mechanism is not correct");

        lruCache.put("key6", 2);
        lruCache.put("key7", 2);
        lruCache.put("key2", 2);
        if(!listValuesAreEqualTo(lastUsed, "key2", "key7", "key6", "key3", "key5")) fail("LRUCache::put mechanism is not correct");

        assertTrue(map.size() == lastUsed.size());
        assertTrue(mapContainsAllKeys(map, "key2", "key7", "key6", "key3", "key5"));
    }

    @Test
    public void whenLruCachePutAndGetTheMechanismIsCorrect() {
        LRUCache lruCache = new LRUCache(5);

        LinkedList<String> lastUsed = getPropertyLastUsedByReflection(lruCache);
        Map<String, Integer> map = getPropertyMapByReflection(lruCache);

        lruCache.put("key1", 1);
        lruCache.put("key2", 1);
        lruCache.put("key3", 1);
        lruCache.put("key4", 1);
        lruCache.put("key5", 1);

        lruCache.get("key2");
        lruCache.get("key3");
        lruCache.put("key6", 2);

        if(!listValuesAreEqualTo(lastUsed, "key6", "key3", "key2", "key5", "key4")) fail("LRUCache::put&get mechanism is not correct");
    }

    @Test
    public void whenLruCacheChangeCapacityTheMechanismIsCorrect() {
        LRUCache lruCache = new LRUCache(3);
        LinkedList<String> lastUsed = getPropertyLastUsedByReflection(lruCache);
        Map<String, Integer> map = getPropertyMapByReflection(lruCache);

        lruCache.put("key1", 1);
        lruCache.put("key2", 1);
        lruCache.put("key3", 1);
        lruCache.changeCapactity(5);
        lruCache.put("key4", 1);
        lruCache.put("key5", 1);
        if(!listValuesAreEqualTo(lastUsed, "key5", "key4", "key3", "key2", "key1")) fail("LRUCache::changeCapactity mechanism is not correct");

        lruCache.changeCapactity(2);
        if(!listValuesAreEqualTo(lastUsed, "key5", "key4")) fail("LRUCache::changeCapactity mechanism is not correct");
        assertTrue(map.size() == lastUsed.size());
    }

    private LinkedList<String> getPropertyLastUsedByReflection(LRUCache lruCache) {
        return (LinkedList<String>) ReflectionTestUtils.getField(lruCache, "lastUsed");
    }

    private Map<String, Integer> getPropertyMapByReflection(LRUCache lruCache) {
        return (Map<String, Integer>) ReflectionTestUtils.getField(lruCache, "map");
    }

    private boolean listValuesAreEqualTo(LinkedList<String> list, String... values) {
        if(list.size() != values.length) return false;

        for(int i=0; i<list.size(); i++) {
            if(!list.get(i).equals(values[i])) return false;
        }

        return true;
    }

    private boolean mapContainsAllKeys(Map<String, Integer> map, String... keys) {
        for(String givenKey : keys) {
            if(!map.containsKey(givenKey)) return false;
        }

        return true;
    }
}
