package ar.edu.itba.paw.models;

import java.util.Objects;

public class City {

    private final String name;
    private final long id;
    private final long provinceId;

    public City(final long id, final String name, final long provinceId){
        this.name = name;
        this.id = id;
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return String.format("City { id: %d, name: '%s', provinceId: %d }",
                id, name, provinceId);
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public long getProvinceId() {
        return provinceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof City))
            return false;
        City other = (City) obj;
        return this.id == other.id;
    }
}
