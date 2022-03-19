package com.paygov.web;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.dockerjava.api.command.CreateConfigResponse;
import com.ingenico.connect.gateway.sdk.java.CommunicatorConfiguration;
import com.ingenico.connect.gateway.sdk.java.Factory;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.CreateHostedCheckoutResponse;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser

public class controlerTest extends AbstractTest{
    private static final String DEFAULT_CIK = "123@";
    private static final String UPDATED_CIK = "BBBBBBBBBB";

    private static final String DEFAULT_CCC = "123456";
    private static final String UPDATED_CCC = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_AMOUNT = 50;
    private static final Integer UPDATED_PAYMENT_AMOUNT = 2;

    private static final String DEFAULT_NAME = "bloom";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "bloom@gmail.com";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "0977777777";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

     private static final String ENTITY_API_URL = "/api/paygoves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String amtUri = "/api/amt";
    private static final String redirectUri = "/api/redirect";

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
@Before
public void setUp() {
   super.setUp();
  // paygove = createEntity(em);
  paygove = createEntity(em);
}

@Test
public  void getPostWithCustomHeaders() throws Exception {
    // String uri = "/redirect";
    /* org.springframework.test.web.servlet.MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(redirectUri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     
     int status = mvcResult.getResponse().getStatus();
     assertEquals(200, status);
     String content = mvcResult.getResponse().getContentAsString();
     CreateHostedCheckoutResponse[] response = super.mapFromJson(content, CreateHostedCheckoutResponse[].class);
     assertTrue(response.length > 0);
     System.out.println("##############"+response);
     System.out.println("xxxxxxxxxxxxx"+ status); */

     restPaygoveMockMvc
         .perform(
             get(redirectUri)
                 .with(csrf())
                 .contentType(MediaType.APPLICATION_JSON)
              //   .content(super.mapToJson(payG))
         )
         .andExpect(status().isOk())
         .andReturn();
  }

@Test
public void amount() throws Exception {
  // String uri = "/amt";
    Paygove payG = new Paygove();
  
   payG.setCik("123w@");
   payG.setCcc("1234567");
   payG.paymentAmount(50);
   payG.setName("abcd");
   payG.setEmail("abcd@gmail.com");
   payG.setPhone("1234567890");
 /*  String inputJson = super.mapToJson(payG);
   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(amtUri)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(inputJson)).andReturn();
   
   int status = mvcResult.getResponse().getStatus();
   assertEquals(200, status);
   String content = mvcResult.getResponse().getContentAsString();
   assertEquals(content, "am called"); 
*/
  restPaygoveMockMvc
         .perform(
             post(amtUri)
                 .with(csrf())
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(super.mapToJson(payG))
         )
         .andExpect(status().isOk());
        // .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
         // .andReturn();
       //  String content = restPaygoveMockMvc.getResponse().getContentAsString();
  // assertEquals(content, "am called"); */
}


//#####

@Test
@Transactional
void createPaygove() throws Exception {
    int databaseSizeBeforeCreate = paygoveRepository.findAll().size();
    // Create the Paygove
    Paygove payment = new Paygove();
    payment.cik("123@")
    .ccc("123456")
    .paymentAmount(50)
    .name("bloom")
    .email("bloom@gmail.com")
    .phone("0977777777");

    restPaygoveMockMvc
        .perform(
            post(ENTITY_API_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(payment))
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
}
