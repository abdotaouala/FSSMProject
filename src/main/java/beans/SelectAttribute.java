package beans;

import beans.util.JsfUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
@ViewScoped
@ManagedBean
public class SelectAttribute {
    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private int year;
    private List<SelectItem> years;
    private List<SelectItem> budget_annees;
    private List<SelectItem>budget_idComptes;
    @PostConstruct
    public void init() {
        //cars
        /*SelectItemGroup g1 = new SelectItemGroup("German Cars");
        g1.setSelectItems(new SelectItem[] {new SelectItem("BMW", "BMW"), new SelectItem("Mercedes", "Mercedes"), new SelectItem("Volkswagen", "Volkswagen")});
         
        SelectItemGroup g2 = new SelectItemGroup("American Cars");
        g2.setSelectItems(new SelectItem[] {new SelectItem("Chrysler", "Chrysler"), new SelectItem("GM", "GM"), new SelectItem("Ford", "Ford")});
         
        years = new ArrayList<SelectItem>();
        years.add(g1);
        years.add(g2);*/
        remplire();
        budget_annees=remplire("annee","Anneebudgetaire");
        budget_idComptes=remplire("idCompte","Compte");
        }
    public void remplire(){
        years = new ArrayList<SelectItem>();
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        for(int i=year-5;i<year;i++){
        years.add(new SelectItem(Integer.toString(i),Integer.toString(i)));
        }
        for(int i=year+1;i<year+5;i++){
        years.add(new SelectItem(Integer.toString(i),Integer.toString(i)));
        }
    }
    public List<SelectItem> remplire(String champ,String table){
        List<SelectItem> list = new ArrayList<SelectItem>();
        try {
        Query req=em.createQuery("select o."+champ+" from "+table+" o");
        List <Object>l=req.getResultList();
        for(Object s:l){
            list.add(new SelectItem(s.toString(),s.toString()));
        }
        }catch(Exception e){
            JsfUtil.addErrorMessage(e,"erreur SQL");
        }
        return list;
    }
    //#f0667c
    public List<SelectItem> getYears() {
        return years;
    }

    public List<SelectItem> getBudget_annees() {
        return budget_annees;
    }

    public List<SelectItem> getBudget_idComptes() {
        return budget_idComptes;
    }

    public int getYear() {
        return year;
    }
}