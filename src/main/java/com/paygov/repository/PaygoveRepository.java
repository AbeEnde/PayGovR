package com.paygov.repository;

import com.paygov.domain.Paygove;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Paygove entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaygoveRepository extends JpaRepository<Paygove, Long> {}
