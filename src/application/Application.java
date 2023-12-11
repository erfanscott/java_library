package application;

import lib.Library;

/**
 * This is the skeleton of the library project
 */

public class Application {
    public static void main(String[] args) {
        Library lib = LibraryImp.getLibInstance();
        lib.start();
    }
}
