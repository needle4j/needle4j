package org.needle4j.injection.method;

import org.needle4j.db.User;
import org.needle4j.injection.CurrentUser;

import jakarta.inject.Inject;
import java.util.Queue;

public class UserDao {

  private User user;

  private User currentUser;

  private Queue<?> queue1;

  private Queue<?> queue2;

  @Inject
  public void setUser(final User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  @Inject
  public void setCurrentUser(final @CurrentUser User user) {
    this.currentUser = user;
  }

  public User getCurrentUser() {
    return currentUser;
  }

  @Inject
  public void init(final Queue<?> queue1, final Queue<?> queue2) {
    this.queue1 = queue1;
    this.queue2 = queue2;
  }

  public Queue<?> getQueue1() {
    return queue1;
  }

  public Queue<?> getQueue2() {
    return queue2;
  }

}
