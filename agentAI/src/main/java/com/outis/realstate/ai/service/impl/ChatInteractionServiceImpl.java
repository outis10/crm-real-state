package com.outis.realstate.ai.service.impl;

import com.outis.realstate.ai.domain.ChatInteraction;
import com.outis.realstate.ai.repository.ChatInteractionRepository;
import com.outis.realstate.ai.service.ChatInteractionService;
import com.outis.realstate.ai.service.dto.ChatInteractionDTO;
import com.outis.realstate.ai.service.mapper.ChatInteractionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.outis.realstate.ai.domain.ChatInteraction}.
 */
@Service
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
    public Page<ChatInteractionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ChatInteractions");
        return chatInteractionRepository.findAll(pageable).map(chatInteractionMapper::toDto);
    }

    @Override
    public Optional<ChatInteractionDTO> findOne(String id) {
        LOG.debug("Request to get ChatInteraction : {}", id);
        return chatInteractionRepository.findById(id).map(chatInteractionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete ChatInteraction : {}", id);
        chatInteractionRepository.deleteById(id);
    }
}
