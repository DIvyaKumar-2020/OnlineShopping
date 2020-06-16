package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.MeatService;
import com.mycompany.myapp.domain.Meat;
import com.mycompany.myapp.repository.MeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Meat}.
 */
@Service
@Transactional
public class MeatServiceImpl implements MeatService {

    private final Logger log = LoggerFactory.getLogger(MeatServiceImpl.class);

    private final MeatRepository meatRepository;

    public MeatServiceImpl(MeatRepository meatRepository) {
        this.meatRepository = meatRepository;
    }

    /**
     * Save a meat.
     *
     * @param meat the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Meat save(Meat meat) {
        log.debug("Request to save Meat : {}", meat);
        return meatRepository.save(meat);
    }

    /**
     * Get all the meats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Meat> findAll(Pageable pageable) {
        log.debug("Request to get all Meats");
        return meatRepository.findAll(pageable);
    }


    /**
     * Get one meat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Meat> findOne(Long id) {
        log.debug("Request to get Meat : {}", id);
        return meatRepository.findById(id);
    }

    /**
     * Delete the meat by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Meat : {}", id);
        meatRepository.deleteById(id);
    }
}
