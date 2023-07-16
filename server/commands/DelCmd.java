package server.commands;

import com.google.gson.JsonElement;
import server.dto.Response;

public class DelCmd implements Command {

    private final JsonElement key;

    public DelCmd(JsonElement index) {
        this.key = index;
    }

    @Override
    public Response execute() {
        String value = database.delete(key);

        Response response;

        if (value != null) {
            response = new Response("OK");
        } else {
            response = new Response("ERROR");
            response.setReason("No such key");
        }

        return response;
    }
}
