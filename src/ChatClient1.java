import java.io.*;
import java.net.*;

public class ChatClient1 {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        System.out.println("Connected to server");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Thread readerThread = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Client: " + message);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
        Thread writerThread = new Thread(() -> {
            try {
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
                String message;
                while ((message = consoleIn.readLine()) != null) {
                    out.println(message);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });

        readerThread.start();
        writerThread.start();
    }
}