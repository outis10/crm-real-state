package com.outis.realstate.ai.repository;

import com.outis.realstate.ai.domain.ChatInteraction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ChatInteraction entity.
 */
@Repository
public interface ChatInteractionRepository extends MongoRepository<ChatInteraction, String> {}
