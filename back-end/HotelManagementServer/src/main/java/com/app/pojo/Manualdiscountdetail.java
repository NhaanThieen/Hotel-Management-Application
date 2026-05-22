/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Nhaan
 */
@Entity
@Table(name = "manualdiscountdetail")
@NamedQueries({
    @NamedQuery(name = "Manualdiscountdetail.findAll", query = "SELECT m FROM Manualdiscountdetail m"),
    @NamedQuery(name = "Manualdiscountdetail.findByManualDiscountDetailId", query = "SELECT m FROM Manualdiscountdetail m WHERE m.manualDiscountDetailId = :manualDiscountDetailId"),
    @NamedQuery(name = "Manualdiscountdetail.findByAmountDiscount", query = "SELECT m FROM Manualdiscountdetail m WHERE m.amountDiscount = :amountDiscount")})
public class Manualdiscountdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "manualDiscountDetailId")
    private Integer manualDiscountDetailId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amountDiscount")
    private BigDecimal amountDiscount;
    @JoinColumn(name = "manualDiscountReasonId", referencedColumnName = "manualDiscountReasonId")
    @ManyToOne(optional = false)
    private Manualdiscountreason manualDiscountReasonId;
    @JoinColumn(name = "receiptId", referencedColumnName = "receiptId")
    @ManyToOne(optional = false)
    private Receipt receiptId;

    public Manualdiscountdetail() {
    }

    public Manualdiscountdetail(Integer manualDiscountDetailId) {
        this.manualDiscountDetailId = manualDiscountDetailId;
    }

    public Integer getManualDiscountDetailId() {
        return manualDiscountDetailId;
    }

    public void setManualDiscountDetailId(Integer manualDiscountDetailId) {
        this.manualDiscountDetailId = manualDiscountDetailId;
    }

    public BigDecimal getAmountDiscount() {
        return amountDiscount;
    }

    public void setAmountDiscount(BigDecimal amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    public Manualdiscountreason getManualDiscountReasonId() {
        return manualDiscountReasonId;
    }

    public void setManualDiscountReasonId(Manualdiscountreason manualDiscountReasonId) {
        this.manualDiscountReasonId = manualDiscountReasonId;
    }

    public Receipt getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Receipt receiptId) {
        this.receiptId = receiptId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manualDiscountDetailId != null ? manualDiscountDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manualdiscountdetail)) {
            return false;
        }
        Manualdiscountdetail other = (Manualdiscountdetail) object;
        if ((this.manualDiscountDetailId == null && other.manualDiscountDetailId != null) || (this.manualDiscountDetailId != null && !this.manualDiscountDetailId.equals(other.manualDiscountDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Manualdiscountdetail[ manualDiscountDetailId=" + manualDiscountDetailId + " ]";
    }
    
}
