package com.outis.crm.repository;

import com.outis.crm.domain.Opportunity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Opportunity entity.
 */
@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    default Optional<Opportunity> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Opportunity> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Opportunity> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select opportunity from Opportunity opportunity left join fetch opportunity.customer",
        countQuery = "select count(opportunity) from Opportunity opportunity"
    )
    Page<Opportunity> findAllWithToOneRelationships(Pageable pageable);

    @Query("select opportunity from Opportunity opportunity left join fetch opportunity.customer")
    List<Opportunity> findAllWithToOneRelationships();

    @Query("select opportunity from Opportunity opportunity left join fetch opportunity.customer where opportunity.id =:id")
    Optional<Opportunity> findOneWithToOneRelationships(@Param("id") Long id);
}
