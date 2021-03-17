package gameCenter.vue;

import javax.swing.*;

import gameCenter.controlleur.*;
import gameCenter.modele.*;
import gameCenter.vue.tetris.Tetris;
import gameCenter.vue.allumettes.Allumettes;

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
        var allumettes = new JButton("Allumettes");
        listeBoutons.add(allumettes);
        allumettes.setAlignmentX(Component.CENTER_ALIGNMENT);
        listeBoutons.add(Box.createVerticalStrut(10));
        var tictactoe = new JButton("Tic-Tac-Toe");
        listeBoutons.add(tictactoe);
        tictactoe.setAlignmentX(Component.CENTER_ALIGNMENT);
        listeBoutons.add(Box.createVerticalStrut(10));
        var retour = new JButton("Quitter");
        listeBoutons.add(retour);
        retour.setAlignmentX(Component.CENTER_ALIGNMENT);

        listeBoutons.add(Box.createVerticalGlue());

        tetris.addActionListener((e) -> Client.asynchrone(() -> {
            try {
                Client.socket.getOutputStream().write(Constantes.CHOIX_TETRIS);
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
                fenetre.add(new Tetris(fenetre, instance));
                fenetre.revalidate();
            }
            tetris.setEnabled(true);
            allumettes.setEnabled(true);
            tictactoe.setEnabled(true);
        }));
        allumettes.addActionListener((e) -> Client.asynchrone(() -> {
            try {
                Client.socket.getOutputStream().write(Constantes.CHOIX_ALLUMETTES);
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
                fenetre.add(new Allumettes(fenetre, instance));
                fenetre.revalidate();
            }
            tetris.setEnabled(true);
            allumettes.setEnabled(true);
            tictactoe.setEnabled(true);
        }));
        tictactoe.addActionListener((e) -> Client.asynchrone(() -> {
            try {
                Client.socket.getOutputStream().write(Constantes.CHOIX_TICTACTOE);
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
            allumettes.setEnabled(true);
            tictactoe.setEnabled(true);
        }));
        retour.addActionListener((e) -> Client.asynchrone(() -> {
            try {
                Client.socket.getOutputStream().write(Constantes.CHOIX_DECONNEXION);
            } catch (IOException exc) {
                return exc;
            }
            return null;
        }, (exc) -> {
            if (exc != null) {
                JOptionPane.showMessageDialog(fenetre, "Impossible de se connecter : " + exc.getMessage(),
                        "Connexion impossible", JOptionPane.ERROR_MESSAGE);
            } else {
                System.exit(0);
            }
            System.exit(-2);
        }));

        var presentation = new JLabel();
        presentation.setAlignmentX(Component.CENTER_ALIGNMENT);
        presentation.setAlignmentY(Component.CENTER_ALIGNMENT);
        presentation.setMaximumSize(Utilites.tailleMax());
        add(presentation, BorderLayout.CENTER);
        presentation.setBackground(Color.RED);
    }
}
