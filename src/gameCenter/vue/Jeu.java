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
    protected static final int MILLISECONDES = 1000 / 30;
    private Timer chrono;

    public Jeu(Window fenetre) {
        setMaximumSize(Utilites.tailleMax());
        chrono = new Timer(MILLISECONDES, (e) -> {
            // fenetre.revalidate();
            fenetre.repaint();
        });
        chrono.start();

    }

    protected abstract void dessiner(Graphics2D g, Duration delta);

    @Override
    protected void paintComponent(Graphics g) {
        dessiner((Graphics2D) g, Duration.ofMillis(MILLISECONDES));
    }
}
