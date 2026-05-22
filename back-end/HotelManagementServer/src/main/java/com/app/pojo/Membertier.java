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
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Nhaan
 */
@Entity
@Table(name = "membertier")
@NamedQueries({
    @NamedQuery(name = "Membertier.findAll", query = "SELECT m FROM Membertier m"),
    @NamedQuery(name = "Membertier.findByMemberTierId", query = "SELECT m FROM Membertier m WHERE m.memberTierId = :memberTierId"),
    @NamedQuery(name = "Membertier.findByType", query = "SELECT m FROM Membertier m WHERE m.type = :type"),
    @NamedQuery(name = "Membertier.findByMemberDiscountPercent", query = "SELECT m FROM Membertier m WHERE m.memberDiscountPercent = :memberDiscountPercent")})
public class Membertier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "memberTierId")
    private Integer memberTierId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "memberDiscountPercent")
    private BigDecimal memberDiscountPercent;
    @OneToMany(mappedBy = "memberTierId")
    private List<User> userList;

    public Membertier() {
    }

    public Membertier(Integer memberTierId) {
        this.memberTierId = memberTierId;
    }

    public Membertier(Integer memberTierId, String type) {
        this.memberTierId = memberTierId;
        this.type = type;
    }

    public Integer getMemberTierId() {
        return memberTierId;
    }

    public void setMemberTierId(Integer memberTierId) {
        this.memberTierId = memberTierId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMemberDiscountPercent() {
        return memberDiscountPercent;
    }

    public void setMemberDiscountPercent(BigDecimal memberDiscountPercent) {
        this.memberDiscountPercent = memberDiscountPercent;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberTierId != null ? memberTierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Membertier)) {
            return false;
        }
        Membertier other = (Membertier) object;
        if ((this.memberTierId == null && other.memberTierId != null) || (this.memberTierId != null && !this.memberTierId.equals(other.memberTierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Membertier[ memberTierId=" + memberTierId + " ]";
    }
    
}
