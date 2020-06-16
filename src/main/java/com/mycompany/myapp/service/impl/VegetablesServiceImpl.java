package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VegetablesService;
import com.mycompany.myapp.domain.Vegetables;
import com.mycompany.myapp.repository.VegetablesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Vegetables}.
 */
@Service
@Transactional
public class VegetablesServiceImpl implements VegetablesService {

    private final Logger log = LoggerFactory.getLogger(VegetablesServiceImpl.class);

    private final VegetablesRepository vegetablesRepository;

    public VegetablesServiceImpl(VegetablesRepository vegetablesRepository) {
        this.vegetablesRepository = vegetablesRepository;
    }

    /**
     * Save a vegetables.
     *
     * @param vegetables the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Vegetables save(Vegetables vegetables) {
        log.debug("Request to save Vegetables : {}", vegetables);
        return vegetablesRepository.save(vegetables);
    }

    /**
     * Get all the vegetables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vegetables> findAll(Pageable pageable) {
        log.debug("Request to get all Vegetables");
        return vegetablesRepository.findAll(pageable);
    }


    /**
     * Get one vegetables by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vegetables> findOne(Long id) {
        log.debug("Request to get Vegetables : {}", id);
        return vegetablesRepository.findById(id);
    }

    /**
     * Delete the vegetables by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vegetables : {}", id);
        vegetablesRepository.deleteById(id);
    }
}
