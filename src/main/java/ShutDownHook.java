/**
 * Created by kron on 11.02.16.
 */
public class ShutDownHook extends Thread {
    @Override
    public void run() {
        Main.result.forEach(System.out::println);
    }
}
