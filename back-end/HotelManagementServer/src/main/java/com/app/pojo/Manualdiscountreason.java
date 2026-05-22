/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import java.util.List;

/**
 *
 * @author Nhaan
 */
@Entity
@Table(name = "manualdiscountreason")
@NamedQueries({
    @NamedQuery(name = "Manualdiscountreason.findAll", query = "SELECT m FROM Manualdiscountreason m"),
    @NamedQuery(name = "Manualdiscountreason.findByManualDiscountReasonId", query = "SELECT m FROM Manualdiscountreason m WHERE m.manualDiscountReasonId = :manualDiscountReasonId"),
    @NamedQuery(name = "Manualdiscountreason.findByName", query = "SELECT m FROM Manualdiscountreason m WHERE m.name = :name")})
public class Manualdiscountreason implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "manualDiscountReasonId")
    private Integer manualDiscountReasonId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manualDiscountReasonId")
    private List<Manualdiscountdetail> manualdiscountdetailList;

    public Manualdiscountreason() {
    }

    public Manualdiscountreason(Integer manualDiscountReasonId) {
        this.manualDiscountReasonId = manualDiscountReasonId;
    }

    public Manualdiscountreason(Integer manualDiscountReasonId, String name) {
        this.manualDiscountReasonId = manualDiscountReasonId;
        this.name = name;
    }

    public Integer getManualDiscountReasonId() {
        return manualDiscountReasonId;
    }

    public void setManualDiscountReasonId(Integer manualDiscountReasonId) {
        this.manualDiscountReasonId = manualDiscountReasonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Manualdiscountdetail> getManualdiscountdetailList() {
        return manualdiscountdetailList;
    }

    public void setManualdiscountdetailList(List<Manualdiscountdetail> manualdiscountdetailList) {
        this.manualdiscountdetailList = manualdiscountdetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manualDiscountReasonId != null ? manualDiscountReasonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manualdiscountreason)) {
            return false;
        }
        Manualdiscountreason other = (Manualdiscountreason) object;
        if ((this.manualDiscountReasonId == null && other.manualDiscountReasonId != null) || (this.manualDiscountReasonId != null && !this.manualDiscountReasonId.equals(other.manualDiscountReasonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Manualdiscountreason[ manualDiscountReasonId=" + manualDiscountReasonId + " ]";
    }
    
}
