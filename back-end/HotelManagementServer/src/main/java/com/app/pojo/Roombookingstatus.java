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
import java.util.List;

/**
 *
 * @author Nhaan
 */
@Entity
@Table(name = "roombookingstatus")
@NamedQueries({
    @NamedQuery(name = "Roombookingstatus.findAll", query = "SELECT r FROM Roombookingstatus r"),
    @NamedQuery(name = "Roombookingstatus.findByRoomBookingStatusId", query = "SELECT r FROM Roombookingstatus r WHERE r.roomBookingStatusId = :roomBookingStatusId"),
    @NamedQuery(name = "Roombookingstatus.findByName", query = "SELECT r FROM Roombookingstatus r WHERE r.name = :name")})
public class Roombookingstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roomBookingStatusId")
    private Integer roomBookingStatusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 155)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "roomBookingStatusId")
    private List<Roombooking> roombookingList;

    public Roombookingstatus() {
    }

    public Roombookingstatus(Integer roomBookingStatusId) {
        this.roomBookingStatusId = roomBookingStatusId;
    }

    public Roombookingstatus(Integer roomBookingStatusId, String name) {
        this.roomBookingStatusId = roomBookingStatusId;
        this.name = name;
    }

    public Integer getRoomBookingStatusId() {
        return roomBookingStatusId;
    }

    public void setRoomBookingStatusId(Integer roomBookingStatusId) {
        this.roomBookingStatusId = roomBookingStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        hash += (roomBookingStatusId != null ? roomBookingStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roombookingstatus)) {
            return false;
        }
        Roombookingstatus other = (Roombookingstatus) object;
        if ((this.roomBookingStatusId == null && other.roomBookingStatusId != null) || (this.roomBookingStatusId != null && !this.roomBookingStatusId.equals(other.roomBookingStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Roombookingstatus[ roomBookingStatusId=" + roomBookingStatusId + " ]";
    }
    
}
