package server.commands;

import server.database.Database;
import server.dto.Response;

public interface Command {
    Database database = Database.getInstance();

    Response execute();
}
