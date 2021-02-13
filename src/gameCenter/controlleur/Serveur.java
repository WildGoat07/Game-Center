package gameCenter.controlleur;

import java.io.*;
import java.net.*;

import gameCenter.modele.*;

public class Serveur {
    public static ServerSocket ecoute;

    public static void main(String[] args) throws IOException {
        ecoute = new ServerSocket(Constantes.PORT);
        while (true) {
            var client = ecoute.accept();
            System.out.println(client.getInetAddress().getCanonicalHostName() + " connecté");
        }
    }
}
