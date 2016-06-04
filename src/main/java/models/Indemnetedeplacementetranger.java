package models;
// Generated 4 juin 2016 20:47:55 by Hibernate Tools 4.3.1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Indemnetedeplacementetranger generated by hbm2java
 */
@Entity
@Table(name="indemnetedeplacementetranger"
    ,catalog="bd_finance_fssm"
)
public class Indemnetedeplacementetranger  implements java.io.Serializable {


     private IndemnetedeplacementetrangerId id;
     private Integer idPrixSej;
     private int idDotation;
     private Integer mntDepEx;

    public Indemnetedeplacementetranger() {
    }

	
    public Indemnetedeplacementetranger(IndemnetedeplacementetrangerId id, int idDotation) {
        this.id = id;
        this.idDotation = idDotation;
    }
    public Indemnetedeplacementetranger(IndemnetedeplacementetrangerId id, Integer idPrixSej, int idDotation, Integer mntDepEx) {
       this.id = id;
       this.idPrixSej = idPrixSej;
       this.idDotation = idDotation;
       this.mntDepEx = mntDepEx;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="idDeplacement", column=@Column(name="idDeplacement", nullable=false) ), 
        @AttributeOverride(name="idDepEx", column=@Column(name="idDepEx", nullable=false) ) } )
    public IndemnetedeplacementetrangerId getId() {
        return this.id;
    }
    
    public void setId(IndemnetedeplacementetrangerId id) {
        this.id = id;
    }

    
    @Column(name="idPrixSej")
    public Integer getIdPrixSej() {
        return this.idPrixSej;
    }
    
    public void setIdPrixSej(Integer idPrixSej) {
        this.idPrixSej = idPrixSej;
    }

    
    @Column(name="idDotation", nullable=false)
    public int getIdDotation() {
        return this.idDotation;
    }
    
    public void setIdDotation(int idDotation) {
        this.idDotation = idDotation;
    }

    
    @Column(name="mntDepEx")
    public Integer getMntDepEx() {
        return this.mntDepEx;
    }
    
    public void setMntDepEx(Integer mntDepEx) {
        this.mntDepEx = mntDepEx;
    }




}


