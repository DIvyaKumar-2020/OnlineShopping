package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.RationService;
import com.mycompany.myapp.domain.Ration;
import com.mycompany.myapp.repository.RationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Ration}.
 */
@Service
@Transactional
public class RationServiceImpl implements RationService {

    private final Logger log = LoggerFactory.getLogger(RationServiceImpl.class);

    private final RationRepository rationRepository;

    public RationServiceImpl(RationRepository rationRepository) {
        this.rationRepository = rationRepository;
    }

    /**
     * Save a ration.
     *
     * @param ration the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Ration save(Ration ration) {
        log.debug("Request to save Ration : {}", ration);
        return rationRepository.save(ration);
    }

    /**
     * Get all the rations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Ration> findAll(Pageable pageable) {
        log.debug("Request to get all Rations");
        return rationRepository.findAll(pageable);
    }


    /**
     * Get one ration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Ration> findOne(Long id) {
        log.debug("Request to get Ration : {}", id);
        return rationRepository.findById(id);
    }

    /**
     * Delete the ration by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ration : {}", id);
        rationRepository.deleteById(id);
    }
}
