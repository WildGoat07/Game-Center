package gameCenter.vue;

import javax.swing.*;

import gameCenter.controlleur.*;
import gameCenter.modele.*;
import gameCenter.vue.tetris.Tetris;

import java.awt.*;
import java.io.IOException;

public class SelectionJeu extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -1443254115872951536L;

    public SelectionJeu(Window fenetre) {
        var instance = this;
        setLayout(new BorderLayout());
        fenetre.setSize(600, 500);
        var listeBoutons = new JPanel();
        add(listeBoutons, BorderLayout.WEST);
        listeBoutons.setMaximumSize(Utilites.HauteurMax(150));
        listeBoutons.setLayout(new BoxLayout(listeBoutons, BoxLayout.Y_AXIS));
        listeBoutons.add(Box.createVerticalGlue());

        var tetris = new JButton("Tetris");
        listeBoutons.add(tetris);
        tetris.setAlignmentX(Component.CENTER_ALIGNMENT);
        listeBoutons.add(Box.createVerticalStrut(10));
        var demineur = new JButton("Demineur");
        listeBoutons.add(demineur);
        demineur.setAlignmentX(Component.CENTER_ALIGNMENT);
        listeBoutons.add(Box.createVerticalStrut(10));
        var pong = new JButton("Pong");
        listeBoutons.add(pong);
        pong.setAlignmentX(Component.CENTER_ALIGNMENT);

        listeBoutons.add(Box.createVerticalGlue());

        tetris.addActionListener((e) -> Client.asynchrone(() -> {
            try {
                Client.socket.getOutputStream().write(Constantes.TETRIS);
            } catch (IOException exc) {
                return exc;
            }
            return null;
        }, (exc) -> {
            if (exc != null) {
                JOptionPane.showMessageDialog(fenetre, "Impossible de se connecter : " + exc.getMessage(),
                        "Connexion impossible", JOptionPane.ERROR_MESSAGE);
            } else {
                fenetre.remove(instance);
                fenetre.add(new Tetris(fenetre));
                fenetre.revalidate();
            }
            tetris.setEnabled(true);
            demineur.setEnabled(true);
            pong.setEnabled(true);
        }));
        demineur.addActionListener((e) -> Client.asynchrone(() -> {
            try {
                Client.socket.getOutputStream().write(Constantes.DEMINEUR);
            } catch (IOException exc) {
                return exc;
            }
            return null;
        }, (exc) -> {
            if (exc != null) {
                JOptionPane.showMessageDialog(fenetre, "Impossible de se connecter : " + exc.getMessage(),
                        "Connexion impossible", JOptionPane.ERROR_MESSAGE);
            } else {
            }
            tetris.setEnabled(true);
            demineur.setEnabled(true);
            pong.setEnabled(true);
        }));
        pong.addActionListener((e) -> Client.asynchrone(() -> {
            try {
                Client.socket.getOutputStream().write(Constantes.PONG);
            } catch (IOException exc) {
                return exc;
            }
            return null;
        }, (exc) -> {
            if (exc != null) {
                JOptionPane.showMessageDialog(fenetre, "Impossible de se connecter : " + exc.getMessage(),
                        "Connexion impossible", JOptionPane.ERROR_MESSAGE);
            } else {
            }
            tetris.setEnabled(true);
            demineur.setEnabled(true);
            pong.setEnabled(true);
        }));

        var presentation = new JLabel();
        presentation.setAlignmentX(Component.CENTER_ALIGNMENT);
        presentation.setAlignmentY(Component.CENTER_ALIGNMENT);
        presentation.setMaximumSize(Utilites.tailleMax());
        add(presentation, BorderLayout.CENTER);
        presentation.setBackground(Color.RED);
    }
}
