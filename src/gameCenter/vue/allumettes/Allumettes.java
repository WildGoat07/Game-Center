package gameCenter.vue.allumettes;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.function.Consumer;

import javax.swing.*;

import gameCenter.controlleur.Client;
import gameCenter.modele.Constantes;

public class Allumettes extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 6881077240097196921L;
    private JPanel conteneur_des_allumettes;
    private JButton bouton_retirer_deux_allumettes;
    private JButton retour;
    private JButton retirer1;
    private Window fenetre;
    private JComponent menu;
    private JLabel compteur;
    private int allumettes_retirees;

    public Allumettes(Window fenetre, JComponent menu) {
        var instance = this;
        this.fenetre = fenetre;
        this.menu = menu;
        allumettes_retirees = 0;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        retour = new JButton("Retour");
        add(retour);
        var panelAction = new JPanel();
        add(panelAction);
        panelAction.setLayout(new BoxLayout(panelAction, BoxLayout.X_AXIS));
        retirer1 = new JButton("Retirer une allumette");
        panelAction.add(retirer1);
        bouton_retirer_deux_allumettes = new JButton("Retirer deux allumettes");
        panelAction.add(bouton_retirer_deux_allumettes);
        compteur = new JLabel();
        panelAction.add(compteur);
        conteneur_des_allumettes = new JPanel();
        add(conteneur_des_allumettes);
        conteneur_des_allumettes.setLayout(new BoxLayout(conteneur_des_allumettes, BoxLayout.X_AXIS));

        retour.addActionListener(e -> {
            retour.setEnabled(false);
            retirer1.setEnabled(false);
            bouton_retirer_deux_allumettes.setEnabled(false);
            Client.asynchrone(() -> {
                try {
                    Client.socket.getOutputStream().write(Constantes.ALLUMETTES_CHOIX_UTILISATEUR_RETOUR);
                    return null;
                } catch (IOException ex) {
                    return ex;
                }
            }, ex -> {
                if (ex != null) {
                    JOptionPane.showMessageDialog(fenetre, "Impossible de se connecter : " + ex.getMessage(),
                            "Connexion impossible", JOptionPane.ERROR_MESSAGE);
                    retour.setEnabled(true);
                    retirer1.setEnabled(true);
                    bouton_retirer_deux_allumettes.setEnabled(true);
                } else {
                    fenetre.remove(instance);
                    fenetre.add(menu);
                    fenetre.revalidate();
                    fenetre.repaint();
                }
            });
        });
        retirer1.addActionListener(e -> retirer_quelques_allumettes(
                Constantes.ALLUMETTES_CHOIX_UTILISATEUR_RETIRER_UNE_ALLUMETTE_DU_PLATEAU_DE_JEU));
        bouton_retirer_deux_allumettes.addActionListener(e -> retirer_quelques_allumettes(
                Constantes.ALLUMETTES_CHOIX_UTILISATEUR_RETIRER_DEUX_ALLUMETTES_DU_PLATEAU_DE_JEU));

        mettre_En_Place_La_Liste_Des_Allumettes_A_Retirer_Du_Jeu();
        fenetre.repaint();
    }

    public void mettre_En_Place_La_Liste_Des_Allumettes_A_Retirer_Du_Jeu() {
        compteur.setText("Allumettes retirées : " + Integer.toString(allumettes_retirees));
        Client.asynchrone(() -> {
            try {
                return Client.socket.getInputStream().read();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
                return Integer.MIN_VALUE;
            }
        }, quantite_d_allumettes_presente_sur_le_plateau_de_jeu -> {
            conteneur_des_allumettes.removeAll();
            for (var i = 0; i < quantite_d_allumettes_presente_sur_le_plateau_de_jeu; ++i) {
                var allumette_a_ajouter = new Baton();
                allumette_a_ajouter.setMinimumSize(allumette_a_ajouter.getPreferredSize());
                allumette_a_ajouter.setMaximumSize(allumette_a_ajouter.getPreferredSize());
                conteneur_des_allumettes.add(allumette_a_ajouter);
                conteneur_des_allumettes.add(Box.createHorizontalStrut(5));
            }
            fenetre.revalidate();
            fenetre.repaint();
            if (quantite_d_allumettes_presente_sur_le_plateau_de_jeu == 1)
                bouton_retirer_deux_allumettes.setEnabled(false);
        });
    }

    void retirer_quelques_allumettes(int cst) {
        var instance = this;
        allumettes_retirees += cst;
        retour.setEnabled(false);
        retirer1.setEnabled(false);
        bouton_retirer_deux_allumettes.setEnabled(false);
        Client.asynchrone(() -> {
            try {
                Client.socket.getOutputStream().write(cst);
                return null;
            } catch (IOException ex) {
                return ex;
            }
        }, ex -> {
            if (ex != null) {
                JOptionPane.showMessageDialog(fenetre, "Impossible de se connecter : " + ex.getMessage(),
                        "Connexion impossible", JOptionPane.ERROR_MESSAGE);
            } else {
                Client.asynchrone(() -> {
                    try {
                        return Client.socket.getInputStream().read();
                    } catch (IOException ex2) {
                        ex2.printStackTrace();
                        System.exit(1);
                        return Integer.MAX_VALUE;
                    }
                }, r -> {
                    switch (r) {
                    case Constantes.ALLUMETTES_ETAT_PARTIE_ORDINATEUR_A_GAGNÉ: {
                        var btn = new JButton("Vous avez perdu !");
                        btn.setForeground(Color.BLACK);
                        btn.setBackground(Color.WHITE);
                        btn.addActionListener(e -> {
                            fenetre.remove(btn);
                            fenetre.add(menu);
                            fenetre.revalidate();
                            fenetre.repaint();
                        });
                        fenetre.remove(instance);
                        fenetre.add(btn);
                        fenetre.revalidate();
                        fenetre.repaint();
                    }
                        break;
                    case Constantes.ALLUMETTES_ETAT_PARTIE_JOUEUR_A_GAGNÉ:
                        var btn = new JButton("Vous avez gagné !");
                        btn.setForeground(Color.BLACK);
                        btn.setBackground(Color.WHITE);
                        btn.addActionListener(e -> {
                            fenetre.remove(btn);
                            fenetre.add(menu);
                            fenetre.revalidate();
                            fenetre.repaint();
                        });
                        fenetre.remove(instance);
                        fenetre.add(btn);
                        fenetre.revalidate();
                        fenetre.repaint();
                        break;
                    case Constantes.ALLUMETTES_ETAT_PARTIE_INDETERMINEE:
                        mettre_En_Place_La_Liste_Des_Allumettes_A_Retirer_Du_Jeu();
                        fenetre.repaint();
                        break;
                    }
                    retour.setEnabled(true);
                    retirer1.setEnabled(true);
                    bouton_retirer_deux_allumettes.setEnabled(true);
                });
            }
        });
    };
}