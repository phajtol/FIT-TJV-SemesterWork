/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hajtol.peter.tjv.klient.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author peter
 */
@Entity
@XmlRootElement
public class Akcia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String nazov;
    private String datumcas;
    
    @ManyToMany(mappedBy = "akcie")
    private List<Uzivatel> uzivatelia = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "adresa_id", referencedColumnName = "id")
    private Adresa adresa;

    @XmlTransient
    public List<Uzivatel> getUzivatelia() {
        return uzivatelia;
    }

    public void setUzivatelia(List<Uzivatel> uzivatelia) {
        this.uzivatelia = uzivatelia;
    }
    
    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getDatumcas() {
        return datumcas;
    }

    public void setDatumcas(String datumcas) {
        this.datumcas = datumcas;
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
        if (!(object instanceof Akcia)) {
            return false;
        }
        Akcia other = (Akcia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.hajtol.peter.tjv.server.entities.Akcia[ id=" + id + " ]";
    }
    
}
