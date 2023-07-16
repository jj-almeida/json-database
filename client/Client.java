package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private final int SERVER_PORT = 23456;

    private static final Client INSTANCE = new Client();

    private Client() {
    }

    public static Client getINSTANCE() {
        return Client.INSTANCE;
    }

    public void start(String[] args) {
        try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT)) {
            new Session(socket, args).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
