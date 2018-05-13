package pl.kflorczyk.lrucache.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import pl.kflorczyk.lrucache.services.LRUCacheService;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.Map;

@RunWith(SpringRunner.class)
public class LRUCacheServiceTests {

    @Test
    public void whenLruCachePutTheMechanismIsCorrect() {
        LRUCacheService lruCacheService = new LRUCacheService();
        lruCacheService.changeCapactity(5);

        LinkedList<String> lastUsed = getPropertyLastUsedByReflection(lruCacheService);
        Map<String, Integer> map = getPropertyMapByReflection(lruCacheService);

        lruCacheService.put("key1", "1");
        lruCacheService.put("key2", "1");
        lruCacheService.put("key3", "1");
        lruCacheService.put("key4", "1");
        lruCacheService.put("key5", "1");
        if(!listValuesAreEqualTo(lastUsed, "key5", "key4", "key3", "key2", "key1")) fail("LRUCacheService::put mechanism is not correct");

        lruCacheService.put("key2", "1");
        lruCacheService.put("key5", "1");
        lruCacheService.put("key3", "1");
        if(!listValuesAreEqualTo(lastUsed, "key3", "key5", "key2", "key4", "key1")) fail("LRUCacheService::put mechanism is not correct");

        lruCacheService.put("key6", "2");
        lruCacheService.put("key7", "2");
        lruCacheService.put("key2", "2");
        if(!listValuesAreEqualTo(lastUsed, "key2", "key7", "key6", "key3", "key5")) fail("LRUCacheService::put mechanism is not correct");

        assertTrue(map.size() == lastUsed.size());
        assertTrue(mapContainsAllKeys(map, "key2", "key7", "key6", "key3", "key5"));
    }

    @Test
    public void whenLruCachePutAndGetTheMechanismIsCorrect() {
        LRUCacheService lruCacheService = new LRUCacheService();
        lruCacheService.changeCapactity(5);

        LinkedList<String> lastUsed = getPropertyLastUsedByReflection(lruCacheService);
        Map<String, Integer> map = getPropertyMapByReflection(lruCacheService);

        lruCacheService.put("key1", "1");
        lruCacheService.put("key2", "1");
        lruCacheService.put("key3", "1");
        lruCacheService.put("key4", "1");
        lruCacheService.put("key5", "1");

        lruCacheService.get("key2");
        lruCacheService.get("key3");
        lruCacheService.put("key6", "2");

        if(!listValuesAreEqualTo(lastUsed, "key6", "key3", "key2", "key5", "key4")) fail("LRUCacheService::put&get mechanism is not correct");
    }

    @Test
    public void whenLruCacheChangeCapacityTheMechanismIsCorrect() {
        LRUCacheService lruCacheService = new LRUCacheService();
        lruCacheService.changeCapactity(3);

        LinkedList<String> lastUsed = getPropertyLastUsedByReflection(lruCacheService);
        Map<String, Integer> map = getPropertyMapByReflection(lruCacheService);

        lruCacheService.put("key1", "1");
        lruCacheService.put("key2", "1");
        lruCacheService.put("key3", "1");
        lruCacheService.changeCapactity(5);
        lruCacheService.put("key4", "1");
        lruCacheService.put("key5", "1");
        if(!listValuesAreEqualTo(lastUsed, "key5", "key4", "key3", "key2", "key1")) fail("LRUCacheService::changeCapactity mechanism is not correct");

        lruCacheService.changeCapactity(2);
        if(!listValuesAreEqualTo(lastUsed, "key5", "key4")) fail("LRUCacheService::changeCapactity mechanism is not correct");
        assertTrue(map.size() == lastUsed.size());
    }

    private LinkedList<String> getPropertyLastUsedByReflection(LRUCacheService lruCacheService) {
        return (LinkedList<String>) ReflectionTestUtils.getField(lruCacheService, "lastUsed");
    }

    private Map<String, Integer> getPropertyMapByReflection(LRUCacheService lruCacheService) {
        return (Map<String, Integer>) ReflectionTestUtils.getField(lruCacheService, "map");
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
