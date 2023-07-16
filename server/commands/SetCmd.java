package server.commands;

import com.google.gson.JsonElement;
import server.dto.Response;

public class SetCmd implements Command {

    private final JsonElement key;
    private final JsonElement value;

    public SetCmd(JsonElement key, JsonElement value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Response execute() {
        database.set(key, value);
        return new Response("OK");
    }
}