package Basic;

import java.util.Scanner;

public class AvgOfThreeNumber {
    static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int a=s.nextInt();
        int b=s.nextInt();
        int c=s.nextInt();
        double avg=(a+b+c)/3;
        System.out.println(avg);
    }
}
