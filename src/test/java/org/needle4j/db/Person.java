package org.needle4j.db;

import jakarta.persistence.*;

@Entity(name = "personEntity")
@Table(name = Person.TABLE_NAME)
public class Person {
  public static final String TABLE_NAME = "NEEDLE_TEST_PERSON";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(nullable = false)
  private String myName;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
  private Address address;

  public String getMyName() {
    return myName;
  }

  public void setMyName(final String myName) {
    this.myName = myName;
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

}
