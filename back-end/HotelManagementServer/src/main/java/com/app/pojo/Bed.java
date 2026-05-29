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

/**
 *
 * @author Nhaan
 */
@Entity
@Table(name = "bed")
@NamedQueries({
    @NamedQuery(name = "Bed.findAll", query = "SELECT b FROM Bed b"),
    @NamedQuery(name = "Bed.findByBedId", query = "SELECT b FROM Bed b WHERE b.bedId = :bedId"),
    @NamedQuery(name = "Bed.findByAmount", query = "SELECT b FROM Bed b WHERE b.amount = :amount")})
public class Bed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bedId")
    private Integer bedId;
    @Column(name = "amount")
    private Integer amount;
    @JoinColumn(name = "bedTypeId", referencedColumnName = "bedTypeId")
    @ManyToOne(optional = false)
    private Bedtype bedTypeId;
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    @ManyToOne(optional = false)
    private Room roomId;

    public Bed() {
    }

    public Bed(Integer bedId) {
        this.bedId = bedId;
    }

    public Integer getBedId() {
        return bedId;
    }

    public void setBedId(Integer bedId) {
        this.bedId = bedId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Bedtype getBedTypeId() {
        return bedTypeId;
    }

    public void setBedTypeId(Bedtype bedTypeId) {
        this.bedTypeId = bedTypeId;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bedId != null ? bedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bed)) {
            return false;
        }
        Bed other = (Bed) object;
        if ((this.bedId == null && other.bedId != null) || (this.bedId != null && !this.bedId.equals(other.bedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Bed[ bedId=" + bedId + " ]";
    }
    
}
