package com.smean.kalkulatorsmean;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageResizing {

    public static BufferedImage loadImage(String filePath) {
        BufferedImage image = null; // Deklarasi variabel image dengan tipe BufferedImage
        try {
            image = ImageIO.read(new File(filePath)); // Membaca file gambar
        } catch (IOException e) {
            System.out.println("File tidak ditemukan: " + e.getMessage());
        }
        return image;
    }

    public static BufferedImage resizeImageWithScale(BufferedImage image, double scaleWidth, double scaleHeight) {
        int originWidth = image.getWidth();
        int originHeight = image.getHeight();
    
        // Hitung dimensi baru berdasarkan skala
        int newWidth = (int) (originWidth * scaleWidth);
        int newHeight = (int) (originHeight * scaleHeight);
        
        // Gambar baru dengan dimensi baru
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
    
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                // Menghitung koordinat x dan y pada gambar asli
                double gx = ((double) x / newWidth) * (originWidth - 1);
                double gy = ((double) y / newHeight) * (originHeight - 1);
    
                // Interpolasi bicubic untuk mendapatkan nilai piksel baru
                int pixelValue = (int) bicubicInterpolationForImage(image, gx, gy);
                int rgb = (pixelValue << 16) | (pixelValue << 8) | pixelValue; // Konversi ke format RGB
                resizedImage.setRGB(x, y, rgb);
            }
        }
        return resizedImage;
    }

    public static double bicubicInterpolationForImage(BufferedImage image, double x, double y) {
        int xInt = (int) x;
        int yInt = (int) y;

        double[] matrix = new double[16];
        for (int i = -1; i < 3; i++) {
            for (int j = -1; j < 3; j++) {
                int px = Math.max(0, Math.min(xInt + i, image.getWidth() - 1));
                int py = Math.max(0, Math.min(yInt + j, image.getHeight() - 1));
                matrix[(i + 1) * 4 + (j + 1)] = image.getRGB(px, py) & 0xFF;
            }
        }

        double[][] coeffs = calculateCoefficients(matrix);
        double a = x - xInt;
        double b = y - yInt;
        return bicubicInterpolation(coeffs, a, b);
    }

    public static double[][] calculateCoefficients(double[] matrix) {
        double[][] F = {
            {matrix[5], matrix[6]},
            {matrix[9], matrix[10]}
        };

        double[][] F_x = {
            {(matrix[6] - matrix[4]) / 2.0, (matrix[10] - matrix[8]) / 2.0},
            {(matrix[7] - matrix[5]) / 2.0, (matrix[11] - matrix[9]) / 2.0}
        };

        double[][] F_y = {
            {(matrix[10] - matrix[6] - matrix[9] + matrix[5]) / 4.0, (matrix[14] - matrix[2] - matrix[13] + matrix[1]) / 4.0},
            {(matrix[11] - matrix[7] - matrix[10] + matrix[6]) / 4.0, (matrix[15] - matrix[3] - matrix[14] + matrix[2]) / 4.0}
        };

        double[][] F_xy = {
            {(matrix[10] - matrix[6] - matrix[9] + matrix[5]) / 4.0, (matrix[14] - matrix[2] - matrix[13] + matrix[1]) / 4.0},
            {(matrix[11] - matrix[7] - matrix[9] + matrix[5]) / 4.0, (matrix[15] - matrix[3] - matrix[13] + matrix[1]) / 4.0}
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

        double[][] C = new double[4][4];
        double[][] AT = OBE.transpose(A);
        double[][] AG = OBE.multiplyMatrix(A, G);
        C = OBE.multiplyMatrix(AG, AT);

        return C;
    }

    public static void handleInput(Scanner scanner) {
        scanner.useLocale(Locale.US);
        Main.clearConsole();
        System.out.println("Masukkan path gambar: ");
        String filePath = scanner.nextLine();
        
        BufferedImage image = loadImage(filePath);
        if (image == null) {
            System.out.println("Gagal memuat gambar.");
            return;
        }
        double scaleWidth = 0;
        double scaleHeight = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Masukkan skala lebar (contoh: 1.5 untuk 150%): ");
                scaleWidth = scanner.nextDouble();
                scanner.nextLine();
                System.out.println("Masukkan skala tinggi (contoh: 1.5 untuk 150%): ");
                scaleHeight = scanner.nextDouble();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka desimal.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        BufferedImage resizedImage = resizeImageWithScale(image, scaleWidth, scaleHeight);
        try {
            // Pastikan folder 'test' ada, jika tidak, buat folder tersebut
            File outputDir = new File("test");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            // Simpan gambar ke folder 'test'
            File outputFile = new File(outputDir, "new_image.jpg");
            ImageIO.write(resizedImage, "jpg", outputFile);
            System.out.println("Berhasil menyimpan gambar dengan ukuran yang baru di folder 'test'.");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan gambar: " + e.getMessage());
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSUB-MENU IMAGE RESIZING:");
            System.out.println("1. Image Resizing");
            System.out.println("2. Keluar");   
            System.out.print("\nMasukkan pilihan: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleInput(scanner);
                    break;
                case 2:
                    System.out.println("Keluar");
                    return;
                default:
                    System.out.println("Pilihan invalid!");
            }
        }
    }
}