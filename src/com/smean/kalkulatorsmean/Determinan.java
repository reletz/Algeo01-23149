package com.smean.kalkulatorsmean;
import java.util.Scanner;

public class Determinan {
    public static double getDeterminan(double[][] matrix, String function) {
        if (function.equalsIgnoreCase("obe")) {
            return determinanOBE(matrix);
        } 
    
        if (function.equalsIgnoreCase("Kofaktor")) {
            return determinanKofaktor(matrix);
        }
        throw new IllegalArgumentException("Fungsi tidak dikenali: " + function);
    }

    public static double determinanOBE(double[][] matrix) {
        int det = 1;

        int pivotRow, col, i;
        col = matrix.length;
        pivotRow = 0;
        for (i = 0; i < col; i++) {
            int nonZeroRow = OBE.nonZeroRowCheck(matrix, pivotRow, i);
            if (nonZeroRow != -1) {
                OBE.rowSwap(matrix, pivotRow, nonZeroRow);
                // rowMultiply(matrix, pivotRow, i);
                OBE.rowSubstract(matrix, pivotRow, i);
                pivotRow += 1;
            }
        }
        // IOMatriks.writeMatrix(matrix);

        for (i = 0; i < matrix.length; i++) {
            det *= matrix[i][i];
            // System.out.println("det: " + det);
        }
        return det;
    }
    
    public static double determinanKofaktor(double[][] matrix){
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        } else if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            double det = 0;
            for (int i = 0; i < n; i++) {
                double[][] subMatrix = new double[n - 1][n - 1];
                for (int j = 1; j < n; j++) {
                    int rowIndex = 0;
                    for (int k = 0; k < n; k++) {
                        if (k != i) {
                            subMatrix[j - 1][rowIndex] = matrix[j][k];
                            rowIndex++;
                        }
                    }
                }
                if (i % 2 == 0) {
                    det += matrix[0][i] * determinanKofaktor(subMatrix);
                } else {
                    det -= matrix[0][i] * determinanKofaktor(subMatrix);
                }
            }
            return det;
        }
    }

    public static void handleInput(Scanner scanner, String function) {
        System.out.println("Pilihan Input");
        System.out.println("1. Keyboard");
        System.out.println("2. File");
        System.out.print("Masukkan pilihan: ");
        int subChoice = scanner.nextInt();
        scanner.nextLine();

        double[][] inputMatrix;
        double determinant;
        

        switch (subChoice) {
            case 1:
                Main.clearConsole();
                inputMatrix = IOMatriks.getUserInput(scanner);
                determinant = getDeterminan(inputMatrix, function);
                System.out.println("Determinan: " + determinant);
                break;
            case 2:
                Main.clearConsole();
                System.out.print("Masukkan file path: ");
                String filePath = scanner.nextLine();
                inputMatrix = IOMatriks.readFile(filePath);
                determinant = getDeterminan(inputMatrix, function);
                System.out.println("Determinan: " + determinant);
                break;
            default:
                System.out.println("Pilihan invalid!");
                break;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Main.clearConsole();
            System.out.println("\nMENU DETERMINAN:");
            System.out.println("1. Metode OBE");
            System.out.println("2. Metode Kofaktor");
            System.out.println("3. Keluar");
            System.out.print("\nMasukkan pilihan: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Main.clearConsole();
                    System.out.println("Metode OBE\n");
                    handleInput(scanner, "obe");
                    break;
                case 2:
                    Main.clearConsole();
                    System.out.println("Metode Kofaktor\n");
                    handleInput(scanner, "kofaktor");
                    break;
                case 3:
                    Main.clearConsole();
                    System.out.println("Kembali ke Menu Utama.");
                    return;
                default:
                    System.out.println("Pilihan Invalid.");
            }
        }
    }
}