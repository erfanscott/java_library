package lib;

public interface Entity {
    String getID();

    boolean hasSearchedKey(String key);

    void showOnConsole();
}