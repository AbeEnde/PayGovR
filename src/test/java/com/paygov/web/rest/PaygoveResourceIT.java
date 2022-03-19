package com.paygov.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ingenico.connect.gateway.sdk.java.CommunicatorConfiguration;
import com.ingenico.connect.gateway.sdk.java.Factory;
import com.paygov.IntegrationTest;
import com.paygov.domain.AbstractTest;
import com.paygov.domain.Paygove;
import com.paygov.repository.PaygoveRepository;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import javax.print.DocFlavor.URL;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Integration tests for the {@link PaygoveResource} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaygoveResourceIT extends AbstractTest{

    private static final String DEFAULT_CIK = "AAAAAAAAAA";
    private static final String UPDATED_CIK = "BBBBBBBBBB";

    private static final String DEFAULT_CCC = "AAAAAAAAAA";
    private static final String UPDATED_CCC = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_AMOUNT = 1;
    private static final Integer UPDATED_PAYMENT_AMOUNT = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/paygoves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaygoveRepository paygoveRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaygoveMockMvc;

    private Paygove paygove;
    private Paygove amt;
    private  Paygove amount;



    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paygove createEntity(EntityManager em) {
        Paygove paygove = new Paygove()
            .cik(DEFAULT_CIK)
            .ccc(DEFAULT_CCC)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return paygove;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paygove createUpdatedEntity(EntityManager em) {
        Paygove paygove = new Paygove()
            .cik(UPDATED_CIK)
            .ccc(UPDATED_CCC)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
            return paygove;
    }
         public void initTest() {
        paygove = createEntity(em);
    }

//#####
/*
@Before
   public void setUp() {
      super.setUp();
   }
   @Test
 public <MvcResult> void getPostWithCustomHeaders() throws Exception {
      String uri = "/redirect";
      org.springframework.test.web.servlet.MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      CreateHostedCheckoutResponse[] response = super.mapFromJson(content, CreateHostedCheckoutResponse[].class);
      assertTrue(response.length > 0);
      System.out.println("##############"+response);
      System.out.println("xxxxxxxxxxxxx"+ status);
   }

 @Test
   public void amount() throws Exception {
      String uri = "/amt";
       Paygove payG = new Paygove();
     
      payG.setCik("123w@");
      payG.setCcc("1234567");
      payG.paymentAmount(50);
      payG.setName("abcd");
      payG.setEmail("abcd@gmail.com");
      payG.setPhone("1234567890");
    /*  String inputJson = super.mapToJson(payG);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertEquals(content, "am called"); */

   /*   restPaygoveMockMvc
            .perform(
                post(uri)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payG))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
            // .andReturn();
          //  String content = restPaygoveMockMvc.getResponse().getContentAsString();
      //assertEquals(content, "am called");
   }

*/
//#####

    @Test
    @Transactional
    void createPaygove() throws Exception {
        int databaseSizeBeforeCreate = paygoveRepository.findAll().size();
        // Create the Paygove
        restPaygoveMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paygove))
            )
            .andExpect(status().isCreated());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeCreate + 1);
        Paygove testPaygove = paygoveList.get(paygoveList.size() - 1);
        assertThat(testPaygove.getCik()).isEqualTo(DEFAULT_CIK);
        assertThat(testPaygove.getCcc()).isEqualTo(DEFAULT_CCC);
        assertThat(testPaygove.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testPaygove.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaygove.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPaygove.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void createPaygoveWithExistingId() throws Exception {
        // Create the Paygove with an existing ID
        paygove.setId(1L);

        int databaseSizeBeforeCreate = paygoveRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaygoveMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paygove))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCikIsRequired() throws Exception {
        int databaseSizeBeforeTest = paygoveRepository.findAll().size();
        // set the field null
        paygove.setCik(null);

        // Create the Paygove, which fails.

        restPaygoveMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paygove))
            )
            .andExpect(status().isBadRequest());

        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaygoves() throws Exception {
        // Initialize the database
        paygoveRepository.saveAndFlush(paygove);

        // Get all the paygoveList
        restPaygoveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paygove.getId().intValue())))
            .andExpect(jsonPath("$.[*].cik").value(hasItem(DEFAULT_CIK)))
            .andExpect(jsonPath("$.[*].ccc").value(hasItem(DEFAULT_CCC)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getPaygove() throws Exception {
        // Initialize the database
        paygoveRepository.saveAndFlush(paygove);

        // Get the paygove
        restPaygoveMockMvc
            .perform(get(ENTITY_API_URL_ID, paygove.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paygove.getId().intValue()))
            .andExpect(jsonPath("$.cik").value(DEFAULT_CIK))
            .andExpect(jsonPath("$.ccc").value(DEFAULT_CCC))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingPaygove() throws Exception {
        // Get the paygove
        restPaygoveMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaygove() throws Exception {
        // Initialize the database
        paygoveRepository.saveAndFlush(paygove);

        int databaseSizeBeforeUpdate = paygoveRepository.findAll().size();

        // Update the paygove
        Paygove updatedPaygove = paygoveRepository.findById(paygove.getId()).get();
        // Disconnect from session so that the updates on updatedPaygove are not directly saved in db
        em.detach(updatedPaygove);
        updatedPaygove
            .cik(UPDATED_CIK)
            .ccc(UPDATED_CCC)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restPaygoveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaygove.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaygove))
            )
            .andExpect(status().isOk());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeUpdate);
        Paygove testPaygove = paygoveList.get(paygoveList.size() - 1);
        assertThat(testPaygove.getCik()).isEqualTo(UPDATED_CIK);
        assertThat(testPaygove.getCcc()).isEqualTo(UPDATED_CCC);
        assertThat(testPaygove.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaygove.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaygove.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaygove.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingPaygove() throws Exception {
        int databaseSizeBeforeUpdate = paygoveRepository.findAll().size();
        paygove.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaygoveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paygove.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paygove))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaygove() throws Exception {
        int databaseSizeBeforeUpdate = paygoveRepository.findAll().size();
        paygove.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaygoveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paygove))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaygove() throws Exception {
        int databaseSizeBeforeUpdate = paygoveRepository.findAll().size();
        paygove.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaygoveMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paygove))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaygoveWithPatch() throws Exception {
        // Initialize the database
        paygoveRepository.saveAndFlush(paygove);

        int databaseSizeBeforeUpdate = paygoveRepository.findAll().size();

        // Update the paygove using partial update
        Paygove partialUpdatedPaygove = new Paygove();
        partialUpdatedPaygove.setId(paygove.getId());

        partialUpdatedPaygove.cik(UPDATED_CIK).name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE);

        restPaygoveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaygove.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaygove))
            )
            .andExpect(status().isOk());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeUpdate);
        Paygove testPaygove = paygoveList.get(paygoveList.size() - 1);
        assertThat(testPaygove.getCik()).isEqualTo(UPDATED_CIK);
        assertThat(testPaygove.getCcc()).isEqualTo(DEFAULT_CCC);
        assertThat(testPaygove.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testPaygove.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaygove.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaygove.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void fullUpdatePaygoveWithPatch() throws Exception {
        // Initialize the database
        paygoveRepository.saveAndFlush(paygove);

        int databaseSizeBeforeUpdate = paygoveRepository.findAll().size();

        // Update the paygove using partial update
        Paygove partialUpdatedPaygove = new Paygove();
        partialUpdatedPaygove.setId(paygove.getId());

        partialUpdatedPaygove
            .cik(UPDATED_CIK)
            .ccc(UPDATED_CCC)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restPaygoveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaygove.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaygove))
            )
            .andExpect(status().isOk());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeUpdate);
        Paygove testPaygove = paygoveList.get(paygoveList.size() - 1);
        assertThat(testPaygove.getCik()).isEqualTo(UPDATED_CIK);
        assertThat(testPaygove.getCcc()).isEqualTo(UPDATED_CCC);
        assertThat(testPaygove.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaygove.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaygove.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaygove.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingPaygove() throws Exception {
        int databaseSizeBeforeUpdate = paygoveRepository.findAll().size();
        paygove.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaygoveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paygove.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paygove))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaygove() throws Exception {
        int databaseSizeBeforeUpdate = paygoveRepository.findAll().size();
        paygove.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaygoveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paygove))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaygove() throws Exception {
        int databaseSizeBeforeUpdate = paygoveRepository.findAll().size();
        paygove.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaygoveMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paygove))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paygove in the database
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaygove() throws Exception {
        // Initialize the database
        paygoveRepository.saveAndFlush(paygove);

        int databaseSizeBeforeDelete = paygoveRepository.findAll().size();

        // Delete the paygove
        restPaygoveMockMvc
            .perform(delete(ENTITY_API_URL_ID, paygove.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paygove> paygoveList = paygoveRepository.findAll();
        assertThat(paygoveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
