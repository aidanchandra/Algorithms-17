
public class Test {

    public static void main(String[] args) {
        for (int c = 0; c <= 65536; c++) {
            int i = c;
            
            i /= 10;
            int a = i % 100;
            i /= 100;
            int b = i;
            String last = getString(b) + getString(a) + "0";
            System.out.println(String.valueOf(c) + " == " + last);
        }
        
    }

    static String getString(int a) {
        String returnable = "";
        returnable += String.valueOf(a);
        if (a < 10) {
            returnable += "0";
        }
        return returnable;
    }
}
