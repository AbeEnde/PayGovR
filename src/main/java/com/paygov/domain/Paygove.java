package com.paygov.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Paygove.
 */
@Entity
@Table(name = "paygove")
public class Paygove implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
  //  @Pattern(regexp = "/^[0-9]+[A-Za-z0-9]+[@#$%^&*()!]/")//"^[a-zA-Z0-9]*$")
    @Column(name = "cik", length = 20, nullable = false, unique = true)
    private String cik;

    @Column(name = "ccc")
    private String ccc;

    @Column(name = "payment_amount")
    private Integer paymentAmount;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paygove id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCik() {
        return this.cik;
    }

    public Paygove cik(String cik) {
        this.setCik(cik);
        return this;
    }

    public void setCik(String cik) {
        this.cik = cik;
    }

    public String getCcc() {
        return this.ccc;
    }

    public Paygove ccc(String ccc) {
        this.setCcc(ccc);
        return this;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public Integer getPaymentAmount() {
        return this.paymentAmount;
    }

    public Paygove paymentAmount(Integer paymentAmount) {
        this.setPaymentAmount(paymentAmount);
        return this;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getName() {
        return this.name;
    }

    public Paygove name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Paygove email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Paygove phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paygove)) {
            return false;
        }
        return id != null && id.equals(((Paygove) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paygove{" +
            "id=" + getId() +
            ", cik='" + getCik() + "'" +
            ", ccc='" + getCcc() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
