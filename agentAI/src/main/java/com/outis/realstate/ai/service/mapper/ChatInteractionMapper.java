package com.outis.realstate.ai.service.mapper;

import com.outis.realstate.ai.domain.ChatInteraction;
import com.outis.realstate.ai.service.dto.ChatInteractionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChatInteraction} and its DTO {@link ChatInteractionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChatInteractionMapper extends EntityMapper<ChatInteractionDTO, ChatInteraction> {}
