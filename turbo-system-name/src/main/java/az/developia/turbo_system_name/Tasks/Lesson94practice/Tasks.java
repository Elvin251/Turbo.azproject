package az.developia.turbo_system_name.Tasks.Lesson94practice;

public class Tasks {

    static class Student {
        String name;
        double grade1, grade2, grade3;
        String university;

        public Student(String name, double g1, double g2, double g3, String university) {
            this.name = name;
            this.grade1 = g1;
            this.grade2 = g2;
            this.grade3 = g3;
            this.university = university;
        }

        public double getAverage() {
            return (grade1 + grade2 + grade3) / 3;
        }

        public String toString() {
            return name + " - " + university + " Ortalama: " + getAverage();
        }
    }

    public static void main(String[] args) {

        // 1) 2 və 3-ə bölünənlər
        int[] arr1 = {12, 5, 18, 7, 9, 6};
        System.out.println("2 ve 3-e bolunenler:");
        for (int n : arr1) {
            if (n % 2 == 0 && n % 3 == 0) {
                System.out.println(n);
            }
        }

        // 2) double cəm
        double[] arr2 = {2.5, 3.1, 4.0, 1.4};
        double sumDouble = 0;
        for (double n : arr2) {
            sumDouble += n;
        }
        System.out.println("Double cem: " + sumDouble);

        // 3) max və min
        int[] arr3 = {10, 3, 25, 7, 1};
        int max = arr3[0];
        int min = arr3[0];

        for (int n : arr3) {
            if (n > max) max = n;
            if (n < min) min = n;
        }
        System.out.println("Max: " + max);
        System.out.println("Min: " + min);

        // 4) ən uzun söz
        String[] words = {"alma", "armud", "banan", "qarpiz"};
        String longest = words[0];

        for (String w : words) {
            if (w.length() > longest.length()) {
                longest = w;
            }
        }
        System.out.println("En uzun soz: " + longest);

        // 5) Student və ən yüksək ortalama
        Student[] students = {
                new Student("Ali", 80, 85, 90, "ADNSU"),
                new Student("Veli", 70, 75, 80, "BDU"),
                new Student("Aysel", 90, 95, 92, "ADA"),
                new Student("Murad", 60, 65, 70, "UNEC"),
                new Student("Leyla", 88, 89, 91, "BMU")
        };

        double maxAvg = students[0].getAverage();

        for (Student s : students) {
            if (s.getAverage() > maxAvg) {
                maxAvg = s.getAverage();
            }
        }

        System.out.println("En yuksek ortalamali telebe(ler):");
        for (Student s : students) {
            if (s.getAverage() == maxAvg) {
                System.out.println(s);
            }
        }

        // 6) tək və cüt
        int[] arr6 = {1, 2, 3, 4, 5, 6};
        System.out.println("Cut ededler:");
        for (int n : arr6) {
            if (n % 2 == 0) System.out.println(n);
        }

        System.out.println("Tek ededler:");
        for (int n : arr6) {
            if (n % 2 != 0) System.out.println(n);
        }

        // 7) tək ədədlərin cəmi
        int[] arr7 = {1, 2, 3, 4, 5};
        int sumOdd = 0;

        for (int n : arr7) {
            if (n % 2 != 0) {
                sumOdd += n;
            }
        }

        System.out.println("Tek ededlerin cemi: " + sumOdd);
    }
}