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
@Table(name = "roombooking")
@NamedQueries({
    @NamedQuery(name = "Roombooking.findAll", query = "SELECT r FROM Roombooking r"),
    @NamedQuery(name = "Roombooking.findByRoomBookingId", query = "SELECT r FROM Roombooking r WHERE r.roomBookingId = :roomBookingId"),
    @NamedQuery(name = "Roombooking.findByUserName", query = "SELECT r FROM Roombooking r WHERE r.userName = :userName"),
    @NamedQuery(name = "Roombooking.findByBookingCheckIn", query = "SELECT r FROM Roombooking r WHERE r.bookingCheckIn = :bookingCheckIn"),
    @NamedQuery(name = "Roombooking.findByBookingCheckOut", query = "SELECT r FROM Roombooking r WHERE r.bookingCheckOut = :bookingCheckOut"),
    @NamedQuery(name = "Roombooking.findByTimeCheckIn", query = "SELECT r FROM Roombooking r WHERE r.timeCheckIn = :timeCheckIn"),
    @NamedQuery(name = "Roombooking.findByExpiredTime", query = "SELECT r FROM Roombooking r WHERE r.expiredTime = :expiredTime"),
    @NamedQuery(name = "Roombooking.findByVoucherDiscountMoney", query = "SELECT r FROM Roombooking r WHERE r.voucherDiscountMoney = :voucherDiscountMoney"),
    @NamedQuery(name = "Roombooking.findByDepositAmount", query = "SELECT r FROM Roombooking r WHERE r.depositAmount = :depositAmount"),
    @NamedQuery(name = "Roombooking.findByBookingSource", query = "SELECT r FROM Roombooking r WHERE r.bookingSource = :bookingSource"),
    @NamedQuery(name = "Roombooking.findByStaffName", query = "SELECT r FROM Roombooking r WHERE r.staffName = :staffName")})
public class Roombooking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roomBookingId")
    private Integer roomBookingId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 155)
    @Column(name = "userName")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bookingCheckIn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingCheckIn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bookingCheckOut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingCheckOut;
    @Column(name = "timeCheckIn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCheckIn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expiredTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredTime;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "voucherDiscountMoney")
    private BigDecimal voucherDiscountMoney;
    @Column(name = "depositAmount")
    private BigDecimal depositAmount;
    @Size(max = 100)
    @Column(name = "bookingSource")
    private String bookingSource;
    @Lob
    @Size(max = 65535)
    @Column(name = "note")
    private String note;
    @Size(max = 100)
    @Column(name = "staffName")
    private String staffName;
    @JoinColumn(name = "paymentMethodId", referencedColumnName = "paymentMethodId")
    @ManyToOne(optional = false)
    private Paymentmethod paymentMethodId;
    @JoinColumn(name = "receiptId", referencedColumnName = "receiptId")
    @ManyToOne
    private Receipt receiptId;
    @JoinColumn(name = "roomBookingStatusId", referencedColumnName = "roomBookingStatusId")
    @ManyToOne(optional = false)
    private Roombookingstatus roomBookingStatusId;
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "staffId", referencedColumnName = "userId")
    @ManyToOne
    private User staffId;
    @JoinColumn(name = "voucherId", referencedColumnName = "voucherId")
    @ManyToOne
    private Voucher voucherId;
    @OneToMany(mappedBy = "roomBookingId")
    private List<Feedback> feedbackList;
    @OneToMany(mappedBy = "roomBookingId")
    private List<Roombookingdetail> roombookingdetailList;

    public Roombooking() {
    }

    public Roombooking(Integer roomBookingId) {
        this.roomBookingId = roomBookingId;
    }

    public Roombooking(Integer roomBookingId, String userName, Date bookingCheckIn, Date bookingCheckOut, Date expiredTime) {
        this.roomBookingId = roomBookingId;
        this.userName = userName;
        this.bookingCheckIn = bookingCheckIn;
        this.bookingCheckOut = bookingCheckOut;
        this.expiredTime = expiredTime;
    }

    public Integer getRoomBookingId() {
        return roomBookingId;
    }

    public void setRoomBookingId(Integer roomBookingId) {
        this.roomBookingId = roomBookingId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBookingCheckIn() {
        return bookingCheckIn;
    }

    public void setBookingCheckIn(Date bookingCheckIn) {
        this.bookingCheckIn = bookingCheckIn;
    }

    public Date getBookingCheckOut() {
        return bookingCheckOut;
    }

    public void setBookingCheckOut(Date bookingCheckOut) {
        this.bookingCheckOut = bookingCheckOut;
    }

    public Date getTimeCheckIn() {
        return timeCheckIn;
    }

    public void setTimeCheckIn(Date timeCheckIn) {
        this.timeCheckIn = timeCheckIn;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public BigDecimal getVoucherDiscountMoney() {
        return voucherDiscountMoney;
    }

    public void setVoucherDiscountMoney(BigDecimal voucherDiscountMoney) {
        this.voucherDiscountMoney = voucherDiscountMoney;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getBookingSource() {
        return bookingSource;
    }

    public void setBookingSource(String bookingSource) {
        this.bookingSource = bookingSource;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Paymentmethod getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Paymentmethod paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Receipt getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Receipt receiptId) {
        this.receiptId = receiptId;
    }

    public Roombookingstatus getRoomBookingStatusId() {
        return roomBookingStatusId;
    }

    public void setRoomBookingStatusId(Roombookingstatus roomBookingStatusId) {
        this.roomBookingStatusId = roomBookingStatusId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public User getStaffId() {
        return staffId;
    }

    public void setStaffId(User staffId) {
        this.staffId = staffId;
    }

    public Voucher getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Voucher voucherId) {
        this.voucherId = voucherId;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
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
        hash += (roomBookingId != null ? roomBookingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roombooking)) {
            return false;
        }
        Roombooking other = (Roombooking) object;
        if ((this.roomBookingId == null && other.roomBookingId != null) || (this.roomBookingId != null && !this.roomBookingId.equals(other.roomBookingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.pojo.Roombooking[ roomBookingId=" + roomBookingId + " ]";
    }
    
}
