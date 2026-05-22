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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nhaan
 */
@Entity
@Table(name = "roombookingdetail")
@NamedQueries({
    @NamedQuery(name = "Roombookingdetail.findAll", query = "SELECT r FROM Roombookingdetail r"),
    @NamedQuery(name = "Roombookingdetail.findByRoomBookingDetailId", query = "SELECT r FROM Roombookingdetail r WHERE r.roomBookingDetailId = :roomBookingDetailId"),
    @NamedQuery(name = "Roombookingdetail.findByPrice", query = "SELECT r FROM Roombookingdetail r WHERE r.price = :price"),
    @NamedQuery(name = "Roombookingdetail.findByTimeIn", query = "SELECT r FROM Roombookingdetail r WHERE r.timeIn = :timeIn"),
    @NamedQuery(name = "Roombookingdetail.findByTimeOut", query = "SELECT r FROM Roombookingdetail r WHERE r.timeOut = :timeOut"),
    @NamedQuery(name = "Roombookingdetail.findByRoomName", query = "SELECT r FROM Roombookingdetail r WHERE r.roomName = :roomName")})
public class Roombookingdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roomBookingDetailId")
    private Integer roomBookingDetailId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "timeIn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeIn;
    @Column(name = "timeOut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOut;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 155)
    @Column(name = "roomName")
    private String roomName;
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    @ManyToOne(optional = false)
    private Room roomId;
    @JoinColumn(name = "roomBookingId", referencedColumnName = "roomBookingId")
    @ManyToOne(optional = false)
    private Roombooking roomBookingId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomBookingDetailId")
    private List<Roombookingservice> roombookingserviceList;

    public Roombookingdetail() {
    }

    public Roombookingdetail(Integer roomBookingDetailId) {
        this.roomBookingDetailId = roomBookingDetailId;
    }

    public Roombookingdetail(Integer roomBookingDetailId, String roomName) {
        this.roomBookingDetailId = roomBookingDetailId;
        this.roomName = roomName;
    }

    public Integer getRoomBookingDetailId() {
        return roomBookingDetailId;
    }

    public void setRoomBookingDetailId(Integer roomBookingDetailId) {
        this.roomBookingDetailId = roomBookingDetailId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public Roombooking getRoomBookingId() {
        return roomBookingId;
    }

    public void setRoomBookingId(Roombooking roomBookingId) {
        this.roomBookingId = roomBookingId;
    }

    public List<Roombookingservice> getRoombookingserviceList() {
        return roombookingserviceList;
    }

    public void setRoombookingserviceList(List<Roombookingservice> roombookingserviceList) {
        this.roombookingserviceList = roombookingserviceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomBookingDetailId != null ? roomBookingDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roombookingdetail)) {
            return false;
        }
        Roombookingdetail other = (Roombookingdetail) object;
        if ((this.roomBookingDetailId == null && other.roomBookingDetailId != null) || (this.roomBookingDetailId != null && !this.roomBookingDetailId.equals(other.roomBookingDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Roombookingdetail[ roomBookingDetailId=" + roomBookingDetailId + " ]";
    }
    
}
