/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "secteurprincipal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Secteurprincipal.findAll", query = "SELECT s FROM Secteurprincipal s"),
    @NamedQuery(name = "Secteurprincipal.findByIdSecteurP", query = "SELECT s FROM Secteurprincipal s WHERE s.idSecteurP = :idSecteurP"),
    @NamedQuery(name = "Secteurprincipal.findByDesignation", query = "SELECT s FROM Secteurprincipal s WHERE s.designation = :designation")})
public class Secteurprincipal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSecteurP")
    private Integer idSecteurP;
    @Size(max = 254)
    @Column(name = "designation")
    private String designation;
    @OneToMany(mappedBy = "idSecteurP", fetch = FetchType.EAGER)
    private List<Secteur> secteurList;

    public Secteurprincipal() {
    }

    public Secteurprincipal(Integer idSecteurP) {
        this.idSecteurP = idSecteurP;
    }

    public Integer getIdSecteurP() {
        return idSecteurP;
    }

    public void setIdSecteurP(Integer idSecteurP) {
        this.idSecteurP = idSecteurP;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @XmlTransient
    public List<Secteur> getSecteurList() {
        return secteurList;
    }

    public void setSecteurList(List<Secteur> secteurList) {
        this.secteurList = secteurList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSecteurP != null ? idSecteurP.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secteurprincipal)) {
            return false;
        }
        Secteurprincipal other = (Secteurprincipal) object;
        if ((this.idSecteurP == null && other.idSecteurP != null) || (this.idSecteurP != null && !this.idSecteurP.equals(other.idSecteurP))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Secteurprincipal[ idSecteurP=" + idSecteurP + " ]";
    }
    
}
