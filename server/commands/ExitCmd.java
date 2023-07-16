package server.commands;

import server.Server;
import server.dto.Response;

public class ExitCmd implements Command {
    @Override
    public Response execute() {
        Server.getInstance().exit();
        return new Response("OK");
    }
}