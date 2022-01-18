import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Broker {
    private static int port;
    private static BufferedReader input;
    private static PrintWriter output;
    private static Socket client;
    private static ServerSocket broker;
    private static Scanner in = new Scanner(System.in);
    private static FileOutputStream receivedFile;
    private static BufferedOutputStream bos;
    private static String vidName;

    public static void main(String[] args) throws IOException {
        System.out.println("Initialising Broker");
        init();
        acceptConnection();
    }

    private static void init() throws IOException {
        System.out.printf("Enter port numbers 9090, 9091, 9092 in this order: ");
        port = in.nextInt();
        System.out.println("You've entered port: " + port);
        broker = new ServerSocket(port);
        System.out.println("Server is now waiting for connection");
        client = broker.accept();
        System.out.println("connection has been established");
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(), true);
    }

    private static void acceptConnection() throws IOException {
        System.out.println("Trying to save vid");
        receiveFile("ks_video.mp4");

        client.close();
        broker.close();
    }
    private static void receiveFile(String name) throws IOException {
        vidName = name;
        System.out.println("Starting to send the video file: " + name);
        receivedFile = new FileOutputStream("/home/teodoro/ks/" + name);
        bos = new BufferedOutputStream(receivedFile);
        int size = 9000;
        byte[] buffer = new byte[size];
        int count;
        InputStream is = client.getInputStream();

        while((count = is.read(buffer)) > 0) {
            bos.write(buffer, 0, count);
        }
        System.out.println("video sent!");
    }

    private static void sendToConsumer() {
        FileInputStream fis;
        BufferedInputStream bis;
        BufferedOutputStream out;
        byte[] buffer = new byte[10000];
        try {
            System.out.println("in try brah");
            File temp = new File(vidName);
            fis = new FileInputStream(temp);
            bis = new BufferedInputStream(fis);
            out = new BufferedOutputStream(client.getOutputStream());
            int count;
            System.out.println("Entering while loop");
            while((count = bis.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}