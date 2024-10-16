import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InterpolasiBicubicSpline {
    public static double[][] calculateCoefficients(double[] matrix) {
        // Given data
        double[][] F = {
            {matrix[0], matrix[1]},
            {matrix[2], matrix[3]}
        };

        double[][] F_x = {
            {matrix[4], matrix[5]},
            {matrix[6], matrix[7]}
        };

        double[][] F_y = {
            {matrix[8], matrix[9]},
            {matrix[10], matrix[11]}
        };

        double[][] F_xy = {
            {matrix[12], matrix[13]},
            {matrix[14], matrix[15]}
        };

        // Matrix for the bicubic spline coefficients
        double[][] A = {
            {1, 0, 0, 0},
            {0, 0, 1, 0},
            {-3, 3, -2, -1},
            {2, -2, 1, 1}
        };

        // Combine the function values and derivatives into a matrix for interpolation
        double[][] G = {
            {F[0][0], F[0][1], F_y[0][0], F_y[0][1]},
            {F[1][0], F[1][1], F_y[1][0], F_y[1][1]},
            {F_x[0][0], F_x[0][1], F_xy[0][0], F_xy[0][1]},
            {F_x[1][0], F_x[1][1], F_xy[1][0], F_xy[1][1]}
        };

        // Compute the interpolation coefficients
        double[][] C = new double[4][4];
        double[][] AT = transposeMatrix(A);
        double[][] AG = multiplyMatrix(A, G);
        C = multiplyMatrix(AG, AT);

        return C;
    }

    public static double[][] transposeMatrix(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] transposed = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    public static double[][] multiplyMatrix(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        double[][] result = new double[rows1][cols2];
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    public static void handleInput(Scanner scanner) {
        System.out.println("Pilihan Input");
        System.out.println("1. Keyboard");
        System.out.println("2. File");
        System.out.print("Masukkan pilihan: ");
        int subChoice = scanner.nextInt();
        scanner.nextLine();
        
        double aVal = 0.0;
        double bVal = 0.0;
        double[][] coeff;
        double result;
        String resultString;
        double[] y;

        switch (subChoice) {
            case 1:
                Main.clearConsole();
                System.out.println("BICUBIC SPLINE INTERPOLATION\n");
                System.out.println("Masukkan matriks:");
                y = new double[16];
                for (int i = 0; i < 16; i++) {
                    y[i] = scanner.nextDouble();
                }
                aVal = scanner.nextDouble();
                bVal = scanner.nextDouble();

                coeff = calculateCoefficients(y);

                // Calculate interpolation value at (a, b)
                result = bicubicInterpolation(coeff, aVal, bVal);

                System.out.println("\nNilai interpolasi pada (" + aVal + ", " + bVal + ") adalah: " + result);
                resultString = Double.toString(result);
                IOMatriks.saveToFile(resultString, scanner);
                break;
            case 2:
                Main.clearConsole();
                y = new double[16];
                System.out.print("Masukkan file path: ");
                String filePath = scanner.nextLine();
                try {
                    Scanner fileScanner = new Scanner(new File(filePath));
                    for (int i = 0; i < 16; i++) {
                        if (fileScanner.hasNextDouble()) {
                            y[i] = fileScanner.nextDouble();
                        }
                    }
                    if (fileScanner.hasNextDouble()) {
                        aVal = fileScanner.nextDouble();
                    }
                    if (fileScanner.hasNextDouble()) {
                        bVal = fileScanner.nextDouble();
                    }
                    fileScanner.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File tidak ditemukan: " + filePath);
                    return;
                }
                coeff = calculateCoefficients(y);
                result = bicubicInterpolation(coeff, aVal, bVal);
                System.out.println("\nNilai interpolasi pada (" + aVal + ", " + bVal + ") adalah: " + result);
                resultString = Double.toString(result);
                IOMatriks.saveToFile(resultString, scanner);
                break;
            default:
                System.out.println("Pilihan invalid!");
                break;
        }        
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("BICUBIC SPLINE INTERPOLATION:");
            System.out.println("\nSUB-MENU BICUBIC SPLINE INTERPOLATION:");
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

    public static double bicubicInterpolation(double[][] coeffs, double a, double b) {
        double result = 0.0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result += coeffs[i][j] * Math.pow(a, i) * Math.pow(b, j);
            }
        }
        return result;
    }
}