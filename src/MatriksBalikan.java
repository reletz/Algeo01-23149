import java.util.Scanner;
public class MatriksBalikan {
    public static void getInvers(double[][] matrix, String function) {
        // Placeholder = 0
        // double det = 0;

        // if (det == 0) {
        //     System.out.println("Matriks tidak memiliki invers.");
        // }

        double[][] matrixInverse = new double[matrix.length][matrix[0].length];
        if (function == "balikan") {
            matrixInverse = matriksBalikan(matrix);
            IOMatriks.writeMatrix(matrixInverse);
        }

        if (function == "adjoin") {
            matrixInverse = matriksAdjoin(matrix);
            IOMatriks.writeMatrix(matrixInverse);
        }
    }

    public static double[][] matriksBalikan(double[][] matrix) {
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
                return null; // Matriks tidak dapat diinvers
            }

            // Membagi baris dengan elemen utama
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[i][j] /= elmtUtama;
            }

            // Mengeliminasi elemen di bawah dan di atas 1 utama
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double elmt = augmentedMatrix[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmentedMatrix[k][j] -= elmt * augmentedMatrix[i][j];
                    }
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

    public static double[][] matriksAdjoin(double[][] matrix) {
        double[][] matrixInverse = new double[1][1];

        return matrixInverse;
    }

    public static void getAdjoin(double[][] matrix) {
        
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMENU:");
            System.out.println("1. Metode Matriks Balikan");
            System.out.println("2. Metode Adjoin");
            System.out.println("3. Keluar");

            int choice = scanner.nextInt();
            scanner.nextLine();

            String inputMatrixText;
            double[][] inputMatrix;

            switch (choice) {
                case 1:
                    inputMatrixText = IOMatriks.getUserInput(scanner);
                    inputMatrix = IOMatriks.convertTextToMatrix(inputMatrixText);
                    getInvers(inputMatrix, "balikan");
                    break;
                case 2:
                    inputMatrixText = IOMatriks.getUserInput(scanner);
                    inputMatrix = IOMatriks.convertTextToMatrix(inputMatrixText);
                    getInvers(inputMatrix, "adjoin");
                    break;
                case 3:
                    System.out.println("Kembali ke Menu Utama.");
                    return;
                default:
                    System.out.println("Pilihan Invalid.");
            }
        }
    }
}
