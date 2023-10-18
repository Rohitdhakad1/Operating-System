import java.util.concurrent.Semaphore;

public class ReaderWriterSemaphore {
    static Semaphore mutex = new Semaphore(1);
    static Semaphore wrt = new Semaphore(1);
    static int readCount = 0;

    static class Reader extends Thread {
        public void run() {
            try {
                while (true) {
                    mutex.acquire();
                    readCount++;
                    if (readCount == 1) {
                        wrt.acquire();
                    }
                    mutex.release();

                    // Reading...
                    System.out.println("Reader is reading...");

                    mutex.acquire();
                    readCount--;
                    if (readCount == 0) {
                        wrt.release();
                    }
                    mutex.release();

                    // Perform other operations
                    Thread.sleep(1000); // Simulate reading time
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Writer extends Thread {
        public void run() {
            try {
                while (true) {
                    wrt.acquire();

                    // Writing...
                    System.out.println("Writer is writing...");

                    wrt.release();

                    // Perform other operations
                    Thread.sleep(2000); // Simulate writing time
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Reader reader1 = new Reader();
        Reader reader2 = new Reader();
        Writer writer = new Writer();
        reader1.start();
        reader2.start();
        writer.start();
    }
}
