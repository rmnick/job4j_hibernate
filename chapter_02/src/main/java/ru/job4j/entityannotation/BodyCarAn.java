package ru.job4j.entityannotation;


import javax.persistence.*;

@Entity
@Table(name = "body_car")
public class BodyCarAn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    public BodyCarAn() {

    }

    public BodyCarAn(final int id) {
        this(id, null);
    }

    public BodyCarAn(final String name) {
        this(0, name);
    }

    public BodyCarAn(final int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("BodyCarAn{id = %d, name = %s}", this.id, this.name);
    }
}
