package pl.kflorczyk.lrucache.models;

public class NodeDTO {
    private String key;
    private String value;

    public NodeDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public NodeDTO() {}

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
