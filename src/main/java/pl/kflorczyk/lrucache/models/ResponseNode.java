package pl.kflorczyk.lrucache.models;

public class ResponseNode {
    private String success;
    private String value;

    public ResponseNode(boolean success, String value) {
        this.success = success ? "true" : "false";
        this.value = value;
    }

    public String getSuccess() {
        return success;
    }

    public String getData() {
        return value;
    }
}
