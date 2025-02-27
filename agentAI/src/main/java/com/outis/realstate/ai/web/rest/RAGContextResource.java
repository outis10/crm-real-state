package com.outis.realstate.ai.web.rest;

import com.outis.realstate.ai.repository.RAGContextRepository;
import com.outis.realstate.ai.service.RAGContextService;
import com.outis.realstate.ai.service.dto.RAGContextDTO;
import com.outis.realstate.ai.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.outis.realstate.ai.domain.RAGContext}.
 */
@RestController
@RequestMapping("/api/rag-contexts")
public class RAGContextResource {

    private static final Logger LOG = LoggerFactory.getLogger(RAGContextResource.class);

    private static final String ENTITY_NAME = "agentAiragContext";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RAGContextService rAGContextService;

    private final RAGContextRepository rAGContextRepository;

    public RAGContextResource(RAGContextService rAGContextService, RAGContextRepository rAGContextRepository) {
        this.rAGContextService = rAGContextService;
        this.rAGContextRepository = rAGContextRepository;
    }

    /**
     * {@code POST  /rag-contexts} : Create a new rAGContext.
     *
     * @param rAGContextDTO the rAGContextDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rAGContextDTO, or with status {@code 400 (Bad Request)} if the rAGContext has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RAGContextDTO> createRAGContext(@Valid @RequestBody RAGContextDTO rAGContextDTO) throws URISyntaxException {
        LOG.debug("REST request to save RAGContext : {}", rAGContextDTO);
        if (rAGContextDTO.getId() != null) {
            throw new BadRequestAlertException("A new rAGContext cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rAGContextDTO = rAGContextService.save(rAGContextDTO);
        return ResponseEntity.created(new URI("/api/rag-contexts/" + rAGContextDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rAGContextDTO.getId()))
            .body(rAGContextDTO);
    }

    /**
     * {@code PUT  /rag-contexts/:id} : Updates an existing rAGContext.
     *
     * @param id the id of the rAGContextDTO to save.
     * @param rAGContextDTO the rAGContextDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rAGContextDTO,
     * or with status {@code 400 (Bad Request)} if the rAGContextDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rAGContextDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RAGContextDTO> updateRAGContext(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody RAGContextDTO rAGContextDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update RAGContext : {}, {}", id, rAGContextDTO);
        if (rAGContextDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rAGContextDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rAGContextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rAGContextDTO = rAGContextService.update(rAGContextDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rAGContextDTO.getId()))
            .body(rAGContextDTO);
    }

    /**
     * {@code PATCH  /rag-contexts/:id} : Partial updates given fields of an existing rAGContext, field will ignore if it is null
     *
     * @param id the id of the rAGContextDTO to save.
     * @param rAGContextDTO the rAGContextDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rAGContextDTO,
     * or with status {@code 400 (Bad Request)} if the rAGContextDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rAGContextDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rAGContextDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RAGContextDTO> partialUpdateRAGContext(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody RAGContextDTO rAGContextDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RAGContext partially : {}, {}", id, rAGContextDTO);
        if (rAGContextDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rAGContextDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rAGContextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RAGContextDTO> result = rAGContextService.partialUpdate(rAGContextDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rAGContextDTO.getId())
        );
    }

    /**
     * {@code GET  /rag-contexts} : get all the rAGContexts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rAGContexts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RAGContextDTO>> getAllRAGContexts(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of RAGContexts");
        Page<RAGContextDTO> page = rAGContextService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rag-contexts/:id} : get the "id" rAGContext.
     *
     * @param id the id of the rAGContextDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rAGContextDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RAGContextDTO> getRAGContext(@PathVariable("id") String id) {
        LOG.debug("REST request to get RAGContext : {}", id);
        Optional<RAGContextDTO> rAGContextDTO = rAGContextService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rAGContextDTO);
    }

    /**
     * {@code DELETE  /rag-contexts/:id} : delete the "id" rAGContext.
     *
     * @param id the id of the rAGContextDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRAGContext(@PathVariable("id") String id) {
        LOG.debug("REST request to delete RAGContext : {}", id);
        rAGContextService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
