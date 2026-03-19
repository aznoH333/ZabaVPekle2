package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.utils.types.FileUtils;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class SoundManager {
    private static SoundManager instance = null;

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }


    private HashMap<String, Sound> soundMap = new HashMap<>();

    private SoundManager() {
        // loadSoundFiles();
    }


    private void loadSoundFiles() {
        ArrayList<String> soundFiles = FileUtils.getFilesInFolder("assets/sounds");


        for (String soundFile : soundFiles) {
            FileHandle path = Gdx.files.internal(soundFile);

            soundMap.put(path.nameWithoutExtension(), Gdx.audio.newSound(path));
        }
    }

    public void playSound(String soundName, float volume, float pitchRandomization) {

        Sound sound = soundMap.get(soundName);


        long soundId = soundMap.get(soundName).play(volume);
        float pitch = NumberUtils.randomFloat(1 - pitchRandomization, 1 + pitchRandomization);

        sound.setPitch(soundId, pitch);
    }

    public void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
    }

}
