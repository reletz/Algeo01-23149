import java.util.Scanner;

public class SPL {
    static void gauss(){

    }

    static void gaussJordan(){

    }

    static void matriksBalikan(){

    }

    static void cramer(){

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
                    SPL.gauss();
                    break;
                case 2:
                    SPL.gaussJordan();
                    break;
                case 3:
                    SPL.matriksBalikan();
                    break;
                case 4:
                    SPL.cramer();
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

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
//         while (true) {
//             System.out.println("MENU:");
//             System.out.println("1. Metode eliminasi Gauss");
//             System.out.println("2. Metode eliminasi Gauss-Jordan");
//             System.out.println("3. Metode matriks balikan");
//             System.out.println("4. Kaidah Cramer");
//             System.out.println("5. Keluar");
//             int choice = scanner.nextInt();
//             scanner.nextLine(); // Consume newline

//             switch (choice) {
//                 //Selesai dipakai, kembali ke menu agar dapat digunakan kembali
//                 case 1:
//                     SPL.Gauss();
//                     return;
//                 case 2:
//                     SPL.GaussJordan();
//                     return;
//                 case 3:
//                     SPL.MatriksBalikan();
//                     return;
//                 case 4:
//                     SPL.Cramer();
//                     return;
//                 case 5:
//                     scanner.close();
//                     return;
//                 default:
//                     System.out.println("Pilihan Invalid.");
//             }
//         }
//     }
// }
