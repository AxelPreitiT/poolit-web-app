package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cities")
public class City {


    private String name;

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="cities_city_id_seq" )
    @SequenceGenerator(sequenceName = "cities_city_id_seq" , name = "cities_city_id_seq", allocationSize = 1)
    @Column(name = "city_id")
    private long id;

    @ManyToOne(fetch=FetchType.EAGER,optional=false)
    @JoinColumn( name = "province_id")
    private Province province;

    protected City(){
    }

    public City(final String name, final Province province){
        this.name = name;
        this.province = province;
    }

    public City(final long id, final String name, final Province province){
        this.name = name;
        this.id = id;
        this.province = province;
    }

    //TODO remove provinceId implementation
    public City(final long id, final String name, final long provinceId){
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("City { id: %d, name: '%s', provinceId: %d }",
                id, name, province.getId());
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public long getProvinceId() {
        return province.getId();
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
