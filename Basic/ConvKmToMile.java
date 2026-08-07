package Basic;

import java.util.Scanner;

public class ConvKmToMile {
    static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int k=s.nextInt();
        double mil=k*0.621371;
        System.out.println(mil);
    }
}
