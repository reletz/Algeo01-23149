public class OBE {
    static final int IDX_UNDEF = -1;

    //Perkalian antar dua matrix
    public static double[][] multiplyMatrix(double[][] matrix1, double[][] matrix2){
        int i, j, k, meetPoint;
        double temp;
        int mResult = matrix1.length;
        int nResult = matrix2[0].length;
        double[][] resultMatrix = new double[mResult][nResult];
        meetPoint = matrix1[0].length;
        for (i = 0; i < mResult; i++){
            for (j = 0; j < nResult; j++){
                temp = 0;
                for (k = 0; k < meetPoint; k++){
                    temp += (matrix1[i][k] * matrix2[k][j]);
                } resultMatrix[i][j] = temp;
            }
        } return resultMatrix;
    }
    //Ubah matrix ke augmented matrix
    public static double[][] toAugmented(double[][] squareMatrix, double[][] rhs){
        int i, j;
        int m = squareMatrix.length;
        int n1 = squareMatrix[0].length;
        int n2 = rhs[0].length;

        double[][] augmentedMatrix = new double[squareMatrix.length][n1+n2];
        for (i = 0; i < m; i++) {
            for (j = 0; j < n1; j++) {
                augmentedMatrix[i][j] = squareMatrix[i][j];
            }
            for (j = 0; j < n2; j++){
                augmentedMatrix[i][j + n1] = rhs[i][j];
            }
        } return augmentedMatrix;
    }

    //pecah matrix ke lhs dan rhs
    public static double[][][] splitMatrix(double[][] augmentedMatrix){
        int i, j;
        int m = augmentedMatrix.length;
        int n = augmentedMatrix[0].length;

        double[][] lhs = new double[m][n-1];
        double[][] rhs = new double[m][1];
        for (i = 0; i < m; i++){
            for (j = 0; j < n-1; j++){
                lhs[i][j] = augmentedMatrix[i][j];
            }   rhs[i][0] = augmentedMatrix[i][n-1];
        } 
        double[][][] result = {lhs, rhs};
        return result;
    }
  
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