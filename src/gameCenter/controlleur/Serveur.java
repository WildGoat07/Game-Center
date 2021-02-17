package gameCenter.controlleur;

import java.io.*;
import java.net.*;
import java.time.LocalTime;

import gameCenter.modele.*;

public class Serveur {
    public static ServerSocket ecoute;

    public static void main(String[] args) throws IOException {
        ecoute = new ServerSocket(Constantes.PORT);
        System.out.println("Serveur lancé sur le port " + Constantes.PORT);
        while (true) {
            var client = ecoute.accept();
            new Thread(() -> gestionClient(client)).start();
        }
    }

    public static void gestionClient(Socket client) {
        System.out.println(
                "[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":" + LocalTime.now().getSecond()
                        + "] " + client.getInetAddress().getCanonicalHostName() + " s'est connecté");
        try {
            var choixJeu = client.getInputStream().read();
            switch (choixJeu) {
                case Constantes.CHOIX_TETRIS: {
                    new ServeurTetris(client).demarrer();
                }
                    break;
                case Constantes.CHOIX_DEMINEUR: {

                }
                    break;
                case Constantes.CHOIX_PONG: {

                }
                    break;
                case Constantes.CHOIX_TICTACTOE: {
                        new ServeurTictactoe(client).demarrer();
                    }
                        break;
            }
        } catch (IOException e) {
            System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                    + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
        }
        System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                + LocalTime.now().getSecond() + "] déconnexion du client.");
        try {
            client.close();
        } catch (IOException e2) {
        }
    }
}
