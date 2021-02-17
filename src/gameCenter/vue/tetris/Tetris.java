package gameCenter.vue.tetris;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.time.Duration;

import javax.swing.JOptionPane;

import gameCenter.controlleur.Client;
import gameCenter.controlleur.dessin.*;
import gameCenter.modele.Constantes;
import gameCenter.vue.*;

public class Tetris extends Jeu {

    /**
     *
     */
    private static final long serialVersionUID = 3718214623734367119L;

    public static final int TAILLE_BLOC = 16;

    private Bloc[][] plateau;
    private int score;
    private gameCenter.controlleur.dessin.Rectangle fond;
    private ObjectInputStream serialiseur;
    private Thread sync;
    private Object mutex;
    private KeyEventDispatcher evenementTouche;
    private Forme formeActuelle;

    public Tetris(Window fenetre) {
        super(fenetre);
        plateau = new Bloc[Constantes.TETRIS_LARGEUR][Constantes.TETRIS_HAUTEUR];
        setMinimumSize(new Dimension(Constantes.TETRIS_LARGEUR * TAILLE_BLOC, Constantes.TETRIS_HAUTEUR * TAILLE_BLOC));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());
        score = 0;
        fond = new gameCenter.controlleur.dessin.Rectangle();
        fond.setCouleur(new Color(20, 20, 60));
        fond.setTaille(new Vecteur(Constantes.TETRIS_LARGEUR * TAILLE_BLOC, Constantes.TETRIS_HAUTEUR * TAILLE_BLOC));
        formeActuelle = new Forme(Forme.S_AUTISTE);
        formeActuelle.setPosition(new Vecteur(50, 50));
        try {
            serialiseur = new ObjectInputStream(Client.socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                    "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
            return;
        }
        mutex = new Object();
        sync = new Thread(() -> {
            while (true) {
                try {
                    score = Client.socket.getInputStream().read();
                    var blocs = (Color[][]) serialiseur.readObject();
                    synchronized (mutex) {
                        for (int i = 0; i < Constantes.TETRIS_LARGEUR; ++i)
                            for (int j = 0; j < Constantes.TETRIS_HAUTEUR; ++j) {
                                if (blocs[i][j] == null)
                                    plateau[i][j] = null;
                                else {
                                    plateau[i][j] = new Bloc(blocs[i][j], TAILLE_BLOC);
                                    plateau[i][j].setPosition(new Vecteur(i * TAILLE_BLOC, j * TAILLE_BLOC));
                                }
                            }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                            "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                            "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(evenementTouche = new KeyEventDispatcher() {

                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                                formeActuelle.orienter(formeActuelle.orientation() + 1);
                        }
                        return false;
                    }
                });
        // sync.start();
    }

    @Override
    protected void dessiner(Graphics2D g, Duration delta) {
        fond.dessiner(g);
        synchronized (mutex) {

            // for (int i = 0; i < Constantes.TETRIS_LARGEUR; ++i)
            // for (int j = 0; j < Constantes.TETRIS_HAUTEUR; ++j)
            // if (plateau[i][j] != null)
            // plateau[i][j].dessiner(g);

            formeActuelle.dessiner(g);
        }
    }
}
