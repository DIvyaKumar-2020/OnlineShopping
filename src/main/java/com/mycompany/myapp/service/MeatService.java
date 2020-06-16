package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Meat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Meat}.
 */
public interface MeatService {

    /**
     * Save a meat.
     *
     * @param meat the entity to save.
     * @return the persisted entity.
     */
    Meat save(Meat meat);

    /**
     * Get all the meats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Meat> findAll(Pageable pageable);


    /**
     * Get the "id" meat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Meat> findOne(Long id);

    /**
     * Delete the "id" meat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
