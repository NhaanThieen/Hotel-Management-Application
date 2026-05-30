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
@Table(name = "receipt")
@NamedQueries({
    @NamedQuery(name = "Receipt.findAll", query = "SELECT r FROM Receipt r"),
    @NamedQuery(name = "Receipt.findByReceiptId", query = "SELECT r FROM Receipt r WHERE r.receiptId = :receiptId"),
    @NamedQuery(name = "Receipt.findByUserName", query = "SELECT r FROM Receipt r WHERE r.userName = :userName"),
    @NamedQuery(name = "Receipt.findByUserPhone", query = "SELECT r FROM Receipt r WHERE r.userPhone = :userPhone"),
    @NamedQuery(name = "Receipt.findByTotalPrice", query = "SELECT r FROM Receipt r WHERE r.totalPrice = :totalPrice"),
    @NamedQuery(name = "Receipt.findByTimeCheckOut", query = "SELECT r FROM Receipt r WHERE r.timeCheckOut = :timeCheckOut"),
    @NamedQuery(name = "Receipt.findByMemberDiscountAmount", query = "SELECT r FROM Receipt r WHERE r.memberDiscountAmount = :memberDiscountAmount"),
    @NamedQuery(name = "Receipt.findByManualDiscountAmount", query = "SELECT r FROM Receipt r WHERE r.manualDiscountAmount = :manualDiscountAmount"),
    @NamedQuery(name = "Receipt.findByStaffName", query = "SELECT r FROM Receipt r WHERE r.staffName = :staffName")})
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "receiptId")
    private Integer receiptId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 155)
    @Column(name = "userName")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "userPhone")
    private String userPhone;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalPrice")
    private BigDecimal totalPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timeCheckOut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCheckOut;
    @Column(name = "memberDiscountAmount")
    private BigDecimal memberDiscountAmount;
    @Column(name = "manualDiscountAmount")
    private BigDecimal manualDiscountAmount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 155)
    @Column(name = "staffName")
    private String staffName;
    @OneToMany(mappedBy = "receiptId")
    private List<Manualdiscountdetail> manualdiscountdetailList;
    @OneToMany(mappedBy = "receiptId")
    private List<Roombooking> roombookingList;
    @JoinColumn(name = "paymentMethodId", referencedColumnName = "paymentMethodId")
    @ManyToOne(optional = false)
    private Paymentmethod paymentMethodId;
    @JoinColumn(name = "userPaidId", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private User userPaidId;
    @JoinColumn(name = "staffId", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private User staffId;

    public Receipt() {
    }

    public Receipt(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public Receipt(Integer receiptId, String userName, String userPhone, BigDecimal totalPrice, Date timeCheckOut, String staffName) {
        this.receiptId = receiptId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.totalPrice = totalPrice;
        this.timeCheckOut = timeCheckOut;
        this.staffName = staffName;
    }

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getTimeCheckOut() {
        return timeCheckOut;
    }

    public void setTimeCheckOut(Date timeCheckOut) {
        this.timeCheckOut = timeCheckOut;
    }

    public BigDecimal getMemberDiscountAmount() {
        return memberDiscountAmount;
    }

    public void setMemberDiscountAmount(BigDecimal memberDiscountAmount) {
        this.memberDiscountAmount = memberDiscountAmount;
    }

    public BigDecimal getManualDiscountAmount() {
        return manualDiscountAmount;
    }

    public void setManualDiscountAmount(BigDecimal manualDiscountAmount) {
        this.manualDiscountAmount = manualDiscountAmount;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public List<Manualdiscountdetail> getManualdiscountdetailList() {
        return manualdiscountdetailList;
    }

    public void setManualdiscountdetailList(List<Manualdiscountdetail> manualdiscountdetailList) {
        this.manualdiscountdetailList = manualdiscountdetailList;
    }

    public List<Roombooking> getRoombookingList() {
        return roombookingList;
    }

    public void setRoombookingList(List<Roombooking> roombookingList) {
        this.roombookingList = roombookingList;
    }

    public Paymentmethod getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Paymentmethod paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public User getUserPaidId() {
        return userPaidId;
    }

    public void setUserPaidId(User userPaidId) {
        this.userPaidId = userPaidId;
    }

    public User getStaffId() {
        return staffId;
    }

    public void setStaffId(User staffId) {
        this.staffId = staffId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiptId != null ? receiptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receipt)) {
            return false;
        }
        Receipt other = (Receipt) object;
        if ((this.receiptId == null && other.receiptId != null) || (this.receiptId != null && !this.receiptId.equals(other.receiptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Receipt[ receiptId=" + receiptId + " ]";
    }
    
}
