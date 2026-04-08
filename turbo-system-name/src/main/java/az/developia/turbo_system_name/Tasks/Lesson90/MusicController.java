package az.developia.turbo_system_name.Tasks.Lesson90;

import java.util.ArrayList;

public class MusicController {

    ArrayList<MusicEntity> musics = new ArrayList<>();

    public void addMusic(MusicEntity music) {
        musics.add(music);
        System.out.println("Music elave olundu!");
    }

    public void getAllMusics() {
        if (musics.isEmpty()) {
            System.out.println("List bosdur!");
            return;
        }

        for (MusicEntity m : musics) {
            System.out.println(m);
        }
    }
    public MusicEntity findById(int id) {
        for (MusicEntity m : musics) {
            if (m.id == id) {
                return m;
            }
        }
        return null;
    }

    public void updateMusic(int id, String name, String artist, double duration, String type) {
        MusicEntity m = findById(id);

        if (m != null) {
            m.name = name;
            m.artist = artist;
            m.duration = duration;
            m.musicType = type;
            System.out.println("Music update olundu!");
        } else {
            System.out.println("Tapilmadi!");
        }
    }


    public void deleteMusic(int id) {
        MusicEntity m = findById(id);

        if (m != null) {
            musics.remove(m);
            System.out.println("Music silindi!");
        } else {
            System.out.println("Tapilmadi!");
        }
    }
}