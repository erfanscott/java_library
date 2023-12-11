package lib.statepattern.states;

import lib.Book;
import lib.Gender;
import lib.Library;
import lib.Member;
import lib.exceptions.BadTargetEntityTypeException;
import lib.exceptions.EntityNotFoundException;
import lib.exceptions.InvalidInputException;
import lib.statepattern.LibState;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Members implements LibState {
    private final Library library;
    private final Scanner scanner;

    public Members(Library library) {
        this.library = library;
        this.scanner = library.getScanner();
    }

    @Override
    public LibState action() {
        LibState nextState;
        System.out.println("1-Add Member");
        System.out.println("2-Delete Member");
        System.out.println("3-Edit Member");
        System.out.println("4-Show Member");
        System.out.println("5-Return");
        System.out.print("Enter a number:");
        /**
         * for the sake of practice
         * implementing Member action methods using an inner class inside the action method
         */
        class Inner {
            void addMember() {
                System.out.println("Enter your name:");
                String name = scanner.next();
                System.out.println("What is your gender? 1)Male 2)Female:");
                int selectedItem = library.getUserSelectedItem(1, 2);
                Gender gender = (selectedItem == 1) ? Gender.MALE : Gender.FEMALE;
                Member member = new Member(name, gender, library.uID());
                library.save(member);
                System.out.println("The Member has been successfully added:");
                member.showOnConsole();
            }

            void deleteMember() {
                try {
                    System.out.println("ID of the member you want to delete:");
                    String id = scanner.next();
                    Member member = library.retrieve(id, Member.class);
                    library.delete(member);
                    System.out.println("The member has been successfully deleted");
                } catch (EntityNotFoundException e) {
                    System.out.println("The member was not found");
                } catch (BadTargetEntityTypeException e) {
                    System.out.println("The member was not found");
                }
            }

            void editMember() {
                try {
                    System.out.print("Enter your ID:");
                    String id = scanner.next();
                    Member member = library.retrieve(id, Member.class);
                    System.out.print("Enter your new name:");
                    String name = scanner.next();
                    System.out.println("What is your new gender? 1)Male 2)Female:");
                    int selectedItem = library.getUserSelectedItem(1, 2);
                    Gender gender = (selectedItem == 1) ? Gender.MALE : Gender.FEMALE;
                    member.setName(name);
                    member.setGender(gender);
                    System.out.println("The Member has been successfully updated:");
                    member.showOnConsole();
                } catch (EntityNotFoundException e) {
                    System.out.println("The member was not found");
                } catch (BadTargetEntityTypeException e) {
                    System.out.println("The member was not found");
                }


            }

            void showMember() {
                try {
                    System.out.println("ID of the member you want see the details of:");
                    String id = scanner.next();
                    Member member = library.retrieve(id, Member.class);
                    member.showOnConsole();
                } catch (EntityNotFoundException e) {
                    System.out.println("The member was not found");
                } catch (BadTargetEntityTypeException e) {
                    System.out.println("The member was not found");
                }
            }
        }
        Inner inner = new Inner();

        int selectedItem = library.getUserSelectedItem(1, 5);

        switch (selectedItem) {
            case 1:
                inner.addMember();
                nextState = new Members(library);
                break;
            case 2:
                inner.deleteMember();
                nextState = new Members(library);
                break;
            case 3:
                inner.editMember();
                nextState = new Members(library);
                break;
            case 4:
                inner.showMember();
                nextState = new Members(library);
                break;
            case 5:
                nextState = new Main(library);
                break;
            default:
                nextState = new Members(library);
                ;
        }
        return nextState;
    }
}
