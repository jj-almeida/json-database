package server.commands;

import com.google.gson.JsonElement;
import server.dto.Response;

public class GetCmd implements Command {

    private final JsonElement key;

    public GetCmd(JsonElement index) {
        this.key = index;
    }

    @Override
    public Response execute() {
        JsonElement value = database.get(key);

        Response response;

        if (value != null) {
            response = new Response("OK");
            response.setValue(value);
        } else {
            response = new Response("ERROR");
            response.setReason("No such key");
        }

        return response;
    }
}