package com.mahsum.puzzle.exceptions;

import java.io.File;

public class FileCouldNotCreated extends Exception {

  public FileCouldNotCreated(File file) {
    super(String.format("Error while creating file: %s", file.toString()));
  }
}
