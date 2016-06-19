package beans;

import model.Budget;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.BudgetFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Anneebudgetaire;
import model.Compte;

@Named("budgetController")
@SessionScoped
public class BudgetController implements Serializable {
    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private Budget current;
    private Compte cpt;
    private List<Budget>items = null;
    @EJB
    private session.BudgetFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private boolean disablCreate = false;
    private boolean disablUpdate = true;
    private boolean disablDelete = true;
    public BudgetController() {
        subjectSelectionChanged();
        items=getItemes();
    }

    public Budget getSelected() {
        if (current == null) {
            current = new Budget();
            current.setBudgetPK(new model.BudgetPK());
            selectedItemIndex = -1;
        }
        return current;
    }
    public List<Budget> getItemes() {
            try {
                Query req = em.createQuery("SELECT o FROM Budget o");
                items= (List<Budget>) req.getResultList();
            } catch (Exception e) {
              JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        return items;
    }
public void subjectSelectionChanged() {
        if (current instanceof Budget && current != null) {
            try {
                current.getBudgetPK().setIdCompte(cpt.getIdCompte());
                Query req = em.createQuery("SELECT o FROM Budget o WHERE o.budgetPK.annee=? and o.budgetPK.idCompte=?").setParameter(1, current.getBudgetPK().getAnnee()).setParameter(2,current.getBudgetPK().getIdCompte());
                Budget b = (Budget) req.getSingleResult();
                if (b != null) {
                    current.setBudgetAnnuel(b.getBudgetAnnuel());
                    current.setReliquat(b.getReliquat());
                    disablCreate = true;
                    disablUpdate = false;
                    disablDelete = false;
                } else {
                    current.setBudgetAnnuel(-1);
                    current.setReliquat(-1);
                    disablCreate = false;
                    disablUpdate = true;
                    disablDelete = true;
                }
            } catch (Exception e) {
                current.setBudgetAnnuel(-1);
                current.setReliquat(-1);
                disablCreate = false;
                disablUpdate = true;
                disablDelete = true;
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }
public List<Compte> completeText(String id){
        List<Compte> FiltredComptes=new ArrayList<Compte>();
        try {
                CompteController cc=new CompteController();
                List<Compte> AllComptes=cc.getItemes();
                for(Compte c:AllComptes){
                    if(c.getIdCompte().toString().startsWith(id)) {
                       FiltredComptes.add(c);
                    }
                }
            } catch (Exception e) {
              JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
    return FiltredComptes;
    }
    public BudgetFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

   /* public String prepareView() {
        current = (Budget) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    */
    public String prepareCreate() {
        current = new Budget();
        current.setBudgetPK(new model.BudgetPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setReliquat(current.getBudgetAnnuel());
            getFacade().create(current);
            items=getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BudgetCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

   /* public String prepareEdit() {
        current = (Budget) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }*/
    public String update() {
        try {
            current.setReliquat(current.getBudgetAnnuel());
            getFacade().edit(current);
            items=getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BudgetUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

  /*  public String destroy() {
        current = (Budget) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }
*/
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    public void performDestroy() {
        try {
            current.setReliquat(current.getBudgetAnnuel());
            getFacade().remove(current);
            items=getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BudgetDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

   /* public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }*/

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Budget getBudget(model.BudgetPK id) {
        return ejbFacade.find(id);
    }

    public Budget getCurrent() {
        return current;
    }

    public void setCurrent(Budget current) {
        this.current = current;
    }

    public boolean isDisablCreate() {
        return disablCreate;
    }

    public void setDisablCreate(boolean disablCreate) {
        this.disablCreate = disablCreate;
    }

    public boolean isDisablUpdate() {
        return disablUpdate;
    }

    public void setDisablUpdate(boolean disablUpdate) {
        this.disablUpdate = disablUpdate;
    }

    public boolean isDisablDelete() {
        return disablDelete;
    }

    public void setDisablDelete(boolean disablDelete) {
        this.disablDelete = disablDelete;
    }

    public List<Budget> getItems() {
        return items;
    }

    public void setItems(List<Budget> items) {
        this.items = items;
    }

    public Compte getCpt() {
        return cpt;
    }

    public void setCpt(Compte cpt) {
        this.cpt = cpt;
    }

    @FacesConverter(forClass = Budget.class)
    public static class BudgetControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BudgetController controller = (BudgetController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "budgetController");
            return controller.getBudget(getKey(value));
        }

        model.BudgetPK getKey(String value) {
            model.BudgetPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new model.BudgetPK();
            key.setIdCompte(Integer.parseInt(values[0]));
            key.setAnnee(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(model.BudgetPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdCompte());
            sb.append(SEPARATOR);
            sb.append(value.getAnnee());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Budget) {
                Budget o = (Budget) object;
                return getStringKey(o.getBudgetPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Budget.class.getName());
            }
        }

    }

}
