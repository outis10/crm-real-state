package com.outis.realstate.notification;

import com.outis.realstate.notification.config.AsyncSyncConfiguration;
import com.outis.realstate.notification.config.EmbeddedMongo;
import com.outis.realstate.notification.config.JacksonConfiguration;
import com.outis.realstate.notification.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = { NotificationApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class }
)
@EmbeddedMongo
public @interface IntegrationTest {
}
