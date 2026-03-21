package com.mygdx.game.utils.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

public class FileUtils {
    public static ArrayList<String> getFilesInFolder(FileHandle handle) {
        ArrayList<String> output = new ArrayList<>();

        if (!handle.exists()) {
            throw new RuntimeException("Could not get files in folder. Folder " + handle.path() + " not found");
        }

        for (FileHandle f : handle.list()) {
            System.out.println(f.name());

            if (f.nameWithoutExtension().isEmpty()) {
                continue;
            }

            if (!f.isDirectory()) {
                output.add(f.path());
            }else {
                output.addAll(getFilesInFolder(f));
            }
        }


        return output;
    }

    public static ArrayList<String> getFilesInFolder(String path) {
        return getFilesInFolder(Gdx.files.internal(path));
    }

    /**
     * A super shitty hack to work around goofy LibGDX file access restrictions.
     * Returns a formated string representing file paths that can be loaded from the jar file
     */
    public static String bakeFilePaths(String startingPath) {
        ArrayList<String> files = getFilesInFolder(startingPath);

        StringBuilder output = new StringBuilder();

        for (String file : files) {

            String stripped = file.substring(file.indexOf("/") + 1);

            output.append("\"").append(stripped).append("\",\n");
        }

        return output.toString();
    }
}
