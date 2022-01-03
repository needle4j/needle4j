package org.needle4j.db;

import javax.persistence.*;

@Entity
@Table(name = Address.TABLE_NAME, uniqueConstraints = { @UniqueConstraint(columnNames = { "id", "zip" }) })
public class Address {
    public static final String TABLE_NAME = "NEEDLE_TEST_ADDRESS";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Person person;

    private String street;

    private String zip;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
