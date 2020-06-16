package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Vegetables;
import com.mycompany.myapp.repository.VegetablesRepository;
import com.mycompany.myapp.service.VegetablesService;

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
 * Integration tests for the {@link VegetablesResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VegetablesResourceIT {

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
    private VegetablesRepository vegetablesRepository;

    @Autowired
    private VegetablesService vegetablesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVegetablesMockMvc;

    private Vegetables vegetables;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vegetables createEntity(EntityManager em) {
        Vegetables vegetables = new Vegetables()
            .name(DEFAULT_NAME)
            .weight(DEFAULT_WEIGHT)
            .price(DEFAULT_PRICE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return vegetables;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vegetables createUpdatedEntity(EntityManager em) {
        Vegetables vegetables = new Vegetables()
            .name(UPDATED_NAME)
            .weight(UPDATED_WEIGHT)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return vegetables;
    }

    @BeforeEach
    public void initTest() {
        vegetables = createEntity(em);
    }

    @Test
    @Transactional
    public void createVegetables() throws Exception {
        int databaseSizeBeforeCreate = vegetablesRepository.findAll().size();
        // Create the Vegetables
        restVegetablesMockMvc.perform(post("/api/vegetables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vegetables)))
            .andExpect(status().isCreated());

        // Validate the Vegetables in the database
        List<Vegetables> vegetablesList = vegetablesRepository.findAll();
        assertThat(vegetablesList).hasSize(databaseSizeBeforeCreate + 1);
        Vegetables testVegetables = vegetablesList.get(vegetablesList.size() - 1);
        assertThat(testVegetables.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVegetables.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testVegetables.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testVegetables.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testVegetables.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createVegetablesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vegetablesRepository.findAll().size();

        // Create the Vegetables with an existing ID
        vegetables.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVegetablesMockMvc.perform(post("/api/vegetables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vegetables)))
            .andExpect(status().isBadRequest());

        // Validate the Vegetables in the database
        List<Vegetables> vegetablesList = vegetablesRepository.findAll();
        assertThat(vegetablesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVegetables() throws Exception {
        // Initialize the database
        vegetablesRepository.saveAndFlush(vegetables);

        // Get all the vegetablesList
        restVegetablesMockMvc.perform(get("/api/vegetables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vegetables.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getVegetables() throws Exception {
        // Initialize the database
        vegetablesRepository.saveAndFlush(vegetables);

        // Get the vegetables
        restVegetablesMockMvc.perform(get("/api/vegetables/{id}", vegetables.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vegetables.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }
    @Test
    @Transactional
    public void getNonExistingVegetables() throws Exception {
        // Get the vegetables
        restVegetablesMockMvc.perform(get("/api/vegetables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVegetables() throws Exception {
        // Initialize the database
        vegetablesService.save(vegetables);

        int databaseSizeBeforeUpdate = vegetablesRepository.findAll().size();

        // Update the vegetables
        Vegetables updatedVegetables = vegetablesRepository.findById(vegetables.getId()).get();
        // Disconnect from session so that the updates on updatedVegetables are not directly saved in db
        em.detach(updatedVegetables);
        updatedVegetables
            .name(UPDATED_NAME)
            .weight(UPDATED_WEIGHT)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restVegetablesMockMvc.perform(put("/api/vegetables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVegetables)))
            .andExpect(status().isOk());

        // Validate the Vegetables in the database
        List<Vegetables> vegetablesList = vegetablesRepository.findAll();
        assertThat(vegetablesList).hasSize(databaseSizeBeforeUpdate);
        Vegetables testVegetables = vegetablesList.get(vegetablesList.size() - 1);
        assertThat(testVegetables.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVegetables.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testVegetables.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testVegetables.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testVegetables.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingVegetables() throws Exception {
        int databaseSizeBeforeUpdate = vegetablesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVegetablesMockMvc.perform(put("/api/vegetables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vegetables)))
            .andExpect(status().isBadRequest());

        // Validate the Vegetables in the database
        List<Vegetables> vegetablesList = vegetablesRepository.findAll();
        assertThat(vegetablesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVegetables() throws Exception {
        // Initialize the database
        vegetablesService.save(vegetables);

        int databaseSizeBeforeDelete = vegetablesRepository.findAll().size();

        // Delete the vegetables
        restVegetablesMockMvc.perform(delete("/api/vegetables/{id}", vegetables.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vegetables> vegetablesList = vegetablesRepository.findAll();
        assertThat(vegetablesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
