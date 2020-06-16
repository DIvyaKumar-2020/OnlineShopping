package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Meat;
import com.mycompany.myapp.repository.MeatRepository;
import com.mycompany.myapp.service.MeatService;

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
 * Integration tests for the {@link MeatResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MeatResourceIT {

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
    private MeatRepository meatRepository;

    @Autowired
    private MeatService meatService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMeatMockMvc;

    private Meat meat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meat createEntity(EntityManager em) {
        Meat meat = new Meat()
            .name(DEFAULT_NAME)
            .weight(DEFAULT_WEIGHT)
            .price(DEFAULT_PRICE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return meat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meat createUpdatedEntity(EntityManager em) {
        Meat meat = new Meat()
            .name(UPDATED_NAME)
            .weight(UPDATED_WEIGHT)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return meat;
    }

    @BeforeEach
    public void initTest() {
        meat = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeat() throws Exception {
        int databaseSizeBeforeCreate = meatRepository.findAll().size();
        // Create the Meat
        restMeatMockMvc.perform(post("/api/meats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(meat)))
            .andExpect(status().isCreated());

        // Validate the Meat in the database
        List<Meat> meatList = meatRepository.findAll();
        assertThat(meatList).hasSize(databaseSizeBeforeCreate + 1);
        Meat testMeat = meatList.get(meatList.size() - 1);
        assertThat(testMeat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMeat.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testMeat.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMeat.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testMeat.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMeatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meatRepository.findAll().size();

        // Create the Meat with an existing ID
        meat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeatMockMvc.perform(post("/api/meats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(meat)))
            .andExpect(status().isBadRequest());

        // Validate the Meat in the database
        List<Meat> meatList = meatRepository.findAll();
        assertThat(meatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMeats() throws Exception {
        // Initialize the database
        meatRepository.saveAndFlush(meat);

        // Get all the meatList
        restMeatMockMvc.perform(get("/api/meats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getMeat() throws Exception {
        // Initialize the database
        meatRepository.saveAndFlush(meat);

        // Get the meat
        restMeatMockMvc.perform(get("/api/meats/{id}", meat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }
    @Test
    @Transactional
    public void getNonExistingMeat() throws Exception {
        // Get the meat
        restMeatMockMvc.perform(get("/api/meats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeat() throws Exception {
        // Initialize the database
        meatService.save(meat);

        int databaseSizeBeforeUpdate = meatRepository.findAll().size();

        // Update the meat
        Meat updatedMeat = meatRepository.findById(meat.getId()).get();
        // Disconnect from session so that the updates on updatedMeat are not directly saved in db
        em.detach(updatedMeat);
        updatedMeat
            .name(UPDATED_NAME)
            .weight(UPDATED_WEIGHT)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restMeatMockMvc.perform(put("/api/meats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeat)))
            .andExpect(status().isOk());

        // Validate the Meat in the database
        List<Meat> meatList = meatRepository.findAll();
        assertThat(meatList).hasSize(databaseSizeBeforeUpdate);
        Meat testMeat = meatList.get(meatList.size() - 1);
        assertThat(testMeat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMeat.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testMeat.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMeat.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testMeat.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMeat() throws Exception {
        int databaseSizeBeforeUpdate = meatRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeatMockMvc.perform(put("/api/meats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(meat)))
            .andExpect(status().isBadRequest());

        // Validate the Meat in the database
        List<Meat> meatList = meatRepository.findAll();
        assertThat(meatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeat() throws Exception {
        // Initialize the database
        meatService.save(meat);

        int databaseSizeBeforeDelete = meatRepository.findAll().size();

        // Delete the meat
        restMeatMockMvc.perform(delete("/api/meats/{id}", meat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Meat> meatList = meatRepository.findAll();
        assertThat(meatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
