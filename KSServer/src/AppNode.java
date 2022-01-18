import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class AppNode {
    private static Socket broker;

    public static void main(String[] args) throws IOException {
        broker = new Socket("127.0.0.1", 9090);
        File file = new File("/home/teodoro/Desktop/ks_video.mp4");
        System.out.println("file exists? " + file.exists());
        push(file);
        System.out.println("Enter your username son: ");
        Scanner key_in = new Scanner(System.in);
        Publisher residentPub = new Publisher(key_in.next());
        System.out.println("Created publisher");
    }

    public static void push(File file) {
        FileInputStream fis;
        BufferedInputStream bis;
        BufferedOutputStream out;
        byte[] buffer = new byte[10000];
        try {
            System.out.println("in try brah");
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            out = new BufferedOutputStream(broker.getOutputStream());
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
