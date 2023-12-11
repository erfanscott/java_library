package lib.statepattern.states;

import lib.Library;
import lib.exceptions.InvalidInputException;
import lib.statepattern.LibState;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main implements LibState {
    private final Library library;
    private final Scanner scanner;

    public Main(Library library) {
        this.library = library;
        this.scanner = library.getScanner();
    }

    @Override
    public LibState action() {
        LibState nextState;
        System.out.println("1-Add/Delete/Edit/Show members");
        System.out.println("2-Add/Delete/Edit/Borrow/Return/Search books");
        System.out.println("3-Exit");
        System.out.println("4-see the library");
        System.out.print("Enter a number:");

        int selectedItem = library.getUserSelectedItem(1, 4);

        switch (selectedItem) {
            case 1:
                nextState = new Members(library);
                break;
            case 2:
                nextState = new Books(library);
                break;
            case 3:
                nextState = new Exit(library);
                break;
            case 4:
                library.showLibrary();
                nextState = new Main(library);
                break;
            default:
                nextState = new Main(library);
        }
        return nextState;
    }


}
