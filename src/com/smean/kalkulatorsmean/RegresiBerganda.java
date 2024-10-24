package com.smean.kalkulatorsmean;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class RegresiBerganda {
    public static int factorial(int n){
        if (n == 0) return 1;
        else return n*factorial(n-1);
    }

    //Kurang lebih algoritma kedua regresi itu sama. Anggap x^2 = x2. Anggapannya git

    public static double[][] toLinearX(double[][] independentVar){ 
        //Cuma gabungin matriks asli sama angka 1 di kolom 0
        //Urutannya: 1, u, v
        int i;
        double[][] ones = new double[independentVar.length][1];
        for (i = 0; i < independentVar.length; i++){
            ones[i][0] = (double) 1;
        } return OBE.toAugmented(ones, independentVar);
    }

    public static double[][] toQuadraticX(double[][] independentVar){
        //Urutannya: Variabel Linear, Variabel Kuadrat, dan Variabel Interaksi
        //Jadi: 1, u, v, u^2, v^2, uv
        int i, j;
        int nInteractionVar;

        int nLinearVar = independentVar[0].length; //Jml Var Linear
        int nQuadraticVar = independentVar[0].length; // Jml Var Kuadrat

        if (independentVar[0].length < 2) nInteractionVar = (int) 0;
        else nInteractionVar =  factorial(independentVar[0].length)/(2*factorial((independentVar[0].length) - 2)); // Jml Var Interaksi

        int column = nLinearVar + nQuadraticVar + nInteractionVar; //Panjang kolom keseluruhan

        double[][] X = new double[independentVar.length][column];
        for (i = 0; i < independentVar.length; i++){
            //Inputted linear and quadratic variable into matrix
            for (j = 0; j < nLinearVar; j++){
                X[i][j] = independentVar[i][j];
                X[i][j + nLinearVar] = independentVar[i][j]*independentVar[i][j];
            }

            //Inputted interaction variable into matrix
            for (j = 0; j < nInteractionVar; j++){
                X[i][j + nLinearVar + nQuadraticVar] = independentVar[i][j % nLinearVar]*independentVar[i][(j + 1) % nLinearVar];
            }

        } return toLinearX(X);
    }

    public static double[] convertSearchX(double[] searchX){
        //Urutannya: Variabel Linear, Variabel Kuadrat, dan Variabel Interaksi
        //Jadi: (u, v, u^2, v^2, uv)T
        int i, j;
        int nInteractionVar;

        int nLinearVar = searchX.length; //Jml Var Linear
        int nQuadraticVar = searchX.length; // Jml Var Kuadrat

        if (searchX.length < 2) nInteractionVar = (int) 0;
        else nInteractionVar =  factorial(searchX.length)/(2*factorial((searchX.length) - 2)); // Jml Var Interaksi

        int newLength = nLinearVar + nQuadraticVar + nInteractionVar; //Panjang kolom keseluruhan

        double[] newSearchX = new double[newLength];
        for (i = 0; i < nLinearVar; i++){
            newSearchX[i] = searchX[i];
            newSearchX[i + nLinearVar] = searchX[i]*searchX[i];
        }
        //Inputted interaction variable into matrix
        for (i = 0; i < nInteractionVar; i++){
            newSearchX[i + nLinearVar + nQuadraticVar] = searchX[i % nLinearVar]*searchX[(i + 1) % nLinearVar];
        } 
        return newSearchX;
    }

    public static double[][] multipleRegressionSolution(double[][] X, double[][] Y){
        //ukuran Y: m x 1
        //ukuran X: m x n (Ada (n - 1) data, kolom 0 diisi anggka 1 semua)
        //ukuran B: m x 1
        //ukuran e: m x 1

        //Jumlahan Normal Estimation Equation bisa didapat dari perkalian matrix (Xt.X)(B) = Xt.Y
        double[][] XT = OBE.transpose(X);
        double[][] XTX = OBE.multiplyMatrix(XT, X);
        double[][] XTY = OBE.multiplyMatrix(XT, Y);

        //Finding B
        double[][] augmented = OBE.toAugmented(XTX, XTY);

        double[][] B = SPL.gauss(augmented);
        return B;
    }

    public static double[][][] multipleRegressionSolver(double[][] augmentedData, double[] searchX){
        int i;
        double result = 0;
        double[][] X = OBE.splitMatrix(augmentedData)[0];
        double[][] Y = OBE.splitMatrix(augmentedData)[1];

        double[][] solution = multipleRegressionSolution(X, Y);
        result += solution[0][0];
        for (i = 1; i < solution.length; i++){
            result += solution[i][0]*searchX[i-1];
        } return new double[][][] {solution, new double[][] {{result}}};
    }

    public static void handleInput(Scanner scanner) {
        scanner.useLocale(Locale.US);
        int i, j, m, n;
        System.out.println("Pilihan Regresi");
        System.out.println("1. Linear");
        System.out.println("2. Kuadratik");
        System.out.println("3. Keluar");
        System.out.print("Masukkan pilihan: ");
        int regressionChoice = scanner.nextInt();
        while (regressionChoice != 1 && regressionChoice != 2 && regressionChoice != 3) {
            System.out.println("Pilihan invalid!");
            System.out.print("Masukkan pilihan: ");
            regressionChoice = scanner.nextInt();
        }

        if (regressionChoice == 3){
            scanner.close();
            return;
        }

        System.out.println("Pilihan Input");
        System.out.println("1. Keyboard");
        System.out.println("2. File");
        System.out.print("Masukkan pilihan: ");
        int inputChoice = scanner.nextInt();
        scanner.nextLine();

        double[] val = null;
        double[][] dataX = null;
        double[][] dataY = null;

        switch (inputChoice) {
            case 1:
                Main.clearConsole();
                System.out.println("REGRESSION CALCULATOR\n");
                System.out.print("Masukkan n (jumlah peubah): ");
                n = scanner.nextInt();
                System.out.print("Masukkan m (jumlah data): ");
                m = scanner.nextInt();
                if (m < 3) {
                    System.out.println("Jumlah data tidak cukup untuk melakukan regresi.");
                    return;
                }
                
                dataX = new double[m][n];
                dataY = new double[m][1];
                System.out.println("Masukkan data-datanya");
                for (i = 1; i < m + 1; i++) {
                    for (j = 1; j < n + 1; j++) {
                        System.out.print("x" + i + j + ": ");
                        dataX[i-1][j-1] = scanner.nextDouble();
                    }

                    System.out.print("y" + i + ": ");
                    dataY[i-1][0] = scanner.nextDouble();
                }

                System.out.println("Masukkan nilai x sehingga y-nya dapat dicari");
                val = new double[n];
                for (i = 1; i < n + 1; i++){
                    System.out.print("x" + i + ": ");
                    val[i-1] = scanner.nextDouble();
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

                    // n = jumlah elemen dibagi baris
                    n = 0;

                    fileScanner = new Scanner(new File(filePath));
                    while (fileScanner.hasNext()) {
                        fileScanner.next();
                        n += 1;
                    } n++; // Elemen kosong dianggap ada
                    n /= m;
                    m--; // Kembalikan jumlah baris sebagaimana sebenarnya
            
                    dataX = new double[m][n];
                    dataY = new double[m][1];
                    fileScanner.close();

                    fileScanner = new Scanner(new File(filePath));
                    fileScanner.useLocale(Locale.US);

                    for (i = 0; i < m; i++) {
                        for (j = 0; j < n; j++) {
                            if (fileScanner.hasNextDouble()) dataX[i][j] = fileScanner.nextDouble();
                        }
                    } 

                    double[][][] splitted = OBE.splitMatrix(dataX);
                    dataX = splitted[0];
                    dataY = splitted[1];

                    val = new double[n - 1];
                    
                    for (i = 0; i < n - 1; i++) {
                        if (fileScanner.hasNextDouble()){
                            val[i] = fileScanner.nextDouble();
                        }
                    }
                    System.out.println("Enter any input to continue..");
                    fileScanner.close();
                    
                } catch (FileNotFoundException e) {
                    System.out.println("File tidak ditemukan: " + filePath);
                    return;
                }
                break;
            default:
                System.out.println("Pilihan invalid!");
                return;
        } processRegression(dataX, dataY, val, scanner, regressionChoice);
    }

    private static void processRegression(double[][] X, double[][] Y, double[] searchX, Scanner scanner, int regressionChoice){
        int i, j;
        double[][] augmented;
        double[] newSearchX;
        String regressionType;
        
        scanner.nextLine();
        if (regressionChoice == 2){
            augmented = OBE.toAugmented(toQuadraticX(X), Y);
            newSearchX = convertSearchX(searchX);
            IOMatriks.writeMatrix(augmented);
            regressionType = "kuadratik";
        }
        else {
            augmented = OBE.toAugmented(toLinearX(X), Y);
            IOMatriks.writeMatrix(augmented);
            newSearchX = searchX;
            regressionType = "linear";
        } //Default: Linear
        double result[][][] = multipleRegressionSolver(augmented, newSearchX);
        System.out.println("Fungsi regresi " + regressionType + " berganda yang memungkinkan adalah: ");

        String regressionParameters = "p(x1";
        for (i = 2; i < X[0].length + 1; i++){
            regressionParameters += ", x" + i + "";
        }

        regressionParameters += ") = ";
        String function = regressionParameters + result[0][0][0];
        System.out.print(function);
        for (i = 1; i < augmented[0].length - 1; i++){
            if (result[0][i][0] < 0) {
                function += " - " + Math.abs(result[0][i][0]) + "x" + i;
                System.out.print(" - " + Math.abs(result[0][i][0]) + "x" + i);
            }
            else {
                function += " + " + result[0][i][0] + "x" + i;
                System.out.print(" + " + result[0][i][0] + "x" + i);
            }
        }

        //Handle print kuadratik
        if (regressionChoice == 2){
            String[] var = new String[newSearchX.length]; //generate original variable
            for (i = 0; i < searchX.length; i++){
                var[i] = "x" + (i + 1);
            }

            for (i = searchX.length; i < 2*searchX.length; i++){
                var[i] = "x" + (i - searchX.length + 1) + "^2";
            }

            for (i = 0; i < newSearchX.length - 2*searchX.length ; i++){
                var[i + 2*searchX.length] = "x" + (i + 1) + "*x" + (((i + 1) % searchX.length + 1));
            }

            System.out.println("\nDengan definisi:");
            function += "\nDengan definisi:\n";
            for (i = 1; i < newSearchX.length + 1; i++){
                System.out.println("x"+ i + " = " + var[i-1]);
                function += "x"+ i + " = " + var[i-1] + "\n";
            }
        }

        System.out.println("\nNilai regresi " + regressionType + " bergandanya adalah: ");
        String regressionValue = "p(" + searchX[0];
        for (i = 1; i < searchX.length; i++){
            regressionValue += ", " + searchX[i];
        }
        regressionValue += ") = ";
        
        System.out.println(regressionValue + result[1][0][0]);
        String resultString = function + "\n" + regressionValue;
        IOMatriks.saveToFile(resultString, scanner);
    }
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        handleInput(scanner);
    }   
}