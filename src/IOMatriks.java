import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOMatriks{
  //Spec: handle input keyboard, handle input file, handle output (print matrix)
  public static char[] convertTextToMatrix(String matrix){
    char matrixElement[] = matrix.toCharArray();
    //Use toCharArray()
    return matrixElement;
  }

  public static void readFile(String filename){
    try {
      File myObj = new File(filename);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        System.out.println(data);
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public static void writeMatrix(double[][] matrix){
    int i, j;
    for (i = 0; i < matrix.length; i++){
      for (j = 0; j < matrix[i].length; j++){
        System.out.print(matrix[i][j] + " ");
      } System.out.println(); //new line
    } 
  }
}
