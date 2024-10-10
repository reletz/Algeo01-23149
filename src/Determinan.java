import java.util.Scanner;
public class Determinan {
    static void Gauss(){

    }

    static void GaussJordan(){

    }
    
    static void MetodeKofaktor(){

    }

    static void MetodeSarrus(){

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("MENU:");
            System.out.println("1. Metode eliminasi Gauss");
            System.out.println("2. Metode Kofaktor");
            System.out.println("3. Keluar");
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
                    Determinan.MetodeKofaktor();
                    return;
                case 4:
                    Determinan.MetodeSarrus();
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