import java.util.Scanner;

public class SPL {
    public static double[][] BackSubstitution(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        double[][] x = new double[n-1][1];

        // Iterate from the last row to the first row
        for (int i = m - 1; i >= 0; i--) {
            x[i][0] = matrix[i][n-1];
            for (int j = i + 1; j < n - 1; j++) {
                x[i][0] -= matrix[i][j] * x[j][0];
            }
            x[i][0] /= matrix[i][i];
        }

        return x;
    }  
    
    //Input: Augmented Matrix
    static double[][] gauss(double[][] matrix){
        double[][] eliminated = OBE.toRowEchelon(matrix);
        return BackSubstitution(eliminated);
    }

    //Input: Augmented Matrix
    static double[][] gaussJordan(double[][] matrix){
        double[][] eliminated = OBE.toReducedRowEchelon(matrix);
        return BackSubstitution(eliminated);
    }

    //Input: Augmented Matrix
    static double[][] matriksBalikan(double[][] matrix){
        int i;
        double[][][] spliitedMatrix = OBE.splitMatrix(matrix);
        double[][] lhs = spliitedMatrix[0];
        double[][] rhs = spliitedMatrix[1];
        double[][] x = OBE.multiplyMatrix(MatriksBalikan.inversBalikan(lhs), rhs);
        double[][] result = new double[x.length][1];
        for (i = 0; i < x.length; i++){
            result[i][0] = x[i][0];
        } return result;
    }

    //Input: Augmented Matrix
    static double[][] cramer(double[][] matrix){
        int i;
        double[][][] spliitedMatrix = OBE.splitMatrix(matrix);
        double[][] lhs = spliitedMatrix[0];
        double[][] rhs = spliitedMatrix[1];
        double[][] result = new double[rhs.length][1];
        for (i = 0; i < lhs.length; i++){
            double[][] tmpMatrix = lhs;
            for (int j = 0; j < lhs[0].length; j++){
                tmpMatrix[j][i] = rhs[j][0];
            } result[i][0] = Determinan.getDeterminan(tmpMatrix, "kofaktor") / Determinan.getDeterminan(lhs, "kofaktor");
        } return result;
    }

    public static void main(String[] args) {
        // Example usage
        double[][] matrix = {
            {2, -1, 0},
            {-1, 2, -1},
            {0, -1, 2}
        };

        double[][] rhs = {{1}, {0}, {1}};
        double[][] newMatrix = OBE.toAugmented(matrix, rhs);
    }
}