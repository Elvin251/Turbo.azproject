package az.developia.turbo_system_name.Tasks.Lesson91;

public class Pet {
    protected String name, species;
    protected int age;

    public Pet(String n, String s, int a) {
        name = n;
        species = s;
        age = a;
    }

    public class DogPet extends Pet {
        private String toy;

        public DogPet(String n, String s, int a, String t) {
            super(n, s, a);
            toy = t;
        }
    }

    public class Bird extends Pet {
        private double wingSpan;

        public Bird(String n, String s, int a, double w) {
            super(n, s, a);
            wingSpan = w;
        }
    }
}