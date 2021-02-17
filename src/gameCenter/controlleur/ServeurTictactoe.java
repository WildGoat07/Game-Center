package gameCenter.controlleur;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;

public class ServeurTictactoe {
    private Socket client;
    
    public ServeurTictactoe (Socket client) {
        this.client = client;
    }

    public void demarrer () {

        ObjectOutputStream serialiseur;
        try {
            serialiseur = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                    + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
            return;
        }

        while (true) {
            try {
                client.getOutputStream().write("hello");
            } catch (IOException e) {
                System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                        + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
                return;
            }

        }
    }
}
