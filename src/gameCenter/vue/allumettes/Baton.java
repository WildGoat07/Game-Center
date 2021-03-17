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

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphiques = (Graphics2D) g;
        Dimension taille = getSize();
        var couleurTete = new Color(155, 48, 32);
        var couleurCorps = new Color(211, 196, 146);

    }
}
