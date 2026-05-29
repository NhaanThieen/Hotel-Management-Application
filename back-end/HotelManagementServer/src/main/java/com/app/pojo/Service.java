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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "service")
@NamedQueries({
    @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s"),
    @NamedQuery(name = "Service.findByServiceId", query = "SELECT s FROM Service s WHERE s.serviceId = :serviceId"),
    @NamedQuery(name = "Service.findByName", query = "SELECT s FROM Service s WHERE s.name = :name"),
    @NamedQuery(name = "Service.findByPrice", query = "SELECT s FROM Service s WHERE s.price = :price"),
    @NamedQuery(name = "Service.findByStock", query = "SELECT s FROM Service s WHERE s.stock = :stock"),
    @NamedQuery(name = "Service.findByIsDeleted", query = "SELECT s FROM Service s WHERE s.isDeleted = :isDeleted")})
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "serviceId")
    private Integer serviceId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 155)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private BigDecimal price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock")
    private int stock;
    @Column(name = "isDeleted")
    private Short isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceId")
    private List<Roombookingservice> roombookingserviceList;
    @JoinColumn(name = "serviceTypeId", referencedColumnName = "serviceTypeId")
    @ManyToOne(optional = false)
    private Servicetype serviceTypeId;

    public Service() {
    }

    public Service(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Service(Integer serviceId, String name, BigDecimal price, int stock) {
        this.serviceId = serviceId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Roombookingservice> getRoombookingserviceList() {
        return roombookingserviceList;
    }

    public void setRoombookingserviceList(List<Roombookingservice> roombookingserviceList) {
        this.roombookingserviceList = roombookingserviceList;
    }

    public Servicetype getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Servicetype serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceId != null ? serviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.serviceId == null && other.serviceId != null) || (this.serviceId != null && !this.serviceId.equals(other.serviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Service[ serviceId=" + serviceId + " ]";
    }
    
}
