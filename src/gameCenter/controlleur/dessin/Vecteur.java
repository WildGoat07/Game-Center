package gameCenter.controlleur.dessin;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Vecteur implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 995682098588667182L;
    public float x;
    public float y;

    public Vecteur() {
        this(0, 0);
    }

    public Vecteur(Point2D pt) {
        this.x = (float) pt.getX();
        this.y = (float) pt.getY();
    }

    public Vecteur(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vecteur(float v) {
        this(v, v);
    }

    public Vecteur(Vecteur v) {
        this(v.x, v.y);
    }

    public Vecteur ajouter(Vecteur autre) {
        return new Vecteur(x + autre.x, y + autre.y);
    }

    public Vecteur retirer(Vecteur autre) {
        return new Vecteur(x - autre.x, y - autre.y);
    }

    public Vecteur multiplier(float facteur) {
        return new Vecteur(x * facteur, y * facteur);
    }

    public Point2D versPoint() {
        return new Point2D.Float(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
