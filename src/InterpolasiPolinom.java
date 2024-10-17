import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InterpolasiPolinom {

    private static double[][] interpolationSolution(double[][] pointMatrix) {
        int i, j;
        int n = pointMatrix.length;
        double[][] augmentedMatrix = new double[n][n + 1];
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                augmentedMatrix[i][j] = Math.pow(pointMatrix[i][0], j);
            }
            augmentedMatrix[i][n] = pointMatrix[i][1];
        } 
        return SPL.gauss(augmentedMatrix);
    }

    private static double[][][] polinomialInterpolation(double[][] pointMatrix, double x){
        double result = 0;
        double[][] solution = interpolationSolution(pointMatrix);
        for (int i = 0; i < solution.length; i++){
            result += solution[i][0] * Math.pow(x, i);
        } return new double[][][] {solution, new double[][] {{result}}};
    }

    public static void handleInput(Scanner scanner) {
        System.out.println("Pilihan Input");
        System.out.println("1. Keyboard");
        System.out.println("2. File");
        System.out.print("Masukkan pilihan: ");
        int subChoice = scanner.nextInt();
        scanner.nextLine();

        double val = 0.0;
        int a = 1;
        double[][] data = null;

        switch (subChoice) {
            case 1:
                Main.clearConsole();
                System.out.println("POLINOMIAL INTERPOLATION\n");
                System.out.print("Masukkan n: ");
                int n = scanner.nextInt();

                data = new double[n][2];
                System.out.println("Masukkan titik yang diketahui.");
                for (int i = 0; i < n; i++) {
                    System.out.print("x" + i + ": ");
                    data[i][0] = scanner.nextDouble();

                    System.out.print("y" + i + ": ");
                    data[i][1] = scanner.nextDouble();
                }

                System.out.println("Masukkan absis yang ingin dicari.");
                System.out.print("x: ");
                val = scanner.nextDouble();
                System.out.println();
                break;

            case 2:
                Main.clearConsole();
                System.out.print("Masukkan file path: ");
                String filePath = scanner.nextLine();
                try {
                    Scanner fileScanner = new Scanner(new File(filePath));
                    int m = 0;
                    while (fileScanner.hasNextLine()) {
                        fileScanner.nextLine();
                        m += 1;
                    } m--;

                    data = new double[m][2];
                    fileScanner.close();

                    fileScanner = new Scanner(new File(filePath));
                    for (int i = 0; i < m; i++) {
                        if (fileScanner.hasNextDouble()) data[i][0] = fileScanner.nextDouble();
                        if (fileScanner.hasNextDouble()) data[i][1] = fileScanner.nextDouble();
                    } if (fileScanner.hasNextDouble()) val = fileScanner.nextDouble();
                    fileScanner.close();
                    
                } catch (FileNotFoundException e) {
                    System.out.println("File tidak ditemukan: " + filePath);
                    return;
                }
                break;
            default:
                System.out.println("Pilihan invalid!");
                return;
        } processInterpolation(data, val, scanner);
    }

    private static void processInterpolation(double[][] setOfPoints, double x, Scanner scanner){
        int i;
        double result[][][] = polinomialInterpolation(setOfPoints, x);
        System.out.println("Fungsi interpolasi yang memungkinkan adalah: ");
        System.out.print("p" + (setOfPoints.length-1) + "(x) = " + result[0][0][0]);
        for (i = 1; i < setOfPoints.length; i++){
            if (result[0][i][0] < 0) System.out.print(" - " + Math.abs(result[0][i][0]) + "x^" + i);
            else System.out.print(" + " + result[0][i][0] + "x^" + i);
        }
        System.out.println("\nNilai interpolasinya, yakni p(" + x + ") = " + result[1][0][0]);
        // String resultString = Double.toString(result[1][0][0]); harus bikin yang bisa save fungsi.
        // IOMatriks.saveToFile(resultString, scanner);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // double[][] pointMatrix = {
        //     {8, 2.0794},
        //     {9, 2.1972},
        //     {9.5, 2.2513},
        // };
        // double x = 9.2;
        // System.out.println(polinomialInterpolation(pointMatrix, x));
        handleInput(scanner);
    }
}
