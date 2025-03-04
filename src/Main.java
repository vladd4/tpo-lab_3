public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] sizes = {100, 500, 1000};
        int[] threadCounts = {2, 4, 8};

        PerformanceExperiment.performanceExperiment(sizes, threadCounts);
    }
}