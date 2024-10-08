import java.util.Scanner;
public class SPL {
    //Mulai bikin OBE
    //BENTAR NI ALURNYA GMN YA BIAR ENAK
    //targeted: matriks tujuan, another: matriks lain;

    //1: Mengalikan baris dengan suatu konstanta non-zero 
    //Objektif: Agar memiliki 1 utama di [targetedRow][targetedCol]
    private static void RowMultiply(double[][] matrix, int targetedRow, int targetedCol){
        int i;
        double k = 1/matrix[targetedRow][targetedCol];
        for (i = 0; i < matrix[targetedRow].length; i++){
            matrix[targetedRow][i] *= k;
        }
    }

    //2: Mengurangi suatu baris dengan baris atau kelipatan baris lain
    //Objektif: Memiliki 0 di depan leading one nantinya
    private static void RowSubstract(double[][] matrix, int anotherRow, int anotherCol, int targetedRow, int targetedCol){
        int i;
        double k = matrix[targetedRow][targetedCol]/matrix[anotherRow][anotherCol];
        for (i = 0; i < matrix[targetedRow].length; i++){
            matrix[targetedRow][i] -= k*matrix[anotherRow][i];
        }
    }

    //3: Menukar baris a dengan baris b
    //Objektif: Memudahkan peletakkan barisan 0 di bawah
    private static void RowSwap(double[][] matrix, int rowA, int rowB){
        int i;
        double[] row1 = matrix[rowA];
        double[] row2 = matrix[rowB];
        for (i = 0; i < matrix[rowA].length; i++){
            matrix[rowA][i] = row2[i];
            matrix[rowB][i] = row1[i];
        }
    }

    static void Gauss(){

    }

    static void GaussJordan(){

    }

    static void MatriksBalikan(){

    }

    static void Cramer(){

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMENU:");
            System.out.println("1. Metode eliminasi Gauss");
            System.out.println("2. Metode eliminasi Gauss-Jordan");
            System.out.println("3. Metode matriks balikan");
            System.out.println("4. Kaidah Cramer");
            System.out.println("5. Keluar");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                //Selesai dipakai, kembali ke menu agar dapat digunakan kembali
                case 1:
                    SPL.Gauss();
                    break;
                case 2:
                    SPL.GaussJordan();
                    break;
                case 3:
                    SPL.MatriksBalikan();
                    break;
                case 4:
                    SPL.Cramer();
                    break;
                case 5:
                    System.out.println("Kembali ke Menu Utama.");
                    return;
                default:
                    System.out.println("Pilihan Invalid.");
            }
        }
    }
}
