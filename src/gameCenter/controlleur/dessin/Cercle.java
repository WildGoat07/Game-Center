package gameCenter.controlleur.dessin;

import java.awt.*;
import java.awt.geom.*;

public class Cercle extends Transformable implements Dessinable {
    private int rayon = 0;
    private int nbVertices = 20;
    private Color couleur = Color.WHITE;

    @Override
    public void dessiner(Graphics2D g, AffineTransform tr) {
        if (tr == null)
            tr = new AffineTransform();
        else
            tr = new AffineTransform(tr);
        tr.concatenate(transformation());
        g.setColor(couleur);
        var ptsX = new int[nbVertices];
        var ptsY = new int[nbVertices];
        for (int i = 0; i < nbVertices; ++i) {
            var pt = tr.transform(new Point2D.Double(rayon + rayon * Math.cos(i * 2 * Math.PI / nbVertices),
                    rayon + rayon * Math.sin(i * 2 * Math.PI / nbVertices)), null);
            ptsX[i] = (int) pt.getX();
            ptsY[i] = (int) pt.getY();
        }
        g.fillPolygon(ptsX, ptsY, nbVertices);
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public int getNbVertices() {
        return nbVertices;
    }

    public void setNbVertices(int nbVertices) {
        this.nbVertices = nbVertices;
    }

    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }

    @Override
    public void dessiner(Graphics2D g) {
        dessiner(g, null);
    }

}
