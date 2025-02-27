package com.outis.realstate.ai.repository;

import com.outis.realstate.ai.domain.RAGContext;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RAGContext entity.
 */
@Repository
public interface RAGContextRepository extends MongoRepository<RAGContext, String> {}
