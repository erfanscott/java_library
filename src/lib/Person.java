package lib;

/**
 * This class is going to be the parent class for library entities that are humans
 * <p>Library entities such as members and staff will inherit from this
 * abstract class so the OOP so features like polymorphism can be used to
 * write more generalized and maintainable codes</p>
 * <p>This class is an abstract class because it refuses to implement any of
 * methods in the Entity interface and it just defines name and gender properties
 * and the corresponding setters and getters</p>
 *
 * @see Entity
 * @see Gender
 */
public abstract class Person implements Entity {
    private String name;
    private Gender gender;

    /**
     * This constructor is used by the subclasses to initialize name and gender properties
     *
     * @param name   the name of the person
     * @param gender the gender o the person
     */
    public Person(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    /**
     * @return the gender of the person
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @return the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * @param gender the gender that is to be assigned to the person
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @param name the name that is to be assigned to the person
     */
    public void setName(String name) {
        this.name = name;
    }
}
