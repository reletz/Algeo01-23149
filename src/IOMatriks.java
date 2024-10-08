import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOMatriks {
    // Spec: handle input keyboard, handle input file, handle output (print matrix)
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
        int i, j;
        for (i = 0; i < matrix.length; i++) {
            for (j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder input = new StringBuilder();
        System.out.println("Masukkan matriks:");
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            input.append(line).append("\n");
        }
        scanner.close();
        return input.toString();
    }

    // Usage Example
    public static void main(String[] args) {
        // String filename = "example.txt";
        // double[][] matrix = readFile(filename);
        // System.out.println("Matrix read from file:");
        // writeMatrix(matrix);
        
        // String input = "3 4.5 2.8 10 12\n-3 7 8.3 11 -4\n0.5 -10 -9 12 0\n";
        // String input = getUserInput();

        // double[][] matrix = convertTextToMatrix(input);

        // System.out.println("Matrix from input string:");
        // writeMatrix(matrix);
    }
}
