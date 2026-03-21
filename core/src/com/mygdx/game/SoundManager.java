package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.files.FileHandleStream;
import com.mygdx.game.utils.types.FileUtils;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
        loadSoundFiles();
    }



    private final static String[] bakedSoundPaths = {
            "sounds/blood_splat.ogg",
            "sounds/fire_ball.ogg",
            "sounds/enemy_hit.ogg",
            "sounds/enemy_death.ogg",
            "sounds/click.ogg"
    };


    private void loadSoundFiles() {
        /*
        LibGDX file handling fucking sucks.
        The assets are baked into the jar executable, but can't be accessed using Gdx.files.internal
        only using Gdx.files.classpath. This wouldn't be an issue if Gdx.files.classpath didn't
        throw an exception when you try to access its children.

        As a workaround to this mess the paths for each file to be loaded has to be prebaked and
        cannot be dynamic, unless you want to duplicate files.
         */

        // System.out.println(FileUtils.bakeFilePaths("assets/sounds"));


        // System.out.println("loading files");


        // ArrayList<String> soundFiles = FileUtils.getFilesInFolder("assets/sounds");



        for (String soundFile : bakedSoundPaths) {
            FileHandle path = Gdx.files.classpath(soundFile);
            System.out.println("loading sound file : " + soundFile);
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
