import java.util.Scanner;

public class Determinan {
    public static double getDeterminan(double[][] matrix, String function) {
        double detResult = 1;
        if (function.equals("gauss")) {
            return determinanGauss(matrix);
        } 

        if (function.equals("Kofaktor")) {
            // return determinanKofaktor(matrix);
            return detResult;
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
    
    static void getKofaktor(){

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
            int subChoice;
            scanner.nextLine();

            double[][] inputMatrix;
            double determinant;

            switch (choice) {
                case 1:
                    Main.clearConsole();
                    System.out.println("Metode Eliminasi Gauss\n");
                    System.out.println("Pilihan Input");
                    System.out.println("1. Keyboard");
                    System.out.println("2. File");
                    System.out.print("Masukkan pilihan: ");

                    subChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (subChoice) {
                        case 1:
                            Main.clearConsole();
                            System.out.println("Metode Eliminasi Gauss\n");
                            inputMatrix = IOMatriks.getUserInput(scanner);
                            determinant = getDeterminan(inputMatrix, "gauss");
                            System.out.println("Determinan: " + determinant);
                            break;
                        case 2:
                            Main.clearConsole();
                            System.out.println("Metode Kofaktor\n");
                            System.out.print("Masukkan file path: ");
                            String filePath = scanner.nextLine();
                            inputMatrix = IOMatriks.readFile(filePath);
                            determinant = getDeterminan(inputMatrix, "gauss");
                            System.out.println("Determinan: " + determinant);
                            break;
                        default:
                            System.out.println("Pilihan Invalid!");
                            break;
                    }
                    break;
                case 2:
                    Main.clearConsole();
                    System.out.println("Metode Kofaktor\n");
                    System.out.println("Pilihan Input");
                    System.out.println("1. Keyboard");
                    System.out.println("2. File");
                    System.out.print("Masukkan pilihan: ");

                    subChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (subChoice) {
                        case 1:
                            Main.clearConsole();
                            System.out.println("Metode Kofaktor\n");
                            inputMatrix = IOMatriks.getUserInput(scanner);
                            determinant = getDeterminan(inputMatrix, "kofaktor");
                            System.out.println("Determinan: " + determinant);
                            break;
                        case 2: 
                            Main.clearConsole();
                            System.out.println("Metode Kofaktor\n");
                            System.out.print("Masukkan file path: ");
                            String filePath = scanner.nextLine();
                            inputMatrix = IOMatriks.readFile(filePath);
                            determinant = getDeterminan(inputMatrix, "kofaktor");
                            System.out.println("Determinan: " + determinant);
                            break;
                        default:
                            System.out.println("Pilihan Invalid!");
                            break;
                    }
                    break;
                case 3:
                    Main.clearConsole();
                    System.out.println("Kembali ke Menu Utama.");
                default:
                    System.out.println("Pilihan Invalid.");
            }
        }
    }
}