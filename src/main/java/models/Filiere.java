package models;
// Generated 4 juin 2016 20:47:55 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Filiere generated by hbm2java
 */
@Entity
@Table(name="filiere"
    ,catalog="bd_finance_fssm"
)
public class Filiere  implements java.io.Serializable {


     private Integer idFiliere;
     private int idDep;
     private int idType;
     private int idUser;
     private String intitule;

    public Filiere() {
    }

	
    public Filiere(int idDep, int idType, int idUser) {
        this.idDep = idDep;
        this.idType = idType;
        this.idUser = idUser;
    }
    public Filiere(int idDep, int idType, int idUser, String intitule) {
       this.idDep = idDep;
       this.idType = idType;
       this.idUser = idUser;
       this.intitule = intitule;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idFiliere", unique=true, nullable=false)
    public Integer getIdFiliere() {
        return this.idFiliere;
    }
    
    public void setIdFiliere(Integer idFiliere) {
        this.idFiliere = idFiliere;
    }

    
    @Column(name="idDep", nullable=false)
    public int getIdDep() {
        return this.idDep;
    }
    
    public void setIdDep(int idDep) {
        this.idDep = idDep;
    }

    
    @Column(name="idType", nullable=false)
    public int getIdType() {
        return this.idType;
    }
    
    public void setIdType(int idType) {
        this.idType = idType;
    }

    
    @Column(name="idUser", nullable=false)
    public int getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    
    @Column(name="intitule", length=254)
    public String getIntitule() {
        return this.intitule;
    }
    
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }




}


