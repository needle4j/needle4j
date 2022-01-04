package org.needle4j.db.operation.h2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.db.Address;
import org.needle4j.db.Person;
import org.needle4j.db.operation.JdbcConfiguration;
import org.needle4j.db.transaction.VoidRunnable;
import org.needle4j.junit.DatabaseRule;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public class H2DeleteOperationTest {
  private static final JdbcConfiguration H2_DB_CONFIGURATION = new JdbcConfiguration("jdbc:h2:mem:test",
      "org.h2.Driver", "sa", "");

  private final H2DeleteOperationForTest h2DeleteOperation = new H2DeleteOperationForTest(H2_DB_CONFIGURATION);

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule("H2TestDataModel", h2DeleteOperation);

  @Test
  public void testDisableReferentialIntegrity() throws Exception {
    try (final Statement statement = h2DeleteOperation.getConnection().createStatement()) {
      h2DeleteOperation.disableReferentialIntegrity(statement);
      insertAddressWithInvalidFk();
      h2DeleteOperation.commit();
    }
  }

  @Test
  public void testEnableReferentialIntegrity() throws Exception {
    try (final Statement statement = h2DeleteOperation.getConnection().createStatement()) {
      h2DeleteOperation.enableReferentialIntegrity(statement);

      Assert.assertThrows(SQLException.class, () -> {
        insertAddressWithInvalidFk();
        h2DeleteOperation.commit();
      });
    }
  }

  private void insertAddressWithInvalidFk() throws Exception {
    final Address address = new Address();

    databaseRule.getTransactionHelper().executeInTransaction(new VoidRunnable() {
      @Override
      public void doRun(final EntityManager entityManager) throws Exception {
        entityManager.persist(address);
        entityManager.flush();
      }
    });

    try (final Statement statement = h2DeleteOperation.getConnection().createStatement()) {
      final int executeUpdate = statement.executeUpdate("update " + Address.TABLE_NAME + " set person_id = 2");
      Assert.assertEquals(1, executeUpdate);
    }
  }

  @Test
  public void testDeleteContent() throws Exception {
    final var person = new Person();
    person.setMyName("Heinz");

    databaseRule.getTransactionHelper().executeInTransaction(new VoidRunnable() {
      @Override
      public void doRun(EntityManager entityManager) {
        entityManager.persist(person);
      }
    });

    try (final Statement statement = h2DeleteOperation.getConnection().createStatement()) {
      final ResultSet rs1 = statement.executeQuery("SELECT * FROM " + Person.TABLE_NAME);
      Assert.assertTrue(rs1.next());

      h2DeleteOperation.deleteContent(Collections.singletonList(Person.TABLE_NAME), statement);

      final ResultSet rs2 = statement.executeQuery("SELECT * FROM " + Person.TABLE_NAME);
      Assert.assertFalse(rs2.next());
    }
  }

  @After
  public void tearDown() throws Exception {
    h2DeleteOperation.closeConnection();
  }

  static class H2DeleteOperationForTest extends H2DeleteOperation {

    public H2DeleteOperationForTest(JdbcConfiguration configuration) {
      super(configuration);
    }

    @Override
    protected Connection getConnection() throws SQLException {
      return super.getConnection();
    }

    @Override
    protected void closeConnection() throws SQLException {
      super.closeConnection();
    }

    @Override
    protected void commit() throws SQLException {
      super.commit();
    }

    @Override
    protected void rollback() throws SQLException {
      super.rollback();
    }

    @Override
    protected void deleteContent(List<String> tables, Statement statement) throws SQLException {
      super.deleteContent(tables, statement);
    }
  }
}
