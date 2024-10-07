import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("MENU:");
            System.out.println("1. Sistem Persamaan Linear");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Interpolasi Bicubic Spline");
            System.out.println("6. Regresi Linear dan Kuadratik Berganda");
            System.out.println("7. Keluar");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                //Selesai dipakai, kembali ke menu agar dapat digunakan kembali
                case 1:
                    SPL.main(new String[0]);
                case 2:
                    Determinan.main(new String[0]);
                case 3:
                    MatriksBalikan.main(new String[0]);
                case 4:
                    InterpolasiPolinom.main(new String[0]);
                case 5:
                    InterpolasiBicubicSpline.main(new String[0]);
                case 6:
                    RegresiBerganda.main(new String[0]);
                case 7:
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan Invalid.");
            }
        }
    }
}
