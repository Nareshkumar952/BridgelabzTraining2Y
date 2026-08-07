package Basic;

import java.util.Scanner;

public class CelToFeh {
    static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int c=s.nextInt();
        int f=c*(9/5)+32;
        System.out.println(f);
    }
}
