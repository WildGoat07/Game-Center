package gameCenter.vue;

import javax.swing.*;

import gameCenter.controlleur.*;

import java.awt.*;

public abstract class Jeu extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Timer chrono;

    public Jeu(Window fenetre) {
        setMaximumSize(Utilites.tailleMax());
        chrono = new Timer(1000 / 60, (e) -> {
            Client.asynchrone(() -> fenetre.repaint());
        });
        chrono.start();
    }

    protected abstract void dessiner(Graphics2D g);

    @Override
    protected void paintComponent(Graphics g) {
        dessiner((Graphics2D) g);
    }
}
