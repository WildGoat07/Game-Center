package gameCenter.vue.tetris;

import java.awt.*;
import java.awt.geom.AffineTransform;

import gameCenter.controlleur.dessin.*;

public class Forme extends Transformable implements Dessinable {

    public static final int CUBE = 0;
    public static final int LONG = 1;
    public static final int S_AUTISTE = 2;
    public static final int S_AUTISTE_INVERSE = 3;
    public static final int T_NAIN = 4;
    public static final int L = 5;
    public static final int L_INVERSE = 6;

    private int type;
    private Bloc[] blocs;
    private int rotation;

    public Forme(int type) {
        this.type = type;
        rotation = 0;
        blocs = new Bloc[4];
        Color couleur;
        switch (type) {
            case CUBE:
                couleur = Color.YELLOW;
                blocs[0] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[0].setPosition(new Vecteur(0, 0));
                blocs[1] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                blocs[2] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                blocs[3] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                break;
            case LONG:
                couleur = Color.CYAN;
                blocs[0] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[0].setPosition(new Vecteur(0, 0));
                blocs[1] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[1].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                blocs[2] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 2));
                blocs[3] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 3));
                break;
            case S_AUTISTE:
                couleur = new Color(118, 232, 62);
                blocs[0] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[0].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                blocs[1] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[1].setPosition(new Vecteur(0, 0));
                blocs[2] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                blocs[3] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[3].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                break;
            case S_AUTISTE_INVERSE:
                couleur = Color.RED;
                blocs[0] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[0].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                blocs[1] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[1].setPosition(new Vecteur(0, 0));
                blocs[2] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                blocs[3] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                break;
            case T_NAIN:
                couleur = new Color(156, 75, 236);
                blocs[0] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[0].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                blocs[1] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[1].setPosition(new Vecteur(0, 0));
                blocs[2] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                blocs[3] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                break;
            case L:
                couleur = Color.BLUE;
                blocs[0] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[0].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                blocs[1] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[1].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                blocs[2] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                blocs[3] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                break;
            case L_INVERSE:
                couleur = new Color(234, 175, 56);
                blocs[0] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[0].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                blocs[1] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[1].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                blocs[2] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                blocs[3] = new Bloc(couleur, Tetris.TAILLE_BLOC);
                blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                break;
        }
    }

    public void orienter(int r) {
        rotation = r % 4;
        if (rotation < 0)
            rotation += 4;
        switch (rotation) {
            case 0:
                switch (type) {
                    case CUBE:
                        blocs[0].setPosition(new Vecteur(0, 0));
                        blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        break;
                    case LONG:
                        blocs[0].setPosition(new Vecteur(0, 0));
                        blocs[1].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 2));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 3));
                        break;
                    case S_AUTISTE:
                        blocs[0].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(0, 0));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        break;
                    case S_AUTISTE_INVERSE:
                        blocs[0].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(0, 0));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        break;
                    case T_NAIN:
                        blocs[0].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(0, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        break;
                    case L:
                        blocs[0].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        break;
                    case L_INVERSE:
                        blocs[0].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        break;
                }
                break;
            case 1:
                switch (type) {
                    case CUBE:
                        blocs[0].setPosition(new Vecteur(0, 0));
                        blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        break;
                    case LONG:
                        blocs[0].setPosition(new Vecteur(0, 0));
                        blocs[1].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC * 2, 0));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        break;
                    case S_AUTISTE:
                        blocs[0].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[1].setPosition(new Vecteur(0, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC * 2));
                        break;
                    case S_AUTISTE_INVERSE:
                        blocs[0].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 2));
                        break;
                    case T_NAIN:
                        blocs[0].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC * 2));
                        break;
                    case L:
                        blocs[0].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC * 2));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 2));
                        break;
                    case L_INVERSE:
                        blocs[0].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC * 2));
                        blocs[3].setPosition(new Vecteur(0, 0));
                        break;
                }
                break;
            case 2:
                switch (type) {
                    case CUBE:
                        blocs[0].setPosition(new Vecteur(0, 0));
                        blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        break;
                    case LONG:
                        blocs[0].setPosition(new Vecteur(0, 0));
                        blocs[1].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 2));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 3));
                        break;
                    case S_AUTISTE:
                        blocs[0].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(0, 0));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        break;
                    case S_AUTISTE_INVERSE:
                        blocs[0].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(0, 0));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        break;
                    case T_NAIN:
                        blocs[0].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[1].setPosition(new Vecteur(0, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        break;
                    case L:
                        blocs[0].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[2].setPosition(new Vecteur(0, 0));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        break;
                    case L_INVERSE:
                        blocs[0].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[1].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[2].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                        blocs[3].setPosition(new Vecteur(0, 0));
                        break;
                }
                break;
            case 3:
                switch (type) {
                    case CUBE:
                        blocs[0].setPosition(new Vecteur(0, 0));
                        blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        break;
                    case LONG:
                        blocs[0].setPosition(new Vecteur(0, 0));
                        blocs[1].setPosition(new Vecteur(-Tetris.TAILLE_BLOC, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC * 2, 0));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        break;
                    case S_AUTISTE:
                        blocs[0].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[1].setPosition(new Vecteur(0, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC * 2));
                        break;
                    case S_AUTISTE_INVERSE:
                        blocs[0].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[1].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        blocs[2].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 2));
                        break;
                    case T_NAIN:
                        blocs[0].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC));
                        blocs[1].setPosition(new Vecteur(0, 0));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[3].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 2));
                        break;
                    case L:
                        blocs[0].setPosition(new Vecteur(0, 0));
                        blocs[1].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 2));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, 0));
                        break;
                    case L_INVERSE:
                        blocs[0].setPosition(new Vecteur(0, 0));
                        blocs[1].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC));
                        blocs[2].setPosition(new Vecteur(0, Tetris.TAILLE_BLOC * 2));
                        blocs[3].setPosition(new Vecteur(Tetris.TAILLE_BLOC, Tetris.TAILLE_BLOC * 2));
                        break;
                }
                break;
        }
    }

    public Vecteur[] collisionLocale() {
        return new Vecteur[] { blocs[0].getPosition(), blocs[1].getPosition(), blocs[2].getPosition(),
                blocs[3].getPosition() };
    }

    public int orientation() {
        return rotation;
    }

    public Bloc[] blocs() {
        return blocs;
    }

    @Override
    public void dessiner(Graphics2D g, AffineTransform tr) {
        if (tr == null)
            tr = new AffineTransform();
        else
            tr = new AffineTransform(tr);
        tr.concatenate(transformation());
        for (int i = 0; i < 4; ++i)
            blocs[i].dessiner(g, tr);
    }

    @Override
    public void dessiner(Graphics2D g) {
        dessiner(g, null);
    }

}
