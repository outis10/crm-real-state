package com.outis.realstate.attachment.service.mapper;

import com.outis.realstate.attachment.domain.Attachment;
import com.outis.realstate.attachment.service.dto.AttachmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {}
