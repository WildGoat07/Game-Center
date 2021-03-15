package gameCenter.vue.tetris;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.time.Duration;

import javax.swing.JComponent;
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
    public static final float VITESSE_DESCENTE = 3;
    public static final float PLUS_RAPIDE_MULT = 3;

    private Bloc[][] plateau;
    private int score;
    private gameCenter.controlleur.dessin.Rectangle fond;
    private ObjectInputStream serialiseurEntree;
    private ObjectOutputStream serialiseurSortie;
    private Thread sync;
    private Object mutex;
    private KeyEventDispatcher evenementTouche;
    private Forme formeActuelle = null;
    private Font font;
    boolean perdu;
    boolean va_plus_vite_s_il_te_plait_petite_piece;

    public Tetris(Window fenetre, JComponent menu) {
        super(fenetre);
        va_plus_vite_s_il_te_plait_petite_piece = false;
        var instance = this;
        plateau = new Bloc[Constantes.TETRIS_LARGEUR][Constantes.TETRIS_HAUTEUR];
        setMinimumSize(new Dimension(Constantes.TETRIS_LARGEUR * TAILLE_BLOC, Constantes.TETRIS_HAUTEUR * TAILLE_BLOC));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());
        score = 0;
        perdu = false;
        fond = new gameCenter.controlleur.dessin.Rectangle();
        fond.setCouleur(new Color(20, 20, 60));
        fond.setTaille(new Vecteur(Constantes.TETRIS_LARGEUR * TAILLE_BLOC, Constantes.TETRIS_HAUTEUR * TAILLE_BLOC));
        try {
            serialiseurEntree = new ObjectInputStream(Client.socket.getInputStream());
            serialiseurSortie = new ObjectOutputStream(Client.socket.getOutputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                    "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        mutex = new Object();
//        try {
//            font = Font.createFont(Font.TRUETYPE_FONT, new File("./assets/fonts/FORCED SQUARE.ttf")).deriveFont(18f);
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
//                    "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
//        } catch (FontFormatException e) {
//            JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
//                    "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
//        }
        sync = new Thread(() -> {
            while (true) {
                try {
                    var score = Client.socket.getInputStream().read();
                    var blocs = (Color[][]) serialiseurEntree.readObject();
                    var typeForme = Client.socket.getInputStream().read();
                    synchronized (mutex) {
                        this.score = score;
                        formeActuelle = new Forme(typeForme);
                        formeActuelle.setPosition(new Vecteur((Constantes.TETRIS_LARGEUR / 2 - 1) * TAILLE_BLOC, 0));
                        for (int i = 0; i < Constantes.TETRIS_LARGEUR; ++i)
                            for (int j = 0; j < Constantes.TETRIS_HAUTEUR; ++j)
                                if (blocs[i][j] == null)
                                    plateau[i][j] = null;
                                else {
                                    plateau[i][j] = new Bloc(blocs[i][j], TAILLE_BLOC);
                                    plateau[i][j].setPosition(new Vecteur(i * TAILLE_BLOC, j * TAILLE_BLOC));
                                }
                    }
                    boolean tourActuel = true;
                    final float delta = 1f / 60;
                    final long attenteMillisecondes = 1000 / 60;
                    while (tourActuel) {
                        long millisecondes = System.nanoTime() / 1000000;
                        synchronized (mutex) {
                            formeActuelle
                                    .deplacerPosition(new Vecteur(0, VITESSE_DESCENTE * TAILLE_BLOC * (va_plus_vite_s_il_te_plait_petite_piece ? PLUS_RAPIDE_MULT : 1)).multiplier(delta));
                            if (collisionPlateau(formeActuelle.collisionFuture(formeActuelle.orientation()))) {
                                tourActuel = false;
                                break;
                            }
                        }
                        millisecondes = System.nanoTime() / 1000000 - millisecondes;
                        if (millisecondes < attenteMillisecondes)
                            try {
                                Thread.sleep(attenteMillisecondes - millisecondes);
                            } catch (InterruptedException e) {
                            }
                    }
                    int[] collisionX = new int[4];
                    int[] collisionY = new int[4];
                    Color couleur;
                    synchronized (mutex) {
                        var collision = formeActuelle.collisionFuture(formeActuelle.orientation());
                        for (var i = 0; i < 4; ++i) {
                            collisionX[i] = ((int) collision[i].x) / TAILLE_BLOC;
                            collisionY[i] = ((int) collision[i].y) / TAILLE_BLOC;
                        }
                        couleur = formeActuelle.getCouleur();
                    }
                    serialiseurSortie.writeObject(couleur);
                    serialiseurSortie.writeObject(collisionX);
                    serialiseurSortie.writeObject(collisionY);
                    if (Client.socket.getInputStream().read() == 0) {
                        perdu = true;
                        break;
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                            "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                } catch (ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                            "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
            chrono.stop();
            repaint();
            formeActuelle = null;
        });
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(evenementTouche = new KeyEventDispatcher() {

                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            if (!perdu && e.getKeyCode() == KeyEvent.VK_S)
                                va_plus_vite_s_il_te_plait_petite_piece = true;
                            if (!perdu && e.getKeyCode() == KeyEvent.VK_A && !collisionPlateau(
                                    formeActuelle.collisionFuture(formeActuelle.orientation() + 1)))
                                formeActuelle.orienter(formeActuelle.orientation() + 1);
                            if (!perdu && e.getKeyCode() == KeyEvent.VK_E && !collisionPlateau(
                                    formeActuelle.collisionFuture(formeActuelle.orientation() - 1)))
                                formeActuelle.orienter(formeActuelle.orientation() - 1);
                            if (!perdu && e.getKeyCode() == KeyEvent.VK_Q && !collisionPlateau(
                                    formeActuelle.collisionFuture(formeActuelle.orientation(), -1)))
                                formeActuelle.deplacerPosition(new Vecteur(-TAILLE_BLOC, 0));
                            if (!perdu && e.getKeyCode() == KeyEvent.VK_D
                                    && !collisionPlateau(formeActuelle.collisionFuture(formeActuelle.orientation(), 1)))
                                formeActuelle.deplacerPosition(new Vecteur(TAILLE_BLOC, 0));
                            if (e.getKeyCode() == KeyEvent.VK_ENTER && perdu) {
                                fenetre.remove(instance);
                                fenetre.add(menu);
                                fenetre.revalidate();
                                fenetre.repaint();
                                KeyboardFocusManager.getCurrentKeyboardFocusManager()
                                        .removeKeyEventDispatcher(evenementTouche);
                            }
                        }
                        else if (e.getID() == KeyEvent.KEY_RELEASED)
                        {
                            if (!perdu && e.getKeyCode() == KeyEvent.VK_S)
                                va_plus_vite_s_il_te_plait_petite_piece = false;
                        }
                        return false;
                    }
                });
        sync.start();
    }

    private boolean collisionPlateau(Vecteur[] pts) {
        for (var pt : pts) {
            int x = ((int) pt.x) / TAILLE_BLOC;
            int y = ((int) pt.y) / TAILLE_BLOC + 1;
            if (x < 0)
                return true;
            if (x >= Constantes.TETRIS_LARGEUR)
                return true;
            if (y >= Constantes.TETRIS_HAUTEUR)
                return true;
            if (y < 0)
                return false;
            if (plateau[x][y] != null)
                return true;
        }
        return false;
    }

    @Override
    protected void dessiner(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        fond.dessiner(g);
        synchronized (mutex) {

            if (plateau != null)
                for (int i = 0; i < Constantes.TETRIS_LARGEUR; ++i)
                    for (int j = 0; j < Constantes.TETRIS_HAUTEUR; ++j)
                        if (plateau[i][j] != null)
                            plateau[i][j].dessiner(g);

            if (formeActuelle != null)
                formeActuelle.dessiner(g);
            g.setColor(Color.BLACK);
            g.setFont(font);
            if (perdu) {
                g.drawString("Perdu !", TAILLE_BLOC * Constantes.TETRIS_LARGEUR + 10, 20);
                g.drawString("Score : " + score, TAILLE_BLOC * Constantes.TETRIS_LARGEUR + 10, 40);
                g.drawString("Appuyez sur Entrée pour retourner au menu", TAILLE_BLOC * Constantes.TETRIS_LARGEUR + 10,
                        60);
            } else {
                g.drawString("Score : " + score, TAILLE_BLOC * Constantes.TETRIS_LARGEUR + 10, 20);
                g.drawString("Contrôles :", TAILLE_BLOC * Constantes.TETRIS_LARGEUR + 10, 40);
                g.drawString("A, E : tourner la pièce", TAILLE_BLOC * Constantes.TETRIS_LARGEUR + 30, 60);
                g.drawString("Q, D : déplacer la pièce", TAILLE_BLOC * Constantes.TETRIS_LARGEUR + 30, 80);
            }
        }
    }
}
