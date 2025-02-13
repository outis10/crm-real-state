package com.outis.crm.service.impl;

import com.outis.crm.domain.Attachment;
import com.outis.crm.repository.AttachmentRepository;
import com.outis.crm.service.AttachmentService;
import com.outis.crm.service.dto.AttachmentDTO;
import com.outis.crm.service.mapper.AttachmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.outis.crm.domain.Attachment}.
 */
@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    private final AttachmentRepository attachmentRepository;

    private final AttachmentMapper attachmentMapper;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
    }

    @Override
    public AttachmentDTO save(AttachmentDTO attachmentDTO) {
        LOG.debug("Request to save Attachment : {}", attachmentDTO);
        Attachment attachment = attachmentMapper.toEntity(attachmentDTO);
        attachment = attachmentRepository.save(attachment);
        return attachmentMapper.toDto(attachment);
    }

    @Override
    public AttachmentDTO update(AttachmentDTO attachmentDTO) {
        LOG.debug("Request to update Attachment : {}", attachmentDTO);
        Attachment attachment = attachmentMapper.toEntity(attachmentDTO);
        attachment = attachmentRepository.save(attachment);
        return attachmentMapper.toDto(attachment);
    }

    @Override
    public Optional<AttachmentDTO> partialUpdate(AttachmentDTO attachmentDTO) {
        LOG.debug("Request to partially update Attachment : {}", attachmentDTO);

        return attachmentRepository
            .findById(attachmentDTO.getId())
            .map(existingAttachment -> {
                attachmentMapper.partialUpdate(existingAttachment, attachmentDTO);

                return existingAttachment;
            })
            .map(attachmentRepository::save)
            .map(attachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachmentDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Attachments");
        return attachmentRepository.findAll(pageable).map(attachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttachmentDTO> findOne(Long id) {
        LOG.debug("Request to get Attachment : {}", id);
        return attachmentRepository.findById(id).map(attachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Attachment : {}", id);
        attachmentRepository.deleteById(id);
    }
}
