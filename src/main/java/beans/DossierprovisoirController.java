package beans;

import model.Dossierprovisoir;
import dao.util.JsfUtil;
import dao.util.JsfUtil.PersistAction;
import session.DossierprovisoirFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("dossierprovisoirController")
@SessionScoped
public class DossierprovisoirController implements Serializable {

    @EJB
    private session.DossierprovisoirFacade ejbFacade;
    private List<Dossierprovisoir> items = null;
    private Dossierprovisoir selected;

    public DossierprovisoirController() {
    }

    public Dossierprovisoir getSelected() {
        return selected;
    }

    public void setSelected(Dossierprovisoir selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DossierprovisoirFacade getFacade() {
        return ejbFacade;
    }

    public Dossierprovisoir prepareCreate() {
        selected = new Dossierprovisoir();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DossierprovisoirCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DossierprovisoirUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DossierprovisoirDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Dossierprovisoir> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Dossierprovisoir getDossierprovisoir(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Dossierprovisoir> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Dossierprovisoir> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Dossierprovisoir.class)
    public static class DossierprovisoirControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DossierprovisoirController controller = (DossierprovisoirController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dossierprovisoirController");
            return controller.getDossierprovisoir(getKey(value));
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
            if (object instanceof Dossierprovisoir) {
                Dossierprovisoir o = (Dossierprovisoir) object;
                return getStringKey(o.getIdDossierProv());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Dossierprovisoir.class.getName()});
                return null;
            }
        }

    }

}
