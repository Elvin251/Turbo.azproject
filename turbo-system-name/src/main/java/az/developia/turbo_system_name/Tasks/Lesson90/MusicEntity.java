package az.developia.turbo_system_name.Tasks.Lesson90;

public class MusicEntity {
    int id;
    String name;
    String artist;
    double duration;
    String musicType;

    public MusicEntity(int id, String name, String artist, double duration, String musicType) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.musicType = musicType;
    }

    @Override
    public String toString() {
        return id + " - " + name + " - " + artist + " - " + duration + " - " + musicType;
    }
}