import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;
    private static PrintWriter output;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        output = new PrintWriter(socket.getOutputStream(), true);
        output.println("yo");
        BufferedReader input = new BufferedReader (new InputStreamReader(socket.getInputStream()));


        String response = input.readLine();
        System.out.println(response);

        System.out.println("Closing mhmm");
        socket.close();
        System.exit(0);
    }
}
