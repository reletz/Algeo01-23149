import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            clearConsole();
            System.out.println("\nMENU UTAMA:");
            System.out.println("1. Sistem Persamaan Linear");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Interpolasi Bicubic Spline");
            System.out.println("6. Regresi Linear dan Kuadratik Berganda");
            System.out.println("7. Keluar");
            System.out.print("\nMasukkan pilihan: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                // Selesai dipakai, kembali ke menu agar dapat digunakan kembali
                case 1:
                    clearConsole();
                    SPL.main(new String[0]);
                    break;
                case 2:
                    clearConsole();
                    Determinan.main(new String[0]);
                    break;
                case 3:
                    clearConsole();
                    MatriksBalikan.main(new String[0]);
                    break;
                case 4:
                    clearConsole();
                    // Call method
                    break;
                case 5:
                    clearConsole();
                    // Call method
                    break;
                case 6:
                    clearConsole();
                    // Call method
                    break;
                case 7:
                    System.out.println("Keluar dari program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan Invalid.");
            }
        }
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}