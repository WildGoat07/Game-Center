package gameCenter.vue;

import javax.swing.*;

import gameCenter.controlleur.*;
import gameCenter.modele.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainWindow extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -4428487152788448811L;

    private JPanel PanelConnexion;

    public MainWindow() {
        super();
        var instance = this;
        setSize(300, 150);
        setLocationRelativeTo(null);
        setTitle("Game Center");
        PanelConnexion = new JPanel();
        PanelConnexion.setLayout(new BoxLayout(PanelConnexion, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        add(PanelConnexion, BorderLayout.CENTER);
        var champIP = new JTextField("localhost", 15);
        champIP.setMaximumSize(Utilites.LargeurMax(20));
        champIP.setAlignmentX(Component.CENTER_ALIGNMENT);
        var titre = new JLabel("Adresse du server :");
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        PanelConnexion.add(titre);
        PanelConnexion.add(Box.createVerticalStrut(15));
        PanelConnexion.add(champIP);
        PanelConnexion.add(Box.createVerticalStrut(15));
        var boutonConnexion = new JButton("Se connecter");
        boutonConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonConnexion.setMaximumSize(Utilites.LargeurMax(100));
        boutonConnexion.setPreferredSize(Utilites.LargeurMax(30));
        PanelConnexion.add(boutonConnexion);
        PanelConnexion.add(Box.createVerticalGlue());
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                champIP.selectAll();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }

        });
        boutonConnexion.addActionListener((e) -> {
            boutonConnexion.setEnabled(false);
            champIP.setEnabled(false);
            Client.asynchrone(() -> {
                try {
                    Client.socket = new Socket(champIP.getText(), Constantes.PORT);
                } catch (UnknownHostException exc) {
                    return exc;
                } catch (IOException exc) {
                    return exc;
                }
                return null;
            }, (exc) -> {
                if (exc != null) {
                    if (exc instanceof UnknownHostException) {
                        JOptionPane.showMessageDialog(instance, "Server introuvable", "Connexion impossible",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (exc instanceof IOException) {
                        JOptionPane.showMessageDialog(instance, "Impossible de se connecter : " + exc.getMessage(),
                                "Connexion impossible", JOptionPane.ERROR_MESSAGE);
                    }
                    boutonConnexion.setEnabled(true);
                    champIP.setEnabled(true);
                } else {
                    instance.remove(PanelConnexion);
                    instance.repaint();
                }
            });
        });
    }
}
