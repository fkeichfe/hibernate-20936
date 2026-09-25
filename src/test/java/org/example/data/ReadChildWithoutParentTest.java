package org.example.data;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.testing.orm.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * <p>
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
@DomainModel(
        annotatedClasses = {
                Child.class,
                Parent.class,
        }
)
@ServiceRegistry(
        // Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
        settings = {
                @Setting( name = AvailableSettings.JAKARTA_JDBC_URL, value = "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1" ),
                @Setting(name = AvailableSettings.SHOW_SQL, value = "true")
        }
)
@JiraKey(value = "HHH-20936")
@SessionFactory
class ReadChildWithoutParentTest {

    @Test
    void hhh20936Test(final SessionFactoryScope scope) {
        final int childrenWithoutParents = 3;
        scope.inTransaction(session -> {
            for (int i = 0; i < childrenWithoutParents; i++) {
                session.persist(
                        new Child(UUID.randomUUID().toString())
                );
            }
        });
        scope.inTransaction(session -> {
            // test works with all hibernate versions, as hibernate generates a left join
            final List<Child> result1 = session.createQuery("select c from Child c where c.parent is null", Child.class).getResultList();
            Assertions.assertEquals(childrenWithoutParents, result1.size());

            // test fails with hibernate >= 7.4.8, as hibernate generates a join
            final List<Long> result2 = session.createQuery("select c.id from Child c where c.parent is null", Long.class).getResultList();
            Assertions.assertEquals(childrenWithoutParents, result2.size());
        });
    }
}