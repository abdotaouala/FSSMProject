/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "dossierrejete")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dossierrejete.findAll", query = "SELECT d FROM Dossierrejete d"),
    @NamedQuery(name = "Dossierrejete.findByIdDossierRejete", query = "SELECT d FROM Dossierrejete d WHERE d.idDossierRejete = :idDossierRejete"),
    @NamedQuery(name = "Dossierrejete.findByIdDossier", query = "SELECT d FROM Dossierrejete d WHERE d.idDossier = :idDossier"),
    @NamedQuery(name = "Dossierrejete.findByDosidDossier", query = "SELECT d FROM Dossierrejete d WHERE d.dosidDossier = :dosidDossier"),
    @NamedQuery(name = "Dossierrejete.findByMotifRejet", query = "SELECT d FROM Dossierrejete d WHERE d.motifRejet = :motifRejet")})
public class Dossierrejete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDossierRejete")
    private Integer idDossierRejete;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDossier")
    private int idDossier;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Dos_idDossier")
    private int dosidDossier;
    @Size(max = 254)
    @Column(name = "motifRejet")
    private String motifRejet;

    public Dossierrejete() {
    }

    public Dossierrejete(Integer idDossierRejete) {
        this.idDossierRejete = idDossierRejete;
    }

    public Dossierrejete(Integer idDossierRejete, int idDossier, int dosidDossier) {
        this.idDossierRejete = idDossierRejete;
        this.idDossier = idDossier;
        this.dosidDossier = dosidDossier;
    }

    public Integer getIdDossierRejete() {
        return idDossierRejete;
    }

    public void setIdDossierRejete(Integer idDossierRejete) {
        this.idDossierRejete = idDossierRejete;
    }

    public int getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(int idDossier) {
        this.idDossier = idDossier;
    }

    public int getDosidDossier() {
        return dosidDossier;
    }

    public void setDosidDossier(int dosidDossier) {
        this.dosidDossier = dosidDossier;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDossierRejete != null ? idDossierRejete.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dossierrejete)) {
            return false;
        }
        Dossierrejete other = (Dossierrejete) object;
        if ((this.idDossierRejete == null && other.idDossierRejete != null) || (this.idDossierRejete != null && !this.idDossierRejete.equals(other.idDossierRejete))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Dossierrejete[ idDossierRejete=" + idDossierRejete + " ]";
    }
    
}
