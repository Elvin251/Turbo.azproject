package az.developia.turbo_system_name.Tasks.Lesson90;

public class Tasks {
    public static void main(String[] args) {

        MusicController controller = new MusicController();

        controller.addMusic(new MusicEntity(1, "Shape of You", "Ed Sheeran", 4.2, "Pop"));
        controller.addMusic(new MusicEntity(2, "Lose Yourself", "Eminem", 5.0, "Rap"));

        System.out.println("----- BUTUN MUSIC -----");
        controller.getAllMusics();

        System.out.println("----- FIND BY ID -----");
        System.out.println(controller.findById(1));

        System.out.println("----- UPDATE -----");
        controller.updateMusic(1, "Perfect", "Ed Sheeran", 4.5, "Pop");

        System.out.println("----- DELETE -----");
        controller.deleteMusic(2);

        System.out.println("----- FINAL LIST -----");
        controller.getAllMusics();
    }
}