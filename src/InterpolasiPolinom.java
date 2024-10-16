public class InterpolasiPolinom {

    public static double[][] interpolationSolution(double[][] pointMatrix) {
        int i, j;
        int n = pointMatrix.length;
        double[][] augmentedMatrix = new double[n][n + 1];
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                augmentedMatrix[i][j] = Math.pow(pointMatrix[i][0], j);
            }
            augmentedMatrix[i][n] = pointMatrix[i][1];
        } return SPL.gauss(augmentedMatrix);
    }

    public static double polinomialInterpolation(double[][] pointMatrix, double x){
        double result = 0;
        double[][] solution = interpolationSolution(pointMatrix);
        for (int i = 0; i < solution.length; i++){
            result += solution[i][0] * Math.pow(x, i);
        } return result;
    }

    public static void main(String[] args) {
        // double[][] pointMatrix = {
        //     {8, 2.0794},
        //     {9, 2.1972},
        //     {9.5, 2.2513},
        // };
        // double x = 9.2;
        // System.out.println(polinomialInterpolation(pointMatrix, x));
    }
}
