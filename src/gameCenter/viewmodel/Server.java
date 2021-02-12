package gameCenter.viewmodel;

import gameCenter.model.*;
import java.io.*;
import java.net.*;

public class Server {
    public static ServerSocket listener;

    public static void main(String[] args) throws IOException {
        listener = new ServerSocket(Constants.PORT);
        while (true) {
            var client = listener.accept();
            System.out.println(client.getInetAddress().getCanonicalHostName() + " connected");
        }
    }
}
