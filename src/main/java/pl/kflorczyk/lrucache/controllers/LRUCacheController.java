package pl.kflorczyk.lrucache.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kflorczyk.lrucache.models.NodeDTO;
import pl.kflorczyk.lrucache.models.ResponseNode;
import pl.kflorczyk.lrucache.services.LRUCacheService;

@RestController
public class LRUCacheController {

    private LRUCacheService lruCacheService;

    public LRUCacheController(@Autowired LRUCacheService lruCacheService) {
        this.lruCacheService = lruCacheService;
    }

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
}
