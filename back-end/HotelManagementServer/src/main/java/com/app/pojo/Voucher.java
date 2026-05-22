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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Nhaan
 */
@Entity
@Table(name = "voucher")
@NamedQueries({
    @NamedQuery(name = "Voucher.findAll", query = "SELECT v FROM Voucher v"),
    @NamedQuery(name = "Voucher.findByVoucherId", query = "SELECT v FROM Voucher v WHERE v.voucherId = :voucherId"),
    @NamedQuery(name = "Voucher.findByName", query = "SELECT v FROM Voucher v WHERE v.name = :name"),
    @NamedQuery(name = "Voucher.findByQuantity", query = "SELECT v FROM Voucher v WHERE v.quantity = :quantity"),
    @NamedQuery(name = "Voucher.findByPercentDiscount", query = "SELECT v FROM Voucher v WHERE v.percentDiscount = :percentDiscount"),
    @NamedQuery(name = "Voucher.findByMaxDiscount", query = "SELECT v FROM Voucher v WHERE v.maxDiscount = :maxDiscount"),
    @NamedQuery(name = "Voucher.findByMinRequire", query = "SELECT v FROM Voucher v WHERE v.minRequire = :minRequire"),
    @NamedQuery(name = "Voucher.findByIsActive", query = "SELECT v FROM Voucher v WHERE v.isActive = :isActive"),
    @NamedQuery(name = "Voucher.findByIsDeleted", query = "SELECT v FROM Voucher v WHERE v.isDeleted = :isDeleted")})
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "voucherId")
    private Integer voucherId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "percentDiscount")
    private BigDecimal percentDiscount;
    @Column(name = "maxDiscount")
    private BigDecimal maxDiscount;
    @Column(name = "minRequire")
    private BigDecimal minRequire;
    @Column(name = "isActive")
    private Short isActive;
    @Column(name = "isDeleted")
    private Boolean isDeleted = false;
    @OneToMany(mappedBy = "voucherId")
    private List<Roombooking> roombookingList;

    public Voucher() {
    }

    public Voucher(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Voucher(Integer voucherId, String name, int quantity) {
        this.voucherId = voucherId;
        this.name = name;
        this.quantity = quantity;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(BigDecimal percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public BigDecimal getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(BigDecimal maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public BigDecimal getMinRequire() {
        return minRequire;
    }

    public void setMinRequire(BigDecimal minRequire) {
        this.minRequire = minRequire;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Roombooking> getRoombookingList() {
        return roombookingList;
    }

    public void setRoombookingList(List<Roombooking> roombookingList) {
        this.roombookingList = roombookingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (voucherId != null ? voucherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Voucher)) {
            return false;
        }
        Voucher other = (Voucher) object;
        if ((this.voucherId == null && other.voucherId != null) || (this.voucherId != null && !this.voucherId.equals(other.voucherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Voucher[ voucherId=" + voucherId + " ]";
    }
    
}
