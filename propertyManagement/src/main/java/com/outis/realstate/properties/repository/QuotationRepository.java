package com.outis.realstate.properties.repository;

import com.outis.realstate.properties.domain.Quotation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Quotation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {}
