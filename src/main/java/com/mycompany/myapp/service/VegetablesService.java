package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Vegetables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Vegetables}.
 */
public interface VegetablesService {

    /**
     * Save a vegetables.
     *
     * @param vegetables the entity to save.
     * @return the persisted entity.
     */
    Vegetables save(Vegetables vegetables);

    /**
     * Get all the vegetables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Vegetables> findAll(Pageable pageable);


    /**
     * Get the "id" vegetables.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Vegetables> findOne(Long id);

    /**
     * Delete the "id" vegetables.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
