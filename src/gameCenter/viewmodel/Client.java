package gameCenter.viewmodel;

import gameCenter.view.MainWindow;

import java.io.*;
import java.net.*;
import java.util.function.*;

import javax.swing.SwingUtilities;

public class Client {
    public static Socket socket;

    public static void main(String[] args) throws IOException {
        new MainWindow().setVisible(true);
    }

    public static void RunAsynchronously(Runnable async, Runnable syncAfter) {
        new Thread(() -> {
            async.run();
            if (syncAfter != null)
                SwingUtilities.invokeLater(syncAfter);
        }).start();
    }

    public static void RunAsynchronously(Runnable async) {
        RunAsynchronously(async, null);
    }

    public static <ReturnType> void RunAsynchronously(Supplier<? extends ReturnType> async,
            Consumer<ReturnType> syncAfter) {
        new Thread(() -> {
            var result = async.get();
            if (syncAfter != null)
                SwingUtilities.invokeLater(() -> syncAfter.accept(result));
        }).start();
    }
}
