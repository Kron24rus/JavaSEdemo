import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by kron on 10.02.16.
 */
public class Main {

    public static ArrayList<String> result = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        if (args.length == 0) {
            System.out.println("No path!");
        } else {
            Runtime.getRuntime().addShutdownHook(new ShutDownHook());
            Thread t = new Thread(new ReadFiles(args[0]));
            t.start();
            while (true) {
                if (sc.nextLine().equals("stop")) {
                    System.exit(0);
                } else {
                    System.out.println("Wrong command: enter \"stop\" to exit.");
                }
            }
        }
    }
}
