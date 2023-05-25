package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "provinces")
public class Province {

    private String name;

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="provinces_province_id_seq" )
    @SequenceGenerator(sequenceName = "provinces_province_id_seq" , name = "provinces_province_id_seq", allocationSize = 1)
    @Column(name = "province_id")
    private long id;

    @OneToMany(fetch=FetchType.LAZY,orphanRemoval=true,	mappedBy="province")
    private List<City> cities;

    protected Province() {

    }

    public Province( final String name){
        this.name = name;
    }

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
