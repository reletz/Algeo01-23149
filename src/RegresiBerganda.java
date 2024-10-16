import java.util.Scanner;

public class RegresiBerganda {
    public static double[][] multipleLinearRegression1(double[][] X, double[][] Y){
        //(Xt.X)(B) = Xt.Y
        //ukuran Y: m x 1
        //ukuran X: m x n (Ada (n - 1) data)
        //ukuran B: m x 1
        //ukuran e: m x 1
        double[][] XT = OBE.transpose(X);
        double[][] XTX = OBE.multiplyMatrix(XT, X);
        IOMatriks.writeMatrix(XTX);
        double[][] XTY = OBE.multiplyMatrix(XT, Y);
        IOMatriks.writeMatrix(XTY);

        //Finding B
        double[][] B = SPL.gauss(OBE.toAugmented(XTX, XTY));
        return B;
    }

    public static void main(String[] args) {
        //Example
        int i;
        double[][] X = 
        {{1, 4},
        {1, 4.5},
        {1, 5},
        {1, 5.5},
        {1, 6},
        {1, 6.5},
        {1, 7}
        };
        double[][] Y = 
        {{33},
        {42},
        {45},
        {51},
        {53},
        {61},
        {62}
        };
        double[][] B = multipleLinearRegression1(X, Y);
        // Scanner scanner = new Scanner(System.in);
        // while (true) {
        //     System.out.println("MENU:");
        //     System.out.println("1. Metode eliminasi Gauss");
        //     System.out.println("2. Metode eliminasi Gauss-Jordan");
        //     System.out.println("3. Metode matriks balikan");
        //     System.out.println("4. Kaidah Cramer");
        //     System.out.println("5. Keluar");
        //     int choice = scanner.nextInt();
        //     scanner.nextLine(); // Consume newline

        //     switch (choice) {
        //         //Selesai dipakai, kembali ke menu agar dapat digunakan kembali
        //         case 1:
        //             RegresiBerganda.gauss();
        //             return;
        //         case 2:
        //             RegresiBerganda.gaussJordan();
        //             return;
        //         case 3:
        //             RegresiBerganda.matriksBalikan();
        //             return;
        //         case 4:
        //             RegresiBerganda.cramer();
        //             return;
        //         case 5:
        //             scanner.close();
        //             return;
        //         default:
        //             System.out.println("Pilihan Invalid.");
        //     }
        // }
    }
}