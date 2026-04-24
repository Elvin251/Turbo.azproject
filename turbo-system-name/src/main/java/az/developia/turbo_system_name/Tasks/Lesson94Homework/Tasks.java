package az.developia.turbo_system_name.Tasks.Lesson94Homework;

import java.util.Scanner;

public class Tasks {

    public static void main(String[] args) {

        // 1) 1-dən 100-ə qədər cüt ədədlər (for)
        System.out.println("1) Cut ededler:");
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println("\n");

        // 2) Vurma cədvəli (for)
        System.out.println("2) Vurma cedveli:");
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                System.out.println(i + " x " + j + " = " + (i * j));
            }
        }
        System.out.println();

        // 3) Kvadratlar (for)
        System.out.println("3) Kvadratlar:");
        for (int i = 1; i <= 10; i++) {
            System.out.println(i + "^2 = " + (i * i));
        }
        System.out.println();

        // 4) Üçbucaq (for)
        System.out.println("4) Ucbucaq:");
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        System.out.println();

        // 5) 1-dən 20-yə qədər (while)
        System.out.println("5) 1-den 20-ye qeder:");
        int i = 1;
        while (i <= 20) {
            System.out.print(i + " ");
            i++;
        }
        System.out.println("\n");

        // 6) Ədədin tərsi (while)
        System.out.println("6) Ededin tersi:");
        int num = 123; // istəsən dəyiş
        int reversed = 0;

        while (num != 0) {
            int digit = num % 10;
            reversed = reversed * 10 + digit;
            num /= 10;
        }
        System.out.println("Ters: " + reversed);
        System.out.println();

        // 7) 0 daxil edilənə qədər cəm (do-while)
        System.out.println("7) Cem (0 daxil edene qeder):");
        Scanner sc = new Scanner(System.in);
        int sum = 0;
        int n;

        do {
            System.out.print("Eded daxil et (0 - stop): ");
            n = sc.nextInt();
            sum += n;
        } while (n != 0);

        System.out.println("Cem: " + sum);
    }
}