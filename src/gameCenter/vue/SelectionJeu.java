package gameCenter.vue;

import javax.swing.*;

import gameCenter.controlleur.*;
import gameCenter.modele.*;
import gameCenter.vue.tetris.Tetris;
import gameCenter.vue.ticTacToe.TicTacToe;

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
        listeBoutons.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        listeBoutons.setBackground(Color.black);
        listeBoutons.add(Box.createVerticalGlue());

        var tetris = new JButton("Tetris");
        tetris.setBackground(Color.white);
        tetris.setPreferredSize(new Dimension(110, 40));
        listeBoutons.add(tetris);
        tetris.setAlignmentX(Component.RIGHT_ALIGNMENT);
        listeBoutons.add(Box.createVerticalStrut(10));
        var demineur = new JButton("Demineur");
        demineur.setBackground(Color.white);
        demineur.setPreferredSize(new Dimension(110, 40));
        listeBoutons.add(demineur);
        demineur.setAlignmentX(Component.RIGHT_ALIGNMENT);
        listeBoutons.add(Box.createVerticalStrut(10));
        var pong = new JButton("Pong");
        pong.setBackground(Color.white);
        pong.setPreferredSize(new Dimension(110, 40));
        listeBoutons.add(pong);
        pong.setAlignmentX(Component.RIGHT_ALIGNMENT);
        listeBoutons.add(Box.createVerticalStrut(10));
        var tictactoe = new JButton("Tic-Tac-Toe");
        tictactoe.setBackground(Color.white);
        tictactoe.setPreferredSize(new Dimension(110, 40));
        listeBoutons.add(tictactoe);
        tictactoe.setAlignmentX(Component.RIGHT_ALIGNMENT);
        listeBoutons.add(Box.createVerticalStrut(10));
        var retour = new JButton("Quitter");
        retour.setBackground(Color.white);
        retour.setPreferredSize(new Dimension(110, 40));
        listeBoutons.add(retour);
        retour.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        retour.setAlignmentX(Component.RIGHT_ALIGNMENT);

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
            demineur.setEnabled(true);
            pong.setEnabled(true);
            tictactoe.setEnabled(true);
        }));
        demineur.addActionListener((e) -> Client.asynchrone(() -> {
            try {
                Client.socket.getOutputStream().write(Constantes.CHOIX_DEMINEUR);
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
            tictactoe.setEnabled(true);
        }));
        pong.addActionListener((e) -> Client.asynchrone(() -> {
            try {
                Client.socket.getOutputStream().write(Constantes.CHOIX_PONG);
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
                fenetre.remove(instance);
                System.out.println("ftr");
                fenetre.add(new TicTacToe(fenetre));
                fenetre.revalidate();
            }
            tetris.setEnabled(true);
            demineur.setEnabled(true);
            pong.setEnabled(true);
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
