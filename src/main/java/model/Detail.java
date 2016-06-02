/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detail.findAll", query = "SELECT d FROM Detail d"),
    @NamedQuery(name = "Detail.findByIdDetail", query = "SELECT d FROM Detail d WHERE d.idDetail = :idDetail"),
    @NamedQuery(name = "Detail.findBySalaireAnnuelleBrut", query = "SELECT d FROM Detail d WHERE d.salaireAnnuelleBrut = :salaireAnnuelleBrut"),
    @NamedQuery(name = "Detail.findByAllocationFamiliale", query = "SELECT d FROM Detail d WHERE d.allocationFamiliale = :allocationFamiliale"),
    @NamedQuery(name = "Detail.findByBrutAdditionner", query = "SELECT d FROM Detail d WHERE d.brutAdditionner = :brutAdditionner"),
    @NamedQuery(name = "Detail.findByAmo", query = "SELECT d FROM Detail d WHERE d.amo = :amo"),
    @NamedQuery(name = "Detail.findByRetenuCmr", query = "SELECT d FROM Detail d WHERE d.retenuCmr = :retenuCmr"),
    @NamedQuery(name = "Detail.findByMutuelleMutialiste", query = "SELECT d FROM Detail d WHERE d.mutuelleMutialiste = :mutuelleMutialiste"),
    @NamedQuery(name = "Detail.findByMutuelleCaisse", query = "SELECT d FROM Detail d WHERE d.mutuelleCaisse = :mutuelleCaisse"),
    @NamedQuery(name = "Detail.findByRachatCmr", query = "SELECT d FROM Detail d WHERE d.rachatCmr = :rachatCmr"),
    @NamedQuery(name = "Detail.findBySommeDeduire", query = "SELECT d FROM Detail d WHERE d.sommeDeduire = :sommeDeduire"),
    @NamedQuery(name = "Detail.findByNbrEnfant", query = "SELECT d FROM Detail d WHERE d.nbrEnfant = :nbrEnfant"),
    @NamedQuery(name = "Detail.findByConjoint", query = "SELECT d FROM Detail d WHERE d.conjoint = :conjoint"),
    @NamedQuery(name = "Detail.findByChargeFamiliale", query = "SELECT d FROM Detail d WHERE d.chargeFamiliale = :chargeFamiliale"),
    @NamedQuery(name = "Detail.findByIrSource", query = "SELECT d FROM Detail d WHERE d.irSource = :irSource"),
    @NamedQuery(name = "Detail.findByIrComplement", query = "SELECT d FROM Detail d WHERE d.irComplement = :irComplement"),
    @NamedQuery(name = "Detail.findByNet", query = "SELECT d FROM Detail d WHERE d.net = :net"),
    @NamedQuery(name = "Detail.findByIr", query = "SELECT d FROM Detail d WHERE d.ir = :ir"),
    @NamedQuery(name = "Detail.findByEchelle", query = "SELECT d FROM Detail d WHERE d.echelle = :echelle"),
    @NamedQuery(name = "Detail.findByEchelon", query = "SELECT d FROM Detail d WHERE d.echelon = :echelon")})
