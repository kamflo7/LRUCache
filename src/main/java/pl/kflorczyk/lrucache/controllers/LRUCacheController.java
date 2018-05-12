package pl.kflorczyk.lrucache.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kflorczyk.lrucache.models.NodeDTO;
import pl.kflorczyk.lrucache.models.ResponseNode;
import pl.kflorczyk.lrucache.services.LRUCacheService;

@RestController
@RequiredArgsConstructor
public class LRUCacheController {

    @NonNull
    private final LRUCacheService lruCacheService;

    @PutMapping(value = "/put", consumes = "application/json")
    public void put(@RequestBody NodeDTO node) {
        lruCacheService.put(node.getKey(), node.getValue());
    }

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseNode get(@RequestParam("key") String key) {
        String value = lruCacheService.get(key);
        ResponseNode response = new ResponseNode(value != null, value);
        return response;
    }

    @PostMapping(value = "/capacity")
    public void changeCapacity(@RequestParam("capacity") int capacity) {
        lruCacheService.changeCapactity(capacity);
    }
}
