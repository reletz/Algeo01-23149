import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
        for (double[] row : matrix) {
            for (double element : row) {
                System.out.printf("%8.3f", element);
            }
            System.out.println();
        }
    }

    public static double[][] getUserInput(Scanner scanner) {
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

    public static void saveToFile(String text, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Usage Example
    public static void main(String[] args) {
    }
}
