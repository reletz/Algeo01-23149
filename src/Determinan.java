import java.util.Scanner;
public class Determinan {
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
            System.out.println("MENU:");
            System.out.println("1. Metode eliminasi Gauss");
            System.out.println("2. Metode eliminasi Gauss-Jordan");
            System.out.println("3. Metode matriks balikan");
            System.out.println("4. Kaidah Cramer");
            System.out.println("5. Keluar");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                //Selesai dipakai, kembali ke menu agar dapat digunakan kembali
                case 1:
                    Determinan.Gauss();
                    return;
                case 2:
                    Determinan.GaussJordan();
                    return;
                case 3:
                    Determinan.MatriksBalikan();
                    return;
                case 4:
                    Determinan.Cramer();
                    return;
                case 5:
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan Invalid.");
            }
        }
    }
}