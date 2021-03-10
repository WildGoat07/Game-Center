package gameCenter.controlleur;

import java.net.Socket;
import java.time.LocalTime;
import java.util.*;

import gameCenter.modele.Constantes;

import java.awt.*;
import java.io.*;

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
                plateau[i][j] = null;
        ObjectOutputStream serialiseurSortie;
        ObjectInputStream serialiseurEntree;
        try {
            serialiseurSortie = new ObjectOutputStream(client.getOutputStream());
            serialiseurEntree = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                    + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
            return;
        }
        boolean perdu = false;
        while (!perdu) {
            try {
                client.getOutputStream().write(score);
                // merci Java qui "optimise" le code, obligé de passer par une copie
                var copie = new Color[Constantes.TETRIS_LARGEUR][Constantes.TETRIS_HAUTEUR];
                for (int i = 0; i < Constantes.TETRIS_LARGEUR; ++i)
                    for (int j = 0; j < Constantes.TETRIS_HAUTEUR; ++j)
                        copie[i][j] = plateau[i][j];
                serialiseurSortie.writeObject(copie);
                client.getOutputStream().write(generateur.nextInt(7));// forme
                // on attends le client...
                var couleur = (Color) serialiseurEntree.readObject();
                var blocsX = (int[]) serialiseurEntree.readObject();
                var blocsY = (int[]) serialiseurEntree.readObject();
                for (var i = 0; i < 4; ++i)
                    if (plateau[blocsX[i]][blocsY[i]] == null)
                        plateau[blocsX[i]][blocsY[i]] = couleur;
                    else
                        perdu = true;
                int bonusBase = 1;
                int pointMarques = 0;
                for (var j = 0; j < Constantes.TETRIS_HAUTEUR; ++j) {
                    boolean ligneComplete = true;
                    for (var i = 0; i < Constantes.TETRIS_LARGEUR; ++i)
                        if (plateau[i][j] == null)
                            ligneComplete = false;
                    if (ligneComplete) {
                        pointMarques += bonusBase;
                        ++bonusBase;
                        for (var decal = j; decal > 0; --decal)
                            for (var i = 0; i < Constantes.TETRIS_LARGEUR; ++i)
                                plateau[i][decal] = plateau[i][decal - 1];
                        for (var i = 0; i < Constantes.TETRIS_LARGEUR; ++i)
                            plateau[i][0] = null;
                    }
                }
                score += pointMarques;
                client.getOutputStream().write(perdu ? 0 : 1);
            } catch (IOException e) {
                System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                        + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
                return;
            } catch (ClassNotFoundException e) {
                System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                        + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
                return;
            }
        }
    }
}
