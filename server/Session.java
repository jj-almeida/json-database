package server;

import com.google.gson.Gson;
import server.commands.*;
import server.dto.Request;
import server.dto.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidParameterException;

public class Session implements Runnable {
    private final Socket socket;

    public Session(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                DataInputStream input = new DataInputStream(this.socket.getInputStream());
                DataOutputStream output = new DataOutputStream(this.socket.getOutputStream())
        ) {

            Request request = new Gson().fromJson(input.readUTF(), Request.class); // reading a message
            System.out.println("Received: " + new Gson().toJson(request));

            Command cmd = buildCmd(request);

            Invoker invoker = new Invoker();
            invoker.setCommand(cmd);

            Response response = invoker.executeCommand();

            output.writeUTF(new Gson().toJson(response)); // resend it to the client
            System.out.println("Sent: " + new Gson().toJson(response));

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Command buildCmd(Request request) {
        switch (request.getType()) {
            case "get" -> {
                return new GetCmd(request.getKey());
            }
            case "set" -> {
                return new SetCmd(request.getKey(), request.getValue());
            }
            case "delete" -> {
                return new DelCmd(request.getKey());
            }
            case "exit" -> {
                return new ExitCmd();
            }
            default -> throw new InvalidParameterException("Invalid type");
        }
    }
}
