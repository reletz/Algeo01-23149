package com.smean.kalkulatorsmean;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class InterpolasiBicubicSpline {
    public static double[][] calculateCoefficients(double[] matrix) {
        double[][] X = createMatrixX();
        double[][] yMatrix = new double[16][1];
        for (int i = 0; i < 16; i++) {
            yMatrix[i][0] = matrix[i];
        }

        // Invers matrix X
        double[][] XInverse = MatriksBalikan.getInvers(X, "balikan");

        // Calculate matrix a
        double[][] a = OBE.multiplyMatrix(XInverse, yMatrix);

        return a;
    }

    public static void handleInput(Scanner scanner) {
        scanner.useLocale(Locale.US);
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

                System.out.println("Nilai interpolasi pada (" + aVal + ", " + bVal + ") adalah: " + result);
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
        double[][] normalizedPoints = {
            {0, 0},
            {1, 0},
            {0, 1},
            {1, 1}
        };

        int index = 0;

        // f(x, y)
        for (int i = 0; i < 4; i++) {
            double x = normalizedPoints[i][0];
            double y = normalizedPoints[i][1];
            for (int j = 0; j <= 3; j++) {
                for (int k = 0; k <= 3; k++) {
                    X[index][j * 4 + k] = Math.pow(x, k) * Math.pow(y, j);
                }
            }
            index++;
        }

        // Turunan parsial terhadap x
        for (int i = 0; i < 4; i++) {
            double x = normalizedPoints[i][0];
            double y = normalizedPoints[i][1];
            for (int j = 0; j <= 3; j++) {
                for (int k = 0; k <= 3; k++) {
                    X[index][j * 4 + k] = (k == 0 ? 0 : k * Math.pow(x, k - 1)) * Math.pow(y, j);
                }
            }
            index++;
        }

        // Turunan parsial terhadap y
        for (int i = 0; i < 4; i++) {
            double x = normalizedPoints[i][0];
            double y = normalizedPoints[i][1];
            for (int j = 0; j <= 3; j++) {
                for (int k = 0; k <= 3; k++) {
                    X[index][j * 4 + k] = Math.pow(x, k) * (j == 0 ? 0 : j * Math.pow(y, j - 1));
                }
            }
            index++;
        }

        // Turunan terhadap x dan y
        for (int i = 0; i < 4; i++) {
            double x = normalizedPoints[i][0];
            double y = normalizedPoints[i][1];
            for (int j = 0; j <= 3; j++) {
                for (int k = 0; k <= 3; k++) {
                    X[index][j * 4 + k] = (k == 0 ? 0 : k * Math.pow(x, k - 1)) * (j == 0 ? 0 : j * Math.pow(y, j - 1));
                }
            }
            index++;
        }

        return X;
    }

    public static double bicubicInterpolation(double[][] coeffs, double a, double b) {
        double result = 0.0;
        int index = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                result += coeffs[index][0] * Math.pow(a, j) * Math.pow(b, i);
                index++;
            }
        }
        return result;
    }
}
