package gameCenter.controlleur.dessin;

import java.awt.*;
import java.awt.geom.*;

public interface Dessinable {
    void dessiner(Graphics2D g, AffineTransform tr);

    void dessiner(Graphics2D g);
}
