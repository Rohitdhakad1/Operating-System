import java.util.concurrent.Semaphore;

public class ProducerConsumerSemaphore {
    static final int BUFFER_SIZE = 5;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore full = new Semaphore(0);
    static Semaphore empty = new Semaphore(BUFFER_SIZE);
    static int[] buffer = new int[BUFFER_SIZE];

    static class Producer extends Thread {
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    empty.acquire();
                    mutex.acquire();
                    int item = i;
                    buffer[i] = item;
                    System.out.println("Produced: " + item);
                    mutex.release();
                    full.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    full.acquire();
                    mutex.acquire();
                    int item = buffer[i];
                    System.out.println("Consumed: " + item);
                    buffer[i] = -1;
                    mutex.release();
                    empty.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        producer.start();
        consumer.start();
    }
}
