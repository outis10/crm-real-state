package com.outis.realstate.crm.service.impl;

import com.outis.realstate.crm.domain.Contact;
import com.outis.realstate.crm.repository.ContactRepository;
import com.outis.realstate.crm.service.ContactService;
import com.outis.realstate.crm.service.dto.ContactDTO;
import com.outis.realstate.crm.service.mapper.ContactMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.outis.realstate.crm.domain.Contact}.
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        LOG.debug("Request to save Contact : {}", contactDTO);
        Contact contact = contactMapper.toEntity(contactDTO);
        contact = contactRepository.save(contact);
        return contactMapper.toDto(contact);
    }

    @Override
    public ContactDTO update(ContactDTO contactDTO) {
        LOG.debug("Request to update Contact : {}", contactDTO);
        Contact contact = contactMapper.toEntity(contactDTO);
        contact = contactRepository.save(contact);
        return contactMapper.toDto(contact);
    }

    @Override
    public Optional<ContactDTO> partialUpdate(ContactDTO contactDTO) {
        LOG.debug("Request to partially update Contact : {}", contactDTO);

        return contactRepository
            .findById(contactDTO.getId())
            .map(existingContact -> {
                contactMapper.partialUpdate(existingContact, contactDTO);

                return existingContact;
            })
            .map(contactRepository::save)
            .map(contactMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Contacts");
        return contactRepository.findAll(pageable).map(contactMapper::toDto);
    }

    public Page<ContactDTO> findAllWithEagerRelationships(Pageable pageable) {
        return contactRepository.findAllWithEagerRelationships(pageable).map(contactMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactDTO> findOne(Long id) {
        LOG.debug("Request to get Contact : {}", id);
        return contactRepository.findOneWithEagerRelationships(id).map(contactMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Contact : {}", id);
        contactRepository.deleteById(id);
    }
}
