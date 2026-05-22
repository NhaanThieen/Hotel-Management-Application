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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
@Table(name = "room")
@NamedQueries({
    @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r"),
    @NamedQuery(name = "Room.findByRoomId", query = "SELECT r FROM Room r WHERE r.roomId = :roomId"),
    @NamedQuery(name = "Room.findByRoomName", query = "SELECT r FROM Room r WHERE r.roomName = :roomName"),
    @NamedQuery(name = "Room.findByIsDeleted", query = "SELECT r FROM Room r WHERE r.isDeleted = :isDeleted"),
    @NamedQuery(name = "Room.findByVersion", query = "SELECT r FROM Room r WHERE r.version = :version")})
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roomId")
    private Integer roomId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "roomName")
    private String roomName;
    @Column(name = "isDeleted")
    private Boolean isDeleted = false;
    @Column(name = "version")
    private Integer version;
    @Lob
    @Size(max = 65535)
    @Column(name = "avatar")
    private String avatar;
    @JoinColumn(name = "roomStatusId", referencedColumnName = "roomStatusId")
    @ManyToOne(optional = false)
    private Roomstatus roomStatusId;
    @JoinColumn(name = "roomTypeId", referencedColumnName = "roomTypeId")
    @ManyToOne(optional = false)
    private Roomtype roomTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomId")
    private List<Roombookingdetail> roombookingdetailList;

    public Room() {
    }

    public Room(Integer roomId) {
        this.roomId = roomId;
    }

    public Room(Integer roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Roomstatus getRoomStatusId() {
        return roomStatusId;
    }

    public void setRoomStatusId(Roomstatus roomStatusId) {
        this.roomStatusId = roomStatusId;
    }

    public Roomtype getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Roomtype roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public List<Roombookingdetail> getRoombookingdetailList() {
        return roombookingdetailList;
    }

    public void setRoombookingdetailList(List<Roombookingdetail> roombookingdetailList) {
        this.roombookingdetailList = roombookingdetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Room[ roomId=" + roomId + " ]";
    }
    
}
