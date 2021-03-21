package gameCenter.vue.ticTacToe;

import gameCenter.controlleur.Client;
import gameCenter.modele.Constantes;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TicTacToe extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 3718214323734367128L;

    private JButton[] cases;
    private int[] etatPlateau;
    private boolean tourActuel;

    public TicTacToe(Window fenetre, JComponent menu) {
        add(new JButton("retour"));
        var panelPrincipal = new JPanel();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        panelPrincipal.setLayout(new GridLayout(3, 3));
        cases = new JButton[9];
        etatPlateau = new int[9];
        tourActuel = false;
        for (var i = 0; i < 9; ++i) {
            final var index = i;
            var bouton = new JButton();
            bouton.setEnabled(false);
            cases[i] = bouton;
            bouton.setBackground(Color.WHITE);
            bouton.setForeground(Color.BLACK);
            panelPrincipal.add(bouton);
            bouton.addActionListener((e) -> {
                for (var j = 0; j < 9; ++j)
                    cases[j].setEnabled(false);
                Client.asynchrone(() -> {
                    try {
                        Client.socket.getOutputStream().write(Constantes.TICTACTOE_VALIDE);
                        Client.socket.getOutputStream().write(index);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        System.exit(1);
                    }

                }, () -> debutTour());
            });
        }
        add(panelPrincipal);
        debutTour();
    }

    void debutTour() {
        Client.asynchrone(() -> {
            try {
                tourActuel = Client.socket.getInputStream().read() == 1;
                for (int i = 0; i < 9; ++i)
                    etatPlateau[i] = Client.socket.getInputStream().read();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }, () -> {
            for (int i = 0; i < 9; ++i) {
                if (tourActuel) {
                    cases[i].setEnabled(etatPlateau[i] == Constantes.TICTACTOE_CASE_NON_COCHEE);
                    cases[i].setText(etatPlateau[i] == Constantes.TICTACTOE_CASE_JOUEUR1 ? "X"
                            : etatPlateau[i] == Constantes.TICTACTOE_CASE_JOUEUR2 ? "O" : "");
                } else {
                    cases[i].setEnabled(false);
                    cases[i].setText(etatPlateau[i] == Constantes.TICTACTOE_CASE_JOUEUR1 ? "X"
                            : etatPlateau[i] == Constantes.TICTACTOE_CASE_JOUEUR2 ? "O" : "");
                }
            }
            if (!tourActuel)
                debutTour();
        });
    }
}
