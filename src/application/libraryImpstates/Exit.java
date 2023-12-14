package application.libraryImpstates;

import lib.Library;
import lib.LibState;

import java.util.Scanner;

public class Exit implements LibState {
    private final Library library;
    private final Scanner scanner;

    public Exit(Library library) {
        this.library = library;
        this.scanner = library.getScanner();
    }

    @Override
    public LibState action() {
        System.out.println("Are you sure you want to terminate the app? 1)Yes 2)No");

        int selectedItem = library.getUserSelectedItem(1, 2);

        if (selectedItem == 1) {
            scanner.close();
            System.exit(0);
        }
        return new Main(library);
    }
}
