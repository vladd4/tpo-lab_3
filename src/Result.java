public class Result {
    private double[][] matrix;

    public Result(int rows, int cols) {
        matrix = new double[rows][cols];
    }

    public void set(int row, int col, double value) {
        matrix[row][col] = value;
    }

    public double get(int row, int col) {
        return matrix[row][col];
    }

    public double[][] getMatrix() {
        return matrix;
    }
}