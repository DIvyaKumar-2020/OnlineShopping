package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Ration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Ration}.
 */
public interface RationService {

    /**
     * Save a ration.
     *
     * @param ration the entity to save.
     * @return the persisted entity.
     */
    Ration save(Ration ration);

    /**
     * Get all the rations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Ration> findAll(Pageable pageable);


    /**
     * Get the "id" ration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ration> findOne(Long id);

    /**
     * Delete the "id" ration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
