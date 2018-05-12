package pl.kflorczyk.lrucache.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kflorczyk.lrucache.dto.GetValueRequest;
import pl.kflorczyk.lrucache.dto.GetValueResponse;
import pl.kflorczyk.lrucache.services.LRUCacheService;

@RestController
@RequiredArgsConstructor
public class LRUCacheController {

    @NonNull
    private final LRUCacheService lruCacheService;

    @PutMapping
    public void put(@RequestBody GetValueRequest body) {
        lruCacheService.put(body.getKey(), body.getValue());
    }

    @GetMapping(value = "/{key}")
    public GetValueResponse get(@PathVariable("key") String key) {
        String value = lruCacheService.get(key);
        return new GetValueResponse(value != null, value);
    }

    @PostMapping(value = "/capacity")
    public void changeCapacity(@RequestParam("capacity") int capacity) {
        lruCacheService.changeCapactity(capacity);
    }

    @PostMapping(value = "/invalidate")
    public void invalidate() {
        lruCacheService.invalidate();
    }
}
