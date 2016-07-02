package beans;

import model.Intervenant;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.IntervenantFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
import model.Comptebc;

@Named("intervenantController")
@SessionScoped
public class IntervenantController implements Serializable {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private List<Intervenant> items = null;
    private boolean disablCreate = false;
    private boolean disablUpdate = true;
    private boolean disablDelete = true;
    private Intervenant current;
    @EJB
    private session.IntervenantFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public IntervenantController() {
    }

    public List<Intervenant> getItemes() {
        try {
            this.items = new ArrayList<Intervenant>();
            Query req = em.createQuery("SELECT o FROM Intervenant o");
            List<Intervenant> l = (List<Intervenant>) req.getResultList();
            items = l;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aucun Compte n'est trouvé !", "Information"));
        }
        return items;
    }

    public List<String> completeText(String id) {
        List<String> FiltredSP = new ArrayList<String>();
        try {
            List<Intervenant> AllSP = getItemes();
            for (Intervenant c : AllSP) {
                if (c.getCinPpr().startsWith(id)) {
                    FiltredSP.add(c.getCinPpr());
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Echec de convertion !", "Erreure"));
        }
        return FiltredSP;
    }

    public void subjectSelectionChanged() {
        if (current instanceof Intervenant && current != null) {
            try {
                Query req = em.createQuery("SELECT o FROM Intervenant o WHERE o.cinPpr =?").setParameter(1, current.getCinPpr());
                Intervenant c = (Intervenant) req.getSingleResult();
                if (c != null && c instanceof Intervenant) {
                    current.setNomComplet(c.getNomComplet());
                    current.setNomArabe(c.getNomArabe());
                    current.setTelephone(c.getTelephone());
                    current.setMail(c.getMail());
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

    public Intervenant getSelected() {
        if (current == null) {
            current = new Intervenant();
            selectedItemIndex = -1;
        }
        return current;
    }

    private IntervenantFacade getFacade() {
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

    /*public String prepareView() {
        current = (Intervenant) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }*/
    public String prepareCreate() {
        current = new Intervenant();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().edit(current);
            this.items = getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IntervenantCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /*public String prepareEdit() {
        current = (Intervenant) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }*/
    public String update() {
        try {
            Query req = em.createQuery("SELECT a FROM Intervenant a WHERE a.cinPpr =?").setParameter(1, current.getCinPpr());
            Intervenant c=(Intervenant) req.getSingleResult();
            current.setCinPpr(c.getCinPpr());
            getFacade().edit(current);
            this.items = getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IntervenantUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /*public String destroy() {
        current = (Intervenant) getItems().getRowData();
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
            Query req = em.createQuery("SELECT a FROM Intervenant a WHERE a.cinPpr =?").setParameter(1, current.getCinPpr());
            Intervenant c=(Intervenant) req.getSingleResult();
            current.setCinPpr(c.getCinPpr());
            getFacade().remove(current);
            this.items = getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IntervenantDeleted"));
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

    public Intervenant getIntervenant(java.lang.String id) {
        return ejbFacade.find(id);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<Intervenant> getItems() {
        return items;
    }

    public void setItems(List<Intervenant> items) {
        this.items = items;
    }
    public Intervenant getCurrent() {
        return current;
    }

    public void setCurrent(Intervenant current) {
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

    public IntervenantFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(IntervenantFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    @FacesConverter(forClass = Intervenant.class)
    public static class IntervenantControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IntervenantController controller = (IntervenantController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "intervenantController");
            return controller.getIntervenant(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Intervenant) {
                Intervenant o = (Intervenant) object;
                return getStringKey(o.getCinPpr());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Intervenant.class.getName());
            }
        }

    }

}
