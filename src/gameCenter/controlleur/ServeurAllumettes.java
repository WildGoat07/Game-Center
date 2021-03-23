package gameCenter.controlleur;

import java.net.Socket;
import java.time.LocalTime;
import java.util.*;

import gameCenter.modele.Constantes;

import java.io.*;

public class ServeurAllumettes {
    private Socket client;
    private int nbAllumettes;
    private Random generateur;

    public ServeurAllumettes(Socket client) {
        this.client = client;
        generateur = new Random();
    }

    public void demarrer() {
        nbAllumettes = generateur.nextInt(12) * 2 + 5;
        var allumettes_retiree_par_le_joueur = 0;
        boolean perdu = false;
        while (!perdu) {
            try {
                client.getOutputStream().write(nbAllumettes);
                switch (client.getInputStream().read()) {
                case Constantes.ALLUMETTES_CHOIX_UTILISATEUR_RETOUR:
                    return;
                case Constantes.ALLUMETTES_CHOIX_UTILISATEUR_RETIRER_DEUX_ALLUMETTES_DU_PLATEAU_DE_JEU:
                    --nbAllumettes;
                    ++allumettes_retiree_par_le_joueur;
                case Constantes.ALLUMETTES_CHOIX_UTILISATEUR_RETIRER_UNE_ALLUMETTE_DU_PLATEAU_DE_JEU:
                    --nbAllumettes;
                    ++allumettes_retiree_par_le_joueur;
                    break;
                }
                if (allumettes_retiree_par_le_joueur % 2 == 1) {
                    if (nbAllumettes % 3 == 0)
                        nbAllumettes -= 2;
                    else if (nbAllumettes % 3 == 1)
                        nbAllumettes -= 1;
                    else if (nbAllumettes % 3 == 2)
                        nbAllumettes -= 1;
                } else {
                    if (nbAllumettes % 3 == 0)
                        nbAllumettes -= 1;
                    else if (nbAllumettes % 3 == 1)
                        nbAllumettes -= 1;
                    else if (nbAllumettes % 3 == 2)
                        nbAllumettes -= 2;
                }
                if (nbAllumettes <= 0) {
                    client.getOutputStream()
                            .write(allumettes_retiree_par_le_joueur % 2 == 1
                                    ? Constantes.ALLUMETTES_ETAT_PARTIE_JOUEUR_A_GAGNÉ
                                    : Constantes.ALLUMETTES_ETAT_PARTIE_ORDINATEUR_A_GAGNÉ);
                    return;
                } else
                    client.getOutputStream().write(Constantes.ALLUMETTES_ETAT_PARTIE_INDETERMINEE);
            } catch (IOException e) {
                System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                        + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
                return;
            }
        }
    }
}
