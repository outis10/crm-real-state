package com.outis.crm.repository;

import com.outis.crm.domain.NearbyLocation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NearbyLocation entity.
 */
@Repository
public interface NearbyLocationRepository extends JpaRepository<NearbyLocation, Long> {
    default Optional<NearbyLocation> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<NearbyLocation> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<NearbyLocation> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select nearbyLocation from NearbyLocation nearbyLocation left join fetch nearbyLocation.property",
        countQuery = "select count(nearbyLocation) from NearbyLocation nearbyLocation"
    )
    Page<NearbyLocation> findAllWithToOneRelationships(Pageable pageable);

    @Query("select nearbyLocation from NearbyLocation nearbyLocation left join fetch nearbyLocation.property")
    List<NearbyLocation> findAllWithToOneRelationships();

    @Query("select nearbyLocation from NearbyLocation nearbyLocation left join fetch nearbyLocation.property where nearbyLocation.id =:id")
    Optional<NearbyLocation> findOneWithToOneRelationships(@Param("id") Long id);
}
