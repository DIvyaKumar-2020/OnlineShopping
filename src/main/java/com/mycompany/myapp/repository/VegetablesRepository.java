package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vegetables;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vegetables entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VegetablesRepository extends JpaRepository<Vegetables, Long> {
}
