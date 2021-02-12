package gameCenter.viewmodel;

import java.awt.*;

public final class Utilities {
    private Utilities() {
    }

    private static final int MAX_SIZE = 1000000;

    public static Dimension getMaxSize() {
        return new Dimension(MAX_SIZE, MAX_SIZE);
    }

    public static Dimension getMaxWidth(int maxHeight) {
        return new Dimension(MAX_SIZE, maxHeight);
    }

    public static Dimension getMaxHeight(int maxwidth) {
        return new Dimension(maxwidth, MAX_SIZE);
    }
}
