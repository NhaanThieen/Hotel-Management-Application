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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author Nhaan
 */
@Entity
@Table(name = "roomimage")
@NamedQueries({
    @NamedQuery(name = "Roomimage.findAll", query = "SELECT r FROM Roomimage r"),
    @NamedQuery(name = "Roomimage.findByRoomImageId", query = "SELECT r FROM Roomimage r WHERE r.roomImageId = :roomImageId")})
public class Roomimage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roomImageId")
    private Integer roomImageId;
    @Lob
    @Size(max = 65535)
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    @ManyToOne(optional = false)
    private Room roomId;

    public Roomimage() {
    }

    public Roomimage(Integer roomImageId) {
        this.roomImageId = roomImageId;
    }

    public Integer getRoomImageId() {
        return roomImageId;
    }

    public void setRoomImageId(Integer roomImageId) {
        this.roomImageId = roomImageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        hash += (roomImageId != null ? roomImageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roomimage)) {
            return false;
        }
        Roomimage other = (Roomimage) object;
        if ((this.roomImageId == null && other.roomImageId != null) || (this.roomImageId != null && !this.roomImageId.equals(other.roomImageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Roomimage[ roomImageId=" + roomImageId + " ]";
    }
    
}
