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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author Nhaan
 */
@Entity
@Table(name = "usersocialaccount")
@NamedQueries({
    @NamedQuery(name = "Usersocialaccount.findAll", query = "SELECT u FROM Usersocialaccount u"),
    @NamedQuery(name = "Usersocialaccount.findByUserSocialAccountId", query = "SELECT u FROM Usersocialaccount u WHERE u.userSocialAccountId = :userSocialAccountId"),
    @NamedQuery(name = "Usersocialaccount.findByProvider", query = "SELECT u FROM Usersocialaccount u WHERE u.provider = :provider"),
    @NamedQuery(name = "Usersocialaccount.findByProviderId", query = "SELECT u FROM Usersocialaccount u WHERE u.providerId = :providerId")})
public class Usersocialaccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UserSocialAccountId")
    private Integer userSocialAccountId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "provider")
    private String provider;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "providerId")
    private String providerId;
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private User userId;

    public Usersocialaccount() {
    }

    public Usersocialaccount(Integer userSocialAccountId) {
        this.userSocialAccountId = userSocialAccountId;
    }

    public Usersocialaccount(Integer userSocialAccountId, String provider, String providerId) {
        this.userSocialAccountId = userSocialAccountId;
        this.provider = provider;
        this.providerId = providerId;
    }

    public Integer getUserSocialAccountId() {
        return userSocialAccountId;
    }

    public void setUserSocialAccountId(Integer userSocialAccountId) {
        this.userSocialAccountId = userSocialAccountId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userSocialAccountId != null ? userSocialAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usersocialaccount)) {
            return false;
        }
        Usersocialaccount other = (Usersocialaccount) object;
        if ((this.userSocialAccountId == null && other.userSocialAccountId != null) || (this.userSocialAccountId != null && !this.userSocialAccountId.equals(other.userSocialAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Usersocialaccount[ userSocialAccountId=" + userSocialAccountId + " ]";
    }
    
}
