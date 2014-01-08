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
@Table(name = "stock", catalog = "javaproject", schema = "")
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s"),
    @NamedQuery(name = "Stock.findByStockid", query = "SELECT s FROM Stock s WHERE s.stockid = :stockid"),
    @NamedQuery(name = "Stock.findByCode", query = "SELECT s FROM Stock s WHERE s.code = :code"),
    @NamedQuery(name = "Stock.findByStock", query = "SELECT s FROM Stock s WHERE s.stock = :stock")})
public class Stock implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stockid")
    private Integer stockid;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Column(name = "stock")
    private double stock;

    public Stock() {
    }

    public Stock(Integer stockid) {
        this.stockid = stockid;
    }

    public Stock(Integer stockid, String code) {
        this.stockid = stockid;
        this.code = code;
    }

    public Integer getStockid() {
        return stockid;
    }

    public void setStockid(Integer stockid) {
        Integer oldStockid = this.stockid;
        this.stockid = stockid;
        changeSupport.firePropertyChange("stockid", oldStockid, stockid);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        String oldCode = this.code;
        this.code = code;
        changeSupport.firePropertyChange("code", oldCode, code);
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        double oldStock = this.stock;
        this.stock = stock;
        changeSupport.firePropertyChange("stock", oldStock, stock);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stockid != null ? stockid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.stockid == null && other.stockid != null) || (this.stockid != null && !this.stockid.equals(other.stockid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaproject.com.Stock[ stockid=" + stockid + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
