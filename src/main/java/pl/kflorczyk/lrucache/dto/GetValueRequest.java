package pl.kflorczyk.lrucache.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetValueRequest {

    @Getter
    @NonNull
    private String key;

    @Getter
    @NonNull private String value;
}
