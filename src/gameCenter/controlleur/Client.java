package gameCenter.controlleur;

import java.net.*;
import java.util.function.*;

import javax.swing.SwingUtilities;

import gameCenter.vue.MainWindow;

public class Client {
    public static Socket socket;

    public static void main(String[] args) {
        new MainWindow().setVisible(true);
    }

    public static void asynchrone(Runnable async, Runnable sync) {
        new Thread(() -> {
            async.run();
            if (sync != null)
                SwingUtilities.invokeLater(sync);
        }).start();
    }

    public static void asynchrone(Runnable async) {
        asynchrone(async, null);
    }

    public static <ReturnType> void asynchrone(Supplier<? extends ReturnType> async, Consumer<ReturnType> sync) {
        new Thread(() -> {
            var result = async.get();
            if (sync != null)
                SwingUtilities.invokeLater(() -> sync.accept(result));
        }).start();
    }
}
