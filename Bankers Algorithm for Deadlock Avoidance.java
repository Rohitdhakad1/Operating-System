import java.util.Arrays;

class BankersAlgorithm {
    private int[] available;
    private int[][] max;
    private int[][] allocation;
    private int numOfProcesses;
    private int numOfResources;

    public BankersAlgorithm(int[] available, int[][] max, int[][] allocation) {
        this.available = available;
        this.max = max;
        this.allocation = allocation.clone();
        this.numOfProcesses = allocation.length;
        this.numOfResources = available.length;
    }

    public boolean isSafe() {
        int[] work = Arrays.copyOf(available, numOfResources);
        boolean[] finish = new boolean[numOfProcesses];

        for (int i = 0; i < numOfProcesses; i++) {
            if (finish[i])
                continue;

            boolean canAllocate = true;

            for (int j = 0; j < numOfResources; j++) {
                if (max[i][j] - allocation[i][j] > work[j]) {
                    canAllocate = false;
                    break;
                }
            }

            if (canAllocate) {
                for (int j = 0; j < numOfResources; j++) {
                    work[j] += allocation[i][j];
                }
                finish[i] = true;
            }
        }

        for (boolean f : finish) {
            if (!f)
                return false;
        }
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] available = {3, 3, 2};
        int[][] max = {{7, 5, 3}, {3, 2, 2}, {9, 0, 2}, {2, 2, 2}, {4, 3, 3}};
        int[][] allocation = {{0, 1, 0}, {2, 0, 0}, {3, 0, 2}, {2, 1, 1}, {0, 2, 2}};

        BankersAlgorithm banker = new BankersAlgorithm(available, max, allocation);
        boolean safe = banker.isSafe();

        System.out.println(safe ? "System is in a safe state" : "System is in an unsafe state");
    }
}