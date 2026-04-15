package az.developia.turbo_system_name.Tasks.Lesson91;

public class Dog {
    private String name;
    private String breed;

    public Dog(String name, String breed) {
        this.name = name;
        this.breed = breed;
    }

    public void setName(String name) { this.name = name; }
    public void setBreed(String breed) { this.breed = breed; }

    public void print() {
        System.out.println(name + " " + breed);
    }
}