public class Parametrik {
  public double[] coeffList;

  public Parametrik() {
    coeffList = new double[53];
    int i;
    for (i = 0; i < 53; i++) {
      coeffList[i] = 0;
    }
  }

  public static String makeVar (Parametrik sb) {
    StringBuilder result = new StringBuilder();
    int i;

    if (sb.coeffList[0] != 0) {
      result.append(String.valueOf(sb.coeffList[0]));
    }

    for (i = 1; i < 53; i++) {
      if (sb.coeffList[i] == 0) {
        continue; // Skip elements with a value of 0
      }
      
      if (!(result.length() == 0) && sb.coeffList[i] > 0) {
        result.append("+");
      }

      char variable = (i <= 26)? (char)(i + 64): (char)(i + 70); //A-Z, a-z

      if (sb.coeffList[i] == 1) {
        result.append(variable);
      } else if (sb.coeffList[i] == -1) {
        result.append("-").append(variable);
      } else {
        result.append(String.valueOf(sb.coeffList[i])).append(variable);
      }
    }

    if (result.length() == 0) {
      result.append("0");
    }
    return result.toString();
  }

  //Kaliin dengan konstan
  public static Parametrik multiplyConstant (Parametrik sb, double k) {
    Parametrik result = new Parametrik();
    int i;
    for (i = 0; i < 53; i++) {
      result.coeffList[i] = sb.coeffList[i] * k;
    }
    return result;
  }

  //Kurangin 2 ruas
  public static Parametrik subtractParametrik (Parametrik sb1, Parametrik sb2) {
    Parametrik result = new Parametrik();
    int i;
    for (i = 0; i < 53; i++) {
      result.coeffList[i] = sb1.coeffList[i] - sb2.coeffList[i];
    }
    return result;
  }

  //Mencari index selanjutnya kalo udah dipake
  public static int nextIndexParametrik(int idx) {
    //dari r (114)
    //idx = var - 64 (karena A = 65)
    //Ntar balik lagi
    switch (idx) {
      case 52:
        return 27;
      case 43:
        return 18;
      case 26:
        return 1;
      default:
        return idx + 1;
    } 
  }
}
