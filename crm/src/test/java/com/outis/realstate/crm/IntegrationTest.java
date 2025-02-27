package com.outis.realstate.crm;

import com.outis.realstate.crm.config.AsyncSyncConfiguration;
import com.outis.realstate.crm.config.EmbeddedSQL;
import com.outis.realstate.crm.config.JacksonConfiguration;
import com.outis.realstate.crm.config.TestSecurityConfiguration;
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
@SpringBootTest(classes = { CrmApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class })
@EmbeddedSQL
public @interface IntegrationTest {
}
