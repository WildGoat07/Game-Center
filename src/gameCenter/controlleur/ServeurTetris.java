package gameCenter.controlleur;

import java.net.Socket;
import java.time.LocalTime;
import java.util.*;

import gameCenter.modele.Constantes;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServeurTetris {
    private Socket client;
    private int score;
    private Color[][] plateau;
    private Random generateur;

    public ServeurTetris(Socket client) {
        this.client = client;
        generateur = new Random();
    }

    public void demarrer() {
        score = 0;
        plateau = new Color[Constantes.TETRIS_LARGEUR][Constantes.TETRIS_HAUTEUR];
        for (int i = 0; i < Constantes.TETRIS_LARGEUR; ++i)
            for (int j = 0; j < Constantes.TETRIS_HAUTEUR; ++j)
                plateau[i][j] = new Color(generateur.nextInt(256), generateur.nextInt(256), generateur.nextInt(256));
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
                client.getOutputStream().write(score);
                serialiseur.writeObject(plateau);
            } catch (IOException e) {
                System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                        + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
                return;
            }

        }
    }
}
