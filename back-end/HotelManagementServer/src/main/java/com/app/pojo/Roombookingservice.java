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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 *
 * @author Nhaan
 */
@Entity
@Builder
@AllArgsConstructor
@Table(name = "roombookingservice")
@NamedQueries({
    @NamedQuery(name = "Roombookingservice.findAll", query = "SELECT r FROM Roombookingservice r"),
    @NamedQuery(name = "Roombookingservice.findByRoomBookingServiceId", query = "SELECT r FROM Roombookingservice r WHERE r.roomBookingServiceId = :roomBookingServiceId"),
    @NamedQuery(name = "Roombookingservice.findByQuantity", query = "SELECT r FROM Roombookingservice r WHERE r.quantity = :quantity"),
    @NamedQuery(name = "Roombookingservice.findByUnitServicePrice", query = "SELECT r FROM Roombookingservice r WHERE r.unitServicePrice = :unitServicePrice"),
    @NamedQuery(name = "Roombookingservice.findByCreateAt", query = "SELECT r FROM Roombookingservice r WHERE r.createAt = :createAt"),
    @NamedQuery(name = "Roombookingservice.findByServiceName", query = "SELECT r FROM Roombookingservice r WHERE r.serviceName = :serviceName")})
public class Roombookingservice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roomBookingServiceId")
    private Integer roomBookingServiceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "unitServicePrice")
    private BigDecimal unitServicePrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Size(max = 155)
    @Column(name = "serviceName")
    private String serviceName;
    @JoinColumn(name = "roomBookingDetailId", referencedColumnName = "roomBookingDetailId")
    @ManyToOne(optional = false)
    private Roombookingdetail roomBookingDetailId;
    @JoinColumn(name = "serviceId", referencedColumnName = "serviceId")
    @ManyToOne(optional = false)
    private Service serviceId;

    public Roombookingservice() {
    }

    public Roombookingservice(Integer roomBookingServiceId) {
        this.roomBookingServiceId = roomBookingServiceId;
    }

    public Roombookingservice(Integer roomBookingServiceId, int quantity, Date createAt) {
        this.roomBookingServiceId = roomBookingServiceId;
        this.quantity = quantity;
        this.createAt = createAt;
    }

    public Integer getRoomBookingServiceId() {
        return roomBookingServiceId;
    }

    public void setRoomBookingServiceId(Integer roomBookingServiceId) {
        this.roomBookingServiceId = roomBookingServiceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitServicePrice() {
        return unitServicePrice;
    }

    public void setUnitServicePrice(BigDecimal unitServicePrice) {
        this.unitServicePrice = unitServicePrice;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Roombookingdetail getRoomBookingDetailId() {
        return roomBookingDetailId;
    }

    public void setRoomBookingDetailId(Roombookingdetail roomBookingDetailId) {
        this.roomBookingDetailId = roomBookingDetailId;
    }

    public Service getServiceId() {
        return serviceId;
    }

    public void setServiceId(Service serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomBookingServiceId != null ? roomBookingServiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roombookingservice)) {
            return false;
        }
        Roombookingservice other = (Roombookingservice) object;
        if ((this.roomBookingServiceId == null && other.roomBookingServiceId != null) || (this.roomBookingServiceId != null && !this.roomBookingServiceId.equals(other.roomBookingServiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Roombookingservice[ roomBookingServiceId=" + roomBookingServiceId + " ]";
    }
    
}
