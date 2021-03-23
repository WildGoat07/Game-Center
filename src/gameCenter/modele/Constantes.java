package gameCenter.modele;

public final class Constantes {
    protected Constantes() {
    }

    public static final int PORT = 12549;

    public static final int CHOIX_TETRIS = 0;
    public static final int CHOIX_ALLUMETTES = 2;
    public static final int CHOIX_TICTACTOE = 3;
    public static final int CHOIX_DECONNEXION = 4;

    public static final int TETRIS_HAUTEUR = 20;
    public static final int TETRIS_LARGEUR = 10;

    public static final int TICTACTOE_HAUTEUR = 40;
    public static final int TICTACTOE_LARGEUR = 30;
    public static final int TICTACTOE_HOTE = 1;
    public static final int TICTACTOE_INVITE = 0;
    public static final int TICTACTOE_VALIDE = 1;
    public static final int TICTACTOE_INVALIDE = 0;
    public static final int TICTACTOE_ACTION_CASE = 1;
    public static final int TICTACTOE_ACTION_RETOUR = 0;
    public static final int TICTACTOE_CASE_JOUEUR1 = 1;
    public static final int TICTACTOE_CASE_JOUEUR2 = -1;
    public static final int TICTACTOE_CASE_NON_COCHEE = 0;

    public static final int ALLUMETTES_CHOIX_UTILISATEUR_RETOUR = 0;
    public static final int ALLUMETTES_CHOIX_UTILISATEUR_RETIRER_UNE_ALLUMETTE_DU_PLATEAU_DE_JEU = 1;
    public static final int ALLUMETTES_CHOIX_UTILISATEUR_RETIRER_DEUX_ALLUMETTES_DU_PLATEAU_DE_JEU = 2;
    public static final int ALLUMETTES_ETAT_PARTIE_INDETERMINEE = 0;
    public static final int ALLUMETTES_ETAT_PARTIE_ORDINATEUR_A_GAGNÉ = 1;
    public static final int ALLUMETTES_ETAT_PARTIE_JOUEUR_A_GAGNÉ = 2;
}
