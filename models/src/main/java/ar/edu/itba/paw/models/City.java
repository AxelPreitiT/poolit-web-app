package ar.edu.itba.paw.models;

public class City {

    private final String name;
    private final int id;

    public City(final String name, final int id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
