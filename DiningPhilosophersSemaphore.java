import java.util.concurrent.Semaphore;

public class DiningPhilosophersSemaphore {
    static final int NUM_PHILOSOPHERS = 5;
    static Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];
    static Semaphore dining = new Semaphore(NUM_PHILOSOPHERS - 1);

    static class Philosopher extends Thread {
        int id;

        Philosopher(int id) {
            this.id = id;
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking.");
            Thread.sleep(100);
        }

        private void eat() throws InterruptedException {
            System.out.println("Philosopher " + id + " is eating.");
            Thread.sleep(100);
        }

        public void run() {
            try {
                while (true) {
                    think();
                    dining.acquire();
                    forks[id].acquire();
                    forks[(id + 1) % NUM_PHILOSOPHERS].acquire();
                    eat();
                    forks[id].release();
                    forks[(id + 1) % NUM_PHILOSOPHERS].release();
                    dining.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i);
            philosophers[i].start();
        }
    }
}
