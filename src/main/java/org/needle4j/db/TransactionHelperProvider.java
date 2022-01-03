package org.needle4j.db;

import org.needle4j.db.transaction.TransactionHelper;
import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionTargetInformation;

class TransactionHelperProvider implements InjectionProvider<TransactionHelper> {

  private final DatabaseTestcase databaseTestcase;

  public TransactionHelperProvider(DatabaseTestcase databaseTestcase) {
    super();
    this.databaseTestcase = databaseTestcase;
  }

  @Override
  public boolean verify(InjectionTargetInformation information) {
    return information.getType() == TransactionHelper.class;
  }

  @Override
  public TransactionHelper getInjectedObject(Class<?> injectionPointType) {
    return databaseTestcase.getTransactionHelper();
  }

  @Override
  public Object getKey(InjectionTargetInformation information) {
    return TransactionHelper.class;
  }
}
