package com.outis.crm.web.rest;

import com.outis.crm.repository.OpportunityRepository;
import com.outis.crm.service.OpportunityService;
import com.outis.crm.service.dto.OpportunityDTO;
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
 * REST controller for managing {@link com.outis.crm.domain.Opportunity}.
 */
@RestController
@RequestMapping("/api/opportunities")
public class OpportunityResource {

    private static final Logger LOG = LoggerFactory.getLogger(OpportunityResource.class);

    private static final String ENTITY_NAME = "opportunity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpportunityService opportunityService;

    private final OpportunityRepository opportunityRepository;

    public OpportunityResource(OpportunityService opportunityService, OpportunityRepository opportunityRepository) {
        this.opportunityService = opportunityService;
        this.opportunityRepository = opportunityRepository;
    }

    /**
     * {@code POST  /opportunities} : Create a new opportunity.
     *
     * @param opportunityDTO the opportunityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opportunityDTO, or with status {@code 400 (Bad Request)} if the opportunity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OpportunityDTO> createOpportunity(@Valid @RequestBody OpportunityDTO opportunityDTO) throws URISyntaxException {
        LOG.debug("REST request to save Opportunity : {}", opportunityDTO);
        if (opportunityDTO.getId() != null) {
            throw new BadRequestAlertException("A new opportunity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        opportunityDTO = opportunityService.save(opportunityDTO);
        return ResponseEntity.created(new URI("/api/opportunities/" + opportunityDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, opportunityDTO.getId().toString()))
            .body(opportunityDTO);
    }

    /**
     * {@code PUT  /opportunities/:id} : Updates an existing opportunity.
     *
     * @param id the id of the opportunityDTO to save.
     * @param opportunityDTO the opportunityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opportunityDTO,
     * or with status {@code 400 (Bad Request)} if the opportunityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opportunityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OpportunityDTO> updateOpportunity(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OpportunityDTO opportunityDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Opportunity : {}, {}", id, opportunityDTO);
        if (opportunityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opportunityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opportunityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        opportunityDTO = opportunityService.update(opportunityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opportunityDTO.getId().toString()))
            .body(opportunityDTO);
    }

    /**
     * {@code PATCH  /opportunities/:id} : Partial updates given fields of an existing opportunity, field will ignore if it is null
     *
     * @param id the id of the opportunityDTO to save.
     * @param opportunityDTO the opportunityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opportunityDTO,
     * or with status {@code 400 (Bad Request)} if the opportunityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the opportunityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the opportunityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OpportunityDTO> partialUpdateOpportunity(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OpportunityDTO opportunityDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Opportunity partially : {}, {}", id, opportunityDTO);
        if (opportunityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opportunityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opportunityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpportunityDTO> result = opportunityService.partialUpdate(opportunityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opportunityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /opportunities} : get all the opportunities.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opportunities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OpportunityDTO>> getAllOpportunities(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Opportunities");
        Page<OpportunityDTO> page;
        if (eagerload) {
            page = opportunityService.findAllWithEagerRelationships(pageable);
        } else {
            page = opportunityService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /opportunities/:id} : get the "id" opportunity.
     *
     * @param id the id of the opportunityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opportunityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OpportunityDTO> getOpportunity(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Opportunity : {}", id);
        Optional<OpportunityDTO> opportunityDTO = opportunityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opportunityDTO);
    }

    /**
     * {@code DELETE  /opportunities/:id} : delete the "id" opportunity.
     *
     * @param id the id of the opportunityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Opportunity : {}", id);
        opportunityService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
