package com.paygov.service;

import com.paygov.domain.Paygove;
import com.paygov.repository.PaygoveRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Paygove}.
 */
@Service
@Transactional
public class PaygoveService {

    private final Logger log = LoggerFactory.getLogger(PaygoveService.class);

    private final PaygoveRepository paygoveRepository;

    public PaygoveService(PaygoveRepository paygoveRepository) {
        this.paygoveRepository = paygoveRepository;
    }

    /**
     * Save a paygove.
     *
     * @param paygove the entity to save.
     * @return the persisted entity.
     */
    public Paygove save(Paygove paygove) {
        log.debug("Request to save Paygove : {}", paygove);
        return paygoveRepository.save(paygove);
    }

    /**
     * Partially update a paygove.
     *
     * @param paygove the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Paygove> partialUpdate(Paygove paygove) {
        log.debug("Request to partially update Paygove : {}", paygove);

        return paygoveRepository
            .findById(paygove.getId())
            .map(existingPaygove -> {
                if (paygove.getCik() != null) {
                    existingPaygove.setCik(paygove.getCik());
                }
                if (paygove.getCcc() != null) {
                    existingPaygove.setCcc(paygove.getCcc());
                }
                if (paygove.getPaymentAmount() != null) {
                    existingPaygove.setPaymentAmount(paygove.getPaymentAmount());
                }
                if (paygove.getName() != null) {
                    existingPaygove.setName(paygove.getName());
                }
                if (paygove.getEmail() != null) {
                    existingPaygove.setEmail(paygove.getEmail());
                }
                if (paygove.getPhone() != null) {
                    existingPaygove.setPhone(paygove.getPhone());
                }

                return existingPaygove;
            })
            .map(paygoveRepository::save);
    }

    /**
     * Get all the paygoves.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Paygove> findAll() {
        log.debug("Request to get all Paygoves");
        return paygoveRepository.findAll();
    }

    /**
     * Get one paygove by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Paygove> findOne(Long id) {
        log.debug("Request to get Paygove : {}", id);
        return paygoveRepository.findById(id);
    }

    /**
     * Delete the paygove by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Paygove : {}", id);
        paygoveRepository.deleteById(id);
    }
}
