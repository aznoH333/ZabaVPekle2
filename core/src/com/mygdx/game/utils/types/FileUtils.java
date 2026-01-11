package com.mygdx.game.utils.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

public class FileUtils {
    public static ArrayList<String> getFilesInFolder(String path) {
        ArrayList<String> output = new ArrayList<>();


        FileHandle handle = Gdx.files.internal(path);
        if (!handle.exists()) {
            System.exit(1);
        }
        for (FileHandle f : handle.list()) {
            if (!f.isDirectory()) {
                output.add(f.path());
            } else {
                output.addAll(getFilesInFolder(f.path()));
            }
        }


        return output;
    }
}
