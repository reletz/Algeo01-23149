import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class SPL {
    public static double[][] BackSubstitution(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        double[][] x = new double[n-1][1];

        // Iterate from the last row to the first row
        for (int i = m - 1; i >= 0; i--) {
            x[i][0] = matrix[i][n-1];
            for (int j = i + 1; j < n - 1; j++) {
                x[i][0] -= matrix[i][j] * x[j][0];
            }
            x[i][0] /= matrix[i][i];
        }

        return x;
    }  
    
    //Input: Augmented Matrix
    static double[][] gauss(double[][] matrix){
        double[][] eliminated = OBE.toRowEchelon(matrix);
        return BackSubstitution(eliminated);
    }

    //Input: Augmented Matrix
    static double[][] gaussJordan(double[][] matrix){
        double[][] eliminated = OBE.toReducedRowEchelon(matrix);
        return BackSubstitution(eliminated);
    }

    //Input: Augmented Matrix
    static double[][] matriksBalikan(double[][] matrix){
        int i;
        double[][][] spliitedMatrix = OBE.splitMatrix(matrix);
        double[][] lhs = spliitedMatrix[0];
        double[][] rhs = spliitedMatrix[1];
        double[][] x = OBE.multiplyMatrix(MatriksBalikan.inversBalikan(lhs), rhs);
        double[][] result = new double[x.length][1];
        for (i = 0; i < x.length; i++){
            result[i][0] = x[i][0];
        } return result;
    }

    //Input: Augmented Matrix
    static double[][] cramer(double[][] matrix){
        int i;
        double[][][] spliitedMatrix = OBE.splitMatrix(matrix);
        double[][] lhs = spliitedMatrix[0];
        double[][] rhs = spliitedMatrix[1];
        double[][] result = new double[rhs.length][1];
        for (i = 0; i < lhs.length; i++){
            double[][] tmpMatrix = lhs;
            for (int j = 0; j < lhs[0].length; j++){
                tmpMatrix[j][i] = rhs[j][0];
            } result[i][0] = Determinan.getDeterminan(tmpMatrix, "kofaktor") / Determinan.getDeterminan(lhs, "kofaktor");
        } return result;
    }   

    public static void printParametrik(double[][] OBEmatrix){
        for (int i = 0; i < OBEmatrix.length; i++){
            System.out.println("x" + i + " = " + OBEmatrix[i][0]);
        }
    }

    public static void handleInput(Scanner scanner) {
        int i, j, m, n;
        System.out.println("Pilihan Metode Penyelesaian SPL");
        System.out.println("1. Gauss");
        System.out.println("2. Gauss-Jordan");
        System.out.println("3. Invers (x = A^-1 * B)");
        System.out.println("4. Cramer");
        System.out.println("5. Keluar");
        System.out.print("Masukkan pilihan: ");
        int methodChoice = scanner.nextInt();
        while (methodChoice < 1 || methodChoice > 5) {
            System.out.println("Pilihan invalid!");
            System.out.print("Masukkan pilihan: ");
            methodChoice = scanner.nextInt();
        }

        if (methodChoice == 5) {
            return;
        }

        System.out.println("Pilihan Input");
        System.out.println("1. Keyboard");
        System.out.println("2. File");
        System.out.print("Masukkan pilihan: ");
        int subChoice = scanner.nextInt();
        scanner.nextLine();

        double val = 0.0;
        double[][] data = null;

        switch (subChoice) {
            case 1:
                Main.clearConsole();
                System.out.println("SPL\n");
                System.out.print("Masukkan m (jumlah baris): ");
                m = scanner.nextInt();
                System.out.print("Masukkan n (jumlah kolom): ");
                n = scanner.nextInt();

                data = new double[m][n];
                System.out.println("Masukkan elemennya.");
                for (i = 0; i < m; i++) {
                    for (j = 0; j < n - 1; j++) {
                        System.out.print("a" + i + j + ": ");
                        data[i][j] = scanner.nextDouble();
                    }

                    System.out.print("b" + i + ": ");
                    data[i][n-1] = scanner.nextDouble();
                }

                System.out.println();
                break;

            case 2:
                Main.clearConsole();
                System.out.print("Masukkan file path: ");
                String filePath = scanner.nextLine();
                try {
                    Scanner fileScanner = new Scanner(new File(filePath));
                    fileScanner.useLocale(Locale.US);

                    m = 0; //print line count
                    while (fileScanner.hasNextLine()) {
                        fileScanner.nextLine();
                        m += 1;
                    }

                    fileScanner.close();

                    fileScanner = new Scanner(new File(filePath));
                    fileScanner.useLocale(Locale.US);
                    n = 0;
                    while (fileScanner.hasNext()) {
                        fileScanner.next();
                        n += 1;
                    } n /= m;
                    fileScanner.close();
                    data = new double[m][n];
                    
                    fileScanner = new Scanner(new File(filePath));
                    fileScanner.useLocale(Locale.US);
                    for (i = 0; i < m; i++) {
                        for (j = 0; j < n; j++) {
                            if (fileScanner.hasNextDouble()) {
                                data[i][j] = fileScanner.nextDouble();
                            }
                        }
                    } fileScanner.close();

                    IOMatriks.writeMatrix(data);
                    
                } catch (FileNotFoundException e) {
                    System.out.println("File tidak ditemukan: " + filePath);
                    return;
                }
                break;
            default:
                System.out.println("Pilihan invalid!");
                return;
        } processSPL(data, scanner, methodChoice);
    }

    private static void processSPL(double[][] setOfPoints, Scanner scanner, int methodChoice){
        int i;
        String solution = null;
        double[][] result = null;
        switch (methodChoice){
            case 1:
                IOMatriks.writeMatrix(OBE.toRowEchelon(setOfPoints));
                result = gauss(setOfPoints);
                System.out.println("Hasil penyelesaian dengan metode Gauss adalah: ");
                break;
            case 2:
                IOMatriks.writeMatrix(OBE.toReducedRowEchelon(setOfPoints));
                result = gaussJordan(setOfPoints);
                System.out.println("Hasil penyelesaian dengan metode Gauss-Jordan adalah: ");
                break;

            case 3:
                if (!OBE.isSquare(OBE.splitMatrix(setOfPoints)[0])){
                    System.out.println("Matriks koefisien tidak persegi, tidak bisa menggunakan metode invers.");
                    return;
                }

                if (!OBE.isInversable(OBE.splitMatrix(setOfPoints)[0])){
                    System.out.println("Determinan matriks sama dengan 0, tidak bisa menggunakan metode invers.");
                    return;
                }

                result = matriksBalikan(setOfPoints);
                System.out.println("Hasil penyelesaian dengan metode Invers adalah: ");
                for (i = 0; i < result[0].length; i++){
                    solution += "x" + i + " = " + result[i][0] + "\n";
                    System.out.println("x" + i + " = " + result[i][0]);
                }
                break;

            case 4:
                if (!OBE.isSquare(OBE.splitMatrix(setOfPoints)[0])){
                    System.out.println("Matriks koefisien tidak persegi, tidak bisa menggunakan metode cramer.");
                    return;
                }

                if (!OBE.isInversable(OBE.splitMatrix(setOfPoints)[0])){
                    System.out.println("Determinan matriks sama dengan 0, tidak bisa menggunakan metode cramer.");
                    return;
                }

                result = cramer(setOfPoints);
                System.out.println("Hasil penyelesaian dengan metode Cramer adalah: ");
                for (i = 0; i < result[0].length; i++){
                    solution += "x" + i + " = " + result[i][0] + "\n";
                    System.out.println("x" + i + " = " + result[i][0]);
                }
                break;
        }
        IOMatriks.saveToFile(solution, scanner);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        handleInput(scanner);
    }
}