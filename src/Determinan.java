import java.util.Scanner;

public class Determinan {
    public static double getDeterminan(double[][] matrix, String function) {
        double detResult = 1;
        if (function.equals("gauss")) {
            return determinanGauss(matrix);
        } 

        if (function.equals("Kofaktor")) {
            return determinanKofaktor(matrix);
        }
        return detResult;
    }

    public static double determinanGauss(double[][] matrix) {
        int n = matrix.length;
        double[][] tmpMatrix = new double[n][n];

        // Salin matriks ke tmpMatrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmpMatrix[i][j] = matrix[i][j];
            }
        }
        double determinan = 1;
        // int counterSwap = 0;

        // Operasi Baris Elemneter (OBE)
        tmpMatrix = OBE.toReducedRowEchelon(tmpMatrix);
        for (int i = 0; i < n; i++) {
            determinan *= tmpMatrix[i][i];
        }
        return determinan;
    }
    
    public static double determinanKofaktor(double[][] matrix){
        int n = matrix.length;
        double[][] matrixKofaktor = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixKofaktor[i][j] = getKofaktor(matrix, i, j);
            }
        }
        return 0;
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
                break;
            case 2:
                Main.clearConsole();
                System.out.print("Masukkan file path: ");
                String filePath = scanner.nextLine();
                inputMatrix = IOMatriks.readFile(filePath);
                determinant = getDeterminan(inputMatrix, function);
                break;
            default:
                System.out.println("Pilihan invalid!");
                break;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMENU DETERMINAN:");
            System.out.println("1. Metode Eliminasi Gauss");
            System.out.println("2. Metode Kofaktor");
            System.out.println("3. Keluar");
            System.out.print("\nMasukkan pilihan: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Main.clearConsole();
                    System.out.println("Metode Eliminasi Gauss\n");
                    handleInput(scanner, "gauss");
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