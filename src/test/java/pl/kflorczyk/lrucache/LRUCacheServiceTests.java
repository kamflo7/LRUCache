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

    private LRUCache lruCache;

    @Before
    public void init() {
        lruCache = new LRUCache();
    }

    @Test
    public void whenSetLruCacheTheMechanismIsCorrect() {
        lruCache.put("key1", 1);
        lruCache.put("key2", 1);
        lruCache.put("key3", 1);
        lruCache.put("key4", 1);
        lruCache.put("key5", 1);

        Object lastUsedObject = ReflectionTestUtils.getField(lruCache, "lastUsed");
        Object mapObject = ReflectionTestUtils.getField(lruCache, "map");
        LinkedList<String> lastUsed = null;
        Map<String, Integer> map = null;

        if(lastUsedObject instanceof LinkedList) {
            lastUsed = (LinkedList<String>) lastUsedObject;
        } else {
            fail("Property lastUsed in the class LRUCache is not an instance of LinkedList");
        }

        if(mapObject instanceof Map) {
            map = (Map<String, Integer>) mapObject;
        } else {
            fail("Property map in the class LRUCache is not an instance of Map");
        }

        if(!listValuesAreEqualTo(lastUsed, "key5", "key4", "key3", "key2", "key1")) fail("LRUCache::set mechanism is not valid");

        lruCache.put("key2", 1);
        lruCache.put("key5", 1);
        lruCache.put("key3", 1);
        if(!listValuesAreEqualTo(lastUsed, "key3", "key5", "key2", "key4", "key1")) fail("LRUCache::set mechanism is not valid");

        lruCache.put("key6", 2);
        lruCache.put("key7", 2);
        lruCache.put("key2", 2);
        if(!listValuesAreEqualTo(lastUsed, "key2", "key7", "key6", "key3", "key5")) fail("LRUCache::set mechanism is not valid");

        assertTrue(map.size() == lastUsed.size());
        assertTrue(mapContainsAllKeys(map, "key2", "key7", "key6", "key3", "key5"));
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
