package com.outis.crm.service.mapper;

import com.outis.crm.domain.Attachment;
import com.outis.crm.service.dto.AttachmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {}
