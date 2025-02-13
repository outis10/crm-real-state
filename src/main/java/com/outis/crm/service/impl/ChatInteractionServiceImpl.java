package com.outis.crm.service.impl;

import com.outis.crm.domain.ChatInteraction;
import com.outis.crm.repository.ChatInteractionRepository;
import com.outis.crm.service.ChatInteractionService;
import com.outis.crm.service.dto.ChatInteractionDTO;
import com.outis.crm.service.mapper.ChatInteractionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.outis.crm.domain.ChatInteraction}.
 */
@Service
@Transactional
public class ChatInteractionServiceImpl implements ChatInteractionService {

    private static final Logger LOG = LoggerFactory.getLogger(ChatInteractionServiceImpl.class);

    private final ChatInteractionRepository chatInteractionRepository;

    private final ChatInteractionMapper chatInteractionMapper;

    public ChatInteractionServiceImpl(ChatInteractionRepository chatInteractionRepository, ChatInteractionMapper chatInteractionMapper) {
        this.chatInteractionRepository = chatInteractionRepository;
        this.chatInteractionMapper = chatInteractionMapper;
    }

    @Override
    public ChatInteractionDTO save(ChatInteractionDTO chatInteractionDTO) {
        LOG.debug("Request to save ChatInteraction : {}", chatInteractionDTO);
        ChatInteraction chatInteraction = chatInteractionMapper.toEntity(chatInteractionDTO);
        chatInteraction = chatInteractionRepository.save(chatInteraction);
        return chatInteractionMapper.toDto(chatInteraction);
    }

    @Override
    public ChatInteractionDTO update(ChatInteractionDTO chatInteractionDTO) {
        LOG.debug("Request to update ChatInteraction : {}", chatInteractionDTO);
        ChatInteraction chatInteraction = chatInteractionMapper.toEntity(chatInteractionDTO);
        chatInteraction = chatInteractionRepository.save(chatInteraction);
        return chatInteractionMapper.toDto(chatInteraction);
    }

    @Override
    public Optional<ChatInteractionDTO> partialUpdate(ChatInteractionDTO chatInteractionDTO) {
        LOG.debug("Request to partially update ChatInteraction : {}", chatInteractionDTO);

        return chatInteractionRepository
            .findById(chatInteractionDTO.getId())
            .map(existingChatInteraction -> {
                chatInteractionMapper.partialUpdate(existingChatInteraction, chatInteractionDTO);

                return existingChatInteraction;
            })
            .map(chatInteractionRepository::save)
            .map(chatInteractionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatInteractionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ChatInteractions");
        return chatInteractionRepository.findAll(pageable).map(chatInteractionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChatInteractionDTO> findOne(Long id) {
        LOG.debug("Request to get ChatInteraction : {}", id);
        return chatInteractionRepository.findById(id).map(chatInteractionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ChatInteraction : {}", id);
        chatInteractionRepository.deleteById(id);
    }
}
