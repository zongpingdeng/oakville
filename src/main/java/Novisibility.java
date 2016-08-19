/**
 * Created by jeff on 16/5/21.
 */
public class Novisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (!ready){}
                //Thread.yield();
            System.out.print(number);
        }
    }

    public static void main(String [] args) throws InterruptedException {
        ReaderThread t = new ReaderThread();
        t.start();
        number = 42;
        ready = true;
        t.join();

    }
}
