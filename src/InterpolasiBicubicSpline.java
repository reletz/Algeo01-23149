import java.util.Scanner;

public class InterpolasiBicubicSpline {
    public static void handleInput(Scanner scanner) {
        double[] y = new double[16];
        System.out.println("Masukkan nilai f(0, 0), f(1, 0), f(0, 1), f(1, 1), fx(0, 0), fx(1, 0), fx(0, 1), fx(1, 1), fy(0, 0), fy(1, 0), fy(0, 1), fy(1, 1), fxy(0, 0), fxy(1, 0), fxy(0, 1), fxy(1, 1):");
        for (int i = 0; i < 16; i++) {
            y[i] = scanner.nextDouble();
        }
        double aVal = scanner.nextDouble();
        double bVal = scanner.nextDouble();

        // Step 2: Create matrices X and y
        double[][] X = createMatrixX();
        double[][] yMatrix = new double[16][1];
        for (int i = 0; i < 16; i++) {
            yMatrix[i][0] = y[i];
        }

        // Step 3: Find the inverse of matrix X
        double[][] XInverse = invertMatrix(X);

        // Step 4: Calculate matrix a
        double[][] a = multiplyMatrices(XInverse, yMatrix);

        double result = bicubicInterpolation(a, aVal, bVal);

        System.out.println("Nilai interpolasi pada (" + aVal + ", " + bVal + ") adalah: " + result);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMENU BICUBIC SPLINE INTERPOLATION:");
            System.out.println("1. Masukkan Matriks");
            System.out.println("2. Keluar");
            System.out.print("\nMasukkan pilihan: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Main.clearConsole();
                    System.out.println("BICUBIC SPLINE INTERPOLATION\n");
                    handleInput(scanner);
                    break;
                case 2:
                    Main.clearConsole();
                    System.out.println("Kembali ke Menu Utama.");
                    return;
                default:
                    System.out.println("Pilihan Invalid.");
            }
        }
    }

    public static double[][] createMatrixX() {
        double[][] X = new double[16][16];
        int index = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                for (int k = 0; k <= 3; k++) {
                    for (int l = 0; l <= 3; l++) {
                        X[index][i * 4 + j] = Math.pow(k, i) * Math.pow(l, j);
                    }
                }
                index++;
            }
        }
        return X;
    }

    public static double[][] invertMatrix(double[][] matrix) {
        // Implement your matrix inversion function here
        // Placeholder for matrix inversion logic
        return new double[16][16]; // Replace with actual inversion logic
    }

    public static double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        int r1 = firstMatrix.length;
        int c1 = firstMatrix[0].length;
        int c2 = secondMatrix[0].length;
        double[][] result = new double[r1][c2];

        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        return result;
    }

    public static double bicubicInterpolation(double[][] coeffs, double a, double b) {
        double result = 0.0;
        int index = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                result += coeffs[index][0] * Math.pow(a, i) * Math.pow(b, j);
                index++;
            }
        }
        return result;
    }
}