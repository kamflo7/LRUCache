package pl.kflorczyk.lrucache.dto;

public class GetValueResponse {
    private String success;
    private String value;

    public GetValueResponse(boolean success, String value) {
        this.success = success ? "true" : "false";
        this.value = value;
    }
}
