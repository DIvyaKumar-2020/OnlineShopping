package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Ration;
import com.mycompany.myapp.service.RationService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Ration}.
 */
@RestController
@RequestMapping("/api")
public class RationResource {

    private final Logger log = LoggerFactory.getLogger(RationResource.class);

    private static final String ENTITY_NAME = "ration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RationService rationService;

    public RationResource(RationService rationService) {
        this.rationService = rationService;
    }

    /**
     * {@code POST  /rations} : Create a new ration.
     *
     * @param ration the ration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ration, or with status {@code 400 (Bad Request)} if the ration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rations")
    public ResponseEntity<Ration> createRation(@RequestBody Ration ration) throws URISyntaxException {
        log.debug("REST request to save Ration : {}", ration);
        if (ration.getId() != null) {
            throw new BadRequestAlertException("A new ration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ration result = rationService.save(ration);
        return ResponseEntity.created(new URI("/api/rations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rations} : Updates an existing ration.
     *
     * @param ration the ration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ration,
     * or with status {@code 400 (Bad Request)} if the ration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rations")
    public ResponseEntity<Ration> updateRation(@RequestBody Ration ration) throws URISyntaxException {
        log.debug("REST request to update Ration : {}", ration);
        if (ration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ration result = rationService.save(ration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rations} : get all the rations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rations in body.
     */
    @GetMapping("/rations")
    public ResponseEntity<List<Ration>> getAllRations(Pageable pageable) {
        log.debug("REST request to get a page of Rations");
        Page<Ration> page = rationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rations/:id} : get the "id" ration.
     *
     * @param id the id of the ration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rations/{id}")
    public ResponseEntity<Ration> getRation(@PathVariable Long id) {
        log.debug("REST request to get Ration : {}", id);
        Optional<Ration> ration = rationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ration);
    }

    /**
     * {@code DELETE  /rations/:id} : delete the "id" ration.
     *
     * @param id the id of the ration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rations/{id}")
    public ResponseEntity<Void> deleteRation(@PathVariable Long id) {
        log.debug("REST request to delete Ration : {}", id);
        rationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
