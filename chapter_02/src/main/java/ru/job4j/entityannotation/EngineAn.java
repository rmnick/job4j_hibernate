package ru.job4j.entityannotation;

import javax.persistence.*;

@Entity
@Table(name = "engine")
public class EngineAn {

    private int id;

    private String name;

    public EngineAn() {

    }

    public EngineAn(String name) {
        this(0, name);
    }

    public EngineAn(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("EngineAn{id = %d, name = %s}", this.id, this.name);
    }
}
