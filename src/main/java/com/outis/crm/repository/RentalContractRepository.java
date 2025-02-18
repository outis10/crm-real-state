package com.outis.crm.repository;

import com.outis.crm.domain.RentalContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RentalContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentalContractRepository extends JpaRepository<RentalContract, Long> {}
