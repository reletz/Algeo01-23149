import java.util.Scanner;

public class RegresiBerganda {
    public static int factorial(int n){
        if (n == 0) return 1;
        else return n*factorial(n-1);
    }

    //Kurang lebih algoritma kedua regresi itu sama. Anggap x^2 = x2. Anggapannya gitu
    public static double[][] multipleRegressionAlgorithm(double[][] X, double[][] Y){
        //ukuran Y: m x 1
        //ukuran X: m x n (Ada (n - 1) data, kolom 0 diisi anggka 1 semua)
        //ukuran B: m x 1
        //ukuran e: m x 1

        //Jumlahan Normal Estimation Equation bisa didapat dari perkalian matrix (Xt.X)(B) = Xt.Y
        double[][] XT = OBE.transpose(X);
        double[][] XTX = OBE.multiplyMatrix(XT, X);
        double[][] XTY = OBE.multiplyMatrix(XT, Y);

        //Finding B
        double[][] augmented = OBE.toAugmented(XTX, XTY);
        IOMatriks.writeMatrix(augmented);

        double[][] B = SPL.gauss(augmented);
        return B;
    }

    public static double[][] toLinearX(double[][] independentVar){ 
        //Cuma gabungin matriks asli sama angka 1 di kolom 0
        //Urutannya: 1, u, v
        int i;
        double[][] ones = new double[independentVar.length][1];
        for (i = 0; i < independentVar.length; i++){
            ones[i][0] = (double) 1;
        } return OBE.toAugmented(ones, independentVar);
    }

    public static double[][] toQuadraticX(double[][] independentVar){
        //Urutannya: Variabel Linear, Variabel Kuadrat, dan Variabel Interaksi
        //Jadi: 1, u, v, u^2, v^2, uv
        int i, j;
        int nInteractionVar;

        int nLinearVar = independentVar[0].length; //Jml Var Linear
        int nQuadraticVar = independentVar[0].length; // Jml Var Kuadrat

        if (independentVar[0].length < 2) nInteractionVar = (int) 0;
        else nInteractionVar =  factorial(independentVar[0].length)/(2*factorial((independentVar[0].length) - 2)); // Jml Var Interaksi

        int column = nLinearVar + nQuadraticVar + nInteractionVar; //Panjang kolom keseluruhan

        double[][] X = new double[independentVar.length][column];
        for (i = 0; i < independentVar.length; i++){
            //Inputted linear and quadratic variable into matrix
            for (j = 0; j < nLinearVar; j++){
                X[i][j] = independentVar[i][j];
                X[i][j + nLinearVar] = independentVar[i][j]*independentVar[i][j];
            }

            //Inputted interaction variable into matrix
            for (j = 0; j < nInteractionVar; j++){
                X[i][j + nLinearVar + nQuadraticVar] = independentVar[i][j % nLinearVar]*independentVar[i][(j + 1) % nLinearVar];
            }

        } return toLinearX(X);
    }
    

    public static void main(String[] args) {
        // Example
        // int i;
        // double[][] X = 
        // {{4, 7},
        // {4.5, 8},
        // {5, 10},
        // {5.5, 7},
        // {6, 9},
        // {6.5, 3},
        // {7, 8}
        // };
        // double[][] Y = 
        // {{33},
        // {42},
        // {45},
        // {51},
        // {53},
        // {61},
        // {62}
        // };
        // double[][] B = multipleRegressionAlgorithm(toLinearX(X), Y);
        // IOMatriks.writeMatrix(B);
        // //Harusnya hasilnya 1.06548 + 9.29762X1 - 0.35417X2

        // System.out.println();
        // B = multipleRegressionAlgorithm(toQuadraticX(X), Y);
        // IOMatriks.writeMatrix(B);
        //Nah ini gue ga tau calculator onlinenya njir
    }

    //     Scanner scanner = new Scanner(System.in);
    //     while (true) {
    //         System.out.println("Regresi tipe apa yang ingin dilakukan?");
    //         System.out.println("1. Regresi Linear Berganda");
    //         System.out.println("2. Regresi Kuadratik Berganda");
    //         System.out.println("5. Keluar");
    //         int choice = scanner.nextInt();
    //         scanner.nextLine(); // Consume newline

    //         switch (choice) {
    //             //Selesai dipakai, kembali ke menu agar dapat digunakan kembali
    //             case 1:
    //                 RegresiBerganda.gauss();
    //                 return;
    //             case 2:
    //                 RegresiBerganda.gaussJordan();
    //                 return;
    //             case 3:
    //                 RegresiBerganda.matriksBalikan();
    //                 return;
    //             case 4:
    //                 RegresiBerganda.cramer();
    //                 return;
    //             case 5:
    //                 scanner.close();
    //                 return;
    //             default:
    //                 System.out.println("Pilihan Invalid.");
    //         }
    //     }
    // }
}