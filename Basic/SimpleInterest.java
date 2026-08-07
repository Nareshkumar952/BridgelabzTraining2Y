package Basic;

import java.util.Scanner;

public class SimpleInterest {
    static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int p=s.nextInt();
        int t=s.nextInt();
        double r=s.nextDouble();
        double si=(p*r*t)/100;
        System.out.println(si);
    }
}
