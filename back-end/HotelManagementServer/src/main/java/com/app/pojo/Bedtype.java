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
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Nhaan
 */
@Entity
@Table(name = "bedtype")
@NamedQueries({
    @NamedQuery(name = "Bedtype.findAll", query = "SELECT b FROM Bedtype b"),
    @NamedQuery(name = "Bedtype.findByBedTypeId", query = "SELECT b FROM Bedtype b WHERE b.bedTypeId = :bedTypeId"),
    @NamedQuery(name = "Bedtype.findByName", query = "SELECT b FROM Bedtype b WHERE b.name = :name")})
public class Bedtype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bedTypeId")
    private Integer bedTypeId;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bedTypeId")
    private List<Bed> bedList;

    public Bedtype() {
    }

    public Bedtype(Integer bedTypeId) {
        this.bedTypeId = bedTypeId;
    }

    public Integer getBedTypeId() {
        return bedTypeId;
    }

    public void setBedTypeId(Integer bedTypeId) {
        this.bedTypeId = bedTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bed> getBedList() {
        return bedList;
    }

    public void setBedList(List<Bed> bedList) {
        this.bedList = bedList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bedTypeId != null ? bedTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bedtype)) {
            return false;
        }
        Bedtype other = (Bedtype) object;
        if ((this.bedTypeId == null && other.bedTypeId != null) || (this.bedTypeId != null && !this.bedTypeId.equals(other.bedTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Bedtype[ bedTypeId=" + bedTypeId + " ]";
    }
    
}
