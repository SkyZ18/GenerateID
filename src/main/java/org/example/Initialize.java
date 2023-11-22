package org.example;

import java.util.Scanner;

public class Initialize {

    public void init() {
        Scanner sc = new Scanner(System.in);
        System.out.println("How much IDs do you want?: ");
        int i = Integer.parseInt(sc.nextLine());
        GeneratorThread t1 = new GeneratorThread(i);
        t1.start();
    }
}
