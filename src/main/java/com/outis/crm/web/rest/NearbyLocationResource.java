package com.outis.crm.web.rest;

import com.outis.crm.repository.NearbyLocationRepository;
import com.outis.crm.service.NearbyLocationService;
import com.outis.crm.service.dto.NearbyLocationDTO;
import com.outis.crm.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.outis.crm.domain.NearbyLocation}.
 */
@RestController
@RequestMapping("/api/nearby-locations")
public class NearbyLocationResource {

    private static final Logger LOG = LoggerFactory.getLogger(NearbyLocationResource.class);

    private static final String ENTITY_NAME = "nearbyLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NearbyLocationService nearbyLocationService;

    private final NearbyLocationRepository nearbyLocationRepository;

    public NearbyLocationResource(NearbyLocationService nearbyLocationService, NearbyLocationRepository nearbyLocationRepository) {
        this.nearbyLocationService = nearbyLocationService;
        this.nearbyLocationRepository = nearbyLocationRepository;
    }

    /**
     * {@code POST  /nearby-locations} : Create a new nearbyLocation.
     *
     * @param nearbyLocationDTO the nearbyLocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nearbyLocationDTO, or with status {@code 400 (Bad Request)} if the nearbyLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NearbyLocationDTO> createNearbyLocation(@Valid @RequestBody NearbyLocationDTO nearbyLocationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save NearbyLocation : {}", nearbyLocationDTO);
        if (nearbyLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new nearbyLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        nearbyLocationDTO = nearbyLocationService.save(nearbyLocationDTO);
        return ResponseEntity.created(new URI("/api/nearby-locations/" + nearbyLocationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, nearbyLocationDTO.getId().toString()))
            .body(nearbyLocationDTO);
    }

    /**
     * {@code PUT  /nearby-locations/:id} : Updates an existing nearbyLocation.
     *
     * @param id the id of the nearbyLocationDTO to save.
     * @param nearbyLocationDTO the nearbyLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nearbyLocationDTO,
     * or with status {@code 400 (Bad Request)} if the nearbyLocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nearbyLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NearbyLocationDTO> updateNearbyLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NearbyLocationDTO nearbyLocationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NearbyLocation : {}, {}", id, nearbyLocationDTO);
        if (nearbyLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nearbyLocationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nearbyLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        nearbyLocationDTO = nearbyLocationService.update(nearbyLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nearbyLocationDTO.getId().toString()))
            .body(nearbyLocationDTO);
    }

    /**
     * {@code PATCH  /nearby-locations/:id} : Partial updates given fields of an existing nearbyLocation, field will ignore if it is null
     *
     * @param id the id of the nearbyLocationDTO to save.
     * @param nearbyLocationDTO the nearbyLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nearbyLocationDTO,
     * or with status {@code 400 (Bad Request)} if the nearbyLocationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nearbyLocationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nearbyLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NearbyLocationDTO> partialUpdateNearbyLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NearbyLocationDTO nearbyLocationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NearbyLocation partially : {}, {}", id, nearbyLocationDTO);
        if (nearbyLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nearbyLocationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nearbyLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NearbyLocationDTO> result = nearbyLocationService.partialUpdate(nearbyLocationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nearbyLocationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nearby-locations} : get all the nearbyLocations.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nearbyLocations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NearbyLocationDTO>> getAllNearbyLocations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of NearbyLocations");
        Page<NearbyLocationDTO> page;
        if (eagerload) {
            page = nearbyLocationService.findAllWithEagerRelationships(pageable);
        } else {
            page = nearbyLocationService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nearby-locations/:id} : get the "id" nearbyLocation.
     *
     * @param id the id of the nearbyLocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nearbyLocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NearbyLocationDTO> getNearbyLocation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NearbyLocation : {}", id);
        Optional<NearbyLocationDTO> nearbyLocationDTO = nearbyLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nearbyLocationDTO);
    }

    /**
     * {@code DELETE  /nearby-locations/:id} : delete the "id" nearbyLocation.
     *
     * @param id the id of the nearbyLocationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNearbyLocation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NearbyLocation : {}", id);
        nearbyLocationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
