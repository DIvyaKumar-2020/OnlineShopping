package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Ration;
import com.mycompany.myapp.repository.RationRepository;
import com.mycompany.myapp.service.RationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RationResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private RationRepository rationRepository;

    @Autowired
    private RationService rationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRationMockMvc;

    private Ration ration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ration createEntity(EntityManager em) {
        Ration ration = new Ration()
            .name(DEFAULT_NAME)
            .weight(DEFAULT_WEIGHT)
            .price(DEFAULT_PRICE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return ration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ration createUpdatedEntity(EntityManager em) {
        Ration ration = new Ration()
            .name(UPDATED_NAME)
            .weight(UPDATED_WEIGHT)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return ration;
    }

    @BeforeEach
    public void initTest() {
        ration = createEntity(em);
    }

    @Test
    @Transactional
    public void createRation() throws Exception {
        int databaseSizeBeforeCreate = rationRepository.findAll().size();
        // Create the Ration
        restRationMockMvc.perform(post("/api/rations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ration)))
            .andExpect(status().isCreated());

        // Validate the Ration in the database
        List<Ration> rationList = rationRepository.findAll();
        assertThat(rationList).hasSize(databaseSizeBeforeCreate + 1);
        Ration testRation = rationList.get(rationList.size() - 1);
        assertThat(testRation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRation.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testRation.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testRation.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testRation.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createRationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rationRepository.findAll().size();

        // Create the Ration with an existing ID
        ration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRationMockMvc.perform(post("/api/rations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ration)))
            .andExpect(status().isBadRequest());

        // Validate the Ration in the database
        List<Ration> rationList = rationRepository.findAll();
        assertThat(rationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRations() throws Exception {
        // Initialize the database
        rationRepository.saveAndFlush(ration);

        // Get all the rationList
        restRationMockMvc.perform(get("/api/rations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getRation() throws Exception {
        // Initialize the database
        rationRepository.saveAndFlush(ration);

        // Get the ration
        restRationMockMvc.perform(get("/api/rations/{id}", ration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ration.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }
    @Test
    @Transactional
    public void getNonExistingRation() throws Exception {
        // Get the ration
        restRationMockMvc.perform(get("/api/rations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRation() throws Exception {
        // Initialize the database
        rationService.save(ration);

        int databaseSizeBeforeUpdate = rationRepository.findAll().size();

        // Update the ration
        Ration updatedRation = rationRepository.findById(ration.getId()).get();
        // Disconnect from session so that the updates on updatedRation are not directly saved in db
        em.detach(updatedRation);
        updatedRation
            .name(UPDATED_NAME)
            .weight(UPDATED_WEIGHT)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restRationMockMvc.perform(put("/api/rations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRation)))
            .andExpect(status().isOk());

        // Validate the Ration in the database
        List<Ration> rationList = rationRepository.findAll();
        assertThat(rationList).hasSize(databaseSizeBeforeUpdate);
        Ration testRation = rationList.get(rationList.size() - 1);
        assertThat(testRation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRation.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testRation.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRation.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testRation.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingRation() throws Exception {
        int databaseSizeBeforeUpdate = rationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRationMockMvc.perform(put("/api/rations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ration)))
            .andExpect(status().isBadRequest());

        // Validate the Ration in the database
        List<Ration> rationList = rationRepository.findAll();
        assertThat(rationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRation() throws Exception {
        // Initialize the database
        rationService.save(ration);

        int databaseSizeBeforeDelete = rationRepository.findAll().size();

        // Delete the ration
        restRationMockMvc.perform(delete("/api/rations/{id}", ration.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ration> rationList = rationRepository.findAll();
        assertThat(rationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
