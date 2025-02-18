package com.outis.crm.repository;

import com.outis.crm.domain.ChatInteraction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ChatInteraction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatInteractionRepository extends JpaRepository<ChatInteraction, Long> {}
