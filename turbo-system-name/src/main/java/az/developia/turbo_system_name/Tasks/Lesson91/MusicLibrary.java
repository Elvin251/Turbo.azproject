package az.developia.turbo_system_name.Tasks.Lesson91;

import java.util.*;

public class MusicLibrary {
    private ArrayList<String> songs = new ArrayList<>();
    private Random random = new Random();

    public void addSong(String s) { songs.add(s); }
    public void removeSong(String s) { songs.remove(s); }

    public void playRandom() {
        System.out.println(songs.get(random.nextInt(songs.size())));
    }
}