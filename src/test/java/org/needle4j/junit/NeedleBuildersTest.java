package org.needle4j.junit;

import static org.needle4j.junit.NeedleBuilders.databaseTestRule;
import static org.needle4j.junit.NeedleBuilders.needleTestRule;
import static org.needle4j.junit.NeedleBuilders.outerRule;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.mock.MockitoProvider;

public class NeedleBuildersTest {

    @Rule
    public TestRule rule = outerRule(needleTestRule(this).build()).around(databaseTestRule().build());

    @ObjectUnderTest()
    private Runnable runnable = new Runnable() {

        @Inject
        private Instance<Runnable> instance;

        @Override
        public void run() {
            instance.get();

        }
    };

    @Inject
    private MockitoProvider mockitoProvider;

    @Inject
    private EntityManager entityManager;

    @Test
    public void testRuleChain() throws Exception {
        Assert.assertNotNull(mockitoProvider);
        Assert.assertNotNull(runnable);
        Assert.assertNotNull(entityManager);
    }

}
