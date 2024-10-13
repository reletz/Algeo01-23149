import java.util.Scanner;

public class SPL {
    public static double[] BackSubstitution(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        double[] x = new double[n-1];

        // Iterate from the last row to the first row
        for (int i = m - 1; i >= 0; i--) {
            x[i] = matrix[i][n-1];
            for (int j = i + 1; j < n - 1; j++) {
                x[i] -= matrix[i][j] * x[j];
            }
            x[i] /= matrix[i][i];
        }

        return x;
    }
    
    static double[] gauss(double[][] matrix){
        OBE.toRowEchelon(matrix);
        return BackSubstitution(matrix);
    }

    static double[] gaussJordan(double[][] matrix){
        OBE.toReducedRowEchelon(matrix);
        return BackSubstitution(matrix);
    }

    static void matriksBalikan(double[][] matrix){

    }

    static void cramer(){

    }

    public static void main(String[] args) {
        // Example usage
        double[][] matrix = {
            {2, -1, 0},
            {-1, 2, -1},
            {0, -1, 2}
        };

        double[][] rhs = {{1}, {0}, {1}};
        double[][] newMatrix = OBE.toAugmented(matrix, rhs);
        IOMatriks.writeMatrix(newMatrix);

        double[] solution1 = gauss(newMatrix);
        double[] solution2 = gaussJordan(newMatrix);

        // Print the solution
        for (double x : solution1) {
            System.out.println(x);
        }

        for (double x : solution2) {
            System.out.println(x);
        }
    }
}
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
//         while (true) {
//             System.out.println("\nMENU:");
//             System.out.println("1. Metode eliminasi Gauss");
//             System.out.println("2. Metode eliminasi Gauss-Jordan");
//             System.out.println("3. Metode matriks balikan");
//             System.out.println("4. Kaidah Cramer");
//             System.out.println("5. Keluar");
//             int choice = scanner.nextInt();
//             scanner.nextLine();

//             switch (choice) {
//                 //Selesai dipakai, kembali ke menu agar dapat digunakan kembali
//                 case 1:
//                     SPL.Gauss();
//                     break;
//                 case 2:
//                     SPL.GaussJordan();
//                     break;
//                 case 3:
//                     SPL.MatriksBalikan();
//                     break;
//                 case 4:
//                     SPL.Cramer();
//                     break;
//                 case 5:
//                     System.out.println("Kembali ke Menu Utama.");
//                     return;
//                 default:
//                     System.out.println("Pilihan Invalid.");
//             }
//         }
//     }
// }