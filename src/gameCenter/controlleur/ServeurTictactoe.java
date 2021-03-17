package gameCenter.controlleur;

import gameCenter.modele.Constantes;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class ServeurTictactoe extends JFrame {

    private class Partie {
        public Socket joueur1;
        public Socket joueur2;
        public Boolean[] listeEtat;
        public String identifiant;
    }

    private Socket client;
    private static final List<Partie> parties = new LinkedList<>();

    public ServeurTictactoe(Socket client) {
        this.client = client;
        build();
    }

    public void demarrer() {
        ObjectOutputStream envoyer;
        ObjectInputStream recevoir;
        try {
            envoyer = new ObjectOutputStream(client.getOutputStream());
            recevoir = new ObjectInputStream(client.getInputStream());

            int choix = client.getInputStream().read();
            if (choix == Constantes.TICTACTOE_HOTE) {
                final Partie partie = new Partie();
                partie.joueur1 = client;
                partie.joueur2 = null;
                partie.listeEtat = new Boolean[9];
                for (int i = 0; i < 9; i++)
                    partie.listeEtat[i] = null;
                partie.identifiant = (String) recevoir.readObject();

                synchronized (parties) {
                    parties.add(partie);
                }

                while (true) {
                    synchronized (partie) {
                        if (partie.joueur2 != null)
                            break;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            } else if (choix == Constantes.TICTACTOE_INVITE) {
                String identifiant = (String) recevoir.readObject();
                Partie trouver = null;

                synchronized (parties) {
                    for (Partie part : parties) {
                        synchronized (part) {
                            if (part.identifiant.equals(identifiant) && part.joueur2 == null) {
                                trouver = part;
                                break;
                            }
                        }
                    }
                }
                if (trouver != null) {
                    synchronized (trouver) {
                        trouver.joueur2 = client;
                        client.getOutputStream().write(Constantes.TICTACTOE_TROUVER);
                    }
                } else {
                    client.getOutputStream().write(Constantes.TICTACTOE_NON_TROUVER);
                }
            }


        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                    + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
            return;
        }


    }

    private void build() {

    }
}
