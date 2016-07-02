package beans;

import model.Comptebc;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.ComptebcFacade;

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
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Article;

@Named("comptebcController")
@ManagedBean
@SessionScoped
public class ComptebcController implements Serializable {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private Comptebc current;
    private List<Comptebc> items = null;
    private boolean disablCreate = false;
    private boolean disablUpdate = true;
    private boolean disablDelete = true;
    @EJB
    private session.ComptebcFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ComptebcController() {
        
    }

    public List<Comptebc> getItemes() {
        try {
            this.items = new ArrayList<Comptebc>();
            Query req = em.createQuery("SELECT o FROM Comptebc o");
            List<Comptebc> l = (List<Comptebc>) req.getResultList();
            items = l;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aucun Compte n'est trouvé !", "Information"));
        }
        return items;
    }
public List<String> completeText(String id) {
        List<String> FiltredSP = new ArrayList<String>();
        try {
            List<Comptebc> AllSP = getItemes();
            for (Comptebc c : AllSP) {
                if (c.getIntitule().startsWith(id)) {
                    FiltredSP.add(c.getIntitule());
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Echec de convertion !", "Erreure"));
        }
        return FiltredSP;
    }
    public void subjectSelectionChanged() {
        if (current instanceof Comptebc && current != null) {
            try {
                Query req = em.createQuery("SELECT o FROM Comptebc o WHERE o.intitule =?").setParameter(1, current.getIntitule());
                Comptebc c = (Comptebc) req.getSingleResult();
                if (c != null && c instanceof Comptebc) {
                    current.setIntitule(c.getIntitule());
                    current.setBc(c.getBc());
                    current.setRib(c.getRib());
                    current.setCinPpr(c.getCinPpr());
                    disablCreate = true;
                    disablUpdate = false;
                    disablDelete = false;
                } else {
                    disablCreate = false;
                    disablUpdate = true;
                    disablDelete = true;
                }
            } catch (Exception e) {
                disablCreate = false;
                disablUpdate = true;
                disablDelete = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aucun Compte n'est trouvé !", "Information"));
            }
        }
    }

    public Comptebc getSelected() {
        if (current == null) {
            current = new Comptebc();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ComptebcFacade getFacade() {
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

    public List<Comptebc> getItems() {
        return items;
    }

    public void setItems(List<Comptebc> items) {
        this.items = items;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    /*public String prepareView() {
        current = (Comptebc) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }*/
    public String prepareCreate() {
        current = new Comptebc();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().edit(current);
            this.items = getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComptebcCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /*public String prepareEdit() {
        current = (Comptebc) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }*/
    public String update() {
        try {
            Query req = em.createQuery("SELECT a FROM Comptebc a WHERE a.intitule =?").setParameter(1, current.getIntitule());
            Comptebc c=(Comptebc) req.getSingleResult();
            current.setIdCptBc(c.getIdCptBc());
            getFacade().edit(current);
            this.items = getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComptebcUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /*  public String destroy() {
        current = (Comptebc) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }*/
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
            Query req = em.createQuery("SELECT a FROM Comptebc a WHERE a.intitule =?").setParameter(1, current.getIntitule());
            Comptebc c=(Comptebc) req.getSingleResult();
            current.setIdCptBc(c.getIdCptBc());
            getFacade().remove(current);
            this.items = getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComptebcDeleted"));
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

    public Comptebc getComptebc(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Comptebc getCurrent() {
        return current;
    }

    public void setCurrent(Comptebc current) {
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

    public ComptebcFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(ComptebcFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    @FacesConverter(forClass = Comptebc.class)
    public static class ComptebcControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ComptebcController controller = (ComptebcController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "comptebcController");
            return controller.getComptebc(getKey(value));
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
            if (object instanceof Comptebc) {
                Comptebc o = (Comptebc) object;
                return getStringKey(o.getIdCptBc());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Comptebc.class.getName());
            }
        }

    }

}
