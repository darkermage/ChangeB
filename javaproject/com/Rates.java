/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaproject.com;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author darkermage
 */
@Entity
@Table(name = "rates", catalog = "javaproject", schema = "")
@NamedQueries({
    @NamedQuery(name = "Rates.findAll", query = "SELECT r FROM Rates r"),
    @NamedQuery(name = "Rates.findByRatesId", query = "SELECT r FROM Rates r WHERE r.ratesId = :ratesId"),
    @NamedQuery(name = "Rates.findByRateName", query = "SELECT r FROM Rates r WHERE r.rateName = :rateName"),
    @NamedQuery(name = "Rates.findByRate", query = "SELECT r FROM Rates r WHERE r.rate = :rate")})
public class Rates implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RatesId")
    private Integer ratesId;
    @Basic(optional = false)
    @Column(name = "RateName")
    private String rateName;
    @Basic(optional = false)
    @Column(name = "Rate")
    private double rate;

    public Rates() {
    }

    public Rates(Integer ratesId) {
        this.ratesId = ratesId;
    }

    public Rates(Integer ratesId, String rateName, double rate) {
        this.ratesId = ratesId;
        this.rateName = rateName;
        this.rate = rate;
    }

    public Integer getRatesId() {
        return ratesId;
    }

    public void setRatesId(Integer ratesId) {
        Integer oldRatesId = this.ratesId;
        this.ratesId = ratesId;
        changeSupport.firePropertyChange("ratesId", oldRatesId, ratesId);
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        String oldRateName = this.rateName;
        this.rateName = rateName;
        changeSupport.firePropertyChange("rateName", oldRateName, rateName);
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        double oldRate = this.rate;
        this.rate = rate;
        changeSupport.firePropertyChange("rate", oldRate, rate);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratesId != null ? ratesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rates)) {
            return false;
        }
        Rates other = (Rates) object;
        if ((this.ratesId == null && other.ratesId != null) || (this.ratesId != null && !this.ratesId.equals(other.ratesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaproject.com.Rates[ ratesId=" + ratesId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
