import java.util.Scanner;

public class MatriksBalikan {
    public static double[][] getInvers(double[][] matrix, String function) {
        double[][] matrixInverse = new double[matrix.length][matrix[0].length];

        if (function.equals("balikan")) {
            matrixInverse = inversBalikan(matrix);
            System.out.println("Invers Matriks: ");
            return matrixInverse;
        }

        if (function.equals("adjoin")) {
            matrixInverse = inversAdjoin(matrix);
            System.out.println("Invers Matriks: ");
            return matrixInverse;
        }
        return matrixInverse;
    }

    public static double[][] inversBalikan(double[][] matrix) {
        int n = matrix.length;
        
        // Matriks gabungan (A | I)
        double[][] augmentedMatrix = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            // Matriks masukan A
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = matrix[i][j];
            }
            // Matriks identitas I
            for (int j = n; j < 2 * n; j++) {
                augmentedMatrix[i][j] = (i == j - n) ? 1 : 0;
            }
        }

        // Operasi Baris Elementer
        for (int i = 0; i < n; i++) {
            // Mencari elemen utama
            double elmtUtama = augmentedMatrix[i][i];
            if (elmtUtama == 0) {
                // Cari baris di bawah yang memiliki elemen utama tidak nol
                boolean found = false;
                for (int k = i + 1; k < n; k++) {
                    if (augmentedMatrix[k][i] != 0) {
                        // Tukar baris
                        swapRows(augmentedMatrix, i, k);
                        elmtUtama = augmentedMatrix[i][i];
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // Jika tidak ditemukan, coba operasi baris lainnya
                    for (int k = i + 1; k < n; k++) {
                        if (augmentedMatrix[k][i] != 0) {
                            // Tambahkan kelipatan baris
                            addMultipleOfRow(augmentedMatrix, i, k, 1);
                            elmtUtama = augmentedMatrix[i][i];
                            if (elmtUtama != 0) {
                                found = true;
                                break;
                            }
                        }
                    }
                }
            }

            // Membagi baris dengan elemen utama
            multiplyRow(augmentedMatrix, i, 1 / elmtUtama);

            // Mengeliminasi elemen di bawah dan di atas 1 utama
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double elmt = augmentedMatrix[k][i];
                    addMultipleOfRow(augmentedMatrix, k, i, -elmt);
                }
            }
        }

        // Memisahkan matriks invers dari matriks gabungan
        double[][] matriksInverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriksInverse[i][j] = augmentedMatrix[i][j + n];
            }
        }

        return matriksInverse;
    }

    public static void swapRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    public static void multiplyRow(double[][] matrix, int row, double koefisien) {
        for (int j = 0; j < matrix[row].length; j++) {
            matrix[row][j] *= koefisien;
        }
    }

    public static void addMultipleOfRow(double[][] matrix, int rowTujuan, int rowAsal, double koefisien) {
        for (int j = 0; j < matrix[rowTujuan].length; j++) {
            matrix[rowAsal][j] += koefisien * matrix[rowAsal][j];
        }
    }

    public static double[][] inversAdjoin(double[][] matrix) {
        int n = matrix.length;
        double[][] matrixInvers = new double[n][n];

        matrixInvers = getMatriksKofaktor(matrix);
        matrixInvers = transpose(matrixInvers);

        double det = getDeterminan(matrix);
        double koefisien = 1 / det;
        matrixInvers = multiplyByCoef(matrixInvers, koefisien);

        return matrixInvers;
    }

    public static double[][] multiplyByCoef(double[][] matrix, double koefisien) {
        int n = matrix.length;
        double[][] multipliedMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                multipliedMatrix[i][j] = matrix[i][j] * koefisien;
            }
        }

        return multipliedMatrix;
    }

    public static double[][] getMatriksKofaktor(double[][] matrix) {
        int n = matrix.length;
        double[][] matrixKofaktor = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixKofaktor[i][j] = getKofaktor(matrix, i, j);
            }
        }

        return matrixKofaktor;
    }

    public static double getKofaktor(double[][] matrix, int x, int y) {
        int n = matrix.length;
        double[][] kofaktor = new double[n - 1][n - 1];
        int kofaktor_I = 0;

        for (int i = 0; i < n; i++) {
            if (i == x) continue;
            int kofaktor_J = 0;
            for (int j = 0; j < n; j++) {
                if (j == y) continue;
                kofaktor[kofaktor_I][kofaktor_J] = matrix[i][j];
                kofaktor_J++;
            }
            kofaktor_I++;
        }

        double det = getDeterminan(kofaktor);
        int sign = ((x + y) % 2 == 0) ? 1 : -1;
        return sign * det;
    }

    public static double[][] transpose(double[][] matrix) {
        int n = matrix.length;
        double[][] transposedMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }

        return transposedMatrix;
    }

    public static double getDeterminan(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }
        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double determinan = 0;
        for (int j = 0; j < n; j++) {
            determinan += matrix[0][j] * getKofaktor(matrix, 0, j);
        }
        return determinan;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMENU MATRIKS BALIKAN:");
            System.out.println("1. Metode OBE");
            System.out.println("2. Metode Adjoin");
            System.out.println("3. Keluar");
            System.out.print("\nMasukkan pilihan: ");

            int choice = scanner.nextInt();
            int subChoice;
            scanner.nextLine();

            double[][] inputMatrix;
            double[][] outputMatrix;
            String outputMatrixText;

            switch (choice) {
                case 1:
                    Main.clearConsole();
                    System.out.println("METODE OBE\n");
                    System.out.println("Pilihan Input");
                    System.out.println("1. Keyboard");
                    System.out.println("2. File");
                    System.out.print("Masukkan pilihan: ");
                    
                    subChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (subChoice) {
                        case 1:
                            Main.clearConsole();
                            System.out.println("METODE OBE\n");
                            inputMatrix = IOMatriks.getUserInput(scanner);
                            outputMatrix = getInvers(inputMatrix, "balikan");
                            outputMatrixText = IOMatriks.convertMatrixToText(outputMatrix);
                            System.out.println("Matriks Balikan:");
                            IOMatriks.writeMatrix(outputMatrix);
                            IOMatriks.saveToFile(outputMatrixText, scanner);
                            break;
                        case 2:
                            Main.clearConsole();
                            System.out.println("METODE OBE\n");
                            System.out.print("Masukkan file path: ");
                            String filePath = scanner.nextLine();
                            inputMatrix = IOMatriks.readFile(filePath);
                            outputMatrix = getInvers(inputMatrix, "balikan");
                            outputMatrixText = IOMatriks.convertMatrixToText(outputMatrix);
                            System.out.println("Matriks Balikan:");
                            IOMatriks.writeMatrix(outputMatrix);
                            IOMatriks.saveToFile(outputMatrixText, scanner);
                            break;
                        default:
                            System.out.println("Pilihan invalid!");
                            break;
                    }
                    break;
                case 2:
                    Main.clearConsole();
                    System.out.println("METODE ADJOIN\n");
                    System.out.println("Pilihan Input");
                    System.out.println("1. Keyboard");
                    System.out.println("2. File");
                    System.out.print("Masukkan pilihan: ");

                    subChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (subChoice) {
                        case 1:
                            Main.clearConsole();
                            System.out.println("METODE ADJOIN\n");
                            inputMatrix = IOMatriks.getUserInput(scanner);
                            outputMatrix = getInvers(inputMatrix, "adjoin");
                            outputMatrixText = IOMatriks.convertMatrixToText(outputMatrix);
                            System.out.println("Matriks Balikan:");
                            IOMatriks.writeMatrix(outputMatrix);
                            IOMatriks.saveToFile(outputMatrixText, scanner);
                            break;
                        case 2:
                            Main.clearConsole();
                            System.out.println("METODE ADJOIN\n");
                            System.out.print("Masukkan file path: ");
                            String filePath = scanner.nextLine();
                            inputMatrix = IOMatriks.readFile(filePath);
                            outputMatrix = getInvers(inputMatrix, "adjoin");
                            outputMatrixText = IOMatriks.convertMatrixToText(outputMatrix);
                            System.out.println("Matriks Balikan:");
                            IOMatriks.writeMatrix(outputMatrix);
                            IOMatriks.saveToFile(outputMatrixText, scanner);
                            break;
                        default:
                            System.out.println("Pilihan invalid!");
                            break;
                    }
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