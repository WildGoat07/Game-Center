package gameCenter.view;

import javax.swing.*;

import gameCenter.model.*;
import gameCenter.viewmodel.*;

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

    private JPanel connectionPanel;

    public MainWindow() {
        super();
        var instance = this;
        setSize(300, 150);
        setLocationRelativeTo(null);
        setTitle("Game Center");
        connectionPanel = new JPanel();
        connectionPanel.setLayout(new BoxLayout(connectionPanel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        add(connectionPanel, BorderLayout.CENTER);
        var ipField = new JTextField("localhost", 15);
        ipField.setMaximumSize(Utilities.getMaxWidth(20));
        ipField.setAlignmentX(Component.CENTER_ALIGNMENT);
        var label = new JLabel("Adresse du server :");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectionPanel.add(label);
        connectionPanel.add(Box.createVerticalStrut(15));
        connectionPanel.add(ipField);
        connectionPanel.add(Box.createVerticalStrut(15));
        var connect = new JButton("Se connecter");
        connect.setAlignmentX(Component.CENTER_ALIGNMENT);
        connect.setMaximumSize(Utilities.getMaxWidth(100));
        connect.setPreferredSize(Utilities.getMaxWidth(30));
        connectionPanel.add(connect);
        connectionPanel.add(Box.createVerticalGlue());
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                ipField.selectAll();
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
        connect.addActionListener((e) -> {
            connect.setEnabled(false);
            ipField.setEnabled(false);
            Client.RunAsynchronously(() -> {
                try {
                    Client.socket = new Socket(ipField.getText(), Constants.PORT);
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
                    connect.setEnabled(true);
                    ipField.setEnabled(true);
                } else {
                    instance.remove(connectionPanel);
                    instance.repaint();
                }
            });
        });
    }
}
