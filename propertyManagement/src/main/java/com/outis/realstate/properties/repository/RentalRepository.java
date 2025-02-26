package com.outis.realstate.properties.repository;

import com.outis.realstate.properties.domain.Rental;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rental entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {}
