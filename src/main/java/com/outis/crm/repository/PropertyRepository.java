package com.outis.crm.repository;

import com.outis.crm.domain.Property;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Property entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {}
