import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class ImageResizing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 

        // Inputan path file gambar yang asli
        System.out.println("Masukkan path gambar: ");
        String filepath = scanner.nextLine(); 

        // Membaca gambar ke dalam BufferedImage
        BufferedImage originalImage = ImageIO.read(new File(filepath));

        // Inputan baru skala width dan height 
        System.out.print("Masukkan skala width: ");
        int newWidth = scanner.nextInt();
        System.out.print("Masukkan skala height: ");
        int newHeight = scanner.nextInt();

        try {
            BufferedImage image = ImageIO.read(new File(filepath));
            BufferedImage newScaledImage = bicubicResize(originalImage, newWidth, newHeight);

            File outputfile = new File("resizedImage.jpg");
            ImageIO.write(newScaledImage, "jpg", outputfile);
            System.out.println("Berhasil menyimpan gambar yang sudah diresize.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan gambar.");
        }
    }
}