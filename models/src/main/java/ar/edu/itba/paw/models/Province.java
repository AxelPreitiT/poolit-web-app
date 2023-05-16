package ar.edu.itba.paw.models;

import java.util.Objects;

public class Province {

    private final String name;
    private final long id;

    public Province(final long id, final String name){
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Province { id: %d, name: '%s' }",
                id, name);
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Province))
            return false;
        Province other = (Province) obj;
        return this.id == other.id;
    }
}
