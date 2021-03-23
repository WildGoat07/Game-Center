package gameCenter.vue.allumettes;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;

public class Baton extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 3766812060863885117L;

    public Baton() {
        setPreferredSize(new Dimension(10, 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphiques = (Graphics2D) g;
        Dimension taille = getSize();
        var couleurTete = new Color(155, 48, 32);
        var couleurCorps = new Color(211, 196, 146);
        var minSize = taille.getWidth() < taille.getHeight() ? taille.getWidth() : taille.getHeight();
        graphiques.setColor(couleurCorps);
        graphiques.fillRoundRect((int) (taille.getWidth() * .2), (int) (taille.getHeight() * .2),
                (int) (taille.getWidth() * .6), (int) (taille.getHeight() * .8), (int) (minSize * .1),
                (int) (minSize * .1));
        graphiques.setColor(couleurTete);
        graphiques.fillRoundRect(0, 0, (int) taille.getWidth(), (int) (taille.getHeight() * .2), (int) (minSize * .1),
                (int) (minSize * .1));
    }
}
