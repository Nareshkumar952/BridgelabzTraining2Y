package Basic;

import java.util.Scanner;

public class VolumeOfCylinder {
    static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int r=s.nextInt();
        int h=s.nextInt();
        double v=3.14*r*r*h;
        System.out.println(v);
    }
}
