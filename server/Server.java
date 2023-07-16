package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final String ADDRESS = "127.0.0.1";
    private final int PORT = 23456;

    private static final Server INSTANCE = new Server();

    private final ExecutorService executor;

    private boolean exit = false;

    private Server() {
        int poolSize = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(poolSize);
    }

    public static Server getInstance() {
        return Server.INSTANCE;
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            while (!exit) {
                Session session = new Session(server.accept());
                executor.submit(session);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        this.exit = true;
    }
}
