package beans;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
@ManagedBean
@ViewScoped
public class SelectAnnee {
    private int year;
    private List<SelectItem> years;
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
    //#f0667c
    public List<SelectItem> getYears() {
        return years;
    }

    public int getYear() {
        return year;
    }
}