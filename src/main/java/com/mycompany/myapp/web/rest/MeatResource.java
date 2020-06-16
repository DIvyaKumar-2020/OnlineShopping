package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Meat;
import com.mycompany.myapp.service.MeatService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Meat}.
 */
@RestController
@RequestMapping("/api")
public class MeatResource {

    private final Logger log = LoggerFactory.getLogger(MeatResource.class);

    private static final String ENTITY_NAME = "meat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeatService meatService;

    public MeatResource(MeatService meatService) {
        this.meatService = meatService;
    }

    /**
     * {@code POST  /meats} : Create a new meat.
     *
     * @param meat the meat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meat, or with status {@code 400 (Bad Request)} if the meat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meats")
    public ResponseEntity<Meat> createMeat(@RequestBody Meat meat) throws URISyntaxException {
        log.debug("REST request to save Meat : {}", meat);
        if (meat.getId() != null) {
            throw new BadRequestAlertException("A new meat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Meat result = meatService.save(meat);
        return ResponseEntity.created(new URI("/api/meats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meats} : Updates an existing meat.
     *
     * @param meat the meat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meat,
     * or with status {@code 400 (Bad Request)} if the meat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meats")
    public ResponseEntity<Meat> updateMeat(@RequestBody Meat meat) throws URISyntaxException {
        log.debug("REST request to update Meat : {}", meat);
        if (meat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Meat result = meatService.save(meat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meats} : get all the meats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meats in body.
     */
    @GetMapping("/meats")
    public ResponseEntity<List<Meat>> getAllMeats(Pageable pageable) {
        log.debug("REST request to get a page of Meats");
        Page<Meat> page = meatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /meats/:id} : get the "id" meat.
     *
     * @param id the id of the meat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meats/{id}")
    public ResponseEntity<Meat> getMeat(@PathVariable Long id) {
        log.debug("REST request to get Meat : {}", id);
        Optional<Meat> meat = meatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meat);
    }

    /**
     * {@code DELETE  /meats/:id} : delete the "id" meat.
     *
     * @param id the id of the meat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meats/{id}")
    public ResponseEntity<Void> deleteMeat(@PathVariable Long id) {
        log.debug("REST request to delete Meat : {}", id);
        meatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
