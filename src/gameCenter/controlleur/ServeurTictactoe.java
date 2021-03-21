package gameCenter.controlleur;

import gameCenter.modele.Constantes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ServeurTictactoe {
    private class Partie {
        public Socket joueur2;
        public Boolean[] listeEtat;
        public String identifiant;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Partie)
                return identifiant.equals(((Partie) obj).identifiant);
            else
                return false;
        }

        @Override
        public int hashCode() {
            return identifiant.hashCode();
        }
    }

    private Socket client;
    private static final List<Partie> parties = new LinkedList<>();

    public ServeurTictactoe(Socket client) {
        this.client = client;
    }

    public void demarrer() {
        Partie p = null;
        try {
            ObjectInputStream recevoir = new ObjectInputStream(client.getInputStream());

            int choix = recevoir.read();

            if (choix == Constantes.TICTACTOE_HOTE) {
                final Partie partie = new Partie();
                partie.joueur2 = null;
                partie.listeEtat = new Boolean[9];
                for (int i = 0; i < 9; i++)
                    partie.listeEtat[i] = null;
                partie.identifiant = (String) recevoir.readObject();

                var echec = false;
                synchronized (parties) {
                    if (parties.contains(partie))
                        echec = true;
                    else
                        parties.add(partie);
                }
                if (echec) {
                    client.getOutputStream().write(Constantes.TICTACTOE_INVALIDE);
                    return;
                } else
                    client.getOutputStream().write(Constantes.TICTACTOE_VALIDE);
                p = partie;
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
                var client2 = partie.joueur2;
                System.out.println(partie.joueur2 == null);
                client.getOutputStream().write(1);
                var generateur = new Random();
                var joueurActuel = generateur.nextInt(2) * 2 - 1;
                int[] plateau = new int[9];
                for (int i = 0; i < 9; ++i)
                    plateau[i] = Constantes.TICTACTOE_CASE_NON_COCHEE;
                plateau[4] = Constantes.TICTACTOE_CASE_JOUEUR2;

                while (true) {
                    var plateauInverse = new int[9];
                    for (int i = 0; i < 9; ++i)
                        plateauInverse[i] = -plateau[i];
                    client.getOutputStream().write(joueurActuel);
                    client2.getOutputStream().write(-joueurActuel);
                    for (int i = 0; i < 9; ++i) {
                        client.getOutputStream().write(plateau[i]);
                        client2.getOutputStream().write(plateauInverse[i]);
                    }
                    if (joueurActuel == 1)
                        if (client.getInputStream().read() == Constantes.TICTACTOE_VALIDE)
                            plateau[client.getInputStream().read()] = Constantes.TICTACTOE_CASE_JOUEUR1;
                        else
                            break;
                    else {
                        if (client2.getInputStream().read() == Constantes.TICTACTOE_VALIDE) {
                            plateau[client2.getInputStream().read()] = Constantes.TICTACTOE_CASE_JOUEUR2;
                        } else
                            break;
                    }
                    joueurActuel = -joueurActuel;
                }

                synchronized (parties) {
                    parties.remove(partie);
                }
            } else if (choix == Constantes.TICTACTOE_INVITE) {
                String identifiant = (String) recevoir.readObject();
                Partie trouvee = null;

                synchronized (parties) {
                    for (Partie part : parties) {
                        synchronized (part) {
                            if (part.identifiant.equals(identifiant) && part.joueur2 == null) {
                                trouvee = part;
                                break;
                            }
                        }
                    }
                }
                if (trouvee != null) {
                    synchronized (trouvee) {
                        trouvee.joueur2 = client;
                        client.getOutputStream().write(Constantes.TICTACTOE_VALIDE);
                    }
                } else {
                    client.getOutputStream().write(Constantes.TICTACTOE_INVALIDE);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                    + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
            if (p != null)
                synchronized (parties) {
                    parties.remove(p);
                }
            return;
        }

    }
}
