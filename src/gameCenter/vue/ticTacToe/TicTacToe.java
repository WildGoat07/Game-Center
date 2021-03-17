package gameCenter.vue.ticTacToe;

import gameCenter.controlleur.Client;
import gameCenter.controlleur.dessin.Rectangle;
import gameCenter.modele.Constantes;
import gameCenter.vue.SelectionJeu;
import gameCenter.vue.tetris.Tetris;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TicTacToe extends JPanel{

    /**
     *
     */
    private static final long serialVersionUID = 3718214323734367128L;

    private static final int TAILLE_CASE = 64;

    private gameCenter.controlleur.dessin.Rectangle fond;
    private ObjectInputStream serialiseur;
    private ObjectOutputStream send;
    private Thread sync;
    private Object mutex;
    private String[][] table;
    private int order;

    private JLabel title;
    private String whosTurn;

    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;
    private JButton[] listButton;

    private JButton retour;

    private String[] listStatement;

    private JLabel labelTitle;
    private JLabel labelTurn;

    private JPanel panelWindow;
    private JPanel panelCase;

    public TicTacToe(Window fenetre) {
        repaint();
        revalidate();
        try {
            serialiseur = new ObjectInputStream(Client.socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                    "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            send = new ObjectOutputStream(Client.socket.getOutputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + e.getMessage(),
                    "erreur de synchronisation", JOptionPane.ERROR_MESSAGE);
            return;
        }

        listButton = new JButton[9];
        listStatement = new String[9];
        setListStatement();
        displayComponent(listStatement);



        listButton[0].addActionListener(e -> {
            System.out.println("1");
            listButton[0].setText("O");
            checkWin();
        });
        listButton[1].addActionListener(e -> {
            System.out.println("2");
            listButton[1].setText("O");
            checkWin();
        });
        listButton[2].addActionListener(e -> {
            System.out.println("3");
            listButton[2].setText("O");
            checkWin();
        });
        listButton[3].addActionListener(e -> {
            System.out.println("4");
            listButton[3].setText("O");
            checkWin();
        });
        listButton[4].addActionListener(e -> {
            System.out.println("5");
            listButton[4].setText("O");
            checkWin();
        });
        listButton[5].addActionListener(e -> {
            System.out.println("6");
            listButton[5].setText("O");
            checkWin();
        });
        listButton[6].addActionListener(e -> {
            System.out.println("7");
            listButton[6].setText("O");
            checkWin();
        });
        listButton[7].addActionListener(e -> {
            System.out.println("8");
            listButton[7].setText("O");
            checkWin();
        });
        listButton[8].addActionListener(e -> {
            System.out.println("9");
            listButton[8].setText("O");
            checkWin();
        });

        retour = new JButton();
        retour.setText("retour au menu");
        retour.setVisible(false);
        retour.setForeground(Color.black);
        retour.setBackground(Color.white);
        panelWindow.add(retour);
        retour.addActionListener(e -> {
            fenetre.add(new SelectionJeu(fenetre));
            fenetre.revalidate();
        });

        repaint();
        revalidate();
    }

    private void displayComponent(String[] listStatement) {
        setListButton();
        //paramètres fenêtre
        setMinimumSize(new Dimension(Constantes.TICTACTOE_LARGEUR * TAILLE_CASE, Constantes.TICTACTOE_HAUTEUR * TAILLE_CASE));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());
        //paramètres panel
        panelCase = new JPanel();
        panelCase.setLayout(new GridLayout(3,3));
        panelWindow = new JPanel();
        panelWindow.setLayout(new GridLayout(3,1));
        //paramètres labels
        labelTitle = new JLabel("TicTacToe");
        labelTitle.setAlignmentX(CENTER_ALIGNMENT);
        panelWindow.add(labelTitle);
        //paramètres boutons
        for (int i=0; i<9; i++) {
            listButton[i] = new JButton(listStatement[i]);
            setButton(listButton[i]);
            System.out.println(listStatement[i]);
            listButton[i].setEnabled(!listStatement[i].equals(whosTurn));
        }

        panelWindow.add(panelCase);

        add(panelWindow);
    }

    private void setButton(JButton btn) {
        btn.setBackground(Color.white);
        btn.setPreferredSize(new Dimension(70, 70));
        panelCase.add(btn);
    }

    private void setListStatement(){
        try {
            listStatement = (String[]) serialiseur.readObject();
            whosTurn = "X";
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setListButton() {
        listButton[0] = btn1;
        listButton[1] = btn2;
        listButton[2] = btn3;
        listButton[3] = btn4;
        listButton[4] = btn5;
        listButton[5] = btn6;
        listButton[6] = btn7;
        listButton[7] = btn8;
        listButton[8] = btn9;
    }

    private void checkWin() {
        boolean victory = false;
        if (listButton[0].getText().equals("O") && listButton[1].getText().equals("O") && listButton[2].getText().equals("O"))
            victory = true;
        else if (listButton[3].getText().equals("O") && listButton[4].getText().equals("O") && listButton[5].getText().equals("O"))
            victory = true;
        else if (listButton[6].getText().equals("O") && listButton[7].getText().equals("O") && listButton[8].getText().equals("O"))
            victory = true;
        else if (listButton[0].getText().equals("O") && listButton[3].getText().equals("O") && listButton[6].getText().equals("O"))
            victory = true;
        else if (listButton[1].getText().equals("O") && listButton[4].getText().equals("O") && listButton[7].getText().equals("O"))
            victory = true;
        else if (listButton[2].getText().equals("O") && listButton[5].getText().equals("O") && listButton[8].getText().equals("O"))
            victory = true;
        else if (listButton[0].getText().equals("O") && listButton[4].getText().equals("O") && listButton[8].getText().equals("O"))
            victory = true;
        else if (listButton[2].getText().equals("O") && listButton[4].getText().equals("O") && listButton[6].getText().equals("O"))
            victory = true;

        if (victory) {
            for (JButton button: listButton) {
                button.setEnabled(false);
                labelTitle.setText("victory");
                retour.setVisible(true);
            }
        }
    }
}
