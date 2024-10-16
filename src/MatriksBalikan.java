import java.util.Scanner;

public class MatriksBalikan {
    // Ensure no -0 value
    public static double[][] normalizeMatrix(double[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == -0.0) {
                    matrix[i][j] = 0.0;
                }
            }
        }
        return matrix;
    }    

    public static double[][] getInvers(double[][] matrix, String function) {
        double[][] copy = OBE.copyMatrix(matrix);
        double det = Determinan.determinanOBE(copy);
        if (det == 0) {
            System.out.println("Matrix berupa matriks singular dan tidak memiliki invers.");
            return new double[0][0];
        }
    
        double[][] result;
        if (function.equals("balikan")) {
            result = inversBalikan(matrix);
        } else if (function.equals("adjoin")) {
            result = inversAdjoin(matrix);
        } else {
            return new double[0][0];
        }
    
        return normalizeMatrix(result);
    }

    public static double[][] inversBalikan(double[][] matrix) {
        int n = matrix.length;
        double[][] augmentedMatrix;
        double[][] identityMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            identityMatrix[i][i] = 1;
        }

        augmentedMatrix = OBE.toAugmented(matrix, identityMatrix);
        augmentedMatrix = OBE.toReducedRowEchelon(augmentedMatrix);

        double[][] inverseMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverseMatrix[i][j] = augmentedMatrix[i][j + n];
            }
        }
        return inverseMatrix;
    }

    public static double[][] inversAdjoin(double[][] matrix) {
        double[][] matrixInvers = OBE.transpose(getMatriksKofaktor(matrix));
        double[][] copy = OBE.copyMatrix(matrix);
        double det = Determinan.determinanOBE(copy);
        return multiplyByCoef(matrixInvers, 1 / det);
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
        double det = Determinan.determinanKofaktor(kofaktor);
        int sign = ((x + y) % 2 == 0) ? 1 : -1;
        return sign * det;
    }

    public static void handleInput(Scanner scanner, String function) {
        System.out.println("Pilihan Input");
        System.out.println("1. Keyboard");
        System.out.println("2. File");
        System.out.print("Masukkan pilihan: ");
        int subChoice = scanner.nextInt();
        scanner.nextLine();

        double[][] inputMatrix;
        double[][] outputMatrix;
        String outputMatrixText;

        switch (subChoice) {
            case 1:
                Main.clearConsole();
                inputMatrix = IOMatriks.getUserInput(scanner);
                outputMatrix = getInvers(inputMatrix, function);
                outputMatrixText = IOMatriks.convertMatrixToText(outputMatrix);
                if (outputMatrix.length != 0) {
                    System.out.println("Matriks Invers:");
                }
                IOMatriks.writeMatrix(outputMatrix);
                IOMatriks.saveToFile(outputMatrixText, scanner);
                break;
            case 2:
                Main.clearConsole();
                System.out.print("Masukkan file path: ");
                String filePath = scanner.nextLine();
                inputMatrix = IOMatriks.readFile(filePath);
                outputMatrix = getInvers(inputMatrix, function);
                outputMatrixText = IOMatriks.convertMatrixToText(outputMatrix);
                if (outputMatrix.length != 0) {
                    System.out.println("Matriks Invers:");
                }
                IOMatriks.writeMatrix(outputMatrix);
                IOMatriks.saveToFile(outputMatrixText, scanner);
                break;
            default:
                System.out.println("Pilihan invalid!");
                break;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("MATRIKS BALIKAN:");
            System.out.println("\nSUB-MENU MATRIKS BALIKAN:");
            System.out.println("1. Metode OBE");
            System.out.println("2. Metode Adjoin");
            System.out.println("3. Keluar");
            System.out.print("\nMasukkan pilihan: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Main.clearConsole();
                    System.out.println("METODE OBE\n");
                    handleInput(scanner, "balikan");
                    break;
                case 2:
                    Main.clearConsole();
                    System.out.println("METODE ADJOIN\n");
                    handleInput(scanner, "adjoin");
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
