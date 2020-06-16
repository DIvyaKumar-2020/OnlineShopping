package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ration;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Ration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RationRepository extends JpaRepository<Ration, Long> {
}
