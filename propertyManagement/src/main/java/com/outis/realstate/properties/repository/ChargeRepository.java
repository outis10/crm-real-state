package com.outis.realstate.properties.repository;

import com.outis.realstate.properties.domain.Charge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Charge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long> {}
