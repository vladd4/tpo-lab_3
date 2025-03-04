import java.util.Random;

public class PerformanceExperiment {
    private static boolean compareResults(Result result1, Result result2) {
        double[][] matrix1 = result1.getMatrix();
        double[][] matrix2 = result2.getMatrix();

        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return false;
        }

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                if (Math.abs(matrix1[i][j] - matrix2[i][j]) > 1e-6) {
                    return false;
                }
            }
        }
        return true;
    }

    private static double[][] generateRandomMatrix(int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextDouble() * 100;
            }
        }
        return matrix;
    }

    public static void performanceExperiment(int[] sizes, int[] threadCounts) throws InterruptedException {
        for (int size : sizes) {
            double[][] A = generateRandomMatrix(size, size);
            double[][] B = generateRandomMatrix(size, size);

            for (int threads : threadCounts) {
                Result strippedResult = MatrixMultiplication.parallelStrippedMultiplication(A, B, threads);
                Result foxResult = MatrixMultiplication.foxMultiplication(A, B, threads);

                boolean resultsMatch = compareResults(strippedResult, foxResult);

                long strippedStart = System.nanoTime();
                strippedResult = MatrixMultiplication.parallelStrippedMultiplication(A, B, threads);
                long strippedEnd = System.nanoTime();

                long foxStart = System.nanoTime();
                foxResult = MatrixMultiplication.foxMultiplication(A, B, threads);
                long foxEnd = System.nanoTime();

                System.out.printf("Size: %d, Threads: %d\n", size, threads);
                System.out.printf("Results Match: %b\n", resultsMatch);
                System.out.printf("Stripped Time: %.4f s\n", (strippedEnd - strippedStart) / 1_000_000_000.0);
                System.out.printf("Fox Time: %.4f s\n\n", (foxEnd - foxStart) / 1_000_000_000.0);
            }
        }
    }
}