/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "indemnetedeplacementetranger")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indemnetedeplacementetranger.findAll", query = "SELECT i FROM Indemnetedeplacementetranger i"),
    @NamedQuery(name = "Indemnetedeplacementetranger.findByIdDeplacement", query = "SELECT i FROM Indemnetedeplacementetranger i WHERE i.indemnetedeplacementetrangerPK.idDeplacement = :idDeplacement"),
    @NamedQuery(name = "Indemnetedeplacementetranger.findByIdDepEx", query = "SELECT i FROM Indemnetedeplacementetranger i WHERE i.indemnetedeplacementetrangerPK.idDepEx = :idDepEx"),
    @NamedQuery(name = "Indemnetedeplacementetranger.findByMntDepEx", query = "SELECT i FROM Indemnetedeplacementetranger i WHERE i.mntDepEx = :mntDepEx")})
public class Indemnetedeplacementetranger implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IndemnetedeplacementetrangerPK indemnetedeplacementetrangerPK;
    @Column(name = "mntDepEx")
    private Integer mntDepEx;
    @JoinColumn(name = "idPrixSej", referencedColumnName = "idPrixSej")
    @ManyToOne(fetch = FetchType.EAGER)
    private Prixsejour idPrixSej;
    @JoinColumn(name = "idDeplacement", referencedColumnName = "idDeplacement", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deplacement deplacement;
    @JoinColumn(name = "idDotation", referencedColumnName = "idDotation")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Dotationsecteur idDotation;

    public Indemnetedeplacementetranger() {
    }

    public Indemnetedeplacementetranger(IndemnetedeplacementetrangerPK indemnetedeplacementetrangerPK) {
        this.indemnetedeplacementetrangerPK = indemnetedeplacementetrangerPK;
    }

    public Indemnetedeplacementetranger(int idDeplacement, int idDepEx) {
        this.indemnetedeplacementetrangerPK = new IndemnetedeplacementetrangerPK(idDeplacement, idDepEx);
    }

    public IndemnetedeplacementetrangerPK getIndemnetedeplacementetrangerPK() {
        return indemnetedeplacementetrangerPK;
    }

    public void setIndemnetedeplacementetrangerPK(IndemnetedeplacementetrangerPK indemnetedeplacementetrangerPK) {
        this.indemnetedeplacementetrangerPK = indemnetedeplacementetrangerPK;
    }

    public Integer getMntDepEx() {
        return mntDepEx;
    }

    public void setMntDepEx(Integer mntDepEx) {
        this.mntDepEx = mntDepEx;
    }

    public Prixsejour getIdPrixSej() {
        return idPrixSej;
    }

    public void setIdPrixSej(Prixsejour idPrixSej) {
        this.idPrixSej = idPrixSej;
    }

    public Deplacement getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    public Dotationsecteur getIdDotation() {
        return idDotation;
    }

    public void setIdDotation(Dotationsecteur idDotation) {
        this.idDotation = idDotation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indemnetedeplacementetrangerPK != null ? indemnetedeplacementetrangerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Indemnetedeplacementetranger)) {
            return false;
        }
        Indemnetedeplacementetranger other = (Indemnetedeplacementetranger) object;
        if ((this.indemnetedeplacementetrangerPK == null && other.indemnetedeplacementetrangerPK != null) || (this.indemnetedeplacementetrangerPK != null && !this.indemnetedeplacementetrangerPK.equals(other.indemnetedeplacementetrangerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Indemnetedeplacementetranger[ indemnetedeplacementetrangerPK=" + indemnetedeplacementetrangerPK + " ]";
    }
    
}
