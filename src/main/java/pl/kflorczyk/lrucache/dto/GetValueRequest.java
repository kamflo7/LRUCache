package pl.kflorczyk.lrucache.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetValueRequest {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
