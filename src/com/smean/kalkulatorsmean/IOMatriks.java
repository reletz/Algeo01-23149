package com.smean.kalkulatorsmean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class IOMatriks {
    public static double[][] convertTextToMatrix(String text) {
        // Array karakter text
        char[] charArray = text.toCharArray();

        // Jumlah baris dan kolom
        int rows = 0, cols = 1;
        for (char x : charArray) {
            if (x == ' ' && rows == 0)
                cols++;
            if (x == '\n')
                rows++;
        }
        double[][] matrix = new double[rows][cols];
        int i = 0, j = 0;
        String tmp = "";
        for (char x : charArray) {
            if (x != ' ' && x != '\n') {
                tmp += x;
            } else {
                if (!tmp.isEmpty()) {
                    matrix[i][j] = Double.parseDouble(tmp);
                    tmp = "";
                    j++;
                    if (j == cols) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
        if (!tmp.isEmpty()) {
            matrix[i][j] = Double.parseDouble(tmp);
        }
        return matrix;
    }

    public static String convertMatrixToText(double[][] matrix) {
        StringBuilder text = new StringBuilder();
        for (double[] row : matrix) {
            for (int j = 0; j < row.length; j++) {
                text.append(row[j]);
                if (j < row.length - 1) {
                    text.append(" ");
                }
            }
            text.append("\n");
        }
        return text.toString();
    }

    public static double[][] readFile(String filename) {
        StringBuilder text = new StringBuilder();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                text.append(myReader.nextLine()).append("\n");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return convertTextToMatrix(text.toString());
    }

    public static void writeMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double element : row) {
                if (element == -0.0) {
                    element = 0.0;
                }
                System.out.printf("%8.3f", element);
            }
            System.out.println();
        }
    }

    public static double[][] getUserInput(Scanner scanner) {
        scanner.useLocale(Locale.US);
        StringBuilder text = new StringBuilder();
        System.out.println("Masukkan matriks:");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            text.append(line).append("\n");
        }
        return convertTextToMatrix(text.toString());
    }

    public static void saveToFile(String text, Scanner scanner) {
        scanner.useLocale(Locale.US);
        System.out.println("\nSimpan Hasil ke File?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");
        System.out.print("Masukkan pilihan: ");

        int simpanChoice = scanner.nextInt();
        scanner.nextLine();

        switch (simpanChoice) {
            case 1:
                System.out.print("Masukkan nama file tujuan: ");
                String filename = scanner.nextLine();

                try {
                    FileWriter writer = new FileWriter(filename);
                    writer.write(text);
                    writer.close();
                    Main.clearConsole();
                    System.out.println("Berhasil menyimpan ke file.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                Main.clearConsole();
                break;
            default:
                break;
        }   
    }

    // Usage Example
    public static void main(String[] args) {
    }
}
