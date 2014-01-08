/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaproject.com;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author darkermage
 */
@Entity
@Table(name = "log", catalog = "javaproject", schema = "")
@NamedQueries({
    @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l"),
    @NamedQuery(name = "Log.findByLogid", query = "SELECT l FROM Log l WHERE l.logid = :logid"),
    @NamedQuery(name = "Log.findByDate", query = "SELECT l FROM Log l WHERE l.date = :date"),
    @NamedQuery(name = "Log.findByCurrencyFrom", query = "SELECT l FROM Log l WHERE l.currencyFrom = :currencyFrom"),
    @NamedQuery(name = "Log.findByCurrencyFromAmount", query = "SELECT l FROM Log l WHERE l.currencyFromAmount = :currencyFromAmount"),
    @NamedQuery(name = "Log.findByRate", query = "SELECT l FROM Log l WHERE l.rate = :rate"),
    @NamedQuery(name = "Log.findByCurrencyTo", query = "SELECT l FROM Log l WHERE l.currencyTo = :currencyTo"),
    @NamedQuery(name = "Log.findByCurrencyToAmount", query = "SELECT l FROM Log l WHERE l.currencyToAmount = :currencyToAmount")})
public class Log implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "logid")
    private Integer logid;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "currency_from")
    private String currencyFrom;
    @Basic(optional = false)
    @Column(name = "currency_from_amount")
    private double currencyFromAmount;
    @Basic(optional = false)
    @Column(name = "rate")
    private double rate;
    @Basic(optional = false)
    @Column(name = "currency_to")
    private String currencyTo;
    @Basic(optional = false)
    @Column(name = "currency_to_amount")
    private double currencyToAmount;

    public Log() {
    }

    public Log(Integer logid) {
        this.logid = logid;
    }

    public Log(Integer logid, Date date, String currencyFrom, double currencyFromAmount, double rate, String currencyTo, double currencyToAmount) {
        this.logid = logid;
        this.date = date;
        this.currencyFrom = currencyFrom;
        this.currencyFromAmount = currencyFromAmount;
        this.rate = rate;
        this.currencyTo = currencyTo;
        this.currencyToAmount = currencyToAmount;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        Integer oldLogid = this.logid;
        this.logid = logid;
        changeSupport.firePropertyChange("logid", oldLogid, logid);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        Date oldDate = this.date;
        this.date = date;
        changeSupport.firePropertyChange("date", oldDate, date);
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        String oldCurrencyFrom = this.currencyFrom;
        this.currencyFrom = currencyFrom;
        changeSupport.firePropertyChange("currencyFrom", oldCurrencyFrom, currencyFrom);
    }

    public double getCurrencyFromAmount() {
        return currencyFromAmount;
    }

    public void setCurrencyFromAmount(double currencyFromAmount) {
        double oldCurrencyFromAmount = this.currencyFromAmount;
        this.currencyFromAmount = currencyFromAmount;
        changeSupport.firePropertyChange("currencyFromAmount", oldCurrencyFromAmount, currencyFromAmount);
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        double oldRate = this.rate;
        this.rate = rate;
        changeSupport.firePropertyChange("rate", oldRate, rate);
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        String oldCurrencyTo = this.currencyTo;
        this.currencyTo = currencyTo;
        changeSupport.firePropertyChange("currencyTo", oldCurrencyTo, currencyTo);
    }

    public double getCurrencyToAmount() {
        return currencyToAmount;
    }

    public void setCurrencyToAmount(double currencyToAmount) {
        double oldCurrencyToAmount = this.currencyToAmount;
        this.currencyToAmount = currencyToAmount;
        changeSupport.firePropertyChange("currencyToAmount", oldCurrencyToAmount, currencyToAmount);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logid != null ? logid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.logid == null && other.logid != null) || (this.logid != null && !this.logid.equals(other.logid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaproject.com.Log[ logid=" + logid + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
