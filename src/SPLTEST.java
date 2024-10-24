public class SPLTEST {

  // Fungsi untuk menyelesaikan SPL dengan metode Gauss
  public static double[][] gauss(double[][] matrix) {
      int rows = matrix.length;
      int cols = matrix[0].length;

      for (int i = 0; i < Math.min(rows, cols); i++) {
          // Pivots
          int maxRow = i;
          for (int k = i + 1; k < rows; k++) {
              if (Math.abs(matrix[k][i]) > Math.abs(matrix[maxRow][i])) {
                  maxRow = k;
              }
          }

          // Swap rows
          double[] temp = matrix[i];
          matrix[i] = matrix[maxRow];
          matrix[maxRow] = temp;

          // Make all rows below this one 0 in current column
          for (int k = i + 1; k < rows; k++) {
              double factor = matrix[k][i] / matrix[i][i];
              for (int j = i; j < cols; j++) {
                  matrix[k][j] -= factor * matrix[i][j];
              }
          }
      }
      return matrix;
  }

  // Fungsi untuk menyelesaikan SPL dengan metode Gauss-Jordan
  public static double[][] gaussJordan(double[][] matrix) {
      int rows = matrix.length;
      int cols = matrix[0].length;

      for (int i = 0; i < Math.min(rows, cols); i++) {
          // Pivots
          int maxRow = i;
          for (int k = i + 1; k < rows; k++) {
              if (Math.abs(matrix[k][i]) > Math.abs(matrix[maxRow][i])) {
                  maxRow = k;
              }
          }

          // Swap rows
          double[] temp = matrix[i];
          matrix[i] = matrix[maxRow];
          matrix[maxRow] = temp;

          // Make the pivot 1
          double pivot = matrix[i][i];
          for (int j = 0; j < cols; j++) {
              matrix[i][j] /= pivot;
          }

          // Make all rows above and below this one 0 in current column
          for (int k = 0; k < rows; k++) {
              if (k != i) {
                  double factor = matrix[k][i];
                  for (int j = 0; j < cols; j++) {
                      matrix[k][j] -= factor * matrix[i][j];
                  }
              }
          }
      }
      return matrix;
  }

  // Fungsi untuk mencetak matriks
  public static void printMatrix(double[][] matrix) {
      for (double[] row : matrix) {
          for (double value : row) {
              System.out.printf("%8.4f", value);
          }
          System.out.println();
      }
  }

  public static void main(String[] args) {
      double[][] matrix = {
          {2, 1, -1, 8},
          {-3, -1, 2, -11},
          {-2, 1, 2, -3},
          {1, 2, 3, 4},
          {3, -1, -1, 3},
          {2, 3, 1, 5},
          {1, -1, 2, 1}
      };

      System.out.println("Matriks Augmented:");
      printMatrix(matrix);

      double[][] gaussResult = gauss(matrix);
      System.out.println("\nHasil Eliminasi Gauss:");
      printMatrix(gaussResult);

      double[][] gaussJordanResult = gaussJordan(matrix);
      System.out.println("\nHasil Eliminasi Gauss-Jordan:");
      printMatrix(gaussJordanResult);
  }
}