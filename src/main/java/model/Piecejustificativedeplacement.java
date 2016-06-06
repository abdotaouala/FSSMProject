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
@Table(name = "piecejustificativedeplacement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Piecejustificativedeplacement.findAll", query = "SELECT p FROM Piecejustificativedeplacement p"),
    @NamedQuery(name = "Piecejustificativedeplacement.findByIdPiece", query = "SELECT p FROM Piecejustificativedeplacement p WHERE p.idPiece = :idPiece"),
    @NamedQuery(name = "Piecejustificativedeplacement.findByDatePiece", query = "SELECT p FROM Piecejustificativedeplacement p WHERE p.datePiece = :datePiece"),
    @NamedQuery(name = "Piecejustificativedeplacement.findByIntutilePiece", query = "SELECT p FROM Piecejustificativedeplacement p WHERE p.intutilePiece = :intutilePiece"),
    @NamedQuery(name = "Piecejustificativedeplacement.findByPiece", query = "SELECT p FROM Piecejustificativedeplacement p WHERE p.piece = :piece")})
public class Piecejustificativedeplacement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPiece")
    private Integer idPiece;
    @Column(name = "datePiece")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePiece;
    @Size(max = 254)
    @Column(name = "intutilePiece")
    private String intutilePiece;
    @Size(max = 254)
    @Column(name = "piece")
    private String piece;
    @JoinColumn(name = "idDeplacement", referencedColumnName = "idDeplacement")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deplacement idDeplacement;

    public Piecejustificativedeplacement() {
    }

    public Piecejustificativedeplacement(Integer idPiece) {
        this.idPiece = idPiece;
    }

    public Integer getIdPiece() {
        return idPiece;
    }

    public void setIdPiece(Integer idPiece) {
        this.idPiece = idPiece;
    }

    public Date getDatePiece() {
        return datePiece;
    }

    public void setDatePiece(Date datePiece) {
        this.datePiece = datePiece;
    }

    public String getIntutilePiece() {
        return intutilePiece;
    }

    public void setIntutilePiece(String intutilePiece) {
        this.intutilePiece = intutilePiece;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
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
        hash += (idPiece != null ? idPiece.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Piecejustificativedeplacement)) {
            return false;
        }
        Piecejustificativedeplacement other = (Piecejustificativedeplacement) object;
        if ((this.idPiece == null && other.idPiece != null) || (this.idPiece != null && !this.idPiece.equals(other.idPiece))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Piecejustificativedeplacement[ idPiece=" + idPiece + " ]";
    }
    
}
