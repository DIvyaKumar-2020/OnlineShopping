package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Meat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Meat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeatRepository extends JpaRepository<Meat, Long> {
}
