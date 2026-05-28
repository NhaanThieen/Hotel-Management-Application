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
@Table(name = "roomstatus")
@NamedQueries({
    @NamedQuery(name = "Roomstatus.findAll", query = "SELECT r FROM Roomstatus r"),
    @NamedQuery(name = "Roomstatus.findByRoomStatusId", query = "SELECT r FROM Roomstatus r WHERE r.roomStatusId = :roomStatusId"),
    @NamedQuery(name = "Roomstatus.findByName", query = "SELECT r FROM Roomstatus r WHERE r.name = :name")})
public class Roomstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roomStatusId")
    private Integer roomStatusId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomStatusId")
    private List<Room> roomList;

    public Roomstatus() {
    }

    public Roomstatus(Integer roomStatusId) {
        this.roomStatusId = roomStatusId;
    }

    public Roomstatus(Integer roomStatusId, String name) {
        this.roomStatusId = roomStatusId;
        this.name = name;
    }

    public Integer getRoomStatusId() {
        return roomStatusId;
    }

    public void setRoomStatusId(Integer roomStatusId) {
        this.roomStatusId = roomStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomStatusId != null ? roomStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roomstatus)) {
            return false;
        }
        Roomstatus other = (Roomstatus) object;
        if ((this.roomStatusId == null && other.roomStatusId != null) || (this.roomStatusId != null && !this.roomStatusId.equals(other.roomStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Roomstatus[ roomStatusId=" + roomStatusId + " ]";
    }
    
}
