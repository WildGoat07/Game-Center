package gameCenter.vue.tetris;

import java.awt.*;
import java.awt.geom.*;

import gameCenter.controlleur.dessin.*;

public class Bloc extends Transformable implements Dessinable {
    private gameCenter.controlleur.dessin.Rectangle centre;
    private Sommets hautgauche;
    private Sommets basdroite;

    public Bloc(Color c, int taille) {
        if (c == null)
            c = Color.WHITE;
        var hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        Color clair, sombre;
        if (hsb[1] != 0) {
            clair = Color.getHSBColor(hsb[0], hsb[1] * .6f, (1 - hsb[2]) * .2f + hsb[2]);
            var distanceRouge = hsb[0] > .5 ? 1 - hsb[0] : hsb[0];
            var distanceVert = Math.abs(hsb[0] - 1 / 3f);
            var distanceBleu = Math.abs(hsb[0] - 2 / 3f);
            if (distanceRouge <= distanceVert && distanceRouge <= distanceBleu)// rouge
                sombre = Color.getHSBColor(hsb[0] > .5f ? (1 - hsb[0]) * .2f + hsb[0] : hsb[0] * .8f,
                        (1 - hsb[1]) * .2f + hsb[1], hsb[2] * .9f);
            else if (distanceVert < distanceRouge && distanceVert < distanceBleu)// vert

                sombre = Color.getHSBColor(hsb[0] + (1 / 3f - hsb[0]) * .2f, (1 - hsb[1]) * .2f + hsb[1], hsb[2] * .9f);
            else // bleu
                sombre = Color.getHSBColor(hsb[0] + (2 / 3f - hsb[0]) * .2f, (1 - hsb[1]) * .2f + hsb[1], hsb[2] * .9f);
        } else {
            clair = Color.getHSBColor(hsb[0], 0, (1 - hsb[2]) * .2f + hsb[2]);
            sombre = Color.getHSBColor(hsb[0], 0, hsb[2] * .9f);
        }
        centre = new gameCenter.controlleur.dessin.Rectangle();
        centre.setCouleur(c);
        centre.setPosition(new Vecteur(taille / 4, taille / 4));
        centre.setTaille(new Vecteur(taille / 2, taille / 2));
        hautgauche = new Sommets(Sommets.Mode.TRIANGLES_CHAINES);
        hautgauche.setCouleur(clair);
        hautgauche.ajouterSommet(new Vecteur(0, taille));
        hautgauche.ajouterSommet(new Vecteur(taille / 4, taille * 3 / 4));
        hautgauche.ajouterSommet(new Vecteur(0, 0));
        hautgauche.ajouterSommet(new Vecteur(taille / 4, taille / 4));
        hautgauche.ajouterSommet(new Vecteur(taille, 0));
        hautgauche.ajouterSommet(new Vecteur(taille * 3 / 4, taille / 4));

        basdroite = new Sommets(Sommets.Mode.TRIANGLES_CHAINES);
        basdroite.setCouleur(sombre);
        basdroite.ajouterSommet(new Vecteur(0, taille));
        basdroite.ajouterSommet(new Vecteur(taille / 4, taille * 3 / 4));
        basdroite.ajouterSommet(new Vecteur(taille, taille));
        basdroite.ajouterSommet(new Vecteur(taille * 3 / 4, taille * 3 / 4));
        basdroite.ajouterSommet(new Vecteur(taille, 0));
        basdroite.ajouterSommet(new Vecteur(taille * 3 / 4, taille / 4));
    }

    @Override
    public void dessiner(Graphics2D g, AffineTransform tr) {
        if (tr == null)
            tr = new AffineTransform();
        else
            tr = new AffineTransform(tr);
        tr.concatenate(transformation());
        centre.dessiner(g, tr);
        hautgauche.dessiner(g, tr);
        basdroite.dessiner(g, tr);
    }

    @Override
    public void dessiner(Graphics2D g) {
        dessiner(g, null);
    }
}
