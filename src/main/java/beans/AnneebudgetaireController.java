package beans;

import model.Anneebudgetaire;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.AnneebudgetaireFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.event.CloseEvent;
@Named("anneebudgetaireController")
@SessionScoped
public class AnneebudgetaireController implements Serializable {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private Anneebudgetaire current;
    private List<Anneebudgetaire> items= null;
    @EJB
    private session.AnneebudgetaireFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private boolean disablCreate = false;
    private boolean disablUpdate = true;
    private boolean disablDelete = true;

    public AnneebudgetaireController() {
        //this.current.setReliquatRap(this.current.getMontantRap());
        subjectSelectionChanged();
        getItems();
    }

    public void subjectSelectionChanged() {
        if (current instanceof Anneebudgetaire && current != null) {
            try {
                Query req = em.createQuery("SELECT o FROM Anneebudgetaire o WHERE o.annee =?").setParameter(1, current.getAnnee());
                Anneebudgetaire a = (Anneebudgetaire) req.getSingleResult();
                if (a != null) {
                    current.setMontantRap(a.getReliquatRap());
                    current.setMontantRap(a.getMontantRap());
                    disablCreate = true;
                    disablUpdate = false;
                    disablDelete = false;
                } else {
                    current.setMontantRap(0);
                    current.setReliquatRap(0);
                    disablCreate = false;
                    disablUpdate = true;
                    disablDelete = true;
                }
            } catch (Exception e) {
                current.setMontantRap(0);
                current.setReliquatRap(0);
                disablCreate = false;
                disablUpdate = true;
                disablDelete = true;
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Anneebudgetaire getSelected() {
        if (current == null) {
            current = new Anneebudgetaire();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AnneebudgetaireFacade getFacade() {
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
    public String prepareCreate() {
        current = new Anneebudgetaire();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setReliquatRap(current.getMontantRap());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnneebudgetaireCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String update() {
        try {
            current.setReliquatRap(current.getMontantRap());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnneebudgetaireUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

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
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnneebudgetaireDeleted"));
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

    public List<Anneebudgetaire> getItems() {
            try {
                if(items==null){
                this.items=new ArrayList<Anneebudgetaire>();
                }
                Query req = em.createQuery("SELECT o FROM Anneebudgetaire o");
                List<Anneebudgetaire> l = (List<Anneebudgetaire>) req.getResultList();
                items=l;
            } catch (Exception e) {
              JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        return items;
    }

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

    public void setCurrent(Anneebudgetaire current) {
        this.current = current;
    }

    public void setDisablDelete(boolean disablDelete) {
        this.disablDelete = disablDelete;
    }

    public Anneebudgetaire getAnneebudgetaire(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Anneebudgetaire.class)
    public static class AnneebudgetaireControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AnneebudgetaireController controller = (AnneebudgetaireController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "anneebudgetaireController");
            return controller.getAnneebudgetaire(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Anneebudgetaire) {
                Anneebudgetaire o = (Anneebudgetaire) object;
                return getStringKey(o.getAnnee());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Anneebudgetaire.class.getName());
            }
        }

    }

}
