package server;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server started!");
        Server.getInstance().start();
    }
}
