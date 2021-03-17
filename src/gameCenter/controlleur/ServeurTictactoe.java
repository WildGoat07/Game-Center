package gameCenter.controlleur;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;

public class ServeurTictactoe extends JFrame {
    private Socket client;
    private String[] listStatement;

    public ServeurTictactoe(Socket client) {
        this.client = client;
        build();
    }

    public void demarrer() throws IOException {
        ObjectOutputStream serialiseur;
        try {
            serialiseur = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.err.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                    + LocalTime.now().getSecond() + "] " + "Une erreur est survenue : " + e.getMessage());
            return;
        }
        PrintWriter out = new PrintWriter(serialiseur);

        listStatement = new String[9];
        for (int i=0; i<9; i++)
            listStatement[i] = "";
//        listStatement[0] = "X";
//        listStatement[1] = "";
//        listStatement[2] = "O";
//        listStatement[3] = "O";
//        listStatement[4] = "X";
//        listStatement[5] = "";
//        listStatement[6] = "";
//        listStatement[7] = "";
//        listStatement[8] = "X";
        serialiseur.writeObject(listStatement);
        out.print("X");

        while (true) {
            client.getOutputStream().write(0);

        }
    }

    private void build() {

    }
}
