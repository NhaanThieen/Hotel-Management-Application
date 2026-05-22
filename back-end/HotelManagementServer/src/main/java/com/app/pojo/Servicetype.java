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
@Table(name = "servicetype")
@NamedQueries({
    @NamedQuery(name = "Servicetype.findAll", query = "SELECT s FROM Servicetype s"),
    @NamedQuery(name = "Servicetype.findByServiceTypeId", query = "SELECT s FROM Servicetype s WHERE s.serviceTypeId = :serviceTypeId"),
    @NamedQuery(name = "Servicetype.findByName", query = "SELECT s FROM Servicetype s WHERE s.name = :name")})
public class Servicetype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "serviceTypeId")
    private Integer serviceTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 155)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceTypeId")
    private List<Service> serviceList;

    public Servicetype() {
    }

    public Servicetype(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Servicetype(Integer serviceTypeId, String name) {
        this.serviceTypeId = serviceTypeId;
        this.name = name;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceTypeId != null ? serviceTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicetype)) {
            return false;
        }
        Servicetype other = (Servicetype) object;
        if ((this.serviceTypeId == null && other.serviceTypeId != null) || (this.serviceTypeId != null && !this.serviceTypeId.equals(other.serviceTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Servicetype[ serviceTypeId=" + serviceTypeId + " ]";
    }
    
}
