package gameCenter.controlleur.dessin;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Sommets extends Transformable implements Dessinable {

    public enum Mode {
        TRIANGLES_EVENTAIL, TRIANGLES_CHAINES, LIGNES
    }

    private Mode mode;
    private Color couleur = Color.WHITE;
    private java.util.List<Vecteur> sommets;

    public Sommets(Mode mode) {
        this.mode = mode;
        sommets = new LinkedList<>();
    }

    public void vider() {
        sommets.clear();
    }

    public int nombreSommets() {
        return sommets.size();
    }

    public void ajouterSommet(Vecteur v) {
        sommets.add(new Vecteur(v));
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    @Override
    public void dessiner(Graphics2D g, AffineTransform tr) {
        if (tr == null)
            tr = new AffineTransform();
        else
            tr = new AffineTransform(tr);
        tr.concatenate(transformation());
        g.setColor(couleur);
        switch (mode) {
            case TRIANGLES_CHAINES: {
                var taille = sommets.size();
                if (taille >= 3) {
                    var ptsX = new int[3];
                    var ptsY = new int[3];
                    Point2D dernier1;
                    Point2D dernier2;
                    var it = sommets.listIterator();
                    {
                        var vec = it.next();
                        var pt = tr.transform(new Point2D.Double(vec.x, vec.y), null);
                        ptsX[0] = (int) pt.getX();
                        ptsY[0] = (int) pt.getY();
                    }
                    {
                        var vec = it.next();
                        var pt = tr.transform(new Point2D.Double(vec.x, vec.y), null);
                        dernier1 = pt;
                        ptsX[1] = (int) pt.getX();
                        ptsY[1] = (int) pt.getY();
                    }
                    {
                        var vec = it.next();
                        var pt = tr.transform(new Point2D.Double(vec.x, vec.y), null);
                        dernier2 = pt;
                        ptsX[2] = (int) pt.getX();
                        ptsY[2] = (int) pt.getY();
                    }
                    g.fillPolygon(ptsX, ptsY, 3);
                    boolean suppr1 = true;
                    while (it.hasNext()) {
                        var vec = it.next();
                        var pt = tr.transform(new Point2D.Double(vec.x, vec.y), null);
                        g.fillPolygon(new int[] { (int) dernier1.getX(), (int) dernier2.getX(), (int) pt.getX() },
                                new int[] { (int) dernier1.getY(), (int) dernier2.getY(), (int) pt.getY() }, 3);
                        if (suppr1)
                            dernier1 = pt;
                        else
                            dernier2 = pt;
                        suppr1 = !suppr1;
                    }
                }
            }
                break;
            case LIGNES: {
                var it = sommets.listIterator();
                while (it.hasNext()) {
                    var vec = it.next();
                    if (!it.hasNext())
                        break;
                    var pt1 = tr.transform(new Point2D.Double(vec.x, vec.y), null);
                    vec = it.next();
                    var pt2 = tr.transform(new Point2D.Double(vec.x, vec.y), null);
                    g.drawLine((int) pt1.getX(), (int) pt1.getY(), (int) pt2.getX(), (int) pt2.getY());
                }
            }
                break;
            case TRIANGLES_EVENTAIL: {
                var taille = sommets.size();
                if (taille >= 3) {
                    var ptsX = new int[3];
                    var ptsY = new int[3];
                    Point2D premier;
                    Point2D dernier;
                    var it = sommets.listIterator();
                    {
                        var vec = it.next();
                        var pt = tr.transform(new Point2D.Double(vec.x, vec.y), null);
                        premier = pt;
                        ptsX[0] = (int) pt.getX();
                        ptsY[0] = (int) pt.getY();
                    }
                    {
                        var vec = it.next();
                        var pt = tr.transform(new Point2D.Double(vec.x, vec.y), null);
                        ptsX[1] = (int) pt.getX();
                        ptsY[1] = (int) pt.getY();
                    }
                    {
                        var vec = it.next();
                        var pt = tr.transform(new Point2D.Double(vec.x, vec.y), null);
                        dernier = pt;
                        ptsX[2] = (int) pt.getX();
                        ptsY[2] = (int) pt.getY();
                    }
                    g.fillPolygon(ptsX, ptsY, 3);
                    while (it.hasNext()) {
                        var vec = it.next();
                        var pt = tr.transform(new Point2D.Double(vec.x, vec.y), null);
                        g.fillPolygon(new int[] { (int) premier.getX(), (int) dernier.getX(), (int) pt.getX() },
                                new int[] { (int) premier.getY(), (int) dernier.getY(), (int) pt.getY() }, 3);
                        dernier = pt;
                    }
                }
            }
                break;
            default:
                break;

        }
    }

    @Override
    public void dessiner(Graphics2D g) {
        dessiner(g, null);
    }

}
