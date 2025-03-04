import java.util.concurrent.*;

public class MatrixMultiplication {
    public static Result parallelStrippedMultiplication(double[][] A, double[][] B, int threads) throws InterruptedException {
        int rowsA = A.length;
        int colsB = B[0].length;
        int colsA = A[0].length;

        Result result = new Result(rowsA, colsB);

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int threadId = 0; threadId < threads; threadId++) {
            final int start = threadId * (rowsA / threads);
            final int end = (threadId == threads - 1) ? rowsA : start + (rowsA / threads);

            executor.submit(() -> {
                    for (int i = start; i < end; i++) {
                        for (int k = 0; k < colsA; k++) {
                            double multiplier = A[i][k];
                            for (int j = 0; j < colsB; j++) {
                                result.set(i, j, result.get(i, j) + multiplier * B[k][j]);
                            }
                        }
                    }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.NANOSECONDS);

        return result;
    }

    public static Result foxMultiplication(double[][] A, double[][] B, int threads) throws InterruptedException {
        int n = A.length;
        Result result = new Result(n, n);

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        int blockSize = n / threads;

        for (int threadId = 0; threadId < threads; threadId++) {
            final int start = threadId * blockSize;
            final int end = (threadId == threads - 1) ? n : start + blockSize;

            executor.submit(() -> {
                    for (int i = start; i < end; i++) {
                        for (int k = 0; k < n; k++) {
                            double multiplier = A[i][k];
                            for (int j = 0; j < n; j++) {
                                result.set(i, j, result.get(i, j) + multiplier * B[k][j]);
                            }
                        }
                    }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.NANOSECONDS);

        return result;
    }
}