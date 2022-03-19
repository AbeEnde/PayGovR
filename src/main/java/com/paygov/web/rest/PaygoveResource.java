package com.paygov.web.rest;

import com.ingenico.connect.gateway.sdk.java.Client;
import com.ingenico.connect.gateway.sdk.java.CommunicatorConfiguration;
import com.ingenico.connect.gateway.sdk.java.Factory;
import com.ingenico.connect.gateway.sdk.java.domain.definitions.Address;
import com.ingenico.connect.gateway.sdk.java.domain.definitions.AmountOfMoney;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.CreateHostedCheckoutRequest;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.CreateHostedCheckoutResponse;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.definitions.HostedCheckoutSpecificInput;
import com.ingenico.connect.gateway.sdk.java.domain.payment.definitions.Customer;
import com.ingenico.connect.gateway.sdk.java.domain.payment.definitions.Order;
import com.paygov.domain.Paygove;
import com.paygov.repository.PaygoveRepository;
import com.paygov.service.PaygoveService;
import com.paygov.web.rest.errors.BadRequestAlertException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.paygov.domain.Paygove}.
 */

@RestController
@RequestMapping("/api")
public class PaygoveResource {

    private final Logger log = LoggerFactory.getLogger(PaygoveResource.class);

    private static final String ENTITY_NAME = "paygove";
     private  Paygove amount;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaygoveService paygoveService;

    private final PaygoveRepository paygoveRepository;

    public PaygoveResource(PaygoveService paygoveService, PaygoveRepository paygoveRepository) {
        this.paygoveService = paygoveService;
        this.paygoveRepository = paygoveRepository;
    }

    
    @CrossOrigin(origins = "http://localhost:9000")
    @PostMapping("/amt")
    public @ResponseBody String amount(@Valid @RequestBody Paygove amt){
      this.amount = amt;
      System.out.println(amt);
return "am called";
    }
        
    /**
     * {@code POST  /paygoves} : Create a new paygove.
     *
     * @param paygove the paygove to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paygove, or with status {@code 400 (Bad Request)} if the paygove has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paygoves")
    public ResponseEntity<Paygove> createPaygove(@Valid @RequestBody Paygove paygove) throws URISyntaxException {
        log.debug("REST request to save Paygove : {}", paygove);
        if (paygove.getId() != null) {
            throw new BadRequestAlertException("A new paygove cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paygove result = paygoveService.save(paygove);
        return ResponseEntity
            .created(new URI("/api/paygoves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paygoves/:id} : Updates an existing paygove.
     *
     * @param id the id of the paygove to save.
     * @param paygove the paygove to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paygove,
     * or with status {@code 400 (Bad Request)} if the paygove is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paygove couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paygoves/{id}")
    public ResponseEntity<Paygove> updatePaygove(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Paygove paygove
    ) throws URISyntaxException {
        log.debug("REST request to update Paygove : {}, {}", id, paygove);
        if (paygove.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paygove.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paygoveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Paygove result = paygoveService.save(paygove);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paygove.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /paygoves/:id} : Partial updates given fields of an existing paygove, field will ignore if it is null
     *
     * @param id the id of the paygove to save.
     * @param paygove the paygove to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paygove,
     * or with status {@code 400 (Bad Request)} if the paygove is not valid,
     * or with status {@code 404 (Not Found)} if the paygove is not found,
     * or with status {@code 500 (Internal Server Error)} if the paygove couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/paygoves/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Paygove> partialUpdatePaygove(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Paygove paygove
    ) throws URISyntaxException {
        log.debug("REST request to partial update Paygove partially : {}, {}", id, paygove);
        if (paygove.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paygove.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paygoveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Paygove> result = paygoveService.partialUpdate(paygove);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paygove.getId().toString())
        );
    }

    
    @GetMapping("/redirect")
    public CreateHostedCheckoutResponse getPostWithCustomHeaders() throws IOException, URISyntaxException {
        // create request
        Client client = getClient();
        //	 URL  propertiesUrl = new URL("C:\\Users\\Dell\\Desktop\\prop.properties");
        //	 com.ingenico.connect.gateway.sdk.java.Client client = Factory.createClient(propertiesUrl.toURI(), "f4d9535aa8d07525", "cQQ5tT8t6xg9aZ+V6GlE/8uoqEanCykGaKB3ZTrLMsY=");

        try {
            HostedCheckoutSpecificInput hostedCheckoutSpecificInput = new HostedCheckoutSpecificInput();
            hostedCheckoutSpecificInput.setLocale("en_GB");
            hostedCheckoutSpecificInput.setVariant("100");
            hostedCheckoutSpecificInput.setReturnUrl("http://localhost:9000/confrm");
            hostedCheckoutSpecificInput.setShowResultPage(false);
            AmountOfMoney amountOfMoney = new AmountOfMoney();
            amountOfMoney.setAmount( Long.valueOf(this.amount.getPaymentAmount()));
            amountOfMoney.setCurrencyCode("USD");

            Address billingAddress = new Address();
            billingAddress.setCountryCode("US");

            Customer customer = new Customer();
            customer.setBillingAddress(billingAddress);
            customer.setMerchantCustomerId("1378-1234");

            Order order = new Order();
            order.setAmountOfMoney(amountOfMoney);
            order.setCustomer(customer);

            CreateHostedCheckoutRequest body = new CreateHostedCheckoutRequest();
            body.setHostedCheckoutSpecificInput(hostedCheckoutSpecificInput);
            body.setOrder(order);

            CreateHostedCheckoutResponse response = client.merchant("1378").hostedcheckouts().create(body);
            System.out.println(response.getPartialRedirectUrl());
            System.out.println("i am called");
            return response;
        } finally {
            client.close();
        }
    }
   /*
    @Value("${spring.application.apiKeyId}")
    String apiKeyId;

    @Value("${spring.application.secretApiKey}")
    String secretApiKey; */

    private com.ingenico.connect.gateway.sdk.java.Client getClient() throws URISyntaxException {
        String apiKeyId = System.getProperty("apiKeyId", "f4d9535aa8d07525");
        String secretApiKey = System.getProperty("secretApiKey","cQQ5tT8t6xg9aZ+V6GlE/8uoqEanCykGaKB3ZTrLMsY=");

        URL propertiesUrl = getClass().getResource("/example-configuration.properties");
        CommunicatorConfiguration configuration = Factory.createConfiguration(propertiesUrl.toURI(), apiKeyId, secretApiKey);
        return Factory.createClient(configuration);
    }
    
    
    /**
     * {@code GET  /paygoves} : get all the paygoves.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paygoves in body.
     */
    @GetMapping("/paygoves")
    public List<Paygove> getAllPaygoves() {
        log.debug("REST request to get all Paygoves");
        return paygoveService.findAll();
    }

    /**
     * {@code GET  /paygoves/:id} : get the "id" paygove.
     *
     * @param id the id of the paygove to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paygove, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paygoves/{id}")
    public ResponseEntity<Paygove> getPaygove(@PathVariable Long id) {
        log.debug("REST request to get Paygove : {}", id);
        Optional<Paygove> paygove = paygoveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paygove);
    }

    /**
     * {@code DELETE  /paygoves/:id} : delete the "id" paygove.
     *
     * @param id the id of the paygove to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paygoves/{id}")
    public ResponseEntity<Void> deletePaygove(@PathVariable Long id) {
        log.debug("REST request to delete Paygove : {}", id);
        paygoveService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
