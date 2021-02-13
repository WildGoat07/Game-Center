package gameCenter.controlleur.dessin;

import java.awt.geom.*;
import java.awt.*;

public class Rectangle extends Transformable implements Dessinable {
    private Vecteur taille = new Vecteur();
    private Color couleur = Color.WHITE;

    @Override
    public void dessiner(Graphics2D g, AffineTransform tr) {
        if (tr == null)
            tr = new AffineTransform();
        else
            tr = new AffineTransform(tr);
        tr.concatenate(transformation());
        g.setColor(couleur);
        var ptsX = new int[4];
        var ptsY = new int[4];
        {
            var pt = tr.transform(new Point2D.Double(0, 0), null);
            ptsX[0] = (int) pt.getX();
            ptsY[0] = (int) pt.getY();
        }
        {
            var pt = tr.transform(new Point2D.Double(taille.x, 0), null);
            ptsX[1] = (int) pt.getX();
            ptsY[1] = (int) pt.getY();
        }
        {
            var pt = tr.transform(new Point2D.Double(taille.x, taille.y), null);
            ptsX[2] = (int) pt.getX();
            ptsY[2] = (int) pt.getY();
        }
        {
            var pt = tr.transform(new Point2D.Double(0, taille.y), null);
            ptsX[3] = (int) pt.getX();
            ptsY[3] = (int) pt.getY();
        }
        g.fillPolygon(ptsX, ptsY, 4);
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public Vecteur getTaille() {
        return taille;
    }

    public void setTaille(Vecteur taille) {
        this.taille = taille;
    }

    @Override
    public void dessiner(Graphics2D g) {
        dessiner(g, null);
    }

}
