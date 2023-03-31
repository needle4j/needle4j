package org.needle4j.injection;

import org.needle4j.db.User;

import jakarta.inject.Inject;
import java.util.Queue;

public class UserDao {

  @Inject
  @CurrentUser
  private User currentUser;

  @Inject
  private User user;

  @Inject
  private Queue<?> queue;

  public User getCurrentUser() {
    return currentUser;
  }

  public User getUser() {
    return user;
  }

  public Queue<?> getQueue() {
    return queue;
  }

}
