package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Vegetables;
import com.mycompany.myapp.service.VegetablesService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Vegetables}.
 */
@RestController
@RequestMapping("/api")
public class VegetablesResource {

    private final Logger log = LoggerFactory.getLogger(VegetablesResource.class);

    private static final String ENTITY_NAME = "vegetables";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VegetablesService vegetablesService;

    public VegetablesResource(VegetablesService vegetablesService) {
        this.vegetablesService = vegetablesService;
    }

    /**
     * {@code POST  /vegetables} : Create a new vegetables.
     *
     * @param vegetables the vegetables to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vegetables, or with status {@code 400 (Bad Request)} if the vegetables has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vegetables")
    public ResponseEntity<Vegetables> createVegetables(@RequestBody Vegetables vegetables) throws URISyntaxException {
        log.debug("REST request to save Vegetables : {}", vegetables);
        if (vegetables.getId() != null) {
            throw new BadRequestAlertException("A new vegetables cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vegetables result = vegetablesService.save(vegetables);
        return ResponseEntity.created(new URI("/api/vegetables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vegetables} : Updates an existing vegetables.
     *
     * @param vegetables the vegetables to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vegetables,
     * or with status {@code 400 (Bad Request)} if the vegetables is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vegetables couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vegetables")
    public ResponseEntity<Vegetables> updateVegetables(@RequestBody Vegetables vegetables) throws URISyntaxException {
        log.debug("REST request to update Vegetables : {}", vegetables);
        if (vegetables.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vegetables result = vegetablesService.save(vegetables);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vegetables.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vegetables} : get all the vegetables.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vegetables in body.
     */
    @GetMapping("/vegetables")
    public ResponseEntity<List<Vegetables>> getAllVegetables(Pageable pageable) {
        log.debug("REST request to get a page of Vegetables");
        Page<Vegetables> page = vegetablesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vegetables/:id} : get the "id" vegetables.
     *
     * @param id the id of the vegetables to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vegetables, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vegetables/{id}")
    public ResponseEntity<Vegetables> getVegetables(@PathVariable Long id) {
        log.debug("REST request to get Vegetables : {}", id);
        Optional<Vegetables> vegetables = vegetablesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vegetables);
    }

    /**
     * {@code DELETE  /vegetables/:id} : delete the "id" vegetables.
     *
     * @param id the id of the vegetables to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vegetables/{id}")
    public ResponseEntity<Void> deleteVegetables(@PathVariable Long id) {
        log.debug("REST request to delete Vegetables : {}", id);
        vegetablesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
