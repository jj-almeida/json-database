package server.dto;

import com.google.gson.JsonElement;

public class Request {
    private String type;
    private JsonElement key;
    private JsonElement value;

    public String getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }
}
