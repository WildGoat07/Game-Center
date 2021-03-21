package gameCenter.vue.ticTacToe;

import gameCenter.controlleur.Client;
import gameCenter.controlleur.Utilites;
import gameCenter.modele.Constantes;
import gameCenter.vue.tetris.Tetris;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ChoixHote extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 8474902020304065203L;
    private JButton btn_connexion;
    private JButton btn_creationSession;
    private JLabel lbl_erreurHote;
    private JLabel lbl_erreurConnexion;
    private JTextField edt_codeHote;
    private JTextField edt_codeConnexion;

    public ChoixHote(Window fenetre, JComponent menu) {

        var instance = this;

        ObjectOutputStream envoyer;
        try {
            envoyer = new ObjectOutputStream(Client.socket.getOutputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                    "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
            return;
        }
        edt_codeConnexion = new JTextField(10);
        edt_codeConnexion.setMaximumSize(new Dimension(150, 20));
        edt_codeHote = new JTextField(10);
        edt_codeHote.setMaximumSize(new Dimension(150, 20));
        btn_connexion = new JButton("Rejoindre session");
        btn_connexion.setMaximumSize(Utilites.LargeurMax(20));
        btn_connexion.setBackground(Color.WHITE);
        btn_connexion.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        btn_creationSession = new JButton("Créer session");
        btn_creationSession.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        btn_creationSession.setMaximumSize(Utilites.LargeurMax(20));
        btn_creationSession.setBackground(Color.WHITE);
        lbl_erreurHote = new JLabel("Code vide, vérifier la saisie");
        lbl_erreurHote.setForeground(Color.red);
        lbl_erreurHote.setVisible(false);
        lbl_erreurConnexion = new JLabel("Code vide, vérifier la saisie");
        lbl_erreurConnexion.setForeground(Color.red);
        lbl_erreurConnexion.setVisible(false);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        add(new JLabel("Identifiant de session :"));
        add(edt_codeHote);
        add(Box.createVerticalStrut(10));
        add(btn_creationSession);
        add(lbl_erreurHote);
        add(Box.createVerticalStrut(50));
        add(new JLabel("Identifiant de session :"));
        add(edt_codeConnexion);
        add(Box.createVerticalStrut(10));
        add(btn_connexion);
        add(lbl_erreurConnexion);
        add(Box.createVerticalGlue());

        btn_creationSession.addActionListener(e -> {
            if (edt_codeHote.getText().equals("")) {
                lbl_erreurHote.setVisible(true);
            } else {
                lbl_erreurHote.setVisible(false);
                edt_codeConnexion.setEnabled(false);
                edt_codeHote.setEnabled(false);
                btn_connexion.setEnabled(false);
                btn_creationSession.setEnabled(false);

                Client.asynchrone(() -> {
                    try {
                        envoyer.write(Constantes.TICTACTOE_HOTE);
                        envoyer.writeObject(edt_codeHote.getText());
                        if (Client.socket.getInputStream().read() == Constantes.TICTACTOE_INVALIDE) {
                            return new InvalidObjectException("non");
                        }
                    } catch (IOException exc) {
                        return exc;
                    }
                    return null;
                }, (exc) -> {
                    edt_codeConnexion.setEnabled(true);
                    edt_codeHote.setEnabled(true);
                    btn_connexion.setEnabled(true);
                    btn_creationSession.setEnabled(true);
                    if (exc instanceof InvalidObjectException)
                        JOptionPane.showMessageDialog(fenetre, "Partie déjà existante", "Partie déjà existante",
                                JOptionPane.WARNING_MESSAGE);
                    else if (exc != null)
                        JOptionPane.showMessageDialog(fenetre, "Impossible de se connecter : " + exc.getMessage(),
                                "Connexion impossible", JOptionPane.ERROR_MESSAGE);
                    else {
                        fenetre.remove(instance);
                        var label = new JLabel("attente d'un autre joueur...");
                        fenetre.add(label);
                        fenetre.revalidate();
                        Client.asynchrone(() -> {
                            try {
                                Client.socket.getInputStream().read(); // attente d'un joueur
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                System.exit(1);
                            }
                        }, () -> {
                            fenetre.remove(label);
                            fenetre.add(new TicTacToe(fenetre, menu));
                            fenetre.revalidate();
                        });
                    }
                });
            }
        });

        btn_connexion.addActionListener(e -> {
            if (edt_codeConnexion.getText().equals("")) {
                lbl_erreurConnexion.setVisible(true);
            } else {
                lbl_erreurConnexion.setVisible(false);
                edt_codeConnexion.setEnabled(false);
                edt_codeHote.setEnabled(false);
                btn_connexion.setEnabled(false);
                btn_creationSession.setEnabled(false);

                Client.asynchrone(() -> {
                    try {
                        envoyer.write(Constantes.TICTACTOE_INVITE);
                        envoyer.writeObject(edt_codeConnexion.getText());
                        if (Client.socket.getInputStream().read() == Constantes.TICTACTOE_INVALIDE) {
                            return new InvalidObjectException("non");
                        }
                    } catch (IOException exc) {
                        return exc;
                    }
                    return null;
                }, (exc) -> {
                    if (exc != null) {
                        if (exc instanceof InvalidObjectException) {
                            JOptionPane.showMessageDialog(fenetre, "Partie non trouvée", "Partie non trouvée",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(fenetre, "Impossible de se connecter : " + exc.getMessage(),
                                    "Connexion impossible", JOptionPane.ERROR_MESSAGE);
                        }
                        edt_codeConnexion.setEnabled(true);
                        edt_codeHote.setEnabled(true);
                        btn_connexion.setEnabled(true);
                        btn_creationSession.setEnabled(true);
                    } else {
                        fenetre.remove(instance);
                        fenetre.add(new TicTacToe(fenetre, menu));
                        fenetre.revalidate();
                    }
                });
            }
        });
    }
}
