package ru.job4j.entityannotation;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cars")
public class CarAn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "year_of_manufact")
    private Timestamp yearOfMan;

    @ManyToOne
    @JoinColumn(name = "id_body_car")
    private BodyCarAn bodyCarAn;

    @ManyToOne
    @JoinColumn(name = "id_engine")
    private EngineAn engineAn;

    @ManyToOne
    @JoinColumn(name = "id_transmission")
    private TransmissionAn transmissionAn;


    public CarAn() {

    }

    public CarAn(final int id) {
        this(id, null, null, null, null, null);
    }

    public CarAn(final int id, final String name, final Timestamp yearOfMan, final BodyCarAn bodyCarAn, final EngineAn engineAn, final TransmissionAn transmissionAn) {
        this.id = id;
        this.name = name;
        this.yearOfMan = yearOfMan;
        this.bodyCarAn = bodyCarAn;
        this.engineAn = engineAn;
        this.transmissionAn = transmissionAn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BodyCarAn getBodyCarAn() {
        return bodyCarAn;
    }

    public void setBodyCarAn(BodyCarAn bodyCarAn) {
        this.bodyCarAn = bodyCarAn;
    }

    public EngineAn getEngineAn() {
        return engineAn;
    }

    public void setEngineAn(EngineAn engineAn) {
        this.engineAn = engineAn;
    }

    public TransmissionAn getTransmissionAn() {
        return transmissionAn;
    }

    public void setTransmissionAn(TransmissionAn transmissionAn) {
        this.transmissionAn = transmissionAn;
    }

    public Timestamp getYearOfMan() {
        return yearOfMan;
    }

    public void setYearOfMan(Timestamp yearOfMan) {
        this.yearOfMan = yearOfMan;
    }

    @Override
    public String toString() {
        return String.format("CarAn{id = %d, name = %s, yearOfMan = %s, engineAn = %s, bodyCarAn = %s, transmissionAn = %s}",
                this.id,
                this.name,
                this.yearOfMan.toString(),
                this.engineAn != null ? this.engineAn.getName() : "null",
                this.bodyCarAn != null ? this.bodyCarAn.getName() : "null",
                this.transmissionAn != null ? this.transmissionAn.getName() : "null"
        );
    }
}
