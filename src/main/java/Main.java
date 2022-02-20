import thread.CheckNewPayment;
import thread.MainThread;

import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        MainThread firstThread = new MainThread();
        firstThread.start();
        Timer timer = new Timer();
        timer.schedule(new CheckNewPayment(), 1000, 1000);
    }
}
