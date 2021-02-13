package gameCenter.controlleur;

import java.awt.*;

public final class Utilites {
    private Utilites() {
    }

    private static final int DIM_MAX = 1000000;

    public static Dimension tailleMax() {
        return new Dimension(DIM_MAX, DIM_MAX);
    }

    public static Dimension LargeurMax(int maxHeight) {
        return new Dimension(DIM_MAX, maxHeight);
    }

    public static Dimension HauteurMax(int maxwidth) {
        return new Dimension(maxwidth, DIM_MAX);
    }
}
