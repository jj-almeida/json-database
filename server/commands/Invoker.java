package server.commands;

import server.dto.Response;

public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public Response executeCommand() {
        return command.execute();
    }
}
