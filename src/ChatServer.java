import java.io.*;
import java.net.*;

public class ChatServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Chat Server started on port 5000");


        Socket client1 = serverSocket.accept();
        System.out.println("Client 1 connected");
        Socket client2 = serverSocket.accept();
        System.out.println("Client 2 connected");


        BufferedReader client1In = new BufferedReader(new InputStreamReader(client1.getInputStream()));
        PrintWriter client1Out = new PrintWriter(client1.getOutputStream(), true);
        BufferedReader client2In = new BufferedReader(new InputStreamReader(client2.getInputStream()));
        PrintWriter client2Out = new PrintWriter(client2.getOutputStream(), true);

        // Start two threads to handle communication with each client
        Thread client1Handler = new Thread(() -> handleClient(client1In, client2Out));
        Thread client2Handler = new Thread(() -> handleClient(client2In, client1Out));
        client1Handler.start();
        client2Handler.start();
    }


    private static void handleClient(BufferedReader in, PrintWriter out) {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                out.println(message);
            }
        } catch (IOException e) {
            System.err.println( e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                System.err.println( e.getMessage());
            }
        }
    }
}