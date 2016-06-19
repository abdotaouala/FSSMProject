package beans;

import model.Secteurprincipal;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.SecteurprincipalFacade;

import java.io.Serializable;
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
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named("secteurprincipalController")
@SessionScoped
public class SecteurprincipalController implements Serializable {
    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private Secteurprincipal current;
    private String newDesignation;
    private DataModel items = null;
    @EJB
    private session.SecteurprincipalFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public SecteurprincipalController() {
    }

    public Secteurprincipal getSelected() {
        if (current == null) {
            current = new Secteurprincipal();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SecteurprincipalFacade getFacade() {
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

    public String prepareView() {
        current = (Secteurprincipal) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Secteurprincipal();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            EntityTransaction et=em.getTransaction();
        if(!et.isActive()){
		et.begin();
		}
            getFacade().create(current);
            et.commit();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecteurprincipalCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Secteurprincipal) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecteurprincipalUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String MyUpdate() {
        try {
            EntityTransaction et=em.getTransaction();
        if(!et.isActive()){
		et.begin();
		}
            Query req=em.createQuery("SELECT s FROM Secteurprincipal s WHERE s.designation =?").setParameter(1,current.getDesignation());
            this.setCurrent((Secteurprincipal)req.getSingleResult());
            current.setDesignation(newDesignation);
            getFacade().edit(current);
            et.commit();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecteurprincipalUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Secteurprincipal) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
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

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecteurprincipalDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    public void MyDestroy() {
        try {
        Query req=em.createQuery("select o from Secteurprincipal o where o.designation=?").setParameter(1,current.getDesignation());
        this.setCurrent((Secteurprincipal)req.getSingleResult());
        EntityTransaction et=em.getTransaction();
        if(!et.isActive()){
		et.begin();
		}
        getFacade().remove(current);
        et.commit();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecteurprincipalDeleted"));
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

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
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

    public EntityManager getEm() {
        return em;
    }
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public String getNewDesignation() {
        return newDesignation;
    }

    public void setNewDesignation(String newDesignation) {
        this.newDesignation = newDesignation;
    }

    public Secteurprincipal getSecteurprincipal(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public void setCurrent(Secteurprincipal current) {
        this.current = current;
    }

    @FacesConverter(forClass = Secteurprincipal.class)
    public static class SecteurprincipalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecteurprincipalController controller = (SecteurprincipalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secteurprincipalController");
            return controller.getSecteurprincipal(getKey(value));
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
            if (object instanceof Secteurprincipal) {
                Secteurprincipal o = (Secteurprincipal) object;
                return getStringKey(o.getIdSecteurP());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Secteurprincipal.class.getName());
            }
        }

    }

}
