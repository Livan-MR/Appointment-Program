package model;

public class Countries {
    private int id;
    private String name;

    public Countries(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the ID
     */
    public int getId() {return id;}

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the Name
     */
    public String getName() {return name;}

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Makes items in combo boxes legible
     * @return the Name
     */
    public String toString() {
        return (this.name );
    }
}
