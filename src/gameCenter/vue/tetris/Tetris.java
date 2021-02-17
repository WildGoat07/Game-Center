package gameCenter.vue.tetris;

import java.awt.*;
import java.awt.geom.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;

import javax.swing.JOptionPane;

import gameCenter.controlleur.Client;
import gameCenter.controlleur.dessin.Rectangle;
import gameCenter.modele.Constantes;
import gameCenter.vue.*;

public class Tetris extends Jeu {

    /**
     *
     */
    private static final long serialVersionUID = 3718214623734367119L;

    private static final int TAILLE_BLOC = 16;

    Bloc[][] plateau;
    int score;
    Rectangle fond;

    public Tetris(Window fenetre) {
        super(fenetre);
        plateau = new Bloc[Constantes.TETRIS_LARGEUR][Constantes.TETRIS_HAUTEUR];
        score = 0;
        fond = new Rectangle();
        fond.setCouleur(new Color(0, 0, 20));
    }

    @Override
    protected void dessiner(Graphics2D g) {
        var tailleFenetre = getSize();

    }

    @Override
    protected void miseAJour(Duration delta) {
        ObjectInputStream serialiseur;
        try {
            serialiseur = new ObjectInputStream(Client.socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                    "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
            return;
        }
        while (true) {
            try {
                score = Client.socket.getInputStream().read();
                var blocs = (Color[][]) serialiseur.readObject();
                for (int i = 0; i < Constantes.TETRIS_LARGEUR; ++i)
                    for (int j = 0; j < Constantes.TETRIS_HAUTEUR; ++j) {
                        if (blocs[i][j] == null)
                            plateau[i][j] = null;
                        else
                            plateau[i][j] = new Bloc(blocs[i][j], TAILLE_BLOC);
                    }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                        "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                        "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
