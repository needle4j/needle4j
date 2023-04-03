package org.needle4j.db;

import jakarta.persistence.*;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
  public static final String TABLE_NAME = "NEEDLE_TEST_USER";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

}
