package gameCenter.controlleur.dessin;

import java.awt.geom.*;

public class Transformable {
    private Vecteur position = new Vecteur();
    private Vecteur origine = new Vecteur();
    private Vecteur echelle = new Vecteur(1);
    private float rotation = 0;

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation % (2 * (float) Math.PI);
    }

    public Vecteur getEchelle() {
        return new Vecteur(echelle);
    }

    public void setEchelle(Vecteur echelle) {
        this.echelle = new Vecteur(echelle);
    }

    public Vecteur getOrigine() {
        return new Vecteur(origine);
    }

    public void setOrigine(Vecteur origine) {
        this.origine = new Vecteur(origine);
    }

    public Vecteur getPosition() {
        return new Vecteur(position);
    }

    public void setPosition(Vecteur position) {
        this.position = new Vecteur(position);
    }

    public void deplacerPosition(Vecteur depl) {
        position = position.ajouter(depl);
    }

    public void deplacerOrigine(Vecteur depl) {
        origine = origine.ajouter(depl);
    }

    public void zoomer(Vecteur zoom) {
        echelle = new Vecteur(echelle.x * zoom.x, echelle.y * zoom.y);
    }

    public void tourner(float rotation) {
        this.rotation += rotation;
    }

    public AffineTransform transformation() {
        var tr = AffineTransform.getTranslateInstance(position.x, position.y);
        tr.concatenate(AffineTransform.getRotateInstance(rotation));
        tr.concatenate(AffineTransform.getScaleInstance(echelle.x, echelle.y));
        tr.concatenate(AffineTransform.getTranslateInstance(-origine.x, -origine.y));
        return tr;
    }

    @Override
    public String toString() {
        return "Position:[" + position.x + ", " + position.y + "] " + "Origine:[" + origine.x + ", " + origine.y + "] "
                + "Echelle:[" + echelle.x + ", " + echelle.y + "] " + "Rotation:[" + rotation + "] ";
    }
}
