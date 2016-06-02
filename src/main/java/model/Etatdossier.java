/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "etatdossier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etatdossier.findAll", query = "SELECT e FROM Etatdossier e"),
    @NamedQuery(name = "Etatdossier.findByIdEtat", query = "SELECT e FROM Etatdossier e WHERE e.idEtat = :idEtat"),
    @NamedQuery(name = "Etatdossier.findByEtat", query = "SELECT e FROM Etatdossier e WHERE e.etat = :etat"),
    @NamedQuery(name = "Etatdossier.findByMotif", query = "SELECT e FROM Etatdossier e WHERE e.motif = :motif"),
    @NamedQuery(name = "Etatdossier.findByRmq", query = "SELECT e FROM Etatdossier e WHERE e.rmq = :rmq"),
    @NamedQuery(name = "Etatdossier.findByObservation", query = "SELECT e FROM Etatdossier e WHERE e.observation = :observation"),
    @NamedQuery(name = "Etatdossier.findByDateEtat", query = "SELECT e FROM Etatdossier e WHERE e.dateEtat = :dateEtat")})
public class Etatdossier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEtat")
    private Integer idEtat;
    @Column(name = "etat")
    private Integer etat;
    @Size(max = 254)
    @Column(name = "motif")
    private String motif;
    @Size(max = 254)
    @Column(name = "rmq")
    private String rmq;
    @Size(max = 254)
    @Column(name = "observation")
    private String observation;
    @Column(name = "dateEtat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEtat;
    @JoinColumn(name = "idDeplacement", referencedColumnName = "idDeplacement")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deplacement idDeplacement;

    public Etatdossier() {
    }

    public Etatdossier(Integer idEtat) {
        this.idEtat = idEtat;
    }

    public Integer getIdEtat() {
        return idEtat;
    }

    public void setIdEtat(Integer idEtat) {
        this.idEtat = idEtat;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getRmq() {
        return rmq;
    }

    public void setRmq(String rmq) {
        this.rmq = rmq;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getDateEtat() {
        return dateEtat;
    }

    public void setDateEtat(Date dateEtat) {
        this.dateEtat = dateEtat;
    }

    public Deplacement getIdDeplacement() {
        return idDeplacement;
    }

    public void setIdDeplacement(Deplacement idDeplacement) {
        this.idDeplacement = idDeplacement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEtat != null ? idEtat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etatdossier)) {
            return false;
        }
        Etatdossier other = (Etatdossier) object;
        if ((this.idEtat == null && other.idEtat != null) || (this.idEtat != null && !this.idEtat.equals(other.idEtat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Etatdossier[ idEtat=" + idEtat + " ]";
    }
    
}
