package client;

import com.beust.jcommander.JCommander;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Session {
    private final Socket socket;
    private final String[] args;

    public Session(Socket socket, String[] args) {
        this.socket = socket;
        this.args = args;
    }

    public void run() {
        Args jArgs = buildArgs();

        try (
                DataInputStream input = new DataInputStream(this.socket.getInputStream());
                DataOutputStream output = new DataOutputStream(this.socket.getOutputStream())
        ) {
            String request = buildRequest(jArgs);

            output.writeUTF(request); // sending message to the server
            System.out.println("Sent: " + request);

            String receivedMsg = input.readUTF(); // response message
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Args buildArgs() {
        Args jArgs = new Args();
        JCommander.newBuilder()
                .addObject(jArgs)
                .build()
                .parse(this.args);
        return jArgs;
    }

    private String buildRequest(Args jArgs) {

        if (jArgs.getFileName() != null)
            return readFileAsString(jArgs.getFileName());

        JsonObject json = new JsonObject();
        json.addProperty("type", jArgs.getType());

        if (jArgs.getKey() != null)
            json.addProperty("key", jArgs.getKey());

        if (jArgs.getValue() != null)
            json.addProperty("value", jArgs.getValue());

        return json.toString();
    }

    private String readFileAsString(String fileName) {
        String file = System.getProperty("user.dir") + File.separator +
                "JSON Database" + File.separator +
                "task" + File.separator +
                "src" + File.separator +
                "client" + File.separator +
                "data" + File.separator +
                fileName;
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + e.getMessage());
        }
    }
}
