package com.mahsum.puzzle;

import java.io.File;

public class FileCouldNotSaved extends Exception {
    public FileCouldNotSaved(File file){
        this(String.format("File could not saved: %s", file.toString()));
    }

    public FileCouldNotSaved(String message) {
        super(message);
    }
}
