package gameCenter.vue;

import javax.swing.*;

import gameCenter.controlleur.*;

import java.awt.*;
import java.time.Duration;

public abstract class Jeu extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final int MILLISECONDES = 1000 / 30;
    private Timer chrono;

    protected Object mutex;

    public Jeu(Window fenetre) {
        setMaximumSize(Utilites.tailleMax());
        mutex = new Object();
        chrono = new Timer(MILLISECONDES, (e) -> {
            synchronized (mutex) {
                miseAJour(Duration.ofMillis(MILLISECONDES));
                Client.asynchrone(() -> {
                    synchronized (mutex) {
                        fenetre.repaint();
                    }
                });
            }
        });
        chrono.start();
    }

    protected abstract void dessiner(Graphics2D g);

    protected abstract void miseAJour(Duration delta);

    @Override
    protected void paintComponent(Graphics g) {
        dessiner((Graphics2D) g);
    }
}
