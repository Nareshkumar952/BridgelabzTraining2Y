package Basic;

import java.util.Scanner;

public class PerimeterOfRectangle {
    static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int l=s.nextInt();
        int b=s.nextInt();
        int p=2*(l+b);
        System.out.println(p);
    }
}
