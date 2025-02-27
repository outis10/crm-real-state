package com.outis.realstate.ai;

import com.outis.realstate.ai.config.AsyncSyncConfiguration;
import com.outis.realstate.ai.config.EmbeddedMongo;
import com.outis.realstate.ai.config.JacksonConfiguration;
import com.outis.realstate.ai.config.TestSecurityConfiguration;
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
@SpringBootTest(classes = { AgentAiApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class })
@EmbeddedMongo
public @interface IntegrationTest {
}
