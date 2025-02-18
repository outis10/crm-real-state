package com.outis.crm.web.rest;

import com.outis.crm.repository.ChatInteractionRepository;
import com.outis.crm.service.ChatInteractionService;
import com.outis.crm.service.dto.ChatInteractionDTO;
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
 * REST controller for managing {@link com.outis.crm.domain.ChatInteraction}.
 */
@RestController
@RequestMapping("/api/chat-interactions")
public class ChatInteractionResource {

    private static final Logger LOG = LoggerFactory.getLogger(ChatInteractionResource.class);

    private static final String ENTITY_NAME = "chatInteraction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChatInteractionService chatInteractionService;

    private final ChatInteractionRepository chatInteractionRepository;

    public ChatInteractionResource(ChatInteractionService chatInteractionService, ChatInteractionRepository chatInteractionRepository) {
        this.chatInteractionService = chatInteractionService;
        this.chatInteractionRepository = chatInteractionRepository;
    }

    /**
     * {@code POST  /chat-interactions} : Create a new chatInteraction.
     *
     * @param chatInteractionDTO the chatInteractionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chatInteractionDTO, or with status {@code 400 (Bad Request)} if the chatInteraction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ChatInteractionDTO> createChatInteraction(@Valid @RequestBody ChatInteractionDTO chatInteractionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ChatInteraction : {}", chatInteractionDTO);
        if (chatInteractionDTO.getId() != null) {
            throw new BadRequestAlertException("A new chatInteraction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        chatInteractionDTO = chatInteractionService.save(chatInteractionDTO);
        return ResponseEntity.created(new URI("/api/chat-interactions/" + chatInteractionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, chatInteractionDTO.getId().toString()))
            .body(chatInteractionDTO);
    }

    /**
     * {@code PUT  /chat-interactions/:id} : Updates an existing chatInteraction.
     *
     * @param id the id of the chatInteractionDTO to save.
     * @param chatInteractionDTO the chatInteractionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chatInteractionDTO,
     * or with status {@code 400 (Bad Request)} if the chatInteractionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chatInteractionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ChatInteractionDTO> updateChatInteraction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChatInteractionDTO chatInteractionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ChatInteraction : {}, {}", id, chatInteractionDTO);
        if (chatInteractionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chatInteractionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chatInteractionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        chatInteractionDTO = chatInteractionService.update(chatInteractionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chatInteractionDTO.getId().toString()))
            .body(chatInteractionDTO);
    }

    /**
     * {@code PATCH  /chat-interactions/:id} : Partial updates given fields of an existing chatInteraction, field will ignore if it is null
     *
     * @param id the id of the chatInteractionDTO to save.
     * @param chatInteractionDTO the chatInteractionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chatInteractionDTO,
     * or with status {@code 400 (Bad Request)} if the chatInteractionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chatInteractionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chatInteractionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChatInteractionDTO> partialUpdateChatInteraction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChatInteractionDTO chatInteractionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ChatInteraction partially : {}, {}", id, chatInteractionDTO);
        if (chatInteractionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chatInteractionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chatInteractionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChatInteractionDTO> result = chatInteractionService.partialUpdate(chatInteractionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chatInteractionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /chat-interactions} : get all the chatInteractions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chatInteractions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ChatInteractionDTO>> getAllChatInteractions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ChatInteractions");
        Page<ChatInteractionDTO> page = chatInteractionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chat-interactions/:id} : get the "id" chatInteraction.
     *
     * @param id the id of the chatInteractionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chatInteractionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ChatInteractionDTO> getChatInteraction(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ChatInteraction : {}", id);
        Optional<ChatInteractionDTO> chatInteractionDTO = chatInteractionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chatInteractionDTO);
    }

    /**
     * {@code DELETE  /chat-interactions/:id} : delete the "id" chatInteraction.
     *
     * @param id the id of the chatInteractionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatInteraction(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ChatInteraction : {}", id);
        chatInteractionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
