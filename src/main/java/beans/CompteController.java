package beans;

import model.Compte;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.CompteFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Anneebudgetaire;

@Named("compteController")
@ManagedBean
@SessionScoped
public class CompteController implements Serializable {
    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private Compte current;
    private List<Compte> items = null;
    private boolean disablCreate = false;
    private boolean disablUpdate = true;
    private boolean disablDelete = true;
    @EJB
    private session.CompteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    public CompteController() {
        items=getItemes();
        subjectSelectionChanged();
    }
    public List<Compte> getItemes() {
            try {
                this.items=new ArrayList<Compte>();
                Query req = em.createQuery("SELECT o FROM Compte o");
                List<Compte> l = (List<Compte>) req.getResultList();
                items=l;
            } catch (Exception e) {
              JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        return items;
    }
    public void subjectSelectionChanged() {
        if (current instanceof Compte && current != null) {
            try {
                Query req = em.createQuery("SELECT o FROM Compte o WHERE o.idCompte =?").setParameter(1, current.getIdCompte());
                Compte c = (Compte) req.getSingleResult();
                if (c != null) {
                    current.setIntitule(c.getIntitule());
                    current.setRap(c.getRap());
                    disablCreate = true;
                    disablUpdate = false;
                    disablDelete = false;
                } else {
                    current.setIntitule("");
                    current.setRap(-1.0);
                    disablCreate = false;
                    disablUpdate = true;
                    disablDelete = true;
                }
            } catch (Exception e) {
                current.setIntitule("");
                current.setRap(-1.0);
                disablCreate = false;
                disablUpdate = true;
                disablDelete = true;
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }
    public Compte getSelected() {
        if (current == null) {
            current = new Compte();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CompteFacade getFacade() {
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
        current = (Compte) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
*/
    public String prepareCreate() {
        current = new Compte();
        selectedItemIndex = -1;
        return "Create";
    }
    public List<Compte> completeText(String id){
        List<Compte> FiltredComptes=new ArrayList<Compte>();
        try {
                List<Compte> AllComptes=getItemes();
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
    public String create() {
        try {
            getFacade().create(current);
            items=getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CompteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

  /*  public String prepareEdit() {
        current = (Compte) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
*/
    public String update() {
        try {
            getFacade().edit(current);
            items=getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CompteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

  /*  public String destroy() {
        current = (Compte) getItems().getRowData();
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
            getFacade().remove(current);
            items=getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CompteDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public List<Compte> getItems() {
        return items;
    }

    public void setItems(List<Compte> items) {
        this.items = items;
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
    }
*/
    private void recreateModel() {
        items = null;
    }

    public Compte getCurrent() {
        return current;
    }

    public void setCurrent(Compte current) {
        this.current = current;
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

    public Compte getCompte(java.lang.Integer id) {
        return ejbFacade.find(id);
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

    @FacesConverter(value="compteConverter",forClass = Compte.class)
    public static class CompteControllerConverter implements Converter {

         @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }else{
                try{
            CompteController controller = (CompteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "compteController");
            Object o=controller.getCompte(getKey(value));
            if(o==null){
                Compte c=new Compte();
                c.setIdCompte(Integer.parseInt(value));
                return c;
            }else{
            return o;
            }
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
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
            if (object!=null) {
                Compte o = (Compte) object;
                return String.valueOf(((Compte) object).getIdCompte());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Compte.class.getName());
            }
        }

    }

}
