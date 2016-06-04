package models;
// Generated 4 juin 2016 20:47:55 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Dossierhsupp generated by hbm2java
 */
@Entity
@Table(name="dossierhsupp"
    ,catalog="bd_finance_fssm"
)
public class Dossierhsupp  implements java.io.Serializable {


     private Integer idDossier;
     private String cinPpr;
     private Integer idBordAut;
     private int idDossierProv;
     private Integer idBordComp;
     private int idDotation;
     private Integer idGrade;
     private int idDetail;
     private Integer nbrHeures;
     private String mois;
     private String semestre;
     private Date dateCreance;
     private Integer montantHsupp;
     private String statutDossier;

    public Dossierhsupp() {
    }

	
    public Dossierhsupp(String cinPpr, int idDossierProv, int idDotation, int idDetail) {
        this.cinPpr = cinPpr;
        this.idDossierProv = idDossierProv;
        this.idDotation = idDotation;
        this.idDetail = idDetail;
    }
    public Dossierhsupp(String cinPpr, Integer idBordAut, int idDossierProv, Integer idBordComp, int idDotation, Integer idGrade, int idDetail, Integer nbrHeures, String mois, String semestre, Date dateCreance, Integer montantHsupp, String statutDossier) {
       this.cinPpr = cinPpr;
       this.idBordAut = idBordAut;
       this.idDossierProv = idDossierProv;
       this.idBordComp = idBordComp;
       this.idDotation = idDotation;
       this.idGrade = idGrade;
       this.idDetail = idDetail;
       this.nbrHeures = nbrHeures;
       this.mois = mois;
       this.semestre = semestre;
       this.dateCreance = dateCreance;
       this.montantHsupp = montantHsupp;
       this.statutDossier = statutDossier;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idDossier", unique=true, nullable=false)
    public Integer getIdDossier() {
        return this.idDossier;
    }
    
    public void setIdDossier(Integer idDossier) {
        this.idDossier = idDossier;
    }

    
    @Column(name="cinPpr", nullable=false, length=254)
    public String getCinPpr() {
        return this.cinPpr;
    }
    
    public void setCinPpr(String cinPpr) {
        this.cinPpr = cinPpr;
    }

    
    @Column(name="idBordAut")
    public Integer getIdBordAut() {
        return this.idBordAut;
    }
    
    public void setIdBordAut(Integer idBordAut) {
        this.idBordAut = idBordAut;
    }

    
    @Column(name="idDossierProv", nullable=false)
    public int getIdDossierProv() {
        return this.idDossierProv;
    }
    
    public void setIdDossierProv(int idDossierProv) {
        this.idDossierProv = idDossierProv;
    }

    
    @Column(name="idBordComp")
    public Integer getIdBordComp() {
        return this.idBordComp;
    }
    
    public void setIdBordComp(Integer idBordComp) {
        this.idBordComp = idBordComp;
    }

    
    @Column(name="idDotation", nullable=false)
    public int getIdDotation() {
        return this.idDotation;
    }
    
    public void setIdDotation(int idDotation) {
        this.idDotation = idDotation;
    }

    
    @Column(name="idGrade")
    public Integer getIdGrade() {
        return this.idGrade;
    }
    
    public void setIdGrade(Integer idGrade) {
        this.idGrade = idGrade;
    }

    
    @Column(name="idDetail", nullable=false)
    public int getIdDetail() {
        return this.idDetail;
    }
    
    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    
    @Column(name="nbrHeures")
    public Integer getNbrHeures() {
        return this.nbrHeures;
    }
    
    public void setNbrHeures(Integer nbrHeures) {
        this.nbrHeures = nbrHeures;
    }

    
    @Column(name="mois", length=254)
    public String getMois() {
        return this.mois;
    }
    
    public void setMois(String mois) {
        this.mois = mois;
    }

    
    @Column(name="semestre", length=254)
    public String getSemestre() {
        return this.semestre;
    }
    
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dateCreance", length=19)
    public Date getDateCreance() {
        return this.dateCreance;
    }
    
    public void setDateCreance(Date dateCreance) {
        this.dateCreance = dateCreance;
    }

    
    @Column(name="montantHsupp", precision=8, scale=0)
    public Integer getMontantHsupp() {
        return this.montantHsupp;
    }
    
    public void setMontantHsupp(Integer montantHsupp) {
        this.montantHsupp = montantHsupp;
    }

    
    @Column(name="statutDossier", length=254)
    public String getStatutDossier() {
        return this.statutDossier;
    }
    
    public void setStatutDossier(String statutDossier) {
        this.statutDossier = statutDossier;
    }




}


