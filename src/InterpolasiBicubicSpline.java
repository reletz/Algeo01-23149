import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InterpolasiBicubicSpline {
    public static double[][] calculateCoefficients(double[] matrix) {
        double[][] F = {
            {matrix[0], matrix[1]},
            {matrix[2], matrix[3]}
        };

        double[][] F_x = {
            {matrix[4], matrix[5]},
            {matrix[6], matrix[7]}
        };

        double[][] F_y = {
            {matrix[8], matrix[9]},
            {matrix[10], matrix[11]}
        };

        double[][] F_xy = {
            {matrix[12], matrix[13]},
            {matrix[14], matrix[15]}
        };

        double[][] A = {
            {1, 0, 0, 0},
            {0, 0, 1, 0},
            {-3, 3, -2, -1},
            {2, -2, 1, 1}
        };

        double[][] G = {
            {F[0][0], F[0][1], F_y[0][0], F_y[0][1]},
            {F[1][0], F[1][1], F_y[1][0], F_y[1][1]},
            {F_x[0][0], F_x[0][1], F_xy[0][0], F_xy[0][1]},
            {F_x[1][0], F_x[1][1], F_xy[1][0], F_xy[1][1]}
        };

        // Kalkulasi koefisien
        double[][] C = new double[4][4];
        double[][] AT = OBE.transpose(A);
        double[][] AG = OBE.multiplyMatrix(A, G);
        C = OBE.multiplyMatrix(AG, AT);

        return C;
    }

    public static void handleInput(Scanner scanner) {
        System.out.println("Pilihan Input");
        System.out.println("1. Keyboard");
        System.out.println("2. File");
        System.out.print("Masukkan pilihan: ");
        int subChoice = scanner.nextInt();
        scanner.nextLine();

        double[] y = new double[16];
        double aVal = 0.0;
        double bVal = 0.0;

        switch (subChoice) {
            case 1:
                Main.clearConsole();
                System.out.println("BICUBIC SPLINE INTERPOLATION\n");
                System.out.println("Masukkan matriks:");
                for (int i = 0; i < 16; i++) {
                    y[i] = scanner.nextDouble();
                }
                aVal = scanner.nextDouble();
                bVal = scanner.nextDouble();
                break;
            case 2:
                Main.clearConsole();
                System.out.print("Masukkan file path: ");
                String filePath = scanner.nextLine();
                try {
                    Scanner fileScanner = new Scanner(new File(filePath));
                    for (int i = 0; i < 16; i++) {
                        if (fileScanner.hasNextDouble()) {
                            y[i] = fileScanner.nextDouble();
                        }
                    }
                    if (fileScanner.hasNextDouble()) {
                        aVal = fileScanner.nextDouble();
                    }
                    if (fileScanner.hasNextDouble()) {
                        bVal = fileScanner.nextDouble();
                    }
                    fileScanner.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File tidak ditemukan: " + filePath);
                    return;
                }
                break;
            default:
                System.out.println("Pilihan invalid!");
                return;
        }

        processInterpolation(y, aVal, bVal, scanner);
    }

    private static void processInterpolation(double[] y, double aVal, double bVal, Scanner scanner) {
        double[][] coeff = calculateCoefficients(y);
        double result = bicubicInterpolation(coeff, aVal, bVal);
        System.out.println("\nNilai interpolasi pada (" + aVal + ", " + bVal + ") adalah: " + result);
        String resultString = Double.toString(result);
        IOMatriks.saveToFile(resultString, scanner);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("BICUBIC SPLINE INTERPOLATION:");
            System.out.println("\nSUB-MENU BICUBIC SPLINE INTERPOLATION:");
            System.out.println("1. Masukkan Matriks");
            System.out.println("2. Keluar");
            System.out.print("\nMasukkan pilihan: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Main.clearConsole();
                    System.out.println("BICUBIC SPLINE INTERPOLATION\n");
                    handleInput(scanner);
                    break;
                case 2:
                    Main.clearConsole();
                    System.out.println("Kembali ke Menu Utama.");
                    return;
                default:
                    System.out.println("Pilihan Invalid.");
            }
        }
    }

    public static double bicubicInterpolation(double[][] coeffs, double a, double b) {
        double result = 0.0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result += coeffs[i][j] * Math.pow(a, i) * Math.pow(b, j);
            }
        }
        return result;
    }
}