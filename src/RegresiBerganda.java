import java.util.Scanner;
public class RegresiBerganda {
    public static double[][][] multipleLinearRegression(double[][] X, double[][] Y){
        //Y = XB + e; Ycap = XB; e = Y - Ycap
        //B = (Xt.X)^-1 . Xt . Y
        //ukuran Y: m x 1
        //ukuran X: m x n
        //ukuran B: n x 1
        //ukuran e: m x 1
        double[][] XT = OBE.transpose(X);
        double[][] XTX = OBE.multiplyMatrix(XT, X);
        double[][] XTXInv = MatriksBalikan.inversBalikan(XTX);
        double[][] XTXInvXT = OBE.multiplyMatrix(XTXInv, XT);

        //Finding B
        double[][] B = OBE.multiplyMatrix(XTXInvXT, Y);

        //Finding e
        double [][] Ycap = OBE.multiplyMatrix(X, B);
        double [][] e = OBE.subtractMatrix(Y, Ycap);
        return new double[][][]{B, e};
    }

    public static void main(String[] args) {
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