package gameCenter.controlleur;

import java.awt.*;

public final class Utilites {
    private Utilites() {
    }

    private static final int DIM_MAX = 1000000;

    public static Dimension tailleMax() {
        return new Dimension(DIM_MAX, DIM_MAX);
    }

    public static Dimension LargeurMax(int hauteurMax) {
        return new Dimension(DIM_MAX, hauteurMax);
    }

    public static Dimension HauteurMax(int largeurMax) {
        return new Dimension(largeurMax, DIM_MAX);
    }
}
