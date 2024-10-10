public class OBE {
    static final int IDX_UNDEF = -1;
  
    // 1: Mengalikan baris dengan suatu konstanta non-zero 
    // Objektif: Agar memiliki 1 utama di [targetedRow][targetedCol]
    private static void rowMultiply(double[][] matrix, int targetedRow, int targetedCol) {
        int i;
        double k = 1 / matrix[targetedRow][targetedCol];
        for (i = 0; i < matrix[targetedRow].length; i++) {
            matrix[targetedRow][i] *= k;
        }
    }
  
    // 2: Mengurangi suatu baris dengan baris atau kelipatan baris lain
    // Objektif: Memiliki 0 di depan leading one nantinya
    private static void rowSubstract(double[][] matrix, int pivotRow, int targetedCol) {
        int i, j;
        for (i = pivotRow + 1; i < matrix.length; i++) {
            double k = matrix[i][targetedCol] / matrix[pivotRow][targetedCol];
            for (j = 0; j < matrix[0].length; j++) {
                matrix[i][j] -= k * matrix[pivotRow][j];
            }
        }
    }
  
    // 3: Menukar baris a dengan baris b
    // Objektif: Memudahkan peletakkan barisan 0 di bawah
    private static void rowSwap(double[][] matrix, int rowA, int rowB) {
        double[] temp = matrix[rowA];
        matrix[rowA] = matrix[rowB];
        matrix[rowB] = temp;
    }
  
    // Keperluan lain: Kembalikan baris yang kolomnya bernilai 0
    private static int nonZeroRowCheck(double[][] matrix, int pivotRow, int col) {
        int i;
        for (i = pivotRow; i < matrix.length; i++) {
            if (matrix[i][col] != 0) return i;
        }
        return IDX_UNDEF;
    }
  
    // Gauss Elimination:
    // Konversi matriks biasa ke eselon baris
    public static double[][] toRowEchelon(double[][] matrix) {
        int pivotRow, col, i;
        col = matrix.length;
        pivotRow = 0;
        for (i = 0; i < col; i++) {
            int nonZeroRow = nonZeroRowCheck(matrix, pivotRow, i);
            if (nonZeroRow != IDX_UNDEF) {
                rowSwap(matrix, pivotRow, nonZeroRow);
                rowMultiply(matrix, pivotRow, i);
                rowSubstract(matrix, pivotRow, i);
                pivotRow += 1;
            }
        }
        return matrix;
    }
  
    public static double[][] toReducedRowEchelon(double[][] matrix) {
        toRowEchelon(matrix);
  
        int rows = matrix.length;
        int cols = matrix[0].length;
  
        for (int i = rows - 1; i >= 0; i--) {
            int leadingOneCol = -1;
            for (int j = 0; j < rows; j++) {
                if (matrix[i][j] == 1) {
                    leadingOneCol = j;
                    break;
                }
            }
  
            if (leadingOneCol != -1) {
                for (int k = i - 1; k >= 0; k--) {
                    double factor = matrix[k][leadingOneCol];
                    for (int j = 0; j < cols; j++) {
                        matrix[k][j] -= factor * matrix[i][j];
                    }
                }
            }
        }
  
        return matrix;
    }
  
    // Usage Example
    public static void main(String[] args) {
    }
  }