package gameCenter.vue.ticTacToe;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;

import javax.swing.JOptionPane;

import gameCenter.controlleur.Client;
import gameCenter.modele.Constantes;
import gameCenter.vue.Jeu;

public class TicTacToe extends Jeu {

    /**
     *
     */
    private static final long serialVersionUID = 3718214323734367128L;
    
    private static final int TAILLE_CASE = 64;

    private gameCenter.controlleur.dessin.Rectangle fond;
    private ObjectInputStream serialiseur;
    private Thread sync;
    private Object mutex;

    public TicTacToe(Window fenetre) {
        super(fenetre);
        setMinimumSize(new Dimension(Constantes.TICTACTOE_LARGEUR * TAILLE_CASE, Constantes.TICTACTOE_HAUTEUR * TAILLE_CASE));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());
        fond = new gameCenter.controlleur.dessin.Rectangle();
        fond.setCouleur(new Color(0, 20, 0));
        try {
            serialiseur = new ObjectInputStream(Client.socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                    "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    @Override
    protected void dessiner(Graphics2D g, Duration delta) {
        // TODO Auto-generated method stub

    }
    
}
