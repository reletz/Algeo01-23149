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
        } 
        // IOMatriks.writeMatrix(result);
        return result;
    }

    //Input: Augmented Matrix
    static double[][] cramer(double[][] matrix){
        int i;
        double[][][] spliitedMatrix = OBE.splitMatrix(matrix);
        double[][] lhs = spliitedMatrix[0];
        double[][] rhs = spliitedMatrix[1];
        double[][] result = new double[rhs.length][1];
        double detLhs = Determinan.getDeterminan(lhs, "kofaktor");

        if (detLhs == 0) {
            return result; 
        }

        for (i = 0; i < lhs.length; i++) {
            double[][] tmpMatrix = new double[lhs.length][lhs[0].length];
            for (int k = 0; k < lhs.length; k++) {
                System.arraycopy(lhs[k], 0, tmpMatrix[k], 0, lhs[0].length);
            }
            for (int j = 0; j < lhs[0].length; j++) {
                tmpMatrix[j][i] = rhs[j][0];
            }
            result[i][0] = Determinan.getDeterminan(tmpMatrix, "kofaktor") / detLhs;
        }
        return result;
    }  

    //Add 0 on the right, jadi bisa di proses pake gauss
    public static double[][] addZero (double[][] augmented){
        int i, j;
        double[][] X = OBE.splitMatrix(augmented)[0];
        double[][] Y = OBE.splitMatrix(augmented)[1];
        if (X.length <= X[0].length){
            return augmented;
        }

        double[][] newX = new double[X.length][X.length];
        for (i = 0; i < X.length; i++){
            for (j = 0; j < X[0].length; j++){
                newX[i][j] = X[i][j];
            }
        }

        for (i = 0; i < X.length; i++){
            for (j = X[0].length ; j < X.length; j++){
                newX[i][j] = 0;
            }
        } double[][] newMatrix = OBE.toAugmented(newX, Y);
        return newMatrix;
    }
    
    public static boolean hasLeadingOne(double[][] matrix, int col){
        int i, j;
        boolean hasLeadingOne = false;
        for (i = 0; i < matrix.length; i++){
            if (matrix[i][col] != 0){
                hasLeadingOne = true;
                for (j = col - 1; j >= 0; j--){
                    if (matrix[i][j] != 0){
                        hasLeadingOne = false;
                    }
                }
                if (hasLeadingOne) return true;
            }
        } return hasLeadingOne;
    }

    public static int checkSolution(double[][] OBEmatrix){
        int i;
        int m = OBEmatrix.length;
        int n = OBEmatrix[0].length;
        int count = 0;
        for (i = 0; i < (n - 1); i++){
            if (OBEmatrix[m - 1][i] == 0){
                count += 1;
            }
        }
        if (count == (n - 1) && OBEmatrix[m - 1][n - 1] != 0){
            return 0; //No Solution
        }

        if (count == (n - 1) && OBEmatrix[m - 1][n - 1] == 0){
            return 2; //Infinite Solution
        }

        return 1; //Unique Solution
    }

    public static Parametrik[] parametrikBackSub(double[][] OBEmatrix){
        int i, j;
        int m = OBEmatrix.length;
        int n = OBEmatrix[0].length;
        Parametrik[] solutions = new Parametrik[n - 1];
        int k = 44;

        for (i = 0; i < n - 1; i++){
            if (!(hasLeadingOne(OBEmatrix, i))) {
                solutions[i] = new Parametrik();
                solutions[i].coeffList[k] = 1;
                k = Parametrik.nextIndexParametrik(k);
            }
        }

        for (i = m - 1; i >= 0; i--){
            int indexTemp = 0;
            if (solutions[i] == null) solutions[i] = new Parametrik();
            solutions[i].coeffList[0] = OBEmatrix[i][n - 1];
            for (j = 0; j < n - 1; j++){
                if (OBEmatrix[i][j] != 0){
                    solutions[j] = new Parametrik();
                    solutions[j].coeffList[0] = OBEmatrix[i][n - 1];
                    indexTemp = j;
                    break;
                }
            } for (j = indexTemp + 1; j < n - 1; j++){
                if (OBEmatrix[i][j] != 0){
                    solutions[indexTemp] = Parametrik.subtractParametrik(solutions[indexTemp], Parametrik.multiplyConstant(solutions[j], OBEmatrix[i][j]));
                }
            }
        } return solutions;
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
                    System.out.println("");
                    
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
                double[][] temp = addZero(setOfPoints);
                int diff = (temp[0].length - setOfPoints[0].length) > 0 ? (temp[0].length - setOfPoints[0].length) : 0;
                result = OBE.toRowEchelon(temp);
                if (checkSolution(result) != 0){
                    solution += "Solusi:\n";
                    System.out.println("Solusi:");
                    Parametrik[] solutions = parametrikBackSub(result);
                    for (i = 0; i < solutions.length - diff; i++){
                        solution += "x" + (i+1) + " = " + Parametrik.makeVar(solutions[i]) + "\n";
                        System.out.println("x" + (i+1) + " = " + Parametrik.makeVar(solutions[i]));
                    }

                } else {
                    solution += "Tidak ada solusi.";
                    System.out.println("Tidak ada solusi.");

                } 
                break;
            case 2:
                temp = addZero(setOfPoints);
                diff = (temp[0].length - setOfPoints[0].length) > 0 ? (temp[0].length - setOfPoints[0].length) : 0;
                result = OBE.toReducedRowEchelon(temp);
                if (checkSolution(result) != 0){
                    solution += "Solusi:\n";
                    System.out.println("Solusi:");
                    Parametrik[] solutions = parametrikBackSub(result);
                    for (i = 0; i < solutions.length - diff; i++){
                        solution += "x" + (i+1) + " = " + Parametrik.makeVar(solutions[i]) + "\n";
                        System.out.println("x" + (i+1) + " = " + Parametrik.makeVar(solutions[i]));
                    }

                } else {
                    solution += "Tidak ada solusi.";
                    System.out.println("Tidak ada solusi.");
                } 
                break;

            case 3:
                if (!OBE.isSquare(OBE.splitMatrix(setOfPoints)[0])){
                    System.out.println("Matriks koefisien tidak persegi, tidak bisa menggunakan metode invers.");
                    solution += "Matriks koefisien tidak persegi, tidak bisa menggunakan metode invers.";
                    break;
                }

                if (!OBE.isInversable(OBE.splitMatrix(setOfPoints)[0])){
                    System.out.println("Determinan matriks sama dengan 0, tidak bisa menggunakan metode invers.");
                    solution += "Determinan matriks sama dengan 0, tidak bisa menggunakan metode invers.";
                    break;
                }

                result = matriksBalikan(setOfPoints);
                System.out.println("Hasil penyelesaian dengan metode Invers adalah: ");
                for (i = 0; i < result.length; i++){
                    solution += "x" + (i+1) + " = " + result[i][0] + "\n";
                    System.out.println("x" + (i+1) + " = " + result[i][0]);
                }
                break;

            case 4:
                if (!OBE.isSquare(OBE.splitMatrix(setOfPoints)[0])){
                    System.out.println("Matriks koefisien tidak persegi, tidak bisa menggunakan metode cramer.");
                    solution += "Matriks koefisien tidak persegi, tidak bisa menggunakan metode cramer.";
                    break;
                }

                if (!OBE.isInversable(OBE.splitMatrix(setOfPoints)[0])){
                    System.out.println("Determinan matriks sama dengan 0, tidak bisa menggunakan metode cramer.");
                    solution += "Determinan matriks sama dengan 0, tidak bisa menggunakan metode cramer.";
                    break;
                }

                result = cramer(setOfPoints);
                System.out.println("Hasil penyelesaian dengan metode Cramer adalah: ");
                for (i = 0; i < result.length; i++){
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