public class Detail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetail")
    private Integer idDetail;
    @Column(name = "salaireAnnuelleBrut")
    private Integer salaireAnnuelleBrut;
    @Column(name = "allocationFamiliale")
    private Integer allocationFamiliale;
    @Column(name = "brutAdditionner")
    private Integer brutAdditionner;
    @Column(name = "amo")
    private Integer amo;
    @Column(name = "retenuCmr")
    private Integer retenuCmr;
    @Column(name = "mutuelleMutialiste")
    private Integer mutuelleMutialiste;
    @Column(name = "mutuelleCaisse")
    private Integer mutuelleCaisse;
    @Column(name = "rachatCmr")
    private Integer rachatCmr;
    @Column(name = "sommeDeduire")
    private Integer sommeDeduire;
    @Column(name = "nbrEnfant")
    private Integer nbrEnfant;
    @Column(name = "conjoint")
    private Boolean conjoint;
    @Column(name = "chargeFamiliale")
    private Integer chargeFamiliale;
    @Column(name = "irSource")
    private Integer irSource;
    @Column(name = "irComplement")
    private Integer irComplement;
    @Column(name = "net")
    private Integer net;
    @Column(name = "ir")
    private Integer ir;
    @Size(max = 254)
    @Column(name = "echelle")
    private String echelle;
    @Size(max = 254)
    @Column(name = "echelon")
    private String echelon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetail", fetch = FetchType.EAGER)
    private List<Dossierhsupp> dossierhsuppList;

    public Detail() {
    }

    public Detail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public Integer getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public Integer getSalaireAnnuelleBrut() {
        return salaireAnnuelleBrut;
    }

    public void setSalaireAnnuelleBrut(Integer salaireAnnuelleBrut) {
        this.salaireAnnuelleBrut = salaireAnnuelleBrut;
    }

    public Integer getAllocationFamiliale() {
        return allocationFamiliale;
    }

    public void setAllocationFamiliale(Integer allocationFamiliale) {
        this.allocationFamiliale = allocationFamiliale;
    }

    public Integer getBrutAdditionner() {
        return brutAdditionner;
    }

    public void setBrutAdditionner(Integer brutAdditionner) {
        this.brutAdditionner = brutAdditionner;
    }

    public Integer getAmo() {
        return amo;
    }

    public void setAmo(Integer amo) {
        this.amo = amo;
    }

    public Integer getRetenuCmr() {
        return retenuCmr;
    }

    public void setRetenuCmr(Integer retenuCmr) {
        this.retenuCmr = retenuCmr;
    }

    public Integer getMutuelleMutialiste() {
        return mutuelleMutialiste;
    }

    public void setMutuelleMutialiste(Integer mutuelleMutialiste) {
        this.mutuelleMutialiste = mutuelleMutialiste;
    }

    public Integer getMutuelleCaisse() {
        return mutuelleCaisse;
    }

    public void setMutuelleCaisse(Integer mutuelleCaisse) {
        this.mutuelleCaisse = mutuelleCaisse;
    }

    public Integer getRachatCmr() {
        return rachatCmr;
    }

    public void setRachatCmr(Integer rachatCmr) {
        this.rachatCmr = rachatCmr;
    }

    public Integer getSommeDeduire() {
        return sommeDeduire;
    }

    public void setSommeDeduire(Integer sommeDeduire) {
        this.sommeDeduire = sommeDeduire;
    }

    public Integer getNbrEnfant() {
        return nbrEnfant;
    }

    public void setNbrEnfant(Integer nbrEnfant) {
        this.nbrEnfant = nbrEnfant;
    }

    public Boolean getConjoint() {
        return conjoint;
    }

    public void setConjoint(Boolean conjoint) {
        this.conjoint = conjoint;
    }

    public Integer getChargeFamiliale() {
        return chargeFamiliale;
    }

    public void setChargeFamiliale(Integer chargeFamiliale) {
        this.chargeFamiliale = chargeFamiliale;
    }

    public Integer getIrSource() {
        return irSource;
    }

    public void setIrSource(Integer irSource) {
        this.irSource = irSource;
    }

    public Integer getIrComplement() {
        return irComplement;
    }

    public void setIrComplement(Integer irComplement) {
        this.irComplement = irComplement;
    }

    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }

    public Integer getIr() {
        return ir;
    }

    public void setIr(Integer ir) {
        this.ir = ir;
    }

    public String getEchelle() {
        return echelle;
    }

    public void setEchelle(String echelle) {
        this.echelle = echelle;
    }

    public String getEchelon() {
        return echelon;
    }

    public void setEchelon(String echelon) {
        this.echelon = echelon;
    }

    @XmlTransient
    public List<Dossierhsupp> getDossierhsuppList() {
        return dossierhsuppList;
    }

    public void setDossierhsuppList(List<Dossierhsupp> dossierhsuppList) {
        this.dossierhsuppList = dossierhsuppList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetail != null ? idDetail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detail)) {
            return false;
        }
        Detail other = (Detail) object;
        if ((this.idDetail == null && other.idDetail != null) || (this.idDetail != null && !this.idDetail.equals(other.idDetail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Detail[ idDetail=" + idDetail + " ]";
    }
    
}
