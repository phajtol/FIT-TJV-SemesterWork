/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hajtol.peter.tjv.klient.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author peter
 */
@Entity
@Table(name = "adresa")
@XmlRootElement
public class Adresa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private Integer cislo;
    private String ulica;
    private String mesto;
    private String psc;
    private String krajina;

    @OneToMany(mappedBy = "adresa")
    private List<Akcia> akcie;
    
    @OneToMany(mappedBy = "adresa")
    private List<Uzivatel> uzivatelia;

    
    @XmlTransient
    public List<Akcia> getAkcie() {
        return akcie;
    }

    public void setAkcie(List<Akcia> akcie) {
        this.akcie = akcie;
    }

    @XmlTransient
    public List<Uzivatel> getUzivatelia() {
        return uzivatelia;
    }

    public void setUzivatelia(List<Uzivatel> uzivatelia) {
        this.uzivatelia = uzivatelia;
    }
  
    public Integer getCislo() {
        return cislo;
    }

    public void setCislo(Integer cislo) {
        this.cislo = cislo;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public String getKrajina() {
        return krajina;
    }

    public void setKrajina(String krajina) {
        this.krajina = krajina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adresa)) {
            return false;
        }
        Adresa other = (Adresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.hajtol.peter.tjv.server.entities.Adresa[ id=" + id + " ]";
    }
    
}
