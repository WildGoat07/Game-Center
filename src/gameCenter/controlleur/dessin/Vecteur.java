package gameCenter.controlleur.dessin;

public class Vecteur {
    float x;
    float y;

    public Vecteur() {
        this(0, 0);
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
}
