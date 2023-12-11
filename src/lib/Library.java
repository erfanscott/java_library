package lib;

import lib.exceptions.BadTargetEntityTypeException;
import lib.exceptions.EntityNotFoundException;
import lib.exceptions.InvalidRequestedActionException;
import lib.statepattern.LibState;

import java.util.ArrayList;
import java.util.Scanner;

public interface Library {

    void save(Entity e);

    void delete(Entity e) throws EntityNotFoundException;

    void borrowBook(Book book, Member member) throws InvalidRequestedActionException;

    void returnBook(Book book, Member member) throws InvalidRequestedActionException;

    /**
     *
     */

    Entity retrieve(String id) throws EntityNotFoundException;

    <T> T retrieve(String id, Class<T> type) throws EntityNotFoundException, BadTargetEntityTypeException;

    ArrayList<Entity> search(String key, Class<?> targetType);


    /**
     * library state management
     */

    public void setState(LibState libState);

    public void start();


    Scanner getScanner();

    String uID();

    int getUserSelectedItem(int from, int to);

    void showLibrary();
}
