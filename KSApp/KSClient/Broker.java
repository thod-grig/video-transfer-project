import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Broker {
    private static final int PORT = 9090;
    private static ServerSocket ss;
    private static Socket client;
    private static ArrayList<RequestHandler> brokers;
    private  static ExecutorService pool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws IOException{
        ss = new ServerSocket(PORT);
        System.out.println("Starting server");
        brokers = new ArrayList<>();

        while (true) {
            System.out.println("Waiting for connection");
            client = ss.accept();
            System.out.println("Server connected to client");
            RequestHandler rh = new RequestHandler(client);
            brokers.add(rh);

            pool.execute(rh);
        }
    }
}